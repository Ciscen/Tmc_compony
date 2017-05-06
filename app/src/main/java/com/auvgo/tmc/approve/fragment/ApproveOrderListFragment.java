package com.auvgo.tmc.approve.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.approve.HotelApproveActivity;
import com.auvgo.tmc.approve.adapter.ApproveOrderListAdapter_hotel;
import com.auvgo.tmc.approve.adapter.ApproveOrderListAdapter_plane;
import com.auvgo.tmc.approve.PlaneApproveActivity;
import com.auvgo.tmc.approve.TrainApproveActivity;
import com.auvgo.tmc.approve.adapter.ApproveOrderListAdapter_train;
import com.auvgo.tmc.bean.HotelOrderListBean;
import com.auvgo.tmc.bean.TrainOrderListBean;
import com.auvgo.tmc.personalcenter.fragment.BaseFragment;
import com.auvgo.tmc.plane.bean.PlaneOrderListBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.UserBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.views.RefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApproveOrderListFragment extends BaseFragment implements RefreshListView.OnRefreshListener, AdapterView.OnItemClickListener {
    private EditText et;
    private RefreshListView lv;
    private View empty_view;
    private int mType = 0;
    private String name = "";
    private int pageNum = 1;
    private boolean isChanging = false;//标记是否是点击切换的时候
    private boolean mIsLoadMore = false;
    private boolean mCanLoadMore = true;
    private ApproveOrderListAdapter_train adapter_train;
    private ApproveOrderListAdapter_plane adapter_plane;
    private ApproveOrderListAdapter_hotel adapter_hotel;
    private ApproveOrderListFragmentCallback callback;
    private String contentFlag = Constant.TRAIN;
    private List<TrainOrderListBean.ListBean> list_train;
    private List<PlaneOrderListBean.ListBean> list_plane;
    private List<HotelOrderListBean.ListBean> list_hotel;

    public ApproveOrderListFragment() {
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_approve;
    }

    @Override
    protected void initData(Fragment fragment) {
        Bundle bundle = getArguments();
        mType = bundle.getInt("type");
        list_train = new ArrayList<>();
        list_plane = new ArrayList<>();
        list_hotel = new ArrayList<>();
        adapter_train = new ApproveOrderListAdapter_train(getContext(), list_train);
        adapter_plane = new ApproveOrderListAdapter_plane(getContext(), list_plane);
        adapter_hotel = new ApproveOrderListAdapter_hotel(getContext(), list_hotel);
    }

    @Override
    protected void findViews(View view) {
        et = (EditText) view.findViewById(R.id.approve_et);
        lv = (RefreshListView) view.findViewById(R.id.approve_lv);
        empty_view = view.findViewById(R.id.empty_view);
    }

    @Override
    protected void initView() {
        lv.setAdapter(adapter_train);
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

    private void getData() {
        HashMap<String, String> map = new LinkedHashMap<>();
        UserBean userInfoBean = MyApplication.mUserInfoBean;
        map.put("cid", String.valueOf(userInfoBean.getCompanyid()));
        map.put("empid", String.valueOf(userInfoBean.getId()));
        map.put("type", mType + "");
        map.put("name", name);
        map.put("pgnum", pageNum + "");
        map.put("ywtype", contentFlag);

        RetrofitUtil.getApproveOrder(getContext(), AppUtils.getJson(map), getClazz(), new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean b, int status, String msg, Object o) {
                if (status == 200) {
                    switch (contentFlag) {
                        case Constant.TRAIN:
                            showAsTrain((TrainOrderListBean) o);
                            break;
                        case Constant.PLANE:
                            showAsPlane((PlaneOrderListBean) o);
                            break;
                        case Constant.HOTEL:
                            showAsHotel((HotelOrderListBean) o);
                            break;
                    }
                } else {
                    lv.setAdapter(null);
                }
                lv.onRefreshComplete(true);
                isChanging = false;
                setEmptyView();
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                mIsLoadMore = false;
                isChanging = false;
                setEmptyView();
                return false;
            }
        });
    }

    private void setEmptyView() {
        boolean isEmpty = false;
        switch (contentFlag) {
            case Constant.HOTEL:
                if (list_hotel == null || list_hotel.size() == 0) isEmpty = true;
                break;
            case Constant.TRAIN:
                if (list_train == null || list_train.size() == 0) isEmpty = true;
                break;
            case Constant.PLANE:
                if (list_plane == null || list_plane.size() == 0) isEmpty = true;
                break;
        }
        empty_view.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
    }

    private void showAsHotel(HotelOrderListBean bean) {
        if (!mIsLoadMore) {
            list_hotel.clear();
        }
        if (bean.isIsLastPage()) {
            mCanLoadMore = false;
            lv.setFooterViewVisibility(View.GONE);
        } else {
            lv.setFooterViewVisibility(View.VISIBLE);
            mCanLoadMore = true;
        }
        list_hotel.addAll(bean.getList());
        if (isChanging) {
            list_train.clear();
            list_plane.clear();
            lv.setAdapter(null);
            lv.setAdapter(adapter_hotel);
        } else {
            adapter_hotel.notifyDataSetChanged();
        }
        callback.setNum(mType, bean.getTotal());
    }

    private void showAsTrain(TrainOrderListBean bean) {
        if (!mIsLoadMore) {
            clearList();
        }
        if (bean.isIsLastPage()) {
            mCanLoadMore = false;
            lv.setFooterViewVisibility(View.GONE);
        } else {
            lv.setFooterViewVisibility(View.VISIBLE);
            mCanLoadMore = true;
        }
        list_train.addAll(bean.getList());
        if (isChanging) {
            list_hotel.clear();
            list_plane.clear();
            lv.setAdapter(null);
            lv.setAdapter(adapter_train);
        } else {
            adapter_train.notifyDataSetChanged();
        }

        callback.setNum(mType, bean.getTotal());
    }

    private void clearList() {
        list_hotel.clear();
        list_train.clear();
        list_plane.clear();
    }

    private void showAsPlane(PlaneOrderListBean bean) {
        if (!mIsLoadMore) {
            list_plane.clear();
        }
        if (bean.isIsLastPage()) {
            mCanLoadMore = false;
            lv.setFooterViewVisibility(View.GONE);
        } else {
            lv.setFooterViewVisibility(View.VISIBLE);
            mCanLoadMore = true;
        }
        list_plane.addAll(bean.getList());
        if (isChanging) {
            list_train.clear();
            list_hotel.clear();
            lv.setAdapter(null);
            lv.setAdapter(adapter_plane);
        } else {
            adapter_plane.notifyDataSetChanged();
        }
        callback.setNum(mType, bean.getTotal());
    }

    private Class getClazz() {
        switch (contentFlag) {
            case Constant.TRAIN:
                return TrainOrderListBean.class;
            case Constant.PLANE:
                return PlaneOrderListBean.class;
            case Constant.HOTEL:
                return HotelOrderListBean.class;
        }
        return null;
    }

    @Override
    public void onRefresh() {
        pageNum = 1;
        mIsLoadMore = false;
        getData();
    }

    @Override
    public void onLoadMore() {
        if (!mCanLoadMore) {
            lv.onRefreshComplete(true);
            return;
        }
        pageNum++;
        mIsLoadMore = true;
        getData();
    }

    public void changeContent(String flag) {
        contentFlag = flag;
//        list_train.clear();
//        list_plane.clear();
        pageNum = 1;
        isChanging = true;
        getData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position < 0) return;
        Intent intent = new Intent();
        String orderNo = "";
        Class c = null;
        switch (contentFlag) {
            case Constant.TRAIN:
                orderNo = list_train.get(position).getOrderno();
                c = TrainApproveActivity.class;
                break;
            case Constant.PLANE:
                String type = list_plane.get(position).getType();
                orderNo = list_plane.get(position).getOrderno();
                intent.putExtra("type", type);
                c = PlaneApproveActivity.class;
                break;
            case Constant.HOTEL:
                orderNo = list_hotel.get(position).getOrderno();
                c = HotelApproveActivity.class;
                break;
        }
        intent.putExtra("orderNo", orderNo);
        intent.setClass(getContext(), c);
        startActivity(intent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null && context instanceof ApproveOrderListFragmentCallback) {
            this.callback = (ApproveOrderListFragmentCallback) context;
        }
    }

    public interface ApproveOrderListFragmentCallback {
        void setNum(int i, int n);
    }
}
