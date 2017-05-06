package com.auvgo.tmc.hotel.activity;

import android.content.Intent;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.PickerListAdapter2;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.hotel.bean.HotelFiltBean;
import com.auvgo.tmc.hotel.bean.HotelLocationBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.RetrofitUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class HotelLocationActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.hotel_location_rg)
    RadioGroup rg;
    @BindView(R.id.hotel_location_lv)
    ListView lv;
    /******************
     * DATA
     *****************/
    /*
    令接口请求到的类继承SelectionBean
     */
    private static List<HotelLocationBean> list_all;//所有的数据
    private int currentCategory;
    private boolean isSearchMode;
    /******************
     * 常量
     *****************/
    public static final String LOCATION_KEY = "locationBean";
    private String cityId;
    private PickerListAdapter2 adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hotel_location;
    }

    @Override
    protected void initData() {
        cityId = getIntent().getStringExtra("cityId");
        if (list_all == null)
            list_all = new ArrayList<>();
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        ((RadioButton) rg.getChildAt(0)).setChecked(true);
    }

    @Override
    protected void getData() {
        super.getData();
        if (list_all.size() > 0 && list_all.get(0).getCityId().equals(cityId)) {
            setContent(list_all.get(0).getPositions());
            return;
        }
        list_all.clear();
        Map<String, String> map = new HashMap<>();
        map.put("cityid", cityId);
        RetrofitUtil.getHotelLocations(this, AppUtils.getJson(map), HotelFiltBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    HotelFiltBean b = (HotelFiltBean) o;
                    list_all.addAll(b.getWeizhi());
                    addNoLimit();
                    setContent(list_all.get(0).getPositions());
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    /*
    添加不限这一项
     */
    private void addNoLimit() {
        for (int i = 0; i < list_all.size(); i++) {
            HotelLocationBean.PositionsBean element = new HotelLocationBean.PositionsBean();
            element.setName("不限");
            element.setCode("");
            element.setChecked(true);
            list_all.get(i).getPositions().add(0, element);
        }
    }

    @Override
    protected void initListener() {
        rg.setOnCheckedChangeListener(this);
    }

    @OnItemClick(R.id.hotel_location_lv)
    void onItemClick(int position) {
        Intent intent = new Intent();
        HotelLocationBean.PositionsBean positionsBean = list_all.get(currentCategory).getPositions().get(position);
        setSelection(position);
        adapter.notifyDataSetChanged();
        intent.putExtra(LOCATION_KEY, positionsBean);
        setResult(Constant.ACTIVITY_HOTEL_LOCATION_FLAG, intent);
        finish();
    }

    private void setSelection(int position) {
        for (int i = 0; i < list_all.size(); i++) {
            if (i == currentCategory) {
                List<HotelLocationBean.PositionsBean> positions = list_all.get(currentCategory).getPositions();
                for (int j = 0; j < positions.size(); j++) {
                    if (j == position) {
                        positions.get(j).setChecked(true);
                    } else {
                        positions.get(j).setChecked(false);
                    }
                }
            } else {
                List<HotelLocationBean.PositionsBean> positions = list_all.get(i).getPositions();
                for (int j = 0; j < positions.size(); j++) {
                    positions.get(j).setChecked(false);
                }
            }
        }


        List<HotelLocationBean.PositionsBean> positions = list_all.get(currentCategory).getPositions();
        for (int i = 0; i < positions.size(); i++) {
            if (i == position) {
                positions.get(i).setChecked(true);
            } else {
                positions.get(i).setChecked(false);
            }
        }
    }

    @Override
    protected void setViews() {
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        for (int i = 0; i < group.getChildCount(); i++) {
            if (group.getChildAt(i).getId() == checkedId) {
                setShowingList(i);
            }
        }
    }

    private void setShowingList(int i) {
        if (list_all.size() > 0) {
            setContent(list_all.get(i).getPositions());
        }
        currentCategory = i;
    }

    private void setContent(List<HotelLocationBean.PositionsBean> list) {
        adapter = new PickerListAdapter2(this, list, R.layout.item_hotel_keyword);
        lv.setAdapter(adapter);
    }

}
