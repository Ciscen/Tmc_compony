
package com.auvgo.tmc.plane.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.PlaneReturnApplyPsgsAdapter;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.home.HomeActivity;
import com.auvgo.tmc.personalcenter.activity.OrderListActivity;
import com.auvgo.tmc.personalcenter.activity.PersonalCenterActivity;
import com.auvgo.tmc.plane.bean.PlaneOrderDetailBean;
import com.auvgo.tmc.plane.bean.PlanePolicyBean;
import com.auvgo.tmc.plane.bean.RequestReturnPlaneBean;
import com.auvgo.tmc.plane.bean.ReturnReasonBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.SelectionBean;
import com.auvgo.tmc.train.bean.UserBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.ItemView;
import com.auvgo.tmc.views.MyDialog;
import com.auvgo.tmc.views.MyListView;
import com.auvgo.tmc.views.MyPickerView;
import com.auvgo.tmc.views.PlaneDetailCardView;
import com.auvgo.tmc.views.TitleView;
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

public class PlaneReturnApplyActivity extends BaseActivity {
    @BindView(R.id.plane_alter_return_title)
    TitleView title;
    @BindView(R.id.plane_alter_return_confirm_tv1)
    TextView tv1;
    @BindView(R.id.plane_alter_return_confirm_tv2)
    TextView tv2;
    @BindView(R.id.plane_alter_return_confirm_tv3)
    TextView tv3;
    @BindView(R.id.plane_alter_return_confirm_notice)
    TextView notice;
    @BindView(R.id.plane_alter_return_confirm_lv)
    MyListView lv;
    @BindView(R.id.plane_alter_return_confirm_cv)
    PlaneDetailCardView cv;
    @BindView(R.id.plane_alter_return_tuipiao_iv)
    ItemView iv;
    @BindView(R.id.plane_alter_return_tuipiao_reason)
    View tuipiaoReason;

    public static PlaneOrderDetailBean mBean;
    private PlaneReturnApplyPsgsAdapter adapter;
    private List<ReturnReasonBean> mReasons;
    private int mCurrentPosition_tuiReason = -1;
    private boolean isAlter;//是否是改签

    @Override
    protected int getLayoutId() {
        return R.layout.activity_plane_return_apply;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        mBean = getIntent().getParcelableExtra("bean");
        isAlter = getIntent().getBooleanExtra("isAlter", false);
        initList();
        adapter = new PlaneReturnApplyPsgsAdapter(this, mBean, true);
    }

    private void initList() {//0退票  1改签
        List<PlaneOrderDetailBean.PassengersBean> list = new ArrayList<>();
        List<PlaneOrderDetailBean.PassengersBean> passengers = mBean.getPassengers();
        for (int i = 0; i < passengers.size(); i++) {
            PlaneOrderDetailBean.PassengersBean passengersBean = passengers.get(i);
            int id = passengersBean.getId();
            int tuipiaostatus = MUtils.getTuipiaostatusByPsgId(mBean, id);
            int gaiqianstatus = MUtils.getGaiqianstatusByPsgId(mBean, id);
            if (isAlter) {
                if (tuipiaostatus == 0 && (gaiqianstatus == 0 || gaiqianstatus == 2)) {
                    list.add(passengersBean);
                }
            } else {
                if (tuipiaostatus == 0) {
                    list.add(passengersBean);
                }
            }
        }
        mBean.setPassengers(list);
    }

