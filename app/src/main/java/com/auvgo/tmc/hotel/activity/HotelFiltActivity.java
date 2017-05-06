package com.auvgo.tmc.hotel.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.hotel.adapter.HotelFiltAdapter;
import com.auvgo.tmc.hotel.bean.HotelFiltBean;
import com.auvgo.tmc.hotel.bean.HotelLocationBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HotelFiltActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private String cityId;
    @BindView(R.id.hotel_filt_rg)
    RadioGroup rg;
    @BindView(R.id.hotel_filt_lv)
    ListView lv;
    public static HotelFiltBean mBean;
    private List<HotelLocationBean> list;
    private int mCurrentCategory;
    private HotelFiltAdapter adapter;
    private HotelFiltBean mBean_copy;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hotel_filt;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        cityId = getIntent().getStringExtra("cityId");
        list = new ArrayList<>();
        adapter = new HotelFiltAdapter(this, list);
    }

    @Override
    protected void initView() {
        lv.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        rg.setOnCheckedChangeListener(this);
    }

    @Override
    protected void setViews() {
    }

    @Override
    protected void getData() {
        if (mBean != null && hasSameId()) {
            mBean_copy = copyBean();
            showFiltView();
            return;
        }
        list.clear();
        Map<String, String> map = new HashMap<>();
        map.put("cityid", cityId);
        RetrofitUtil.getHotelLocations(this, AppUtils.getJson(map), HotelFiltBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    mBean = (HotelFiltBean) o;
                    initHotelFiltBean(mBean);
                    mBean_copy = copyBean();
                    showFiltView();
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    private HotelFiltBean copyBean() {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(mBean), HotelFiltBean.class);
    }


    private boolean hasSameId() {
        List<HotelLocationBean> aihao = mBean.getAihao();
        List<HotelLocationBean> brands = mBean.getBrands();
        List<HotelLocationBean> sheshifuwu = mBean.getSheshifuwu();
        List<HotelLocationBean> weizhi = mBean.getWeizhi();
        return aihao.size() > 0 && aihao.get(0).getCityId().equals(cityId)
                || brands.size() > 0 && brands.get(0).getCityId().equals(cityId)
                || sheshifuwu.size() > 0 && sheshifuwu.get(0).getCityId().equals(cityId)
                || weizhi.size() > 0 && weizhi.get(0).getCityId().equals(cityId);
    }

    /*
    拿到数据以后，进行显示
     */
    private void showFiltView() {
        setContent(mBean_copy.getBrands());
        ((RadioButton) rg.getChildAt(0)).setChecked(true);
    }

    private void initHotelFiltBean(HotelFiltBean b) {
        List<HotelLocationBean> aihao = b.getAihao();
        List<HotelLocationBean> brands = b.getBrands();
        List<HotelLocationBean> sheshifuwu = b.getSheshifuwu();
        addLimitless(aihao);
        addLimitless(brands);
        addLimitless(sheshifuwu);
    }

    private void addLimitless(List<HotelLocationBean> l) {
        if (l != null) {
            for (int i = 0; i < l.size(); i++) {
                HotelLocationBean.PositionsBean b = new HotelLocationBean.PositionsBean();
                b.setCode("");
                b.setName("不限");
                b.setChecked(true);
                l.get(i).getPositions().add(0, b);
            }
        }
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
        if (mBean_copy == null) return;
        List<HotelLocationBean> l;
        switch (i) {
            case 0:
                l = mBean_copy.getBrands();
                break;
            case 1:
                l = mBean_copy.getSheshifuwu();
                break;
            case 2:
                l = mBean_copy.getAihao();
                break;
            default:
                l = mBean_copy.getBrands();
                break;
        }
        setContent(l);
        mCurrentCategory = i;
    }

    private void setContent(List<HotelLocationBean> l) {
        list.clear();
        list.addAll(l);
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.hotel_filt_cancle, R.id.hotel_filt_sure})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.hotel_filt_cancle:
                clearAll();
                break;
            case R.id.hotel_filt_sure:
                mBean = mBean_copy;
                gatherResult();
                break;
        }
    }

    private void clearAll() {
        List<HotelLocationBean> brands = mBean_copy.getBrands();
        List<HotelLocationBean> aihao = mBean_copy.getAihao();
        List<HotelLocationBean> sheshifuwu = mBean_copy.getSheshifuwu();
        initOuter(brands);
        initOuter(aihao);
        initOuter(sheshifuwu);
        adapter.notifyDataSetChanged();
    }

    private void initOuter(List<HotelLocationBean> list) {
        for (int i = 0; i < list.size(); i++) {
            initStateInner(list.get(i).getPositions());
        }
    }

    private void gatherResult() {
        List<HotelLocationBean> brands = mBean_copy.getBrands();
        List<HotelLocationBean> aihao = mBean_copy.getAihao();
        List<HotelLocationBean> sheshifuwu = mBean_copy.getSheshifuwu();
        String brands_str = getSelectedIds(brands);
        String aihaoAndsheshifuwu = getSelectedIds(aihao) + "," + getSelectedIds(sheshifuwu);
        aihaoAndsheshifuwu = aihaoAndsheshifuwu.startsWith(",") ? aihaoAndsheshifuwu.substring(1) : aihaoAndsheshifuwu;
        Intent intent = new Intent();
        intent.putExtra("brand", brands_str);
        intent.putExtra("facilities", aihaoAndsheshifuwu);
        setResult(Constant.ACTIVITY_HOTEL_FILT_FLAG, intent);
        finish();
    }

    private String getSelectedIds(List<HotelLocationBean> l) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < l.size(); i++) {
            List<HotelLocationBean.PositionsBean> ps = l.get(i).getPositions();
            for (int i1 = 0; i1 < ps.size(); i1++) {
                if (ps.get(i1).isChecked() && !TextUtils.isEmpty(ps.get(i1).getCode())) {
                    sb.append(ps.get(i1).getCode()).append(",");
                }
            }
        }
        String s = sb.toString();
        return s.endsWith(",") ? s.substring(0, s.length() - 1) : s;
    }

    private void initStateInner(List<HotelLocationBean.PositionsBean> positions) {
        positions.get(0).setChecked(true);
        if (positions.get(0).isChecked()) {
            for (int i = 0; i < positions.size(); i++) {
                if (i > 0 && positions.get(i).isChecked()) {
                    positions.get(i).setChecked(false);
                }
            }
        } else {
            for (int i = 0; i < positions.size(); i++) {
                if (positions.get(i).isChecked()) {
                    return;
                }
            }
        }
        positions.get(0).setChecked(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBean_copy = null;
        System.gc();
    }
}
