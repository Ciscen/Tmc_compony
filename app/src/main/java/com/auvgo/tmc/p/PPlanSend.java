package com.auvgo.tmc.p;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.adapter.ChoseApproveLevelAdapter;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.base.MyList;
import com.auvgo.tmc.home.HomeActivity;
import com.auvgo.tmc.personalcenter.activity.OrderListActivity;
import com.auvgo.tmc.plane.activity.PlaneSendActivity;
import com.auvgo.tmc.plane.activity.PlaneSendDetailActivity;
import com.auvgo.tmc.plane.bean.PlaneOrderDetailBean;
import com.auvgo.tmc.train.bean.ApproveLevelBean;
import com.auvgo.tmc.train.bean.ComSettingBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.WbReasonBean;
import com.auvgo.tmc.train.bean.requestbean.RequestSendApproveBean;
import com.auvgo.tmc.train.interfaces.ViewManager_planesend;
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

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lc on 2016/12/29
 */

public class PPlanSend extends BaseP {
    private ViewManager_planesend vm;
    private String orderNo;
    private List<WbReasonBean> mWbReasons;//请求到的违背原因的的集合
    private int mCurrentApprovePosition = -1;//表示所选的审批规则
    private int mCurrentPosition_weiReason = -1;//违背原因的位置
    private ChoseApproveLevelAdapter adapter;
    private List<ApproveLevelBean> albs;//可选审批级别的实体


    private int costCenterId;
    private int projectId;
    private String costCenterName;
    private String projectName;
    private PlaneOrderDetailBean mBean;
    private double totalPrice;//总价

    public PPlanSend(Context context, ViewManager_planesend vm) {
        super(context);
        this.vm = vm;
    }

    public void init(Intent intent) {
        orderNo = intent.getStringExtra("orderNo");
        mBean = intent.getParcelableExtra("bean");
    }

    public void showPriceDetail() {
        DialogUtil.showPlanePriceDetailPop(context, mBean);
    }

    public void jumpToDetail() {
        Intent intent = new Intent(context, PlaneSendDetailActivity.class);
        intent.putExtra("bean", mBean);
        context.startActivity(intent);
    }

    public void commit() {
        int flag = getConfig();
        switch (flag) {
            case -1:
                ToastUtils.showTextToast("请填写企业审批号");
                break;
            case -2:
                ToastUtils.showTextToast("请选择成本中心");
                break;
            case -3:
                ToastUtils.showTextToast("请选择出差项目");
                break;
            case -4:
                ToastUtils.showTextToast("请填写出差事由");
                break;
            case -5:
                ToastUtils.showTextToast("请选择违背原因");
                break;
            case -6:
                ToastUtils.showTextToast("请选择审批规则");
                break;
        }
    }

    private int getConfig() {
        MyList<ComSettingBean.ProductSetBean.ProconfvalueBean> settings = MyApplication.mComSettingBean.getProductSet().getProconfValue();
        String applyNoStr = vm.getApplyNoStr();
        String reasonStr = vm.getReasonStr();
        String weiReasonStr = vm.getWeiReasonStr();
//        //出差单号必填但是没填
//        if (settings.get("travelorder").equals("1") && TextUtils.isEmpty(applyNoStr)) {
//            return -1;
//        }
        //成本中心必填但是没填
        if (settings.get("costcenter").equals("1")
                && TextUtils.isEmpty(vm.getCostCenterStr())) {
            return -2;
        }
        //项目信息必填但是没填
        if (settings.get("projectinfo").equals("1") && TextUtils.isEmpty(vm.getProjectStr())) {
            return -3;
        }
        //出差事由必填但是没填
        if (settings.get("travelreason").equals("1") && TextUtils.isEmpty(reasonStr)) {
            return -4;
        }
        //是违背的单子，但是没有填写违背的原因
        if (mBean.getWeibeiflag() == 1
                && TextUtils.isEmpty(vm.getWeiReasonStr())) {
            return -5;
        }
        if (mCurrentApprovePosition == -1) {
            return -6;
        }
        BaseActivity ba = (BaseActivity) this.context;
        ba.showDialog("取消", "确定", "确定送审吗？", new MyDialog.OnButtonClickListener() {
            @Override
            public void onLeftClick() {
            }

            @Override
            public void onRightClick() {
                send();
            }
        });
        return 1;
    }

