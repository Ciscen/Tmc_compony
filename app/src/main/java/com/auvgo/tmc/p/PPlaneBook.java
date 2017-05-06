package com.auvgo.tmc.p;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.PsgListAdapter;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.common.AddLsPsgActivity;
import com.auvgo.tmc.common.ApplyNoChoseActivity;
import com.auvgo.tmc.common.PassengerListActivity;
import com.auvgo.tmc.common.PeisongListActivity;
import com.auvgo.tmc.plane.activity.PlaneBookActivity;
import com.auvgo.tmc.plane.activity.PlaneSendActivity;
import com.auvgo.tmc.plane.bean.InsuranceBean;
import com.auvgo.tmc.plane.bean.PlaneListBean;
import com.auvgo.tmc.plane.bean.PlaneOrderDetailBean;
import com.auvgo.tmc.plane.bean.PlanePolicyBean;
import com.auvgo.tmc.plane.bean.PlaneRouteBeanWF;
import com.auvgo.tmc.plane.bean.RequestCreatePlaneOrder;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.UserBean;
import com.auvgo.tmc.train.interfaces.ViewManager_planebook;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.MyDialog;
import com.auvgo.tmc.views.MyPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.auvgo.tmc.constants.Constant.ApproveStatus.APPROVE_STATUS_DAISONGSHEN;

/**
 * Created by lc on 2016/12/27
 */

public class PPlaneBook extends BaseP {
    private ViewManager_planebook vm;
    private PlaneRouteBeanWF firstRoute;
    private PlaneRouteBeanWF secondRoute;
    private List<UserBean> psgList;
    private int baoxian;//保险价格
    private String bxCode = "";
    private PsgListAdapter adapter;
    private List<InsuranceBean> insurances;//存放保险种类
    private int mPosition;//保险的位置
    private String bxName;//保险的代码
    private boolean isWei1;//是否是违背单
    private boolean isWei2;//是否是违背单
    private double totalPrice;//总价
    private String payType;


    public PPlaneBook(Context context, ViewManager_planebook vm) {
        super(context);
        this.vm = vm;
    }

    public void init(Intent intent) {
        firstRoute = (PlaneRouteBeanWF) intent.getSerializableExtra("firstRoute");
        secondRoute = (PlaneRouteBeanWF) intent.getSerializableExtra("secondRoute");
        psgList = new ArrayList<>();
        psgList.addAll(MyApplication.getApplication().selectedPsgs);
        insurances = new ArrayList<>();
        insurances.add(new InsuranceBean("不选择"));
//        insurances.add(new SelectionBean("太平洋-君安行1天80万", ""));
//        insurances.add(new SelectionBean("泰康-7天60万-航意险", ""));
        adapter = new PsgListAdapter(context, psgList, new PsgListAdapter.OnPsgChangeListener() {
            @Override
            public void onPsgChange() {
                caculatePrice();
            }
        });
        String fukuankemu = MUtils.getFukuankemu();
        payType = fukuankemu.equals("3") ? "2" : fukuankemu;
    }


    public void caculatePrice() {
        PlaneListBean firstRouteBean = firstRoute.getBean();
        //票价
        double piaojia = firstRouteBean.getCangweis().get(firstRoute.getCangwei()).getPrice();
        //服务费
        double fuwufei = MyApplication.mComSettingBean.getFuwufei().getGnapp();
        //机建费、税费
        int jijianfei = firstRouteBean.getAirporttax() + firstRouteBean.getFueltax();
        //保险费
        int baoxianfei = this.baoxian;
        //乘客人数
        int psgNum = psgList.size();
        if (secondRoute != null) {
            PlaneListBean bean = secondRoute.getBean();

            piaojia += bean.getCangweis().get(secondRoute.getCangwei()).getPrice();
            fuwufei *= 2;
            baoxianfei *= 2;
            jijianfei *= 2;
        }
        totalPrice = (piaojia + fuwufei + jijianfei + baoxianfei) * psgNum;
        vm.setTotalPrice(totalPrice);
    }

