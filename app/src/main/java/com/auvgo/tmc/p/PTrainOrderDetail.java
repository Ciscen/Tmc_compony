package com.auvgo.tmc.p;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.ApproveStateAdapter;
import com.auvgo.tmc.adapter.ChoseApproveLevelAdapter;
import com.auvgo.tmc.adapter.TrainOrderDetailPsgAdapter;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.common.CostCenterActivity;
import com.auvgo.tmc.common.module.PayModule;
import com.auvgo.tmc.common.ProjectAcitivity;
import com.auvgo.tmc.train.activity.NoticeActivity;
import com.auvgo.tmc.train.activity.AlterReturnTrainApplyActivity;
import com.auvgo.tmc.train.activity.TrainOrderDetailActivity;
import com.auvgo.tmc.train.bean.ApproveLevelBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.requestbean.RequestSendApproveBean;
import com.auvgo.tmc.train.bean.TrainOrderDetailBean;
import com.auvgo.tmc.train.bean.WbReasonBean;
import com.auvgo.tmc.train.interfaces.ViewManager_trainOrderDetail;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.MyDialog;
import com.auvgo.tmc.views.MyPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.auvgo.tmc.constants.Constant.TrainAlterStatus.*;
import static com.auvgo.tmc.constants.Constant.TrainReturnStatus.*;

/**
 * Created by lc on 2016/11/21
 */

public class PTrainOrderDetail extends BaseP {
    private String mOrderNo;
    private int mOrderStatus;//表示当前的订单状态，待审批、待支付、、、、等
    private TrainOrderDetailBean mBean;//订单的实体
    private List<ApproveLevelBean> alb;//可选审批级别的实体
    private List<WbReasonBean> mWbReasons;//请求到的违背原因的的集合
    private int costCenterId;//所选成本中心id
    private String costCenterName; //所选成本中心名称
    private int projectId;//所选项目id
    private String projectName; //所选项目中心名称
    private ViewManager_trainOrderDetail vm;
    private TrainOrderDetailPsgAdapter adapter_psg;
    private ChoseApproveLevelAdapter adapter_approve_chose;
    private ApproveStateAdapter adapter_approve_state;
    private int mCurrentPosition_weiReason = -1;
    private int mCurrentPosition_approve = -1;//审批等级的选定情况

    public static final int STATUS_DAITIJIAO = 1;//等待提交的状态
    public static final int STATUS_DAICHUPIAO = 2;//可以出票的状态
    public static final int STATUS_YIZHANZUO = 3;//已经占座，但是未/暂未通过，这时候只能取消订单
    public static final int STATUS_YICHUPIAO = 4;//已经出票，可以取消、改签
    public static final int STATUS_YIQUXIAO = 5;//已经取消

    /*
    *订单详情页面，标记弹出popupWindow的种类
    */
    public static final int TRAIN_ORDER_DETAIL_POP_COSTCENTER = 0;
    public static final int TRAIN_ORDER_DETAIL_POP_ITEM = 1;
    public static final int TRAIN_ORDER_DETAIL_POP_REASON = 2;
    public static final int TRAIN_ORDER_DETAIL_POP_WEIITEM = 3;
    public static final int TRAIN_ORDER_DETAIL_POP_WEIREASON = 4;

    private String totalPrice;

    public int getmCurrentPosition_approve() {
        return mCurrentPosition_approve;
    }

    public void setmCurrentPosition_approve(int mCurrentPosition_approve) {
        this.mCurrentPosition_approve = mCurrentPosition_approve;
    }


    public int getmOrderStatus() {
        return mOrderStatus;
    }

    public void setmOrderStatus(int mOrderStatus) {
        this.mOrderStatus = mOrderStatus;
    }

    public TrainOrderDetailBean getmBean() {
        return mBean;
    }

    public void setmBean(TrainOrderDetailBean mBean) {
        this.mBean = mBean;
    }

    public PTrainOrderDetail(Context context, ViewManager_trainOrderDetail vm) {
        super(context);
        this.vm = vm;
    }

    public int getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(int costCenterId) {
        this.costCenterId = costCenterId;
    }

    public String getCostCenterName() {
        return costCenterName;
    }

