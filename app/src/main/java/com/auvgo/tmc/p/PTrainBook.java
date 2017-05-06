package com.auvgo.tmc.p;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.adapter.PsgListAdapter;
import com.auvgo.tmc.adapter.TimeAdapter;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.common.AddLsPsgActivity;
import com.auvgo.tmc.common.ApplyNoChoseActivity;
import com.auvgo.tmc.common.PassengerListActivity;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.train.activity.NoticeActivity;
import com.auvgo.tmc.train.activity.WaitingActivity;
import com.auvgo.tmc.train.bean.OrderNoBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.TrainBean;
import com.auvgo.tmc.train.bean.TrainPolicyBean;
import com.auvgo.tmc.train.bean.TrainTimeBean;
import com.auvgo.tmc.train.bean.UserBean;
import com.auvgo.tmc.train.bean.requestbean.RequestCreateTrainOrderBean;
import com.auvgo.tmc.train.interfaces.ViewManager_trainbook;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.LogFactory;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.MyDialog;
import com.google.gson.Gson;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by lc on 2016/11/17
 */

public class PTrainBook extends BaseP {

    private TrainBean.DBean dBean;//车次的实例
    private int seattypeposition;//座次的位置
    private int booktypeposition;//预订方式的标记 暂时没有 送票上门 0行旅 1 12306
    private String fromCode, toCode;//出发城市，到达城市的编码
    private List<UserBean> psgList;//已经选中的乘客
    private String accountName = "";
    private String accountPwd = "";
    private ViewManager_trainbook vm;
    private PsgListAdapter adapter;
    private String price;
    private String payType;

    private float peisongFei;

    public PTrainBook(Context context, ViewManager_trainbook vm) {
        super(context);
        this.vm = vm;
    }


    public void initDate(Bundle bundle) {
        dBean = (TrainBean.DBean) bundle.getSerializable("bean");
        seattypeposition = bundle.getInt("seattypeposition");
        booktypeposition = bundle.getInt("booktypeposition");
        fromCode = bundle.getString("from");
        toCode = bundle.getString("to");
        String accountName = bundle.getString("accountName");
        String accountPsw = bundle.getString("accountPsw");
        this.accountName = accountName == null ? "" : accountName;
        this.accountPwd = accountPsw == null ? "" : accountPsw;
        price = getdBean().getCanBook().get(getSeattypeposition()).get(2);
        psgList = new ArrayList<>();
        psgList.addAll(MyApplication.getApplication().selectedPsgs);
        adapter = new PsgListAdapter(context, psgList, new PsgListAdapter.OnPsgChangeListener() {
            @Override
            public void onPsgChange() {
                vm.caculatePrice();
            }
        });
        String fukuankemu = MUtils.getFukuankemu();
        payType = fukuankemu.equals("3") ? "2" : fukuankemu;
    }

    public PsgListAdapter getAdapter() {
        return adapter;
    }

    public float getPeisongFei() {
        return peisongFei;
    }

    public void setAdapter(PsgListAdapter adapter) {
        this.adapter = adapter;
    }

