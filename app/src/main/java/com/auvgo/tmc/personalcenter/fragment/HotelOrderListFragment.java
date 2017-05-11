package com.auvgo.tmc.personalcenter.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.hotel.activity.HotelOrderDetailActivity;
import com.auvgo.tmc.hotel.activity.HotelSendActivity;
import com.auvgo.tmc.personalcenter.activity.OrderFilterActivity;
import com.auvgo.tmc.personalcenter.adapter.HotelOrderListAdapter;
import com.auvgo.tmc.bean.HotelOrderListBean;
import com.auvgo.tmc.personalcenter.interfaces.FreshByTypeFragment;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.views.RefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static com.auvgo.tmc.constants.Constant.ApproveStatus.*;
import static com.auvgo.tmc.utils.HotelStateCons.OrderStatus.HOTEL_ORDER_STATUS_CANCEL;

/**
 * {
 *     "cid":"1",
 *     "empid":"",
 *     "type":"geren/all",
 *     "tag":"zc/tp/gq",
 *     "datetype":"bookdate/arrivaldate",
 *     "begindt":"yyyy-MM-dd",
 *     "enddt":"yyyy-MM-dd",
 *     "status":"",
 *     "approvestatus":"",
 *     "paystatus":"",
 *     "pgnum":1,
 *     "hotelname":"酒店名称",
 *     "cityid":"0101",
 *     "guestname":"入住人姓名"
 * }
 */
public class HotelOrderListFragment extends BaseFragment implements
        FreshByTypeFragment, RefreshListView.OnRefreshListener, AdapterView.OnItemClickListener {

    private RefreshListView lv;
    private View empty_view;

    private String levelType;
    private String dateType = "";
    private String begindt = "";
    private String enddt = "";
    private String pname = "";
    private String status = "";
    private String approvestatus = "";
    private String paystatus = "";
    private int pgnum = 1;
    private String cityid = "";
    private String hotelname = "";
    private String guestname = "";
    private HotelOrderListBean mBean;
    private List<HotelOrderListBean.ListBean> list;
    private HotelOrderListAdapter adapter;

    public HotelOrderListFragment() {
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_hotel_order_list;
    }

    @Override
    protected void initData(Fragment fragment) {
        Bundle b = getArguments();
        levelType = b.getString("levelType", "geren");
        list = new ArrayList<>();
        adapter = new HotelOrderListAdapter(getContext(), list);
    }

    @Override
    protected void findViews(View view) {
        empty_view = view.findViewById(R.id.empty_view);
        lv = (RefreshListView) view.findViewById(R.id.hotel_order_list_lv);
    }

    @Override
    protected void initView() {
        lv.setAdapter(adapter);
        lv.setEmptyView(empty_view);
        empty_view.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initListener() {
        lv.setOnRefreshListener(this);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData();
    }

    HashMap<String, String> map = new LinkedHashMap<>();

    private void getData() {
        map.put("cid", String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        map.put("empid", String.valueOf(MyApplication.mUserInfoBean.getId()));
        map.put("type", levelType);
        map.put("tag", "zc");
        map.put("datetype", dateType);
        map.put("begindt", begindt);
        map.put("enddt", enddt);
        map.put("status", status);
        map.put("approvestatus", approvestatus);
        map.put("paystatus", paystatus);
        map.put("pgnum", pgnum + "");
        map.put("hotelname", hotelname);
        map.put("cityid", cityid);
        map.put("guestname", guestname);
        RetrofitUtil.getHotelOrderList(getContext(), AppUtils.getJson(map), HotelOrderListBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    mBean = (HotelOrderListBean) o;
                    if (mBean.isIsFirstPage()) {
                        list.clear();
                        list.addAll(mBean.getList());
                        adapter = new HotelOrderListAdapter(getContext(), list);
                        lv.setAdapter(adapter);
                    } else {
                        list.addAll(mBean.getList());
                        adapter.notifyDataSetChanged();
                    }
                }
                setEmptyView();
                lv.onRefreshComplete(true);
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                setEmptyView();
                return false;
            }
        });
    }

    private void setEmptyView() {
        if (list == null || list.size() == 0) {
            empty_view.setVisibility(View.VISIBLE);
        } else {
            empty_view.setVisibility(View.GONE);
        }
    }

    @Override
    public void setTypeAndFresh(String type) {
        this.levelType = type;
        pgnum = 1;
        if (list != null) {
            getData();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HotelOrderListBean.ListBean lb = list.get(position);
        Intent intent = new Intent();
        int approvestatus = lb.getApprovestatus();
        int statu = lb.getStatu();
        intent.putExtra("orderNo", lb.getOrderno());
        //未送审
        if (approvestatus == APPROVE_STATUS_DAISONGSHEN && statu != HOTEL_ORDER_STATUS_CANCEL) {
            intent.setClass(getContext(), HotelSendActivity.class);
        } else {
            intent.setClass(getContext(), HotelOrderDetailActivity.class);
        }
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        pgnum = 1;
        list.clear();
        getData();
    }

    @Override
    public void onLoadMore() {
        lv.onRefreshComplete(true);
        if (!mBean.isIsLastPage()) {
            pgnum++;
            getData();
        }
    }

    public void doFilter(String name, String dateType, String startDate, String endDate, OrderFilterActivity.FilterBean filterBean) {
        guestname = name;
        this.dateType = dateType;
        this.begindt = startDate;
        this.enddt = endDate;
        int type = filterBean.getType();
        switch (type) {
            case OrderFilterActivity.FILT_APPROVE_STATE:
                this.approvestatus = filterBean.getCode();
                this.status = "";
                this.paystatus = "";
                break;
            case OrderFilterActivity.FILT_ORDER_STATE:
                this.status = filterBean.getCode();
                this.approvestatus = "";
                this.paystatus = "";
                break;
            case OrderFilterActivity.FILT_PAY_STATE:
                this.status = "";
                this.approvestatus = "";
                this.paystatus = filterBean.getCode();
                break;
        }
        this.pgnum = 1;
        getData();
    }
}