    @Override
    protected void initView() {
        final PlaneOrderDetailBean.RoutesBean routesBean = mBean.getRoutes().get(0);
        cv.setAirline(routesBean.getCarriername() + routesBean.getAirline() + "|" + routesBean.getPlanestyle());
        cv.setStart_date(routesBean.getDeptdate().substring(5) + " " + TimeUtils.getTomorrowWeekDay(routesBean.getDeptdate()));
        cv.setEnd_date(routesBean.getArridate().substring(5) + " " + TimeUtils.getTomorrowWeekDay(routesBean.getArridate()));
        cv.setStart_time(routesBean.getDepttime());
        cv.setEnd_time(routesBean.getArritime());
        cv.setOrgname(routesBean.getOrgname());
        cv.setArriname(routesBean.getArriname());
        cv.setCangwei2(routesBean.getCodeDes() + "/" + routesBean.getDisdes());
        lv.setAdapter(adapter);
        TextView tuigaiqian = (TextView) cv.findViewById(R.id.plane_detail_costtime);
        tuigaiqian.setText("退改签");
        tuigaiqian.setTextColor(getResources().getColor(R.color.appTheme1));
        tuigaiqian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaneReturnApplyActivity.this, PlanTuiGaiQianActivity.class);
                intent.putExtra("tuipiao", routesBean.getRefundrule());
                intent.putExtra("gaiqian", routesBean.getChangerule());
                startActivity(intent);
            }
        });
        if (isAlter) {
            title.setTitle("申请改签");
            tv1.setText("请选择需要改签的乘客");
            tv2.setText("需改签的航班");
            tv3.setText("申请改签");
            notice.setText("我们尽量帮您申请改签，改签结果以航司为准");
            tuipiaoReason.setVisibility(View.GONE);
        }
    }

    @Override
    protected void setViews() {

    }

    @Override
    protected void getData() {
        super.getData();

    }

    @OnClick({R.id.plane_alter_return_tuipiao_reason, R.id.plane_alter_return_confirm_tv3})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plane_alter_return_tuipiao_reason:
                showPop();
                break;
            case R.id.plane_alter_return_confirm_tv3:
                if (!isAlter) applyReturn();
                else applyAlter();
                break;
        }
    }

    private void applyAlter() {
        if (!checkIsNoPsg()) {
            ToastUtils.showTextToast("请选择操作的乘客");
            return;
        }
        if (!checkIfSecondAlter())
            getPolicy(getPsgs());
        else
            DialogUtil.showDialog(this, "提示", "确定", "", "您提交的订单中包含已改签过的机票，如需再次改签请拨打客服电话：400 606 0011", null);
    }

    private boolean checkIfSecondAlter() {
        List<PlaneOrderDetailBean.PassengersBean> passengers = mBean.getPassengers();
        for (int i = 0; i < passengers.size(); i++) {
            if (MUtils.getGaiqianstatusByPsgId(mBean, passengers.get(i).getId()) == 2) {
                return true;
            }
        }
        return false;
    }

    private void getPolicy(List<PlaneOrderDetailBean.PassengersBean> psgs) {
        RetrofitUtil.getPlanePolicy(this, MUtils.getRequestStringByPsg(psgs), null,
                new RetrofitUtil.OnResponse() {
                    @Override
                    public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                        if (status == 200) {
                            if (bean.getData().length() != 3) {
                                MyApplication.mPlanePolicy = new Gson().fromJson(bean.getData(), PlanePolicyBean.class);
                            } else {
                                MyApplication.mPlanePolicy = null;
                            }
                            MUtils.jumpToPage(PlaneReturnApplyActivity.this, PlaneAlterQueryActivity.class, -1);
                        }
                        return false;
                    }

                    @Override
                    public boolean onFailed(Throwable e) {
                        return false;
                    }
                });
    }

    private void applyReturn() {
        if (!checkIsNoPsg()) {
            ToastUtils.showTextToast("请选择操作的乘客");
            return;
        }
        if (mCurrentPosition_tuiReason == -1 && !isAlter) {
            ToastUtils.showTextToast("请选择退票原因");
            return;
        }
        RequestReturnPlaneBean rrpb = new RequestReturnPlaneBean();
        UserBean mUserInfoBean = MyApplication.mUserInfoBean;
        rrpb.setCid(String.valueOf(mUserInfoBean.getCompanyid()));
        rrpb.setEmpid(String.valueOf(mUserInfoBean.getId()));
        rrpb.setOrderfrom("2");
        rrpb.setPassids(getUserIds());
        rrpb.setRouteids(getRouteIds());
        rrpb.setOrderno(mBean.getOrderno());
        rrpb.setTpreason(mReasons.get(mCurrentPosition_tuiReason).getName());
        RetrofitUtil.returnPlaneTicket(this, AppUtils.getJson(rrpb), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    DialogUtil.showDialog(PlaneReturnApplyActivity.this, "提示", "确定", "", "提交退票成功", new MyDialog.OnButtonClickListener() {
                        @Override
                        public void onLeftClick() {
                            MUtils.jumpToPage(PlaneReturnApplyActivity.this, HomeActivity.class,
                                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            MUtils.jumpToPage(PlaneReturnApplyActivity.this, PersonalCenterActivity.class, -1);

                            Intent intent = new Intent(PlaneReturnApplyActivity.this, OrderListActivity.class);
                            intent.putExtra("class", Constant.PLANE);
                            startActivity(intent);
                        }

                        @Override
                        public void onRightClick() {

                        }
                    });
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    private List<String> getRouteIds() {
        ArrayList<String> list = new ArrayList<String>();
        list.add(String.valueOf(mBean.getRoutes().get(0).getId()));
        return list;
    }

    private boolean checkIsNoPsg() {
        List<PlaneOrderDetailBean.PassengersBean> passengers = mBean.getPassengers();
        for (int i = 0; i < passengers.size(); i++) {
            if (passengers.get(i).isChecked()) {
                return true;
            }
        }
        return false;
    }

    private void showPop() {
        Map<String, String> map = new HashMap<>();
        map.put("cid", String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        RetrofitUtil.getReturnReasons(this, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<ReturnReasonBean>>() {
                    }.getType();
                    mReasons = gson.fromJson(bean.getData(), type);
//                    final List<SelectionBean> selectionBeens = initWbReasonSelections(mReasons);
                    if (mReasons != null) {
                        DialogUtil.showPickerPopWithSureHeight(PlaneReturnApplyActivity.this, "选择原因"
                                , mCurrentPosition_tuiReason,
                                mReasons, new MyPickerView.OnPickerViewSure() {
                                    @Override
                                    public void onSingleSureClick(int p) {
                                        mCurrentPosition_tuiReason = p;
                                        iv.setContent(mReasons.get(mCurrentPosition_tuiReason).getName());
                                    }

                                    @Override
                                    public void onMultiSureClick(List<Integer> s) {

                                    }
                                });
                    }
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    private List<SelectionBean> initWbReasonSelections(List<ReturnReasonBean> list) {
        if (list == null) return null;
        List<SelectionBean> sbs = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            SelectionBean sb = new SelectionBean(list.get(i).getName(), false);
            sbs.add(sb);
        }
        return sbs;
    }

    private List<String> getUserIds() {
        List<String> list = new ArrayList<>();
        List<PlaneOrderDetailBean.PassengersBean> psgs = getPsgs();
        for (int i = 0; i < psgs.size(); i++) {
            list.add(String.valueOf(psgs.get(i).getId()));
        }
        return list;
    }

    private List<PlaneOrderDetailBean.PassengersBean> getPsgs() {
        List<PlaneOrderDetailBean.PassengersBean> users = mBean.getPassengers();
        List<PlaneOrderDetailBean.PassengersBean> list = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).isChecked()) {
                list.add(users.get(i));
            }
        }
        return list;
    }

    @OnItemClick(R.id.plane_alter_return_confirm_lv)
    public void onItemClick(int position) {
        PlaneOrderDetailBean.PassengersBean passengersBean = mBean.getPassengers().get(position);
        passengersBean.setChecked(!passengersBean.isChecked());
        adapter.notifyDataSetChanged();
    }
}
