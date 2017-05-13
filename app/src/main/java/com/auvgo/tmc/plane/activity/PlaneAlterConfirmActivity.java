package com.auvgo.tmc.plane.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.PlaneOrderDetailPsgAdapter;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.p.PPlaneBook;
import com.auvgo.tmc.plane.bean.PlaneListBean;
import com.auvgo.tmc.plane.bean.PlaneOrderDetailBean;
import com.auvgo.tmc.plane.bean.PlanePolicyBean;
import com.auvgo.tmc.plane.bean.PlaneRouteBeanWF;
import com.auvgo.tmc.plane.bean.RequestCreatePlaneAlterBean;
import com.auvgo.tmc.plane.bean.RequestCreatePlaneOrder;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.views.MyDialog;
import com.auvgo.tmc.views.PlaneDetailCardView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlaneAlterConfirmActivity extends BaseActivity {

    @BindView(R.id.plane_alter_confirm_orderNo)
    TextView orderNo_tv;
    @BindView(R.id.plane_alter_confirm_ticketStatus)
    TextView ticketState_tv;
    @BindView(R.id.plane_alter_confirm_time_dept)
    TextView time_dept_tv;
    @BindView(R.id.plane_alter_confirm_airport_dept)
    TextView airport_dept_tv;
    @BindView(R.id.plane_alter_confirm_airport_arri)
    TextView airport_arri_tv;
    @BindView(R.id.plane_alter_confirm_airline)
    TextView airline_tv;
    @BindView(R.id.plane_alter_confirm_time_arri)
    TextView time_arri_tv;
    @BindView(R.id.plane_alter_confirm_price_all)
    TextView price_all_tv;
    @BindView(R.id.plane_alter_confirm_commit)
    TextView commit_tv;
    @BindView(R.id.plane_alter_confirm_cv)
    PlaneDetailCardView cv;
    @BindView(R.id.plane_alter_confirm_psgs_lv)
    ListView psgs_lv;
    private PlaneOrderDetailBean oldBean;//旧订单
    private PlaneListBean mBean;//新选择的车次实体
    private int cangwei;
    private boolean isWei;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_plane_alter_confirm;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        oldBean = PlaneReturnApplyActivity.mBean;
        PlaneRouteBeanWF rb = (PlaneRouteBeanWF) getIntent().getSerializableExtra("firstRoute");
        mBean = rb.getBean();
        cangwei = rb.getCangwei();
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void initView() {
        ticketState_tv.setText(MUtils.getOrgTicketStateByCode(oldBean.getStatus()));
        orderNo_tv.setText(String.format("订单号:%s", oldBean.getOrderno()));
        List<PlaneOrderDetailBean.RoutesBean> routes = oldBean.getRoutes();
        PlaneOrderDetailBean.RoutesBean routesBean = routes.get(0);
        time_dept_tv.setText(routesBean.getDeptdate().substring(5) + " " + routesBean.getDepttime());
        time_arri_tv.setText(routesBean.getArridate().substring(5) + " " + routesBean.getArritime());
        airport_dept_tv.setText(routesBean.getOrgname());
        airport_arri_tv.setText(routesBean.getArriname());
        airline_tv.setText(routesBean.getCarriername() + routesBean.getAirline() + "|" + routesBean.getCodeDes());

        cv.setAirline("新航程");
        final PlaneListBean.CangweisBean cangweisBean = mBean.getCangweis().get(cangwei);
        cv.setFood(mBean.getCarriername() + mBean.getAirline() + "|" + cangweisBean.getCodeDes());
        cv.setStart_date(TimeUtils.getMMdd(mBean.getDeptdate()) + " " + TimeUtils.getTomorrowWeekDay(mBean.getDeptdate()));
        cv.setEnd_date(TimeUtils.getMMdd(mBean.getArridate()) + " " + TimeUtils.getTomorrowWeekDay(mBean.getArridate()));
        cv.setStart_time(mBean.getDepttime());
        cv.setEnd_time(mBean.getArritime());
        cv.setOrgname(mBean.getOrgname() + mBean.getDeptterm());
        cv.setArriname(mBean.getArriname() + mBean.getArriterm());
        TextView tuigaiqian = (TextView) cv.findViewById(R.id.plane_detail_costtime);
        tuigaiqian.setText("退改签");
        cv.setGaiStr(mBean.getCangweis().get(cangwei).getChangerule());
        cv.setTuiStr(mBean.getCangweis().get(cangwei).getRefundrule());

        tuigaiqian.setTextColor(getResources().getColor(R.color.appTheme1));
        tuigaiqian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaneAlterConfirmActivity.this, PlanTuiGaiQianActivity.class);
                intent.putExtra("tuipiao", cangweisBean.getRefundrule());
                intent.putExtra("gaiqian", cangweisBean.getChangerule());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setViews() {
        psgs_lv.setAdapter(new PlaneOrderDetailPsgAdapter(this, getPsgs()));
    }

    private List<PlaneOrderDetailBean.PassengersBean> getPsgs() {
        List<PlaneOrderDetailBean.PassengersBean> list = new ArrayList<>();
        for (int i = 0; i < oldBean.getPassengers().size(); i++) {
            List<PlaneOrderDetailBean.PassengersBean> passengers = oldBean.getPassengers();
            if (passengers.get(i).isChecked()) {
                list.add(passengers.get(i));
            }
        }
        return list;
    }

    @OnClick(R.id.plane_alter_confirm_commit)
    public void onSureClick() {
        DialogUtil.showDialog(this, "提示", "确定", "取消", "确定改签吗？", new MyDialog.OnButtonClickListener() {
            @Override
            public void onLeftClick() {
                getPolicy();
            }

            @Override
            public void onRightClick() {

            }
        });

    }

    private void getPolicy() {
        RetrofitUtil.getPlanePolicy(this, MUtils.getRequestStringByPsg(getPsgs()), null,
                new RetrofitUtil.OnResponse() {
                    @Override
                    public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                        if (status == 200) {
                            if (bean.getData().length() != 3) {
                                MyApplication.mPlanePolicy = new Gson().fromJson(bean.getData(), PlanePolicyBean.class);
                            } else {
                                MyApplication.mPlanePolicy = null;
                            }
                            checkIsWei();
                        }
                        return false;
                    }

                    @Override
                    public boolean onFailed(Throwable e) {
                        return false;
                    }
                });
    }

    private void checkIsWei() {
        int flag = MUtils.getWeibeiFlag(mBean, cangwei);
        if (flag != -5) {
            isWei = flag != 0;
            create();
        } else {
            MUtils.checkNHour(this, mBean.getOrgcode(), mBean.getArricode(), mBean, cangwei, new PPlaneBook.OnCheckResult() {
                @Override
                public void onGotResultWei() {
                    isWei = true;
                }

                @Override
                public void onGotResultNotWei() {
                    isWei = false;
                }

                @Override
                public void onNext() {
                    create();
                }
            });
        }
    }

    private void create() {
        RequestCreatePlaneAlterBean rcpa = new RequestCreatePlaneAlterBean();
        rcpa.setPassids(getPsgIds());
        rcpa.setOrderfrom("3");
        rcpa.setWeibeiflag(isWei ? "1" : "0");
        rcpa.setWbreason(MUtils.getPlanePolicyString(MyApplication.mPlanePolicy));
        rcpa.setEmpid(String.valueOf(MyApplication.mUserInfoBean.getId()));
        rcpa.setCid(String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        rcpa.setRouteids(getRouteId());
        rcpa.setOrderno(oldBean.getOrderno());
        rcpa.setRoute(getRoute());

        RetrofitUtil.createPlaneAlter(this, AppUtils.getJson(rcpa), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(final ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    PlaneReturnApplyActivity.mBean = null;

                    MyDialog dialog = DialogUtil.showDialog(PlaneAlterConfirmActivity.this, "提示", "确定", "", "提交成功", new MyDialog.OnButtonClickListener() {
                        @Override
                        public void onLeftClick() {
                            jumpToOrderList(Constant.PLANE);
                            Intent intent = new Intent(PlaneAlterConfirmActivity.this, PlaneAlterDetailActivity.class);
                            intent.putExtra("orderNo", bean.getData());
                            startActivity(intent);
                        }

                        @Override
                        public void onRightClick() {

                        }
                    });
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            return keyCode == KeyEvent.KEYCODE_BACK;
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

    private List<RequestCreatePlaneOrder.Route> getRoute() {
        List<RequestCreatePlaneOrder.Route> list = new ArrayList<>();

        PlaneListBean.CangweisBean cangweisBean1 = mBean.getCangweis().get(cangwei);
        RequestCreatePlaneOrder.Route route = new RequestCreatePlaneOrder.Route();
        route.setOrgcitycode(MyApplication.fromCityCode);
        route.setOrgcityname(MyApplication.fromCityName);
        route.setDstcitycode(MyApplication.toCityCode);
        route.setDstcityname(MyApplication.toCityName);

        route.setAirline(mBean.getAirline());
        route.setCode(cangweisBean1.getCode());
        route.setCodeDes(cangweisBean1.getCodeDes());
        route.setWorktime(cangweisBean1.getWorktime());
        route.setTpafterfee(cangweisBean1.getTpafterfee());
        route.setTpbeforefee(cangweisBean1.getTpbeforefee());
        route.setGqbeforefee(cangweisBean1.getGqbeforefee());
        route.setGqafterfee(cangweisBean1.getGqafterfee());
        route.setIncludeflage(cangweisBean1.getIncludeflage());


        route.setDstcode(mBean.getArricode());
        route.setOrgcode(mBean.getOrgcode());
        route.setOrgname(mBean.getOrgname());
        route.setPrice(cangweisBean1.getPrice() * 1d);
        route.setXuhao("0");
        route.setCarriecode(mBean.getCarriecode());
        route.setCarriername(mBean.getCarriername());
        route.setPlanestyle(mBean.getPlanestyle());
        route.setArriname(mBean.getArriname());
        route.setArricode(mBean.getArricode());
        route.setArriterm(mBean.getArriterm());
        route.setDeptterm(mBean.getDeptterm());
        route.setDepttime(mBean.getDepttime());
        route.setDeptdate(mBean.getDeptdate());
        route.setArridate(mBean.getArridate());
        route.setArritime(mBean.getArritime());
        route.setMealcode(mBean.getMealcode());
        route.setCangwei(cangweisBean1.getCode());
        route.setStopnumber(mBean.getStopnumber());
        route.setDiscount(cangweisBean1.getDiscount());
        route.setDisdes(cangweisBean1.getDisdes());
        route.setFlytime(mBean.getFlytime());
        route.setYprice(0);// TODO: 2017/1/7 Y仓价格，到时候直接取
        route.setRewardprice(cangweisBean1.getRewardprice());
        route.setRefundrule(cangweisBean1.getRefundrule());
        route.setChangerule(cangweisBean1.getChangerule());
        route.setSignrule(cangweisBean1.getSignrule());
        route.setFueltax(mBean.getFueltax());
        route.setAirporttax(mBean.getAirporttax());

        list.add(route);


        return list;
    }

    private List<String> getRouteId() {
        List<String> list = new ArrayList<>();
        list.add(String.valueOf(oldBean.getRoutes().get(0).getId()));
        return list;
    }

    private List<String> getPsgIds() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < oldBean.getPassengers().size(); i++) {
            List<PlaneOrderDetailBean.PassengersBean> passengers = oldBean.getPassengers();
            if (passengers.get(i).isChecked()) {
                list.add(String.valueOf(passengers.get(i).getId()));
            }
        }
        return list;
    }
}