    public void showPriceDetail() {
        DialogUtil.showPlanePriceDetailPop(context, firstRoute, secondRoute, baoxian, psgList.size());
    }

    public PsgListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(PsgListAdapter adapter) {
        this.adapter = adapter;
    }

    public PlaneRouteBeanWF getFirstRoute() {
        return firstRoute;
    }


    public void setFirstRoute(PlaneRouteBeanWF firstRoute) {
        this.firstRoute = firstRoute;
    }

    public PlaneRouteBeanWF getSecondRoute() {
        return secondRoute;
    }

    public void setSecondRoute(PlaneRouteBeanWF secondRoute) {
        this.secondRoute = secondRoute;
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

    public void showEnsuranceDialog() {
        if (insurances.size() == 1) {
            HashMap<String, String> map = new HashMap<>();
            map.put("cid", String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
            RetrofitUtil.getInsurance(context, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
                @Override
                public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                    if (status == 200) {
                        Type type = new TypeToken<List<InsuranceBean>>() {
                        }.getType();
                        Gson gson = new Gson();
                        List<InsuranceBean> list = gson.fromJson(bean.getData(), type);
                        initSelectionBeans(list);
                        showDialog();
                    }
                    return false;
                }

                @Override
                public boolean onFailed(Throwable e) {
                    return false;
                }
            });
        } else {
            showDialog();
        }

    }

    private void showDialog() {
        DialogUtil.showExpandablePickDialog(context, "选择保险信息", mPosition, insurances,
                new MyPickerView.OnPickerViewSure() {
                    @Override
                    public void onSingleSureClick(int p) {
                        mPosition = p;
                        baoxian = insurances.get(p).getSalePrice();
                        bxCode = insurances.get(p).getCode();
                        bxName = insurances.get(p).getName();
                        vm.setEnsurance(p, insurances.get(p).getName());
                        caculatePrice();
                    }

                    @Override
                    public void onMultiSureClick(List<Integer> s) {

                    }
                });
    }

    private void initSelectionBeans(List<InsuranceBean> list) {
        for (int i = 0; i < list.size(); i++) {
            InsuranceBean insuranceBean = list.get(i);
            insurances.add(new InsuranceBean(insuranceBean.getName(), insuranceBean.getCode(), insuranceBean.getSalePrice()));
        }
    }

    public void createOrder() {
        if (MUtils.checkIdDulpicated(psgList)) {
            ToastUtils.showTextToast("身份证号码不能相同");
            return;
        } else if (psgList.size() == 0) {
            ToastUtils.showTextToast("请选择乘机人");
            return;
        } else if (TextUtils.isEmpty(vm.getContact())) {
            ToastUtils.showTextToast("请填写联系人");
            return;
        } else if (TextUtils.isEmpty(vm.getMobile())) {
            ToastUtils.showTextToast("请填写手机号");
            return;
        } else if (psgList.size() < 1) {
            ToastUtils.showTextToast("请选择出行人员");
            return;
        } else if (vm.getApplyNo().isEmpty() && ((BaseActivity) context).getApplyNoSettingCode().equals("1")) {
            ToastUtils.showTextToast("请输入出差单号");
            return;
        } else if (vm.getPeisong().isEmpty() && ((BaseActivity) context).getPeiSongAddrSettingCode().equals("1")) {
            ToastUtils.showTextToast("请输入配送地址");
            return;
        }
        getPolicy(psgList);
    }

