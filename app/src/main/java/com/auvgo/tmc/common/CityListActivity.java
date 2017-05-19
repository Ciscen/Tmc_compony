package com.auvgo.tmc.common;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.CityListAdapter;
import com.auvgo.tmc.adapter.CityListAdapter_pinned;
import com.auvgo.tmc.adapter.HotCityAdapter;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.train.bean.CitiesBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.DbHelper;
import com.auvgo.tmc.utils.DensityUtils;
import com.auvgo.tmc.utils.DeviceUtils;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.KeyBoardUtils;
import com.auvgo.tmc.utils.KeyboardChangeListener;
import com.auvgo.tmc.utils.LogFactory;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.HotelSideBar;
import com.auvgo.tmc.views.MyGridView;
import com.auvgo.tmc.views.PinnedHeaderListView;
import com.google.gson.Gson;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class CityListActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "CityListActivity";

    private HotelSideBar sb;
    private FrameLayout dialog;
    private TextView dialog_tv;
    private TextView history_tv;
    private View headerView;
    private PinnedHeaderListView lv;//显示城市列表
    private MyGridView gv_hot;//显示热门城市
    private MyGridView gv_history;//显示历史城市
    private EditText et;//搜索框
    private View back, delete, cancel;//回退按钮，删除按钮,取消按钮

    //    private CityListAdapter adapter;
    private CityListAdapter_pinned adapter;
    private HotCityAdapter hotAdapter;
    private HotCityAdapter historyAdapter;
    private List<CitiesBean.CityBean> cities;//存放城市
    private List<CitiesBean.CityBean> historyCities;//存放历史城市
    private List<CitiesBean.CityBean> hotCities;//存放热门城市
    private List<CitiesBean.CityBean> searchList;//用于存放搜索时的内容
    private boolean isSearchMode = false;//标记是否是搜索模式
    private int index;//标记出发城市0，还是到达城市1
    private String cityCode;//用于对比出发城市跟到达城市
    private String type;//来自飞机、火车票、酒店
    private String tablename;
    private String historyTableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        init();
    }

    private void init() {//初始化
        setSoftInputHiden();//隐藏软键盘
        initData();//初始化list跟adapter
        initView();//初始化控件的状态
        getData();//数据请求
    }

    private void initData() {
        cities = new ArrayList<>();
        hotCities = new ArrayList<>();
        historyCities = new ArrayList<>();
        searchList = new ArrayList<>();
//        adapter = new CityListAdapter(this, cities);
        adapter = new CityListAdapter_pinned(this, cities);
        hotAdapter = new HotCityAdapter(this, hotCities);
        historyAdapter = new HotCityAdapter(this, historyCities);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        String from = bundle.getString("from", Constant.TRAIN);
        switch (from) {
            case Constant.TRAIN:
                type = "train";
                tablename = "traincities";
                historyTableName = "TRAINCITYHISTORY";
                break;
            case Constant.PLANE:
                type = "domair";
                tablename = "planecities";
                historyTableName = "PLANECITYHISTORY";
                break;
            case Constant.HOTEL:
                type = "hotel";
                tablename = "hotelcities";
                historyTableName = "HOTELCITYHISTORY";
                break;
        }
        index = bundle.getInt("index");
        cityCode = bundle.getString("cityCode");
    }

    private void initHistory() {
        DbHelper helper = new DbHelper(this);
        Cursor cursor = helper.query(historyTableName, new String[]{"CODE", "NAME"}, null, null);
        int count = 0;
        historyCities.clear();
        if (cursor.moveToLast()) {
            readDb(historyCities, cursor);
            count = cursor.getPosition();
        }
        while (cursor.moveToPrevious()) {//历史
            readDb(historyCities, cursor);
            if ((count - cursor.getPosition()) > 5) {//实践发现，cursor的位置不会随着删除而改变！！！
                helper.delete(historyTableName, "CODE = ?", new String[]{historyCities.get(historyCities.size() - 1).getCode()});
                historyCities.remove(historyCities.size() - 1);
            }
        }
        cursor.close();
        helper.close();
        historyAdapter.notifyDataSetChanged();
        if (historyCities.size() == 0) {
            history_tv.setVisibility(View.GONE);
        } else {
            history_tv.setVisibility(View.VISIBLE);
        }

    }

    private void readDb(List<CitiesBean.CityBean> history, Cursor cursor) {
        CitiesBean.CityBean bean = new CitiesBean.CityBean();
        String code = cursor.getString(cursor.getColumnIndex("CODE"));
        String name = cursor.getString(cursor.getColumnIndex("NAME"));
        bean.setCode(code);
        bean.setName(name);
        history.add(bean);
    }

    private void add2History(CitiesBean.CityBean bean) {
        DbHelper helper = new DbHelper(this);
        Cursor cur = helper.query(historyTableName, new String[]{"CODE", "NAME"}, "NAME = ?",
                new String[]{bean.getName()});
        if (cur.moveToNext()) {
            helper.delete(historyTableName, "NAME = ?", new String[]{bean.getName()});
        }
        ContentValues values = new ContentValues();
        values.put("NAME", bean.getName());
        values.put("CODE", bean.getCode());
        helper.add(historyTableName, values);
        helper.close();
        cur.close();
        initHistory();
    }

    //进行数据请求
    private void getData() {
        final DbHelper helper = new DbHelper(this);
        Cursor cursor = helper.query(tablename, new String[]{"json"}, null, null);
        if (cursor.moveToNext()) {
            String str = cursor.getString(cursor.getColumnIndex("json"));
            Gson gson = new Gson();
            CitiesBean citiesBean = gson.fromJson(str, CitiesBean.class);
            initList(citiesBean, false);
            helper.close();
            cursor.close();
            return;
        }
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("type", type);
        final Cursor finalCursor = cursor;
        RetrofitUtil.getCities(this, AppUtils.getJson(map), CitiesBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    CitiesBean cb = (CitiesBean) o;
                    initList(cb, true);
                    ContentValues values = new ContentValues();
                    cb.setAllcity(cities);
                    values.put("json", new Gson().toJson(cb));
                    helper.add(tablename, values);
                    helper.close();
                    finalCursor.close();
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {

                return false;
            }

        });


    }

    private void initList(CitiesBean o, boolean isFromNet) {

        cities.addAll(o.getAllcity());
        if (isFromNet) {
            final Collator collator = Collator.getInstance(Locale.CHINA);
            Collections.sort(cities, new Comparator<CitiesBean.CityBean>() {
                @Override
                public int compare(CitiesBean.CityBean o1, CitiesBean.CityBean o2) {
                    return collator.compare(o1.getFullp(), o2.getFullp());
                }
            });
        }
        hotCities.addAll(o.getHotcity());
        adapter.notifyDataSetChanged();
        hotAdapter.notifyDataSetChanged();
    }

    private void setSoftInputHiden() {
        AppUtils.initSoftInput(this);
    }

    private void initView() {
        findViews();//对View进行FindViewById
        lv.addHeaderView(headerView);
        lv.setAdapter(adapter);
        gv_hot.setAdapter(hotAdapter);
        gv_history.setAdapter(historyAdapter);
        lv.setEmptyView(findViewById(R.id.empty_view));
        lv.setPinnedHeader(getLayoutInflater().inflate(R.layout.city_list_fixed_header, lv, false));
        lv.setOnScrollListener(adapter);
        dialog.setVisibility(View.INVISIBLE);
        initHistory();
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION + 3,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        getWindowManager().addView(dialog, params);
        sb.setDialog(dialog);
        /*********************设置监听******************************/
        sb.setOnTouchingLetterChangedListener(new HotelSideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                dialog.setVisibility(View.VISIBLE);
                dialog_tv.setText(s);
                dialog_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                if (s.equals("热门")) {
                    lv.setSelection(0);
                    return;
                }
                lv.setSelection(adapter.getPositionForSection(s.toUpperCase().charAt(0)) + 1);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
                intent.putExtra("index", index);
                if (isSearchMode) {
                    intent.putExtra("code", searchList.get(position).getCode());
                    intent.putExtra("name", searchList.get(position).getName());
                    if (searchList.get(position).getCode().equals(cityCode)) {
                        ToastUtils.showTextToast("出发城市与到达城市不可相同");
                        return;
                    }
                    add2History(searchList.get(position));
                } else {
                    if (position == 0) {
                        return;
                    }
                    if (cities.size() > 0) {
                        intent.putExtra("code", cities.get(position - 1).getCode());
                        intent.putExtra("name", cities.get(position - 1).getName());
                        if (cities.get(position - 1).getCode().equals(cityCode)) {
                            DialogUtil.showDialog(CityListActivity.this, "提示", "确定", "", "出发城市和到达城市不能相同", null);
                            return;
                        }
                        add2History(cities.get(position - 1));
                    }
                }
                setResult(Constant.ACTIVITY_CITY_FLAG, intent);
                finish();
            }
        });
        gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (hotCities.get(position).getCode().equals(cityCode)) {
                    DialogUtil.showDialog(CityListActivity.this, "提示", "确定", "", "出发城市和到达城市不能相同", null);
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("index", index);
                intent.putExtra("code", hotCities.get(position).getCode());
                intent.putExtra("name", hotCities.get(position).getName());
                setResult(Constant.ACTIVITY_CITY_FLAG, intent);
                add2History(hotCities.get(position));
                finish();
            }
        });
        gv_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (historyCities.get(position).getCode().equals(cityCode)) {
                    DialogUtil.showDialog(CityListActivity.this, "提示", "确定", "", "出发城市和到达城市不能相同", null);
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("index", index);
                intent.putExtra("code", historyCities.get(position).getCode());
                intent.putExtra("name", historyCities.get(position).getName());
                setResult(Constant.ACTIVITY_CITY_FLAG, intent);
                add2History(historyCities.get(position));
                finish();
            }
        });
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                showFiltedList(s.toString());
            }

            private void showFiltedList(String s) {
                if (TextUtils.isEmpty(s)) {
                    lv.setAdapter(null);
                    lv.removeHeaderView(headerView);
                    lv.addHeaderView(headerView);
//                    adapter = new CityListAdapter(CityListActivity.this, cities);
                    adapter = new CityListAdapter_pinned(CityListActivity.this, cities);
                    lv.setAdapter(adapter);
                    isSearchMode = false;
                    sb.setVisibility(View.VISIBLE);
                    delete.setVisibility(View.GONE);
                } else {
                    delete.setVisibility(View.VISIBLE);
                    searchList.clear();
                    for (int i = 0; i < cities.size(); i++) {
                        CitiesBean.CityBean cityBean = cities.get(i);
                        String trim = s.trim();
                        if (cityBean.getName().contains(trim) || cityBean.getJianp().toLowerCase().startsWith(trim.toLowerCase())
                                || cityBean.getFullp().toLowerCase().startsWith(s.trim())) {
                            searchList.add(cityBean);
                        }
                    }
                    adapter.refresh(searchList);
                    lv.removeHeaderView(headerView);
                    lv.setAdapter(adapter);
                    isSearchMode = true;
                    sb.setVisibility(View.GONE);
                }
            }
        });
        new KeyboardChangeListener(this).setKeyBoardListener(new KeyboardChangeListener.KeyBoardListener() {

            @Override
            public void onKeyboardChange(boolean isShow, int keyboardHeight) {
                LogFactory.d("onKeyboardChange----" + isShow);
                cancel.setVisibility(isShow ? View.VISIBLE : View.GONE);
                back.setVisibility(isShow ? View.GONE : View.VISIBLE);
                sb.setVisibility(isShow ? View.GONE : View.VISIBLE);
            }
        });
        back.setOnClickListener(this);
        delete.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    private void findViews() {
        sb = (HotelSideBar) findViewById(R.id.side_bar2);
        dialog = (FrameLayout) getLayoutInflater().inflate(R.layout.layout_city_fixed_header, null);
        dialog_tv = (TextView) dialog.findViewById(R.id.city_list_dialog_text);
        lv = (PinnedHeaderListView) findViewById(R.id.hotel_city_list_lv);
        et = (EditText) findViewById(R.id.et_cityList_search);
        back = findViewById(R.id.city_list_back);
        delete = findViewById(R.id.city_list_delete);
        cancel = findViewById(R.id.city_list_cancel);
        headerView = View.inflate(this, R.layout.layout_city_list_header, null);
        history_tv = (TextView) headerView.findViewById(R.id.city_list_header_history_tv);
        gv_hot = (MyGridView) headerView.findViewById(R.id.gv_city_hot);
        gv_history = (MyGridView) headerView.findViewById(R.id.gv_city_history);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getWindowManager().removeViewImmediate(dialog);
        adapter = null;
        hotAdapter = null;
        historyAdapter = null;
        cities = null;
        historyCities = null;
        hotCities = null;
        searchList = null;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.city_list_back:
                finish();
                break;
            case R.id.city_list_delete:
                et.setText("");
                break;
            case R.id.city_list_cancel:
                et.setText("");
                AppUtils.closeSoftInput(this);
                cancel.setVisibility(View.GONE);
                back.setVisibility(View.VISIBLE);
                break;
        }
    }
}
