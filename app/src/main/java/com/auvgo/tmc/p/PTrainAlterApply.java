package com.auvgo.tmc.p;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListAdapter;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.TrainDetailPsgsAdapter;
import com.auvgo.tmc.home.HomeActivity;
import com.auvgo.tmc.personalcenter.activity.OrderListActivity;
import com.auvgo.tmc.train.activity.AlterQueryActivity;
import com.auvgo.tmc.train.bean.requestbean.RequestOfLevel;
import com.auvgo.tmc.train.bean.requestbean.RequestOfPolicy;
import com.auvgo.tmc.train.bean.requestbean.RequestReturnTrainBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.TrainPolicyBean;
import com.auvgo.tmc.train.bean.TrainOrderDetailBean;
import com.auvgo.tmc.train.interfaces.ViewManager_trainAlterConfirm;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.MyDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lc on 2016/12/5
 */

public class PTrainAlterApply extends BaseP {
    public static TrainOrderDetailBean detailBean;
    private TrainDetailPsgsAdapter adapter;
    private ViewManager_trainAlterConfirm vm;
    private int mAction = Constant.ACTION_ALTER;

    public PTrainAlterApply(Context context, ViewManager_trainAlterConfirm vm, Bundle bundle) {
        super(context);
        this.vm = vm;
        detailBean = (TrainOrderDetailBean) bundle.getSerializable(Constant.INTENT_KEY_ORDERDETAIL);
        mAction = bundle.getInt("action", 0);
        adapter = new TrainDetailPsgsAdapter(context, getUsers(), R.layout.item_alter_psgs);
    }

    private List<TrainOrderDetailBean.UsersBean> getUsers() {
        List<TrainOrderDetailBean.UsersBean> list = new ArrayList<>();
        List<TrainOrderDetailBean.UsersBean> users = detailBean.getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (mAction == Constant.ACTION_RETURN && users.get(i).getTuipiaostatus() == 0) {
                list.add(users.get(i));
            }
            if (mAction == Constant.ACTION_ALTER && users.get(i).getGaiqianstatus() == 0) {
                list.add(users.get(i));
            }
        }
        detailBean.setUsers(list);
        return detailBean.getUsers();
    }

    public int getmAction() {
        return mAction;
    }

    public ListAdapter getAdapter() {
        return this.adapter;
    }

    public void setCardView() {
        vm.updateCard(detailBean);
    }

    public TrainOrderDetailBean getDetailBean() {
        return detailBean;
    }

    public void setDetailBean(TrainOrderDetailBean detailBean) {
        this.detailBean = detailBean;
    }

    /**
     * 确定，进入下一步流程
     */
    public void alter() {
        if (checkPsgEmpty()) {
            ToastUtils.showTextToast("请选择您要改签的人员");
            return;
        }
//        if (MUtils.isWoPu(detailBean.getUsers().get(0).getSeatCode())) {
//            ToastUtils.showTextToast("卧铺不能改签");
//            return;
//        }
        getLevel();
    }

    private boolean checkPsgEmpty() {
        List<TrainOrderDetailBean.UsersBean> users = detailBean.getUsers();
        boolean b = true;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).isChecked()) {
                b = false;
            }
        }
        return b;
    }

    public void getLevel() {
        RequestOfLevel rol = new RequestOfLevel();
        rol.setCid(MyApplication.mUserInfoBean.getCompanyid());
        List<Integer> level = new ArrayList<>();
        List<TrainOrderDetailBean.UsersBean> users = detailBean.getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).isChecked()) {
                level.add(users.get(i).getUserId());
            }
        }
        rol.setEmpids(level);

        RetrofitUtil.getEmpLevel(context, AppUtils.getJson(rol), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    List<String> list = gson.fromJson(bean.getData(), type);
                    RequestOfPolicy rop = new RequestOfPolicy();
                    rop.setCid(String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
                    rop.setLevel(list);
                    getPolicy(rop);
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    private void getPolicy(RequestOfPolicy rop) {
        RetrofitUtil.getPolicy(context, AppUtils.getJson(rop), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    if (bean.getData().length() != 3) {
                        MyApplication.mTrainPolicy = new Gson().fromJson(bean.getData(), TrainPolicyBean.class);
                    } else {
                        MyApplication.mTrainPolicy = null;
                    }
                    Intent intent = new Intent(context, AlterQueryActivity.class);
                    context.startActivity(intent);
                } else {
                    ToastUtils.showTextToast("请求差旅政策失败");
                    return true;
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    public void onItemClick(int position) {
        TrainOrderDetailBean.UsersBean usersBean = detailBean.getUsers().get(position);
        usersBean.setChecked(!usersBean.isChecked());
        adapter.notifyDataSetChanged();
    }

    public void returnTicket() {
        // TODO: 2016/12/7 退票
        if (checkPsgEmpty()) {
            ToastUtils.showTextToast("请选择您要退票的人员");
            return;
        }

        DialogUtil.showDialog(context, "提示", "取消", "确定", "您确定要进行退票操作吗？", new MyDialog.OnButtonClickListener() {
            @Override
            public void onLeftClick() {
            }

            @Override
            public void onRightClick() {
                doReturn();
            }
        });
    }

    private void doReturn() {
        RequestReturnTrainBean requestBean = new RequestReturnTrainBean();
        requestBean.setCid(String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        requestBean.setEmpid(String.valueOf(MyApplication.mUserInfoBean.getId()));
        requestBean.setOrderno(detailBean.getOrderNo());
        requestBean.setUserids(getUserIds());
        requestBean.setOrderfrom("2");
        RetrofitUtil.doReturnTicket(context, AppUtils.getJson(requestBean), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    DialogUtil.showDialog(context, "提示", "知道了", "", context.getString(R.string.retrun_success_notice), new MyDialog.OnButtonClickListener() {
                        @Override
                        public void onLeftClick() {
                            MUtils.jumpToPage(context, HomeActivity.class,
                                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            MUtils.jumpToPage(context, OrderListActivity.class, -1);
                        }

                        @Override
                        public void onRightClick() {

                        }
                    });
                } else {

                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {

                return false;
            }
        });
    }

    private List<String> getUserIds() {
        List<String> list = new ArrayList<>();
        List<TrainOrderDetailBean.UsersBean> psgs = getAlterPsgs();
        for (int i = 0; i < psgs.size(); i++) {
            list.add(String.valueOf(psgs.get(i).getUserId()));
        }
        return list;
    }

    private List<TrainOrderDetailBean.UsersBean> getAlterPsgs() {
        List<TrainOrderDetailBean.UsersBean> users = detailBean.getUsers();
        List<TrainOrderDetailBean.UsersBean> list = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).isChecked()) {
                list.add(users.get(i));
            }
        }
        return list;
    }
}
