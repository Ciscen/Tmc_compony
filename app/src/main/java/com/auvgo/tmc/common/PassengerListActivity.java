package com.auvgo.tmc.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.PassengerAdapter;
import com.auvgo.tmc.adapter.PsgChosedAdapter;
import com.auvgo.tmc.bean.PassengersBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.UserBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.LogFactory;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.MyGridView;
import com.auvgo.tmc.views.RefreshListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lc on 2016/11/14
 * 返回的intent中，psgs是一个arraylist，里面包含了所选乘客信息
 * 输入为bundle，key = bundle；bundle中存放（"psgs",ArrayList<UserBean>,,,"nums",int，表示最大选择数量）,作为上个页面传来已经选择过的元素
 * 输出为intent;intetn中存放（"psgs",ArrayList<UserBean>）,作为选择的元素
 */
public class PassengerListActivity extends FragmentActivity implements View.OnClickListener,
        RefreshListView.OnRefreshListener, View.OnKeyListener {

    private MyGridView gv_choosed_passengers; // 顶部被选中的乘客
    private RefreshListView lv_passenger_list; // 列表
    private EditText et_search_passenger_input; // 搜索
    private PassengerAdapter adapter; //adapter
    private PsgChosedAdapter chosedAdapter;//选中的adapter
    private Map<Integer, View> viewMap;
    private View sure;
    private View cancle;


    /*
    所有list
     */
    private List<UserBean> list_all;
    /*
    用于显示的list
     */
    private List<List<UserBean>> passengerList;
    /*
    用于存放已经选中的passenger的list
     */
    private ArrayList<UserBean> choosedPassengerList;
    /*
    用于搜索
     */
    private List<UserBean> searchList;
    /*
    用于记录上个页面传来的已经选择的乘客
     */
    private ArrayList<UserBean> list_passed;
    private String keyword = "";//关键词，做搜索用
    private int mCurrentPage = 1;//当前页数
    private PassengersBean pb;
    private boolean isSingleChose = false;
    private int MAX_CHOSE = 5;//最多选择的人数
    private String[] selectedIds;
    private String fromFlag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_list);
        dealIntent();
        init();
    }


    private void init() {
        findViews();
        initViews();
        viewMap = new HashMap<>();
        lv_passenger_list.setOnRefreshListener(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initAdapter();
        initListener();
        getPassengerList();

    }

    private void initViews() {
        lv_passenger_list.setEmptyView(findViewById(R.id.empty_view));
        sure.setVisibility(isSingleChose ? View.GONE : View.VISIBLE);
        gv_choosed_passengers.setVisibility(isSingleChose ? View.GONE : View.VISIBLE);

    }

    private void findViews() {
        gv_choosed_passengers = (MyGridView) findViewById(R.id.gv_choosed_passengers);
        lv_passenger_list = (RefreshListView) findViewById(R.id.lv_passenger_list);
        et_search_passenger_input = (EditText) findViewById(R.id.et_psg_list_search);
        cancle = findViewById(R.id.psg_list_cancle);
        sure = findViewById(R.id.psg_list_sure);
    }


    private void dealIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        if (bundle != null) {
            isSingleChose = bundle.getBoolean("isSingle");
            fromFlag = bundle.getString("from", Constant.TRAIN);
            MAX_CHOSE = fromFlag.equals(Constant.PLANE) ? 9 : (fromFlag.equals(Constant.TRAIN) ? 5 : bundle.getInt("nums"));
            list_passed = (ArrayList<UserBean>) bundle.getSerializable("psgs");
            selectedIds = (String[]) bundle.getCharSequenceArray("selectedIds");
        }
    }

    private void initAdapter() {
        list_all = new ArrayList<>();
        choosedPassengerList = new ArrayList<>();
        initChoosedList();
        adapter = new PassengerAdapter(this, list_all, R.layout.item_psg_list_sub);
        chosedAdapter = new PsgChosedAdapter(this, choosedPassengerList);
        lv_passenger_list.setAdapter(adapter);
        gv_choosed_passengers.setAdapter(chosedAdapter);
    }


    private void initListener() {
        et_search_passenger_input.setOnKeyListener(this);
        lv_passenger_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserBean userBean = list_all.get(position);
                if (isSingleChose) {
                    List<String> list = Arrays.asList(selectedIds);
                    if (list.contains(list_all.get(position).getId() + "")) {
                        ToastUtils.showTextToast("不能选择重复入住人");
                        return;
                    }
                }
                if (choosedPassengerList.size() == MAX_CHOSE && !userBean.isChecked()) {
                    DialogUtil.showDialog(PassengerListActivity.this, "提示", "确定", "",
                            "最多选择" + MAX_CHOSE + (fromFlag.equals(Constant.HOTEL) ? "名入住人" : "位乘客"), null);
                    return;
                }
                userBean.setChecked(!userBean.isChecked());
                if (userBean.isChecked()) {
                    choosedPassengerList.add(userBean);
                } else {
                    choosedPassengerList.remove(userBean);
                }
                if (choosedPassengerList.size() == 0) {
                    gv_choosed_passengers.setVisibility(View.GONE);
                } else {
                    gv_choosed_passengers.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
                chosedAdapter.notifyDataSetChanged();
                if (isSingleChose) {
                    setResultAndFinish();
                }
            }
        });
        gv_choosed_passengers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserBean userBean = choosedPassengerList.get(position);
                userBean.setChecked(false);
                int i = list_all.indexOf(userBean);
                if (i != -1) {
                    list_all.get(i).setChecked(false);
                }
                choosedPassengerList.remove(position);
                chosedAdapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
                if (choosedPassengerList.size() < 1) {
                    gv_choosed_passengers.setVisibility(View.GONE);
                }
            }
        });
        cancle.setOnClickListener(this);
        sure.setOnClickListener(this);
    }


    /*
   点击顶部的名字后，将该项从list中移除
     */
    private void removePos2(int uid) {
        for (int i = 0; i < choosedPassengerList.size(); i++) {
            if (choosedPassengerList.get(i).getId() == uid) {
                choosedPassengerList.remove(i);
                break;
            }
        }
    }


    private void removePos(List<UserBean> list, int position) {//点击选中的item后，在存放所选元素的集合中移除
        for (int i = 0; i < choosedPassengerList.size(); i++) {
            if (choosedPassengerList.get(i).getId() == list.get(position).getId()) {
                choosedPassengerList.remove(i);
                break;
            }
        }
    }

    /**
     * 获取乘客列表
     */
    private void getPassengerList() {
        Map<String, String> requestMap = new LinkedHashMap<>();
        requestMap.put("cid", String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        requestMap.put("userid", String.valueOf(MyApplication.mUserInfoBean.getId()));
        requestMap.put("keyword", keyword);
        requestMap.put("pagenum", mCurrentPage + "");
        requestMap.put("pagesize", "");
        RetrofitUtil.getPsgList(this, AppUtils.getJson(requestMap), PassengersBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                lv_passenger_list.onRefreshComplete(true);
                if (status == 200) {
                    pb = (PassengersBean) o;
                    if (pb.getPageNum() == 1) {
                        list_all.clear();
                    }
                    list_all.addAll(pb.getList());
                    initList(list_all);
                    adapter.notifyDataSetChanged();
                    chosedAdapter.notifyDataSetChanged();
                    LogFactory.d(bean.getData());
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                lv_passenger_list.onRefreshComplete(true);
                return false;
            }
        });

    }

    private void initChoosedList() {
        if (list_passed != null && list_passed.size() > 0) {
            for (UserBean userBean : list_passed) {
                userBean.setChecked(true);
                choosedPassengerList.add(userBean);
            }
        }
    }

    private void initList(List<UserBean> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int i1 = 0; i1 < choosedPassengerList.size(); i1++) {
                if (list.get(i).equals(choosedPassengerList.get(i1))) {
                    list.get(i).setChecked(true);
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.psg_list_cancle:
                et_search_passenger_input.setText("");
                keyword = "";
                mCurrentPage = 1;
                getPassengerList();
                break;
            case R.id.psg_list_sure:
                setResultAndFinish();
                break;
        }
    }

    private void setResultAndFinish() {
        Intent intent = new Intent();
        intent.putExtra("psgs", choosedPassengerList);
        setResult(Constant.ACTIVITY_PASSENGER_FLAG, intent);
        finish();
    }

    private void getPassengerListParameter(String s) {
        searchList.clear();
        passengerList.clear();
        if (TextUtils.isEmpty(s)) {
            initList(list_all);
        } else {
            for (int i = 0; i < list_all.size(); i++) {
                if (list_all.get(i).getName().contains(s) || list_all.get(i).getMobile().contains(s)) {
                    searchList.add(list_all.get(i));
                }
            }
            passengerList.clear();
            initList(searchList);
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onRefresh() {
        mCurrentPage = 0;
        getPassengerList();
    }

    @Override
    public void onLoadMore() {
        lv_passenger_list.onRefreshComplete(true);
        if (pb.getPageNum() != pb.getPages()) {
            mCurrentPage++;
            getPassengerList();
        }
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
            keyword = et_search_passenger_input.getText().toString();
            mCurrentPage = 1;
            getPassengerList();
            return true;
        }
        return false;
    }
}