    public void setCostCenterName(String costCenterName) {
        this.costCenterName = costCenterName;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getOrderno() {
        return mOrderNo;
    }

    public void setOrderno(String mOrderNo) {
        this.mOrderNo = mOrderNo;
    }

    public void getOrderDetail() {
        final Map<String, String> map = new LinkedHashMap<>();
        map.put("orderno", mOrderNo);
        RetrofitUtil.getOrder(context, AppUtils.getJson(map), TrainOrderDetailBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    doNext((TrainOrderDetailBean) o);
                    if (mBean.getApprovestatus() == Constant.ApproveStatus.APPROVE_STATUS_DAISONGSHEN) {
                        requstApproveLevel(map);
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

    /**
     * 请求到了详情的数据后，进行系一步的操作
     *
     * @param o
     */
    private void doNext(TrainOrderDetailBean o) {
        mBean = o;
        adapter_psg = new TrainOrderDetailPsgAdapter(context, mBean.getUsers());
        adapter_approve_state = new ApproveStateAdapter(context, mBean.getApproves());
        vm.updateViews(mBean, adapter_psg, adapter_approve_state);
        vm.setVisibility(mBean);
    }

    private void requstApproveLevel(Map<String, String> map) {
        RetrofitUtil.getTrainApprove(context, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (bean.getStatus() == 200) {
                    if (bean.getData() != null) {
                        Type typeToken = new TypeToken<List<ApproveLevelBean>>() {
                        }.getType();
                        Gson gson = new Gson();
                        alb = gson.fromJson(bean.getData(), typeToken);
                        adapter_approve_chose = new ChoseApproveLevelAdapter(context, alb);
                        vm.setAdapter(adapter_approve_chose);
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


    public void toNotice() {
        Intent intent = new Intent(context, NoticeActivity.class);
        context.startActivity(intent);
    }

    public void showPop(int flag) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("cid", String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        map.put("type", "train");
        String json = AppUtils.getJson(map);
        final MyPickerView mpv = new MyPickerView(context, null);
        switch (flag) {
            case TRAIN_ORDER_DETAIL_POP_COSTCENTER:
                Activity proA = (Activity) context;
                proA.startActivityForResult(new Intent(context, CostCenterActivity.class), Constant.ACTIVITY_TRAIN_ORDER_DETAIL_FLAG);
                break;
            case TRAIN_ORDER_DETAIL_POP_ITEM:
                Activity costA = (Activity) context;
                costA.startActivityForResult(new Intent(context, ProjectAcitivity.class), Constant.ACTIVITY_TRAIN_ORDER_DETAIL_FLAG);
                break;
            case TRAIN_ORDER_DETAIL_POP_WEIREASON:
                RetrofitUtil.getWbReason(context, json, null, new RetrofitUtil.OnResponse() {
                    @Override
                    public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                        if (status == 200) {
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<WbReasonBean>>() {
                            }.getType();
                            mWbReasons = gson.fromJson(bean.getData(), type);
//                            final List<SelectionBean> selectionBeens = initWbReasonSelections(mWbReasons);
                            if (mWbReasons != null) {
                                mpv.setTitle("请选择违背原因")
                                        .setPosition(mCurrentPosition_weiReason)
                                        .setSelections(mWbReasons)
                                        .setListener(new MyPickerView.OnPickerViewSure() {
                                            @Override
                                            public void onSingleSureClick(int p) {
                                                mCurrentPosition_weiReason = p;
                                                vm.setWeiReason(mWbReasons.get(p).getName());
                                            }

                                            @Override
                                            public void onMultiSureClick(List<Integer> s) {

                                            }
                                        }).showSingleChoseExpandable();
                            }
                        }
                        return false;
                    }

                    @Override
                    public boolean onFailed(Throwable e) {
                        return false;
                    }
                });
                break;
        }
    }

    public void commit(String reasonStr, String weiReasonStr) {
        //// TODO: 2016/12/13 送审
        RequestSendApproveBean sab = new RequestSendApproveBean();
        sab.setApproveid(String.valueOf(alb.get(mCurrentPosition_approve).getId()));
        sab.setChailvitem(reasonStr);
        sab.setCostid(String.valueOf(costCenterId));
        sab.setCostname(costCenterName);
        sab.setOrderno(mBean.getOrderNo());
        sab.setProid(String.valueOf(projectId));
        sab.setProname(projectName);
//        sab.setShenqingno(applyNoStr);
        sab.setWbreason(weiReasonStr);
        RetrofitUtil.sendOrderApprove(context, AppUtils.getJson(sab), TrainOrderDetailBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
//                    doNext((TrainOrderDetailBean) o);
                    ToastUtils.showTextToast("送审成功");
                    TrainOrderDetailActivity a = (TrainOrderDetailActivity) context;
                    a.jumpToOrderList(Constant.TRAIN);
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    public void cancleOrder() {
        DialogUtil.showDialog(context, "提示", "取消", "确定", "您确定要取消这张订单吗，取消后将无法恢复？", new MyDialog.OnButtonClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                cancle();
            }
        });

    }

    private void cancle() {
        Map<String, String> map = new HashMap<>();
        map.put("orderno", mOrderNo);
        RetrofitUtil.cancleOrder(context, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
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

    public void alterTicket() {
        // TODO: 2016/11/22 改签
        if (!checkIfAlterReturn(0) || !checkIfAlterReturn(1)) {
            DialogUtil.showDialog(context, "提示", "知道了", "", context.getString(R.string.noGqTpNotice), null);
            return;
        }
        Intent intent = new Intent(context, AlterReturnTrainApplyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.INTENT_KEY_ORDERDETAIL, mBean);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    //检测是否有可以改签，退票的 乘客，0改签  1退票
    private boolean checkIfAlterReturn(int i) {
        List<TrainOrderDetailBean.UsersBean> users = mBean.getUsers();
        for (int i1 = 0; i1 < users.size(); i1++) {
            TrainOrderDetailBean.UsersBean usersBean = users.get(i1);
            if (i == 0) {
                /*没有过改签而且没有过退票，表示可以改签*/
                if (!hasAltered(usersBean.getGaiqianstatus()) && !hasReturned(usersBean.getTuipiaostatus())) {
                    return true;
                }
            }
            if (i == 1) {
                 /*没有过改签或者没有过退票，表示可以退票*/
                if (!hasAltered(usersBean.getGaiqianstatus()) && !hasReturned(usersBean.getTuipiaostatus())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 根据人身上的状态值  判断是否改签过
     *
     * @param gaiqianstatus
     * @return
     */
    private boolean hasAltered(int gaiqianstatus) {
          /*如果已改签或者改签中，标记为有过改签*/
        return gaiqianstatus == TRAIN_GQ_GAIQIANCHENGGONG || gaiqianstatus == TRAIN_GQ_GAIQIANZHONG;
    }

    /**
     * 根据人身上的状态值  判断是否改签过
     *
     * @param tuipiaostatus
     * @return
     */
    private boolean hasReturned(int tuipiaostatus) {
        /*如果以退票或者退票中，标记为有过退票*/
        return tuipiaostatus == TRAIN_TP_TUIPIAOZHONG || tuipiaostatus == TRAIN_TP_YITUIPIAO;
    }

    public void payToGetTicket() {
        if (mBean.getPayType() == null) {
            return;
        }
        // TODO: 2016/11/22 出票
        PayModule.getInstance().pay(context, mOrderNo, PayModule.ORDER_TYPE_TRAIN, mBean.getPayType().equals("1"),
                AppUtils.keepNSecimal(String.valueOf(mBean.getTotalprice()), 2), mBean.getLastPayTime(),
                new PayModule.OnPayResultListener() {
                    @Override
                    public void onPaySuccess() {
                        BaseActivity baseActivity = (BaseActivity) context;
                        baseActivity.freshOrderDetail(mOrderNo);
                    }

                    @Override
                    public void onPayFailed(int code, String msg) {

                    }
                });
    }


    public void returnTicket() {

        // TODO: 2016/12/10 退票
        if (!checkIfAlterReturn(0) || !checkIfAlterReturn(1)) {
            DialogUtil.showDialog(context, "提示", "知道了", "", context.getString(R.string.noGqTpNotice), null);
            return;
        }
        Intent intent = new Intent(context, AlterReturnTrainApplyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("action", Constant.ACTION_RETURN);
        bundle.putSerializable(Constant.INTENT_KEY_ORDERDETAIL, mBean);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    public void showPriceDialog(int h) {
        int size = mBean.getUsers().size();
        /*
        服务费
         */
        int serFee = mBean.getUsers().get(0).getFuwufei();//服务费
        String serFee1 = serFee + "x" + size;
        String serFeeTotal = AppUtils.keepNSecimal(String.valueOf(serFee * size), 1);
        /*
        票价
         */
        double amount = mBean.getUsers().get(0).getAmount();
        String ticketFee = amount + "x" + size;//票价
        String totalFee = AppUtils.keepNSecimal(String.valueOf(amount * size), 1);
        /*
        配送费
         */
        String peisongfei = mBean.getUsers().get(0).getTrainpeison() == 0 ? "" : mBean.getUsers().get(0).getTrainpeison() + "x" + size;
        String peisongfeiTotal = AppUtils.keepNSecimal(String.valueOf(mBean.getUsers().get(0).getTrainpeison() * size), 1);

        DialogUtil.showPriceDialog(context, h, serFee1, serFeeTotal,
                peisongfei, peisongfeiTotal, ticketFee, totalFee, null);
    }

    public String getTotalPrice() {//总价 = 票单价 X 数量 + 服务费 X 数量
        return new DecimalFormat("0.0").format(mBean.getUsers().get(0).getAmount() * mBean.getUsers().size()
                + mBean.getUsers().get(0).getFuwufei() * mBean.getUsers().size());
    }

}
