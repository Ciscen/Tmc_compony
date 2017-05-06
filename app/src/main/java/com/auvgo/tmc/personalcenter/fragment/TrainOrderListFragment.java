package com.auvgo.tmc.personalcenter.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.bean.OrderListBean;
import com.auvgo.tmc.personalcenter.activity.OrderFilterActivity;
import com.auvgo.tmc.personalcenter.adapter.TrainOrderListAdapter;
import com.auvgo.tmc.personalcenter.interfaces.FreshByTypeFragment;
import com.auvgo.tmc.train.activity.AlterOrderDetailActivity;
import com.auvgo.tmc.train.activity.TrainReturnDetailActivity;
import com.auvgo.tmc.train.activity.TrainOrderDetailActivity;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.LogFactory;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.views.RefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 需要接收的字段为bundle中的ticketType跟level,分别表示票的类型，所选择的权限（个人、全部）
 */
public class TrainOrderListFragment extends BaseFragment implements RefreshListView.OnRefreshListener,
        AdapterView.OnItemClickListener, FreshByTypeFragment {

    private RefreshListView lv;
    private View empty_view;

    private int ticketType = 0;//0正常单，1退票单，2改签单

    /**
     * 请求所需要的字段
     */
    private String levelType = "geren";
    private String dateType = "";
    private String begindt = "";
    private String enddt = "";
    private String status = "";
    private String approvestatus = "";
    private String paystatus = "";
    private String pname = "";
    private int pgnum = 1;
    /*
    请求到的实体
     */
    private OrderListBean mBean;
    private List<OrderListBean.ListBean> list;
    private TrainOrderListAdapter adapter;

    public static final int NORMAL = 0;
    public static final int RETURNL = 1;
    public static final int ALTER = 2;
    private OnFragmentInteractionListener mListener;
    private Context context;

    public TrainOrderListFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_personal_order;
    }

    @Override
    protected void initData(Fragment fragment) {
        Bundle b = getArguments();
        levelType = b.getString("levelType", "geren");
        ticketType = b.getInt("ticketType");
        list = new ArrayList<>();
        adapter = new TrainOrderListAdapter(getContext(), list, R.layout.item_order_list, ticketType);
        LogFactory.d("onCreateView---->" + ticketType);
    }

    @Override
    protected void findViews(View view) {
        empty_view = view.findViewById(R.id.empty_view);
        lv = (RefreshListView) view.findViewById(R.id.pc_order_list_lv);
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
        LogFactory.d("onActivityCreated------>" + ticketType);
    }

    private void getData() {
        HashMap<String, String> map = new LinkedHashMap<>();
        map.put("cid", String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        map.put("empid", String.valueOf(MyApplication.mUserInfoBean.getId()));
        map.put("type", levelType);
        map.put("datetype", dateType);
        map.put("begindt", begindt);
        map.put("enddt", enddt);
        map.put("pname", pname);
        map.put("status", status);
        map.put("approvestatus", approvestatus);
        map.put("paystatus", paystatus);
        map.put("pgnum", pgnum + "");
        switch (ticketType) {
            case NORMAL:
                map.put("tag", "zc");
                break;
            case RETURNL:
                map.put("tag", "tp");
                break;
            case ALTER:
                map.put("tag", "gq");
                break;
        }
        getOrder(map);
    }

    private void getOrder(HashMap<String, String> map) {
        RetrofitUtil.getTrainOrderList(context, AppUtils.getJson(map), OrderListBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    mBean = (OrderListBean) o;
                    if (mBean.isIsFirstPage()) {
                        list.clear();
                        list.addAll(mBean.getList());
                        adapter = new TrainOrderListAdapter(getContext(), list, R.layout.item_order_list, ticketType);
                        lv.setAdapter(adapter);
                    } else {
                        list.addAll(mBean.getList());
                        adapter.notifyDataSetChanged();
                    }
                    setEmptyView();
                    lv.onRefreshComplete(true);
                    mListener.onRequestComplete(ticketType, mBean.getTotal());
                }
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
            empty_view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRefresh() {
        pgnum = 1;
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("orderNo", list.get(position).getOrderno());
        switch (ticketType) {
            case NORMAL:
                intent.setClass(getContext(), TrainOrderDetailActivity.class);
                break;
            case RETURNL:
                intent.setClass(getContext(), TrainReturnDetailActivity.class);
                break;
            case ALTER:
                intent.setClass(getContext(), AlterOrderDetailActivity.class);
                break;
        }
        startActivity(intent);
    }

    public void doFilter(String name, String dateType, String startDate, String endDate, OrderFilterActivity.FilterBean filterBean) {
        pname = name;
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

    @Override
    public void setTypeAndFresh(String type) {
        this.levelType = type;
        pgnum = 1;
        if (list != null) {
            getData();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onRequestComplete(int ticketType, int i);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