    private void getPolicy(List<UserBean> psgList) {
        RetrofitUtil.getPlanePolicy(context, MUtils.getRequestStringByPsg(psgList), null,
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
        int flag1 = MUtils.getWeibeiFlag(firstRoute.getBean(), firstRoute.getCangwei());
//        if (flag1 != -4 || flag2 != -4) {
//            isWei1 = flag1 != 0 || flag2 != 0;
//            create();
//        } else {
//            checkNHour(false, firstRoute, new RetrofitUtil.OnResponse() {
//                @Override
//                public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
//                    if (status == 200) {
//                        Type token = new TypeToken<List<PlaneListBean>>() {
//                        }.getType();
//                        List<PlaneListBean> list = new Gson().fromJson(bean.getData(), token);
//                        int savePrice = checkIsLow(list, firstRoute.getBean().getCangweis().get(firstRoute.getCangwei()).getPrice());
//                        if (savePrice > 0) {
//                            isWei1 = true;
//                            create();
//                        } else {
//                            if (secondRoute != null) {
//                                checkNHour(true, secondRoute, new RetrofitUtil.OnResponse() {
//                                    @Override
//                                    public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
//                                        if (status == 200) {
//                                            Type token = new TypeToken<List<PlaneListBean>>() {
//                                            }.getType();
//                                            List<PlaneListBean> list = new Gson().fromJson(bean.getData(), token);
//                                            int savePrice = checkIsLow(list, secondRoute.getBean().getCangweis().get(secondRoute.getCangwei()).getPrice());
//                                            isWei1 = savePrice > 0;
//                                            create();
//                                        }
//                                        return false;
//                                    }
//
//                                    @Override
//                                    public boolean onFailed(Throwable e) {
//                                        return false;
//                                    }
//                                });
//                            } else {
//                                isWei1 = false;
//                                create();
//                            }
//                        }
//                    }
//                    return false;
//                }
//
//                @Override
//                public boolean onFailed(Throwable e) {
//                    return false;
//                }
//            });
//        }
        if (flag1 != -5) {
            isWei1 = flag1 != 0;
            if (secondRoute == null) {
                isWei2 = false;
                create();
            } else


                MUtils.checkNHour(context, MyApplication.toCityCode, MyApplication.fromCityCode, secondRoute.getBean(), secondRoute.getCangwei(), new OnCheckResult() {
                    @Override
                    public void onGotResultWei() {
                        isWei2 = true;
                    }

                    @Override
                    public void onGotResultNotWei() {
                        isWei2 = false;
                    }

                    @Override
                    public void onNext() {
                        create();
                    }
                });

        } else {
            MUtils.checkNHour(context, MyApplication.fromCityCode, MyApplication.toCityCode, firstRoute.getBean(), firstRoute.getCangwei(), new OnCheckResult() {
                @Override
                public void onGotResultWei() {
                    isWei1 = true;
                }

                @Override
                public void onGotResultNotWei() {
                    isWei1 = false;
                }

                @Override
                public void onNext() {
                    if (secondRoute != null) {
                        int flag2 = MUtils.getWeibeiFlag(secondRoute.getBean(), secondRoute.getCangwei());
                        if (flag2 != -5) {
                            isWei2 = flag2 != 0;
                            create();
                        } else {
                            MUtils.checkNHour(context, MyApplication.toCityCode, MyApplication.fromCityCode, secondRoute.getBean(), secondRoute.getCangwei(), new OnCheckResult() {
                                @Override
                                public void onGotResultWei() {
                                    isWei2 = true;
                                }

                                @Override
                                public void onGotResultNotWei() {
                                    isWei2 = false;
                                }

                                @Override
                                public void onNext() {
                                    create();
                                }
                            });
                        }
                    } else {
                        create();
                    }
                }
            });
        }
    }

    private void create() {
        RequestCreatePlaneOrder rcp = new RequestCreatePlaneOrder();
        rcp.setCompanyid(MyApplication.mUserInfoBean.getCompanyid());
        rcp.setChuchaitype(0);
        rcp.setOrdertype(secondRoute == null ? "ow" : "rt");
        rcp.setOrderLevel(baoxian == 0 ? "0" : "1");//订单级别
        rcp.setOrderFrom(3);
        rcp.setLinkAddress(vm.getPeisong());
        rcp.setLinkName(vm.getContact());
        rcp.setLinkEmail(vm.getEmail());
        rcp.setLinkPhone(vm.getMobile());
        rcp.setCostname("");
        rcp.setCostid(0);
        rcp.setProname("");
        rcp.setProid(0);
        rcp.setShenqingno(vm.getApplyNo());
        rcp.setChailvitem("");
        rcp.setPayType(payType);
        rcp.setTotalprice(totalPrice);
        rcp.setBookUserId(MyApplication.mUserInfoBean.getId());
        rcp.setBookUserName(MyApplication.mUserInfoBean.getName());
        rcp.setBookpolicy(MUtils.getPlanePolicyString(MyApplication.mPlanePolicy));//由于在外层，不能具体到每一程，所以不可以设置为违背的政策
        boolean b;
        if (secondRoute == null) {
            b = isWei1;
        } else {
            b = isWei1 || isWei2;
        }

        rcp.setWeibeiflag(b ? 1 : 0);
        rcp.setTickettype(0);
        rcp.setUsers(getUsers());
        rcp.setRoutes(getRoute());
        RetrofitUtil.createPlaneOrder(context, AppUtils.getJson(rcp), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    List<String> result = new Gson().fromJson(bean.getData(), new TypeToken<List<String>>() {
                    }.getType());
                    if (result.size() == 1) {//单程
                        getDetailData(result.get(0));
                    } else {//往返
                        Dialog dialog = DialogUtil.showDialog(context, "提示", "确定", "", "您所下的订单被拆分成两个订单", new MyDialog.OnButtonClickListener() {
                            @Override
                            public void onLeftClick() {
                                PlaneBookActivity a = (PlaneBookActivity) context;
                                a.jumpToOrderList(Constant.PLANE);
                            }

                            @Override
                            public void onRightClick() {
                            }
                        });
                        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                            @Override
                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                return keyCode == KeyEvent.KEYCODE_BACK;
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

    private List<RequestCreatePlaneOrder.Route> getRoute() {
        List<RequestCreatePlaneOrder.Route> list = new ArrayList<>();

        PlaneListBean firstRouteBean = firstRoute.getBean();
        int cangwei = firstRoute.getCangwei();
        PlaneListBean.CangweisBean cangweisBean1 = null;
        if (firstRouteBean.getCangweis() == null) {
            cangweisBean1 = firstRouteBean.getLow();
        } else {
            cangweisBean1 = firstRouteBean.getCangweis().get(cangwei);
        }
        RequestCreatePlaneOrder.Route route1 = new RequestCreatePlaneOrder.Route();
        route1.setOrgcitycode(MyApplication.fromCityCode);
        route1.setOrgcityname(MyApplication.fromCityName);
        route1.setDstcitycode(MyApplication.toCityCode);
        route1.setDstcityname(MyApplication.toCityName);

        route1.setAirline(firstRouteBean.getAirline());
        route1.setCode(cangweisBean1.getCode());
        route1.setDstcode(firstRouteBean.getArricode());
        route1.setOrgcode(firstRouteBean.getOrgcode());
        route1.setOrgname(firstRouteBean.getOrgname());
        route1.setPrice(cangweisBean1.getPrice() * 1d);
        route1.setXuhao("0");
        route1.setCarriecode(firstRouteBean.getCarriecode());
        route1.setCarriername(firstRouteBean.getCarriername());
        route1.setPlanestyle(firstRouteBean.getPlanestyle());
        route1.setArriname(firstRouteBean.getArriname());
        route1.setArricode(firstRouteBean.getArricode());
        route1.setArriterm(firstRouteBean.getArriterm());
        route1.setDeptterm(firstRouteBean.getDeptterm());
        route1.setDepttime(firstRouteBean.getDepttime());
        route1.setDeptdate(firstRouteBean.getDeptdate());
        route1.setArridate(firstRouteBean.getArridate());
        route1.setArritime(firstRouteBean.getArritime());

        route1.setWeibeiflag(isWei1 ? 1 : 0);
        StringBuilder sb = new StringBuilder();
        MUtils.isCabinWei(firstRouteBean, sb);
//        route1.setWbreason(sb.toString());
        route1.setPricefrom(cangweisBean1.getPfrom());
        route1.setMealcode(firstRouteBean.getMealcode());
        route1.setCangwei(cangweisBean1.getCode());
        route1.setStopnumber(firstRouteBean.getStopnumber());
        route1.setDiscount(cangweisBean1.getDiscount());
        route1.setDisdes(cangweisBean1.getDisdes());
        route1.setFlytime(firstRouteBean.getFlytime());
        route1.setYprice(0);// TODO: 2017/1/7 Y仓价格，到时候直接取
        route1.setRewardprice(cangweisBean1.getRewardprice());
        route1.setRefundrule(cangweisBean1.getRefundrule());
        route1.setChangerule(cangweisBean1.getChangerule());
        route1.setSignrule(cangweisBean1.getSignrule());
        route1.setFueltax(firstRouteBean.getFueltax());
        route1.setAirporttax(firstRouteBean.getAirporttax());

        list.add(route1);

        if (secondRoute != null) {
            PlaneListBean secondRouteBean = secondRoute.getBean();

            PlaneListBean.CangweisBean cangweisBean2 = null;
            if (firstRouteBean.getCangweis() == null) {
                cangweisBean2 = secondRouteBean.getLow();
            } else {
                cangweisBean2 = secondRouteBean.getCangweis().get(secondRoute.getCangwei());
            }

            RequestCreatePlaneOrder.Route route2 = new RequestCreatePlaneOrder.Route();
            route2.setOrgcitycode(MyApplication.toCityCode);
            route2.setOrgcityname(MyApplication.toCityName);
            route2.setDstcitycode(MyApplication.fromCityCode);
            route2.setDstcityname(MyApplication.toCityName);
            route2.setAirline(secondRouteBean.getAirline());
            route2.setCode(cangweisBean2.getCode());
            route2.setDstcode(secondRouteBean.getArricode());
            route2.setOrgcode(secondRouteBean.getOrgcode());
            route2.setOrgname(secondRouteBean.getOrgname());
            route2.setPrice(cangweisBean2.getPrice() * 1d);
            route2.setXuhao("1");
            route2.setCarriecode(secondRouteBean.getCarriecode());
            route2.setWeibeiflag(isWei2 ? 1 : 0);
            StringBuilder sb2 = new StringBuilder();
            MUtils.isCabinWei(secondRouteBean, sb2);
//            route2.setWbreason(sb.toString());
            route2.setPricefrom(cangweisBean2.getPfrom());
            route2.setCarriername(secondRouteBean.getCarriername());
            route2.setPlanestyle(secondRouteBean.getPlanestyle());
            route2.setArriname(secondRouteBean.getArriname());
            route2.setArricode(secondRouteBean.getArricode());
            route2.setArriterm(secondRouteBean.getArriterm());
            route2.setDeptterm(secondRouteBean.getDeptterm());
            route2.setDepttime(secondRouteBean.getDepttime());
            route2.setDeptdate(secondRouteBean.getDeptdate());
            route2.setArridate(secondRouteBean.getArridate());
            route2.setArritime(secondRouteBean.getArritime());
            route2.setMealcode(secondRouteBean.getMealcode());
            route2.setCangwei(cangweisBean2.getCode());
            route2.setStopnumber(secondRouteBean.getStopnumber());
            route2.setDiscount(cangweisBean2.getDiscount());
            route2.setDisdes(cangweisBean2.getDisdes());
            route2.setFlytime(secondRouteBean.getFlytime());
            route2.setYprice(0);// TODO: 2017/1/7 Y仓价格，到时候直接取
            route2.setRewardprice(cangweisBean2.getRewardprice());
            route2.setRefundrule(cangweisBean2.getRefundrule());
            route2.setChangerule(cangweisBean2.getChangerule());
            route2.setSignrule(cangweisBean2.getSignrule());
            route2.setFueltax(secondRouteBean.getFueltax());
            route2.setAirporttax(secondRouteBean.getAirporttax());
            list.add(route2);
        }
        return list;
    }

    private void getDetailData(String s) {
        Map<String, String> map = new HashMap<>();
        map.put("orderno", s);
        RetrofitUtil.getPlaneOrderDetail(context, AppUtils.getJson(map), PlaneOrderDetailBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    PlaneOrderDetailBean b = (PlaneOrderDetailBean) o;
                    boolean needApprove = b.getApprovestatus() == APPROVE_STATUS_DAISONGSHEN;
                    Intent intent = new Intent();
                    intent.putExtra("bean", b);
                    if (needApprove) {
                        intent.setClass(context, PlaneSendActivity.class);
                    } else {
                        final BaseActivity ba = (BaseActivity) context;
                        ba.showDialog("取消", "确定", context.getString(R.string.order_committing),
                                new MyDialog.OnButtonClickListener() {
                                    @Override
                                    public void onLeftClick() {
                                    }

                                    @Override
                                    public void onRightClick() {
                                        ba.jumpToOrderList(Constant.PLANE);
                                    }
                                });
                        return false;
                    }
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    private List<RequestCreatePlaneOrder.PsgBean> getUsers() {
        List<RequestCreatePlaneOrder.PsgBean> users = new ArrayList<>();
        for (int i = 0; i < psgList.size(); i++) {
//            //票价
//            int price1 = isReturn ? secondRoute.getBean().getCangweis().get(secondRoute.getCode()).getPrice() : 0;
//            int price2 = firstRoute.getBean().getCangweis().get(firstRoute.getCode()).getPrice();
//            //机建燃油费
//            int jijian1 = firstRoute.getBean().getFueltax() + firstRoute.getBean().getAirporttax();
//            int jijian2 = isReturn ? secondRoute.getBean().getFueltax() + secondRoute.getBean().getAirporttax() : 0;
            //服务费
            double fuwufei = MyApplication.mComSettingBean.getFuwufei().getGnapp();
            UserBean userBean = psgList.get(i);
            RequestCreatePlaneOrder.PsgBean bean = new RequestCreatePlaneOrder.PsgBean();
            bean.setBxCode(bxCode);
            bean.setBxName(bxName);
            bean.setBxPayMoney(baoxian * 1d);
            bean.setCertno(userBean.getCertno());
            bean.setCerttype(userBean.getCerttype());
            bean.setDepname(userBean.getDeptname());
            bean.setDeptid(String.valueOf(userBean.getDeptid()));
            bean.setEmployeeid((long) userBean.getId());
            bean.setFuwufee((double) fuwufei);
            bean.setMobile(userBean.getMobile());
            bean.setName(userBean.getName());
            bean.setPasstype("AD");
            bean.setPeisongfee(0d);
            bean.setZhiwei(userBean.getZhiwei());
//            int p = isReturn ? price2 : price1;
//            int j = isReturn ? jijian1 : jijian2;
//            bean.setPrice((double) p);
//            bean.setTotalprice((double) (p + fuwufei + baoxian + j));
            users.add(bean);
        }
        return users;
    }

    private void setRoutes(List<RequestCreatePlaneOrder.Route> list) {
        PlaneListBean firstRouteBean = firstRoute.getBean();
        int cangwei = firstRoute.getCangwei();
        PlaneListBean.CangweisBean cangweisBean1 = firstRouteBean.getCangweis().get(cangwei);
        RequestCreatePlaneOrder.Route route1 = new RequestCreatePlaneOrder.Route();
        route1.setOrgcitycode(MyApplication.fromCityCode);
        route1.setOrgcityname(MyApplication.fromCityName);
        route1.setDstcitycode(MyApplication.toCityCode);
        route1.setDstcityname(MyApplication.toCityName);

        route1.setAirline(firstRouteBean.getAirline());
        route1.setCode(cangweisBean1.getCode());
        route1.setDstcode(firstRouteBean.getArricode());
        route1.setOrgcode(firstRouteBean.getOrgcode());
        route1.setOrgname(firstRouteBean.getOrgname());
        route1.setPrice(cangweisBean1.getPrice() * 1d);
        route1.setXuhao("0");
        route1.setCarriecode(firstRouteBean.getCarriecode());
        route1.setCarriername(firstRouteBean.getCarriername());
        route1.setPlanestyle(firstRouteBean.getPlanestyle());
        route1.setArriname(firstRouteBean.getArriname());
        route1.setArricode(firstRouteBean.getArricode());
        route1.setArriterm(firstRouteBean.getArriterm());
        route1.setDeptterm(firstRouteBean.getDeptterm());
        route1.setDepttime(firstRouteBean.getDepttime());
        route1.setDeptdate(firstRouteBean.getDeptdate());
        route1.setArridate(firstRouteBean.getArridate());
        route1.setArritime(firstRouteBean.getArritime());
        route1.setMealcode(firstRouteBean.getMealcode());
        route1.setCangwei(cangweisBean1.getCode());
        route1.setStopnumber(firstRouteBean.getStopnumber());
        route1.setDiscount(cangweisBean1.getDiscount());
        route1.setDisdes(cangweisBean1.getDisdes());
        route1.setFlytime(firstRouteBean.getFlytime());
        route1.setYprice(0);// TODO: 2017/1/7 Y仓价格，到时候直接取
        route1.setRewardprice(cangweisBean1.getRewardprice());
        route1.setRefundrule(cangweisBean1.getRefundrule());
        route1.setChangerule(cangweisBean1.getChangerule());
        route1.setSignrule(cangweisBean1.getSignrule());
        route1.setFueltax(firstRouteBean.getFueltax());
        route1.setAirporttax(firstRouteBean.getAirporttax());

        list.add(route1);

        if (secondRoute != null) {
            PlaneListBean secondRouteBean = secondRoute.getBean();
            PlaneListBean.CangweisBean cangweisBean2 = secondRouteBean.getCangweis().get(secondRoute.getCangwei());

            RequestCreatePlaneOrder.Route route2 = new RequestCreatePlaneOrder.Route();
            route2.setOrgcitycode(MyApplication.toCityCode);
            route2.setOrgcityname(MyApplication.toCityName);
            route2.setDstcitycode(MyApplication.fromCityCode);
            route2.setDstcityname(MyApplication.toCityName);
            route2.setAirline(secondRouteBean.getAirline());
            route2.setCode(cangweisBean2.getCode());
            route2.setDstcode(secondRouteBean.getArricode());
            route2.setOrgcode(secondRouteBean.getOrgcode());
            route2.setOrgname(secondRouteBean.getOrgname());
            route2.setPrice(cangweisBean2.getPrice() * 1d);
            route2.setXuhao("1");
            route2.setCarriecode(secondRouteBean.getCarriecode());
            route2.setCarriername(secondRouteBean.getCarriername());
            route2.setPlanestyle(secondRouteBean.getPlanestyle());
            route2.setArriname(secondRouteBean.getArriname());
            route2.setArricode(secondRouteBean.getArricode());
            route2.setArriterm(secondRouteBean.getArriterm());
            route2.setDeptterm(secondRouteBean.getDeptterm());
            route2.setDepttime(secondRouteBean.getDepttime());
            route2.setDeptdate(secondRouteBean.getDeptdate());
            route2.setArridate(secondRouteBean.getArridate());
            route2.setArritime(secondRouteBean.getArritime());
            route2.setMealcode(secondRouteBean.getMealcode());
            route2.setCangwei(cangweisBean2.getCode());
            route2.setStopnumber(secondRouteBean.getStopnumber());
            route2.setDiscount(cangweisBean2.getDiscount());
            route2.setDisdes(cangweisBean2.getDisdes());
            route2.setFlytime(secondRouteBean.getFlytime());
            route2.setYprice(0);// TODO: 2017/1/7 Y仓价格，到时候直接取
            route2.setRewardprice(cangweisBean2.getRewardprice());
            route2.setRefundrule(cangweisBean2.getRefundrule());
            route2.setChangerule(cangweisBean2.getChangerule());
            route2.setSignrule(cangweisBean2.getSignrule());
            route2.setFueltax(secondRouteBean.getFueltax());
            route2.setAirporttax(secondRouteBean.getAirporttax());
            list.add(route2);
        }
    }

    /**
     * 判断是否符合前后n小时的标准。
     *
     * @param isReturn false去程true返程
     * @param response
     */
    private void checkNHour(boolean isReturn, PlaneListBean plb, int cangwei, RetrofitUtil.OnResponse response) {
        Map<String, String> map = new HashMap<>();
        map.put("from", isReturn ? MyApplication.toCityCode : MyApplication.fromCityCode);
        map.put("to", isReturn ? MyApplication.fromCityCode : MyApplication.toCityCode);
        map.put("startdate", plb.getDeptdate());
        map.put("airline", plb.getAirline());
        map.put("hour", String.valueOf(MyApplication.mPlanePolicy.getFlighthour()));
        map.put("price", String.valueOf(plb.getCangweis().get(cangwei).getPrice()));
        RetrofitUtil.getNHourLowest(context, AppUtils.getJson(map), null, response);
    }

    private void checkNHour(boolean isReturn, final PlaneListBean plb, final int cangwei, final OnCheckResult l) {
        Map<String, String> map = new HashMap<>();
        map.put("from", isReturn ? MyApplication.toCityCode : MyApplication.fromCityCode);
        map.put("to", isReturn ? MyApplication.fromCityCode : MyApplication.toCityCode);
        map.put("startdate", plb.getDeptdate());
        map.put("airline", plb.getAirline());
        map.put("hour", String.valueOf(MyApplication.mPlanePolicy.getFlighthour()));
        map.put("price", String.valueOf(plb.getCangweis().get(cangwei).getPrice()));
        RetrofitUtil.getNHourLowest(context, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    Type token = new TypeToken<List<PlaneListBean>>() {
                    }.getType();
                    List<PlaneListBean> list = new Gson().fromJson(bean.getData(), token);
                    int savePrice = checkIsLow(list, plb.getCangweis().get(cangwei).getPrice());
                    if (savePrice > 0) {
                        l.onGotResultWei();
                    } else {
                        l.onGotResultNotWei();
                    }
                    l.onNext();
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    private int checkIsLow(List<PlaneListBean> list, double price) {
        for (int i = 0; i < list.size(); i++) {
            double price1 = list.get(i).getLow().getPrice();
            if (price1 < price) {
                return (int) (price - price1);
            }
        }
        return -1;
    }

    public void jumpToApplyNo() {
        Intent intent = new Intent(context, ApplyNoChoseActivity.class);
        intent.putExtra("fromdate", firstRoute.getBean().getDeptdate());
        intent.putExtra("backdate", secondRoute == null ? "" : secondRoute.getBean().getDeptdate());
        ((Activity) context).startActivityForResult(intent, Constant.ACTIVITY_PLANE_BOOK_FLAG);
    }

    public void jumpToAddLsPsg() {

        Intent intent = new Intent(context, AddLsPsgActivity.class);
        ((Activity) context).startActivityForResult(intent, Constant.ACTIVITY_PLANE_BOOK_FLAG);
    }

    public void addPsg(UserBean userBean) {
        psgList.add(userBean);
    }

    public void jumpToAddPsg() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("psgs", getPsg());
        bundle.putString("from", Constant.PLANE);
        intent.setClass(context, PassengerListActivity.class);
        intent.putExtra("bundle", bundle);
        ((Activity) context).startActivityForResult(intent, Constant.ACTIVITY_PLANE_BOOK_FLAG);
    }

    private Serializable getPsg() {
        ArrayList<UserBean> ubs = new ArrayList<>();
        for (int i = 0; i < psgList.size(); i++) {
            UserBean ub = psgList.get(i);
            if (ub != null && ub.getId() != 0) {
                ubs.add(ub);
            }
        }
        return ubs;
    }

    public void jumpToPeisong() {
        Intent intent = new Intent(context, PeisongListActivity.class);
        ((Activity) context).startActivityForResult(intent, Constant.ACTIVITY_PLANE_BOOK_FLAG);
    }

    public void setPayTypeByRadio(int i) {
        payType = i == 0 ? "2" : "1";
    }


    public interface OnCheckResult {
        void onGotResultWei();

        void onGotResultNotWei();

        void onNext();
    }
}
