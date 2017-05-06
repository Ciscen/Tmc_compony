package com.auvgo.tmc.hotel.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.PickerListAdapter2;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.hotel.adapter.KeywordCategaryAdapter;
import com.auvgo.tmc.hotel.bean.HotelKeywordBean;
import com.auvgo.tmc.hotel.bean.HotelKeywordSearchBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.views.MyPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;

public class HotelKeywordActivity extends BaseActivity implements View.OnKeyListener, AdapterView.OnItemClickListener {
    /******************
     * View
     *****************/
    @BindView(R.id.hotel_keyword_et)
    EditText et;
    @BindView(R.id.hotel_keyword_lv0)
    ListView lv0;
    @BindView(R.id.hotel_keyword_lv)
    ListView lv;
    /******************
     * DATA
     *****************/
    /*
    令接口请求到的类继承SelectionBean
     */
    private List<HotelKeywordBean> list_all;//所有的数据
    private List<HotelKeywordSearchBean> list_search;//查询到的数据
    private int currentCategory = 0;
    private boolean isSearchMode;
    private String keyword = "";
    /******************
     * 常量
     *****************/
    public static final String KEYWORD_KEY = "keywordBean";
    private String cityId = "0101";
    private KeywordCategaryAdapter adapter_catg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hotel_keyword;
    }

    @Override
    protected void initData() {
        list_all = new ArrayList<>();
        list_search = new ArrayList<>();
        ButterKnife.bind(this);
        cityId = getIntent().getStringExtra("cityId");
//        list_show.add();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        lv0.setOnItemClickListener(this);
        et.setOnKeyListener(this);
    }

    @Override
    protected void setViews() {
    }

    @OnItemClick(R.id.hotel_keyword_lv)
    void onItemClick(int position) {
        Intent intent = new Intent();
        intent.putExtra(KEYWORD_KEY, isSearchMode ? list_search.get(position) :
                list_all.get(currentCategory).getTypeNameList().get(position));
        setResult(Constant.ACTIVITY_HOTEL_KEYWORD_FLAG, intent);
        finish();
    }


    @Override
    protected void getData() {
        super.getData();
        Map<String, String> map = new HashMap<>();
        map.put("cityid", cityId);
        RetrofitUtil.getHotData(this, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    Type t = new TypeToken<List<HotelKeywordBean>>() {
                    }.getType();
                    list_all = new Gson().fromJson(bean.getData(), t);
                    list_all.get(0).setChecked(true);
                    setContent(list_all.get(0).getTypeNameList());
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    private void setShowingList(int i) {
        setContent(list_all.get(i).getTypeNameList());
        currentCategory = i;
    }

    @OnTextChanged(R.id.hotel_keyword_et)
    void onTextChange(CharSequence s) {
        if (TextUtils.isEmpty(s)) {
            lv0.setVisibility(View.VISIBLE);
            if (list_all.size() == 0) {
                return;
            }
            setContent(list_all.get(currentCategory).getTypeNameList());
            isSearchMode = false;
            return;
        } else {
            keyword = s.toString();
            lv0.setVisibility(View.GONE);
            getKeyword();
        }
    }

    @OnClick(R.id.hotel_keyword_back)
    void onBack() {
        finish();
    }

    @OnClick(R.id.hotel_keyword_delete)
    void onClearClick() {
        et.setText("");
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
            String s = et.getText().toString();
            keyword = s;
            getKeyword();
        }

        return false;
    }

    private void getKeyword() {
        Map<String, String> map = new HashMap<>();
        map.put("keyword", keyword);
        map.put("cityid", cityId);
        RetrofitUtil.getHotelKeyword(this, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    Type type = new TypeToken<List<HotelKeywordSearchBean>>() {
                    }.getType();
                    List<HotelKeywordSearchBean> l = new Gson().fromJson(bean.getData(), type);
                    list_search.clear();
                    list_search.addAll(l);
                    isSearchMode = true;
                    lv0.setVisibility(View.GONE);
                    setContent(list_search);
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    private void setContent(List<? extends MyPickerView.Selection> list) {
        adapter_catg = new KeywordCategaryAdapter(this, list_all);
        lv0.setAdapter(adapter_catg);
        PickerListAdapter2 adapter = new PickerListAdapter2(HotelKeywordActivity.this, list, R.layout.item_hotel_keyword);
        lv.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        for (int i = 0; i < list_all.size(); i++) {
            if (i == position) {
                list_all.get(i).setChecked(true);
            } else {
                list_all.get(i).setChecked(false);
            }
        }
        adapter_catg.notifyDataSetChanged();

        setShowingList(position);
    }
}