    private void send() {
        RequestSendApproveBean sab = new RequestSendApproveBean();
        sab.setApproveid(String.valueOf(albs.get(mCurrentApprovePosition).getId()));
        sab.setChailvitem(vm.getReasonStr());
        sab.setCostid(String.valueOf(costCenterId));
        sab.setCostname(costCenterName);
        sab.setOrderno(mBean.getOrderno());
        sab.setProid(String.valueOf(projectId));
        sab.setProname(projectName);
        sab.setShenqingno(vm.getApplyNoStr());
        sab.setWbreason(vm.getWeiReasonStr());
        RetrofitUtil.sendPlaneApprove(context, AppUtils.getJson(sab), PlaneOrderDetailBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    PlaneOrderDetailBean pdb = (PlaneOrderDetailBean) o;
                    ToastUtils.showTextToast("送审成功！");
                    PlaneSendActivity a = (PlaneSendActivity) context;
                    a.jumpToOrderList(Constant.PLANE);
                } else {
                    DialogUtil.showDialog(context, "提示", "确定", "", "送审失败\n" + msg, null);
                }
                return true;
            }

            @Override
            public boolean onFailed(Throwable e) {

                return false;
            }
        });
    }

    public void cancle() {
        Map<String, String> map = new HashMap<>();
        map.put("orderno", mBean.getOrderno());
        RetrofitUtil.canclePlaneOrder(context, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    DialogUtil.showDialog(context, "提示", "确定", "", "取消成功", new MyDialog.OnButtonClickListener() {
                        @Override
                        public void onLeftClick() {
                            Activity a = (Activity) context;
                            a.finish();
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

    public void chowWeiPop() {
        MUtils.choseWeireason(context, "air", mCurrentPosition_weiReason, new MUtils.OnWeibeiListener() {
            @Override
            public void onSureClick(MyPickerView.Selection selection, int pos) {
                mCurrentPosition_weiReason = pos;
                vm.setWbReason(selection.getName());
            }
        });
    }

//    private List<SelectionBean> initWbReasonSelections(List<WbReasonBean> list) {
//        if (list == null) return null;
//        List<SelectionBean> sbs = new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//            SelectionBean sb = new SelectionBean(list.get(i).getName(), false);
//            sbs.add(sb);
//        }
//        return sbs;
//    }

    public int getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(int costCenterId) {
        this.costCenterId = costCenterId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getCostCenterName() {
        return costCenterName;
    }

    public void setCostCenterName(String costCenterName) {
        this.costCenterName = costCenterName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public PlaneOrderDetailBean getmBean() {
        return mBean;
    }

    public void setmBean(PlaneOrderDetailBean mBean) {
        this.mBean = mBean;
    }

    public void getApproveLevels() {
        Map<String, String> map = new HashMap<>();
        map.put("orderno", mBean.getOrderno());
        map.put("type", "book");
        RetrofitUtil.getPlaneApproveLevel(context, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    if (bean.getData() != null) {
                        Type typeToken = new TypeToken<List<ApproveLevelBean>>() {
                        }.getType();
                        Gson gson = new Gson();
                        albs = gson.fromJson(bean.getData(), typeToken);
                        adapter = new ChoseApproveLevelAdapter(context, albs);
                        vm.updateViews(adapter, mBean);
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

    public void setmCurrentPosition_approve(int position) {
        mCurrentApprovePosition = position;
    }

    public void caculatePrice() {
        //票价
        double piaojia = mBean.getTotalticketprice();
        //服务费
        double fuwufei = mBean.getRoutePass().get(0).getFuwufee();
        //机建费、税费
        PlaneOrderDetailBean.RoutesBean routesBean = mBean.getRoutes().get(0);
        double jijianfei = routesBean.getAirporttax() + routesBean.getFueltax();
        //保险费
        double baoxianfei = mBean.getRoutePass().get(0).getBxPayMoney();
        //乘客人数
        int psgNum = mBean.getPassengers().size();
        totalPrice = (piaojia + fuwufei + jijianfei + baoxianfei) * psgNum;
        vm.setTotalPrice(totalPrice);
    }
}
