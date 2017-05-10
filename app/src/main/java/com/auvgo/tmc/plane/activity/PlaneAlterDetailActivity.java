package com.auvgo.tmc.plane.activity;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.ApproveStateAdapter;
import com.auvgo.tmc.adapter.ChoseApproveLevelAdapter;
import com.auvgo.tmc.adapter.PlaneAlterOrderDetailPsgAdapter;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.common.module.PayModule;
import com.auvgo.tmc.plane.bean.PlaneAlterDetailBean;
import com.auvgo.tmc.train.bean.ApproveLevelBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.ItemView;
import com.auvgo.tmc.views.MyDialog;
import com.auvgo.tmc.views.PlaneDetailCardView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.auvgo.tmc.constants.Constant.ApproveStatus.*;
import static com.auvgo.tmc.constants.Constant.AirAlterStatus.*;
import static com.auvgo.tmc.constants.Constant.PayStatus.*;

public class PlaneAlterDetailActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    @BindView(R.id.plane_alter_detail_orderno)
    TextView orderNo_tv;
    @BindView(R.id.plane_alter_detail_status)
    TextView status_tv;
    @BindView(R.id.plane_alter_detail_contact)
    TextView contact_tv;
    @BindView(R.id.plane_alter_detail_tel)
    TextView tel_tv;
    @BindView(R.id.plane_alter_detail_email)
    TextView email_tv;
    @BindView(R.id.plane_alter_detail_priceall)
    TextView price_tv;

    @BindView(R.id.plane_alter_confirm_cv)
    PlaneDetailCardView cv;
    @BindView(R.id.plane_alter_detail_psgs_lv)
    ListView lv_psgs;
    @BindView(R.id.plane_alter_detail_approve_lv)
    ListView lv_approve;
    @BindView(R.id.approve_chose_lv)
    ListView lv_approve_chose;
    @BindView(R.id.plane_alter_detail_insurance)
    ItemView insurance_iv;
    @BindView(R.id.plane_alter_detail_applyNo)
    ItemView applyNo_iv;
    @BindView(R.id.plane_alter_detail_costCenter)
    ItemView costCenter_iv;
    @BindView(R.id.plane_alter_detail_project)
    ItemView project_iv;
    @BindView(R.id.plane_alter_detail_reason)
    ItemView reason_iv;
    @BindView(R.id.plane_alter_detail_weiItem)
    ItemView weiItem_iv;
    @BindView(R.id.plane_alter_detail_weiReason)
    ItemView weiReason_iv;
    @BindView(R.id.plane_alter_detail_approveinfo)
    View approveInfo_vg;
    @BindView(R.id.plane_alter_detail_item_approveStatus)
    View approveStatus_vg;
    @BindView(R.id.plane_alter_detail_pricedetail)
    View priceDetail_vg;
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
    @BindView(R.id.plane_alter_detail_bt1)
    TextView bt1;
    @BindView(R.id.plane_alter_detail_bt2)
    TextView bt2;
    @BindView(R.id.layout_approve_chose)
    View approve_chose_vg;
    @BindView(R.id.plane_alter_detail_notice)
    View alter_notice;
    private PlaneAlterDetailBean mBean;

    private ChoseApproveLevelAdapter adapter;
    private List<ApproveLevelBean> albs;//可选审批级别的实体
    private int mCurrentApprovePosition = -1;//表示所选的审批规则
    private String orderNo;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_plane_alter_detail;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        orderNo = getIntent().getStringExtra("orderNo");
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initListener() {
        bt2.setOnClickListener(this);
        bt1.setOnClickListener(this);
        lv_approve_chose.setOnItemClickListener(this);
    }

    @Override
    protected void setViews() {
        bt1.setText("送审");
        bt2.setText("取消改签");
    }

    @Override
    protected void getData() {
        super.getData();
        Map<String, String> map = new HashMap<>();
        map.put("gqorderno", orderNo);
        map.put("cid", String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        RetrofitUtil.getPlaneAlterDetail(this, AppUtils.getJson(map), PlaneAlterDetailBean.class,
                new RetrofitUtil.OnResponse() {
                    @Override
                    public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                        if (status == 200) {
                            mBean = (PlaneAlterDetailBean) o;
                            updateViews();
                            getApproveLevel();
                        }
                        return false;
                    }

                    @Override
                    public boolean onFailed(Throwable e) {
                        return false;
                    }
                });

    }

    private void getApproveLevel() {
        Map<String, String> map = new HashMap<>();
        map.put("orderno", mBean.getGqorderno());
        map.put("type", "gaiqian");
        RetrofitUtil.getPlaneApproveLevel(this, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    if (bean.getData() != null) {
                        Type typeToken = new TypeToken<List<ApproveLevelBean>>() {
                        }.getType();
                        Gson gson = new Gson();
                        albs = gson.fromJson(bean.getData(), typeToken);
                        adapter = new ChoseApproveLevelAdapter(PlaneAlterDetailActivity.this, albs);
                        lv_approve_chose.setAdapter(adapter);
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

    private void updateViews() {
         /*
        审批信息的显示隐藏
         */
        int paystatus = mBean.getPaystatus();
        int approvestatus = mBean.getApprovestatus();
        int status = mBean.getStatus();
        /*无需审批，不显示任何审批的控件*/
        if (approvestatus == APPROVE_STATUS_WUXUSHENPI) {
            approveInfo_vg.setVisibility(View.GONE);
            approveStatus_vg.setVisibility(View.GONE);
            approve_chose_vg.setVisibility(View.GONE);
            bt1.setVisibility(View.GONE);
            bt2.setVisibility(View.GONE);
            /*待送审，显示审批信息，审批规则，不现实审批状态,两个按钮都显示*/
        } else if (approvestatus == APPROVE_STATUS_DAISONGSHEN) {
//          approveInfo_vg.setVisibility(View.VISIBLE);
//            approve_chose_vg.setVisibility(View.VISIBLE);
            approveStatus_vg.setVisibility(View.GONE);
            bt1.setVisibility(View.VISIBLE);
            alter_notice.setVisibility(View.VISIBLE);
            bt2.setVisibility(View.VISIBLE);
            /*送审以后，包括审批中，审批通过、否决，，不显示审批规则，显示审批信息跟审批状态*/
        } else if (approvestatus == APPROVE_STATUS_SHENPITONGGUO ||
                approvestatus == APPROVE_STATUS_SHENPIFOUJUE ||
                approvestatus == APPROVE_STATUS_SHENPIZHONG) {
//          approveInfo_vg.setVisibility(View.VISIBLE);
            approve_chose_vg.setVisibility(View.GONE);
            approveStatus_vg.setVisibility(View.VISIBLE);
            bt1.setVisibility(View.GONE);
            /*默认状态*/
        } else if (approvestatus == APPROVE_STATUS_WEISONGSHEN) {
            approveInfo_vg.setVisibility(View.GONE);
            approveStatus_vg.setVisibility(View.GONE);
            approve_chose_vg.setVisibility(View.GONE);
            bt1.setVisibility(View.GONE);
            bt2.setVisibility(View.GONE);
        }


        /*如果被改签失败了，订单标记为已经取消，隐藏两个按钮*/
        if (status == AIR_GQ_FAILED) {
            bt2.setVisibility(View.GONE);
            bt1.setVisibility(View.GONE);
            findViewById(R.id.plane_alter_detail_price_logo).setVisibility(View.GONE);
        }
        /*待支付*/
        if (paystatus == PAY_STATUS_DAIZHIFU) {
            bt1.setVisibility(View.VISIBLE);
            bt2.setVisibility(View.VISIBLE);
            bt1.setText("支付");
            bt2.setText("取消改签");
        }
        /*改签成功*/
        if (status == AIR_GQ_SUCCESS) {
            bt1.setVisibility(View.VISIBLE);
            bt1.setBackground(getResources().getDrawable(R.drawable.selector_button_red_noradius));
            bt1.setText("退票");
            bt2.setText("改签");
            bt2.setTextColor(Color.WHITE);
            bt2.setVisibility(View.VISIBLE);
            bt2.setBackground(getResources().getDrawable(R.drawable.selector_button_blue_noradius));
        }

        List<PlaneAlterDetailBean.PassengersBean> passengers = mBean.getPassengers();

        orderNo_tv.setText(String.format("订单号:%s", mBean.getGqorderno()));
        status_tv.setText(getTicketStatus(status, paystatus) + "|"
                + MUtils.getApproveStateByCode(approvestatus));

        PlaneAlterDetailBean.RoutesBean order = mBean.getRoutes().get(0);
        List<PlaneAlterDetailBean.PassengersBean> psgs = passengers;
        contact_tv.setText(mBean.getLinkName());
        tel_tv.setText(mBean.getLinkPhone());

        String linkEmail = mBean.getLinkEmail();
        if (TextUtils.isEmpty(linkEmail)) {
            email_tv.setVisibility(View.GONE);
        } else {
            email_tv.setText(linkEmail);
        }

        String bxName = psgs.get(0).getBxName();
        insurance_iv.setContent(TextUtils.isEmpty(bxName) ? "- -" : bxName);
        lv_psgs.setAdapter(new PlaneAlterOrderDetailPsgAdapter(this, passengers));

        PlaneAlterDetailBean.OldroutesBean oldroutesBean = mBean.getOldroutes().get(0);
        PlaneAlterDetailBean.RoutesBean rb = mBean.getRoutes().get(0);
        //原航班
        time_dept_tv.setText(oldroutesBean.getDeptdate().substring(5) + "  " + oldroutesBean.getDepttime());
        time_arri_tv.setText(oldroutesBean.getArridate().substring(5) + "  " + oldroutesBean.getArritime());
        airport_dept_tv.setText(String.format("%s%s", oldroutesBean.getOrgname(), oldroutesBean.getDeptterm()));
        airport_arri_tv.setText(String.format("%s%s", oldroutesBean.getArriname(), oldroutesBean.getArriterm()));
        airline_tv.setText(oldroutesBean.getAirline() + "|" + oldroutesBean.getCodeDes());
        //新航班
        cv.setAirline("新航程");
        cv.setFood(rb.getCarriername() + rb.getAirline() + "|" + rb.getCodeDes());
        cv.setStart_date(TimeUtils.getMMdd(rb.getDeptdate()) + " " + TimeUtils.getTomorrowWeekDay(rb.getDeptdate()));
        cv.setEnd_date(TimeUtils.getMMdd(rb.getArridate()) + " " + TimeUtils.getTomorrowWeekDay(rb.getArridate()));
        cv.setStart_time(rb.getDepttime());
        cv.setEnd_time(rb.getArritime());
        cv.setOrgname(rb.getOrgname());
        cv.setArriname(rb.getArriname());
        String text = String.valueOf(mBean.getPassengers().get(0).getKhYinshou() * mBean.getPassengers().size());
        if (status == AIR_GQ_CANCELED || status == AIR_GQ_FAILED
                || status == AIR_GQ_COMMITTED || status == AIR_GQ_WEIGAIQIAN) {
            text = "--";
        }
        price_tv.setText(/*价格确认以后才显示价格*/AppUtils.keepNSecimal(text, 2));
        if (mBean.getApproves() == null || mBean.getApproves().size() == 0) {
            approveStatus_vg.setVisibility(View.GONE);
        } else {
            lv_approve.setAdapter(new ApproveStateAdapter(this, mBean.getApproves()));
        }
    }

    private String getTicketStatus(int status, int paystatus) {
//        if (paystatus == PAY_STATUS_DAIZHIFU) return "待支付";
        return MUtils.getAlterStateByCode(status);
    }

    @Override
    public void onClick(View v) {
        final Map<String, String> map = new HashMap<>();
        if (mBean == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.plane_alter_detail_bt1:
                if (mBean.getPaystatus() == PAY_STATUS_DAIZHIFU) {
                    doPay();
                } else if (mBean.getApprovestatus() == APPROVE_STATUS_DAISONGSHEN) {
                    doApprove(map);
                } else if (mBean.getStatus() == AIR_GQ_SUCCESS) {
                    doReturn(map);
                }
                break;
            case R.id.plane_alter_detail_bt2:
                if (mBean.getStatus() == AIR_GQ_SUCCESS) {
                    doGaiqian(map);
                } else {
                    CancelAlterOrder(map);
                }
                break;
        }
    }

    /*
    改签成功后再次改签
     */
    private void doGaiqian(Map<String, String> map) {
        DialogUtil.showDialog(this, "提示", "取消", "拨打", getString(R.string.gqorderNotice),
                new MyDialog.OnButtonClickListener() {
                    @Override
                    public void onLeftClick() {

                    }

                    @Override
                    public void onRightClick() {
                        AppUtils.callPhone(context, "400 606 0011");
                    }
                });

    }

    /**
     * 退票
     */
    private void doReturn(Map<String, String> map) {
        DialogUtil.showDialog(this, "提示", "取消", "拨打", getString(R.string.gqorderNotice),
                new MyDialog.OnButtonClickListener() {
                    @Override
                    public void onLeftClick() {

                    }

                    @Override
                    public void onRightClick() {
                        AppUtils.callPhone(context, "400 606 0011");
                    }
                });

    }

    /**
     * 支付改签费用
     */
    private void doPay() {
        final PayModule instance = PayModule.getInstance();
        PlaneAlterDetailBean.OrderPaymentBean orderPayment = mBean.getOrderPayment();
        if (orderPayment.getReceivprice() <= 0 || orderPayment.getPaytype().equals("1")) {//价格是0元，或者是月结的话，走月结
            showDialog("取消", "确定", "确定支付？", new MyDialog.OnButtonClickListener() {
                @Override
                public void onLeftClick() {
                }

                @Override
                public void onRightClick() {
                    instance.pay_plane_gq(context, orderNo);
                }
            });

        } else {
            instance.gotoPaylist(this, orderNo, PayModule.ORDER_TYPE_AIR_GQ,
                    AppUtils.keepNSecimal(String.valueOf(mBean.getGaiqianTotalPrice()), 2), 0L);
        }
    }

    /**
     *
     */
    private void CancelAlterOrder(final Map<String, String> map) {
        // TODO: 2017/1/18 取消改签
        map.put("orderno", orderNo);
        DialogUtil.showDialog(PlaneAlterDetailActivity.this, "提示", "取消", "确定", "确定取消吗？",
                new MyDialog.OnButtonClickListener() {
                    @Override
                    public void onLeftClick() {
                    }

                    @Override
                    public void onRightClick() {
                        cancel(map);
                    }
                });
    }

    private void doApprove(Map<String, String> map) {
        // TODO: 2017/1/18 提交审批
        if (mCurrentApprovePosition == -1) {
            ToastUtils.showTextToast("请选择审批级别");
            return;
        }
        map.put("orderno", orderNo);
        map.put("approveid", String.valueOf(albs.get(mCurrentApprovePosition).getId()));
        RetrofitUtil.sendPlaneAlterApprove(this, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    DialogUtil.showDialog(PlaneAlterDetailActivity.this, "提示", "确定", "", "送审成功",
                            new MyDialog.OnButtonClickListener() {
                                @Override
                                public void onLeftClick() {
                                    finish();
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

    private void cancel(Map<String, String> map) {
        RetrofitUtil.canclePlaneOrder(this, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    DialogUtil.showDialog(PlaneAlterDetailActivity.this, "提示", "确定", "", "取消成功",
                            new MyDialog.OnButtonClickListener() {
                                @Override
                                public void onLeftClick() {
                                    finish();
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mCurrentApprovePosition = position;
        for (int i = 0; i < albs.size(); i++) {
            if (i == position) {
                albs.get(i).setCheck(true);
            } else {
                albs.get(i).setCheck(false);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
