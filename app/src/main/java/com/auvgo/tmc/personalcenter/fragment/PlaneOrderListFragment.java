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
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.personalcenter.activity.OrderFilterActivity;
import com.auvgo.tmc.personalcenter.adapter.PlaneOrderListAdapter;
import com.auvgo.tmc.personalcenter.interfaces.FreshByTypeFragment;
import com.auvgo.tmc.plane.activity.PlaneAlterDetailActivity;
import com.auvgo.tmc.plane.activity.PlaneOrderDetailActivity;
import com.auvgo.tmc.plane.activity.PlaneReturnDetailActivity;
import com.auvgo.tmc.plane.activity.PlaneSendActivity;
import com.auvgo.tmc.plane.bean.PlaneOrderDetailBean;
import com.auvgo.tmc.plane.bean.PlaneOrderListBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.LogFactory;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.views.RefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.auvgo.tmc.constants.Constant.AirTicketStatus.TICKET_STATUS_YIDINGZUO;

public class PlaneOrderListFragment extends BaseFragment implements FreshByTypeFragment,
        RefreshListView.OnRefreshListener, AdapterView.OnItemClickListener {

    private RefreshListView lv;
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
    private PlaneOrderListBean mBean;
    private List<PlaneOrderListBean.ListBean> list;
    private PlaneOrderListAdapter adapter;

    public static final int NORMAL = 0;
    public static final int RETURNL = 1;
    public static final int ALTER = 2;
    private Context context;
    private View empty_view;
    private OnFragmentInteractionListener mListener;

    public PlaneOrderListFragment() {
    }


    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_plane_order_list;
    }

    @Override
    protected void initData(Fragment fragment) {
        Bundle b = getArguments();
        levelType = b.getString("levelType", "geren");
        ticketType = b.getInt("ticketType");
        list = new ArrayList<>();
        adapter = new PlaneOrderListAdapter(getContext(), list, ticketType);
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

    public void updateNumber(int ticketType, int i) {
        if (mListener != null) {
            mListener.onRequestComplete(ticketType, i);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData();
    }

    private void getData() {
        HashMap<String, String> map = new LinkedHashMap<>();
        map.put("cid", String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        map.put("empid", String.valueOf(MyApplication.mUserInfoBean.getId()));
        map.put("type", levelType);
        map.put("datetype", dateType);
        map.put("begindt", begindt);
        map.put("enddt", enddt);
        map.put("guestname", pname);
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
        RetrofitUtil.getPlaneOrderList(context, AppUtils.getJson(map), PlaneOrderListBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200 && o != null) {
                    mBean = (PlaneOrderListBean) o;
                    pgnum = mBean.getPageNum();
                    if (mBean.isIsFirstPage()) {
                        list.clear();
                        list.addAll(mBean.getList());
                        adapter = new PlaneOrderListAdapter(getContext(), list, ticketType);
                        lv.setAdapter(adapter);
                    } else {
                        list.addAll(mBean.getList());
                        adapter.notifyDataSetChanged();
                    }
                    updateNumber(ticketType, mBean.getTotal());
                } else {
                    pgnum--;
                }
                lv.onRefreshComplete(true);
                setEmptyView();
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                pgnum--;
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

    @Override
    public void setTypeAndFresh(String type) {
        this.levelType = type;
        pgnum = 1;
        if (list != null) {
            getData();
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
        String orderNo = list.get(position).getOrderno();
        switch (ticketType) {
            case NORMAL:
                getNormalDetail(orderNo);
                break;
            case RETURNL:
                jumpToDetail(RETURNL, orderNo);
                break;
            case ALTER:
                jumpToDetail(ALTER, orderNo);
                break;
        }
    }

    private void jumpToDetail(int i, String orderNo) {
        Intent intent = new Intent();
        intent.putExtra("orderNo", orderNo);
        switch (ticketType) {
            case NORMAL:
                break;
            case RETURNL:
                intent.setClass(getContext(), PlaneReturnDetailActivity.class);
                break;
            case ALTER:
                intent.setClass(getContext(), PlaneAlterDetailActivity.class);
                break;
        }
        startActivity(intent);
    }

    private void getNormalDetail(String orderNo) {
        Map<String, String> map = new HashMap<>();
        map.put("orderno", orderNo);
        RetrofitUtil.getPlaneOrderDetail(context, AppUtils.getJson(map), PlaneOrderDetailBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    PlaneOrderDetailBean b = (PlaneOrderDetailBean) o;
                    boolean needApprove = b.getApprovestatus() == Constant.ApproveStatus.APPROVE_STATUS_DAISONGSHEN;
                    Intent intent = new Intent();
                    intent.putExtra("bean", b);
                    /*如果已定座、未审批，跳转到审批*/
                    if (needApprove && b.getStatus() == TICKET_STATUS_YIDINGZUO) {
                        intent.setClass(context, PlaneSendActivity.class);
                    } else {
                        intent.setClass(context, PlaneOrderDetailActivity.class);
                    }
                    context.startActivity(intent);
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    public void doFilter(String name, String dateType, String startDate, String endDate,
                         OrderFilterActivity.FilterBean fb) {
        pname = name;
        this.dateType = dateType;
        this.begindt = startDate;
        this.enddt = endDate;
        int type = fb.getType();
        switch (type) {
            case OrderFilterActivity.FILT_APPROVE_STATE:
                this.approvestatus = fb.getCode();
                this.status = "";
                this.paystatus = "";
                break;
            case OrderFilterActivity.FILT_ORDER_STATE:
                this.status = fb.getCode();
                this.approvestatus = "";
                this.paystatus = "";
                break;
            case OrderFilterActivity.FILT_PAY_STATE:
                this.status = "";
                this.approvestatus = "";
                this.paystatus = fb.getCode();
                break;
        }

        this.pgnum = 1;
        getData();
    }

    public interface OnFragmentInteractionListener {
        void onRequestComplete(int ticketType, int i);
    }
}