    public void getTrainTime() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("qd", TimeUtils.changePattern(dBean.getTrain_start_date()));
        map.put("fc", dBean.getFrom_station_code());
        map.put("tc", dBean.getTo_station_code());
        map.put("tn", dBean.getTrain_no());
        RetrofitUtil.getTrainTimeList(context, AppUtils.getJson(map), TrainTimeBean.class,
                new RetrofitUtil.OnResponse() {
                    @Override
                    public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                        if (status == 200) {
                            TrainTimeBean ttb = (TrainTimeBean) o;
                            TimeAdapter ta = new TimeAdapter(context, ttb.getData(),
                                    dBean.getFrom_station_name(), dBean.getTo_station_name());
                            DialogUtil.showTimeDialog(context, ta);
                        }
                        return false;
                    }

                    @Override
                    public boolean onFailed(Throwable e) {
                        return false;
                    }
                });
    }

    public void getPolicy() {
        RetrofitUtil.getPolicy(context, MUtils.getRequestStringByPsg(psgList), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    if (bean.getData().length() != 3) {
                        Gson gson = new Gson();
                        TrainPolicyBean rpb = gson.fromJson(bean.getData(), TrainPolicyBean.class);
                        MyApplication.mTrainPolicy = rpb;
                    }
                    boolean b = MUtils.isCanbook(getdBean().getCanBook().get(seattypeposition).get(3),
                            getdBean().getTrain_code());
                    if (b) {
                        if (MUtils.isSeatWei(getdBean().getCanBook().get(seattypeposition).get(3))) {
                            DialogUtil.showDialog(context, "违背提示", "取消", "继续", "您违背了" +
                                    MUtils.getWeibeiItemByTrainCode(getdBean().getTrain_code().substring(0, 1))
                                    + "的标准，请问继续预订吗", new MyDialog.OnButtonClickListener() {
                                @Override
                                public void onLeftClick() {

                                }

                                @Override
                                public void onRightClick() {
                                    create();
                                }
                            });
                        } else {
                            create();
                        }
                    } else {
                        DialogUtil.showDialog(context, "违背提示", "取消", "", "不允许预订该违背车次", null);
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

    private boolean isSeatWei(String s) {
        if (MyApplication.mTrainPolicy == null) return false;
        //  判断传入的s是否在规则给定的坐席里面，在false，不在true
        if (MyApplication.mTrainPolicy.getDonche().contains(s)) {
            return false;
        }
        if (MyApplication.mTrainPolicy.getGaotie().contains(s)) {
            return false;
        }
        if (MyApplication.mTrainPolicy.getPukuai().contains(s)) {
            return false;
        }

        return true;
    }

    public void createOrder() {
        if (MUtils.checkIdDulpicated(psgList)) {
            ToastUtils.showTextToast("身份证号码不能相同！");
            return;
        } else if (vm.getContact().isEmpty()) {
            ToastUtils.showTextToast("请填写联系人");
            return;
        } else if (vm.getMobile().isEmpty()) {
            ToastUtils.showTextToast("请填写手机号");
            return;
        } else if (psgList.size() < 1) {
            ToastUtils.showTextToast("请选择出行人员");
            return;
        } else if (vm.getApplyNo().isEmpty() && ((BaseActivity) context).getApplyNoSettingCode().equals("1")) {
            ToastUtils.showTextToast("请输入出差单号");
            return;
        }
        getPolicy();
    }


    private void create() {
        boolean isWei = isSeatWei(getdBean().getCanBook().get(getSeattypeposition()).get(3));
        UserBean userInfoBean = MyApplication.mUserInfoBean;
        RequestCreateTrainOrderBean orderBean = new RequestCreateTrainOrderBean();
        orderBean.setArriveStation(dBean.getTo_station_name());
        orderBean.setArriveStationCode(dBean.getTo_station_code());
        orderBean.setArriveTime(dBean.getArrive_time());
        orderBean.setBookUserId(Long.valueOf(userInfoBean.getId()));
        orderBean.setBookUserName(userInfoBean.getName());
        orderBean.setCompanyid(Long.valueOf(userInfoBean.getCompanyid()));
        orderBean.setChailvitem("");//差旅事项
        orderBean.setCostid(0l);//成本中心
        orderBean.setCostname("");//成本中心名称
        orderBean.setCosttime(Integer.parseInt(dBean.getRun_time_minute()));
        orderBean.setFromStation(dBean.getFrom_station_name());
        orderBean.setFromStationCode(dBean.getFrom_station_code());
        orderBean.setFromcitycode(MyApplication.fromCityCode);
        orderBean.setArrivecitycode(MyApplication.toCityCode);
        orderBean.setArrivecityname(MyApplication.toCityName);
        orderBean.setFromcityname(MyApplication.fromCityName);
        orderBean.setFromTime(dBean.getStart_time());
//        orderBean.setLinkAddress(vm.getAddr());
        orderBean.setLinkEmail(vm.getEmail());
        orderBean.setLinkName(vm.getContact());
        orderBean.setLinkPhone(vm.getMobile());
        orderBean.setOrderFrom(3);
        orderBean.setOrderLevel("0");
        orderBean.setProid(0l);
        orderBean.setPayType(payType);
        orderBean.setProname("");
        orderBean.setRunTime(dBean.getRun_time());
        orderBean.setShenqingno(vm.getApplyNo());
        orderBean.setTotalprice(Double.valueOf(getTotalPrice(MyApplication.mComSettingBean.getFuwufei().getTrainapp())));
        orderBean.setTrainCode(dBean.getTrain_code());
        orderBean.setTrainNo(dBean.getTrain_no());
        orderBean.setTravelTime(TimeUtils.changePattern(dBean.getTrain_start_date()));
        orderBean.setArriveDays(dBean.getArrive_days());
        if (isWei) orderBean.setWeibeiflag(1);
        orderBean.setBookpolicy(MUtils.getWeibeiItemByTrainCode(dBean.getTrain_code().substring(0, 1)));
        List<RequestCreateTrainOrderBean.Order_psgBean> list = new ArrayList<>();
        for (int i = 0; i < psgList.size(); i++) {
            RequestCreateTrainOrderBean.Order_psgBean orderPsg = new RequestCreateTrainOrderBean.Order_psgBean();
            UserBean psg = psgList.get(i);
            orderPsg.setTotalprice(Double.valueOf(calculatePrice()) / psgList.size());//每个人需要支付的总价  包括配送费、服务费、票价
            orderPsg.setAccountName(accountName);
            orderPsg.setAccountPwd(accountPwd);
            orderPsg.setAmount(Double.valueOf(price));//票价
            orderPsg.setBxPayMoney(0d);//保险费
//            orderPsg.setTrainpeison(booktypeposition == 0 ? MyApplication.mComSettingBean.getFuwufei().getTrainpeison() : 0);
            orderPsg.setDeptid(Long.valueOf(psg.getDeptid()));
            orderPsg.setDeptname(psg.getDeptname());
            String ct = psg.getCerttype();
            orderPsg.setIdsType(ct.equals("NI") ? "1" : ct);
            orderPsg.setSeatCode(dBean.getCanBook().get(seattypeposition).get(3));
            orderPsg.setSeatType(dBean.getCanBook().get(seattypeposition).get(0));
            orderPsg.setSort(i);
            orderPsg.setTicketType(1);
            orderPsg.setUserId(String.valueOf(psg.getId()));
            orderPsg.setUserIds(String.valueOf(psg.getCertno()));
            orderPsg.setUserName(psg.getName());
            orderPsg.setUserPhone(psg.getMobile());
            list.add(orderPsg);
        }
        orderBean.setUsers(list);
        String json = AppUtils.getJson(orderBean);
        LogFactory.d("------------------------" + json);
        RetrofitUtil.createOrder(context, json, OrderNoBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    Intent intent = new Intent(context, WaitingActivity.class);
                    OrderNoBean b = (OrderNoBean) o;
                    intent.putExtra("flag", 1);
                    intent.putExtra("orderNo", b.getOrderNo().get(0));
                    context.startActivity(intent);
                    Activity a = (Activity) context;
                    a.finish();
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {

                return false;
            }
        });
    }

    public String getTotalPrice(int serFee) {
        float p = Float.parseFloat(price);
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(p * getPsgList().size() + serFee * psgList.size());
    }

    public void showPriceDialog(View cover, int h) {
        int serFee = MyApplication.mComSettingBean.getFuwufei().getTrainapp();//服务费
        int size = psgList.size();
        String ticketFee = price + "x" + size;//票价
        String totalFee = AppUtils.keepNSecimal(String.valueOf(Float.parseFloat(price) * size), 1);
//        String peisongfei = booktypeposition == 0 ? peisongFei + "x" + size : "";
        String peisongFeiTotal = AppUtils.keepNSecimal(peisongFei * size + "", 1);

        DialogUtil.showPriceDialog(context, h + 1, serFee + "x" + size,
                AppUtils.keepNSecimal(String.valueOf(serFee * size), 1), "", peisongFeiTotal
                , ticketFee, totalFee, null);
    }

    public String calculatePrice() {
        // TODO: 2016/12/14 计算价格  价格 = （票价+服务费+配送费）*张数
        return AppUtils.keepNSecimal(
                String.valueOf(
                        (Float.parseFloat(dBean.getCanBook().get(getSeattypeposition()).get(2))
                                + MyApplication.mComSettingBean.getFuwufei().getTrainapp()
                                + getPeisongFei()
                        ) * getPsgList().size())
                , 1);
    }

    public void back() {
        DialogUtil.showDialog(context, "提示", "取消", "确定", "订单还未完成，您确定要离开吗？",
                new MyDialog.OnButtonClickListener() {
                    @Override
                    public void onLeftClick() {
                    }

                    @Override
                    public void onRightClick() {
                        Activity a = (Activity) context;
                        a.finish();
                    }
                });
    }


    public int getSeattypeposition() {
        return seattypeposition;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<UserBean> getPsgList() {
        return psgList;
    }

    public void setPsgList(List<UserBean> psgs) {
        ArrayList<UserBean> ar = new ArrayList<>();
        for (int i = 0; i < psgList.size(); i++) {
            UserBean userBean = psgList.get(i);
            if (userBean != null && userBean.getId() == 0) {
                ar.add(userBean);
            }
        }
        psgList.clear();
        psgList.addAll(psgs);
        psgList.addAll(ar);
        adapter.notifyDataSetChanged();
    }

    public void setSeattypeposition(int seattypeposition) {
        this.seattypeposition = seattypeposition;
    }

    public int getBooktypeposition() {
        return booktypeposition;
    }

    public void setBooktypeposition(int booktypeposition) {
        this.booktypeposition = booktypeposition;
    }

    public TrainBean.DBean getdBean() {
        return dBean;
    }

    public void setdBean(TrainBean.DBean dBean) {
        this.dBean = dBean;
    }

    public String getFromCode() {
        return fromCode;
    }

    public void setFromCode(String fromCode) {
        this.fromCode = fromCode;
    }

    public String getToCode() {
        return toCode;
    }

    public void setToCode(String toCode) {
        this.toCode = toCode;
    }

    public void jumpToAddLsPsg() {
        Intent intent = new Intent(context, AddLsPsgActivity.class);
        ((Activity) context).startActivityForResult(intent, Constant.ACTIVITY_TRAIN_BOOK_FLAG);
    }

    public void addPsg(UserBean ub) {
        psgList.add(ub);
    }

    public void jumpToAddPsg() {
        Intent intent = new Intent(context, PassengerListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("psgs", getPsgs());
        bundle.putString("from", Constant.TRAIN);
        intent.putExtra("bundle", bundle);
        ((Activity) context).startActivityForResult(intent, Constant.ACTIVITY_TRAIN_BOOK_FLAG);
    }

    private Serializable getPsgs() {
        ArrayList<UserBean> ubs = new ArrayList<>();
        for (int i = 0; i < psgList.size(); i++) {
            UserBean ub = psgList.get(i);
            if (ub != null && ub.getId() != 0) {
                ubs.add(ub);
            }
        }
        return ubs;
    }

    public void jumpNotice() {
        Intent intent = new Intent(context, NoticeActivity.class);
        context.startActivity(intent);
    }

    public void jumpToApplyNo() {
        Intent intent = new Intent(context, ApplyNoChoseActivity.class);
        intent.putExtra("fromdate", TimeUtils.changePattern(getdBean().getTrain_start_date()));
        ((Activity) context).startActivityForResult(intent, Constant.ACTIVITY_TRAIN_BOOK_FLAG);
    }

    public void setPayTypeByRadio(int i) {
        payType = i == 0 ? "2" : "1";
    }
}
