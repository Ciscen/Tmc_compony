package com.auvgo.tmc.train.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.ApproveStateAdapter;
import com.auvgo.tmc.adapter.ChoseApproveLevelAdapter;
import com.auvgo.tmc.adapter.TrainOrderDetailPsgAdapter;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.base.MyList;
import com.auvgo.tmc.train.bean.ApproveLevelBean;
import com.auvgo.tmc.train.bean.ComSettingBean;
import com.auvgo.tmc.train.bean.TrainOrderDetailBean;
import com.auvgo.tmc.train.interfaces.ViewManager_trainOrderDetail;
import com.auvgo.tmc.p.PTrainOrderDetail;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.KeyBoardUtils;
import com.auvgo.tmc.utils.LogFactory;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.ItemView;

import java.util.List;

import static com.auvgo.tmc.constants.Constant.ApproveStatus.*;
import static com.auvgo.tmc.constants.Constant.TrainTicketStatus.*;
import static com.auvgo.tmc.p.PTrainOrderDetail.STATUS_DAICHUPIAO;

public class TrainOrderDetailActivity extends BaseActivity implements ViewManager_trainOrderDetail, View.OnClickListener {
    private TextView ticketNo, orderNo, state, contact, tel, email,
            costCenter, item,//项目
            weiItem,//违背项目
            weiReason,//违背原因
            totalprice,//总价
            commit,//送审、确认出票按钮
            cancle,//取消按钮
            applyNo;//出差单号
    private EditText reason;//出差事由


    //车次详情的控件
    private TextView traincode, notice,
            start_station, end_station,
            start_time, start_date, end_time,
            end_date, seattype, run_time;

    private View priceDetail, arrow_costCenter, arrow_item, arrow_reason, arrow_weiReason;
    private View item_costCenter, item_project, item_weiItem, item_weiReason, item_approveinfo,
            item_approveState, item_approve_chose, item_applyNo;
    private ListView psgs_lv, approve_lv, approveLevel_lv;
    private PTrainOrderDetail pTrainOrderDetail;
    private ItemView addr;//配送地址

    @Override
    protected int getLayoutId() {
        return R.layout.activity_train_order_detail;
    }

    @Override
    protected void initData() {
        pTrainOrderDetail = new PTrainOrderDetail(this, this);
        String orderno = getIntent().getStringExtra("orderNo");
        pTrainOrderDetail.setOrderno(orderno);
    }


    @Override
    protected void findViews() {
        item_applyNo = findViewById(R.id.train_order_detail_item_applyNo);
        traincode = (TextView) findViewById(R.id.plane_detail_airline);
        notice = (TextView) findViewById(R.id.plane_detail_food);
        start_station = (TextView) findViewById(R.id.train_detail_start_station);
        end_station = (TextView) findViewById(R.id.train_detail_end_station);
        start_time = (TextView) findViewById(R.id.train_detail_start_time);
        start_date = (TextView) findViewById(R.id.train_detail_start_date);
        end_time = (TextView) findViewById(R.id.train_detail_end_time);
        end_date = (TextView) findViewById(R.id.train_detail_end_date);
        seattype = (TextView) findViewById(R.id.train_detail_time);
        run_time = (TextView) findViewById(R.id.train_detail_runtime);
        ticketNo = (TextView) findViewById(R.id.train_order_detail_ticketNo);
        orderNo = (TextView) findViewById(R.id.train_order_detail_orderNo);
        state = (TextView) findViewById(R.id.train_order_detail_state);
        contact = (TextView) findViewById(R.id.train_order_detail_contact);
        tel = (TextView) findViewById(R.id.train_order_detail_tel);
        email = (TextView) findViewById(R.id.train_order_detail_email);
        addr = (ItemView) findViewById(R.id.train_order_detail_addr);
        applyNo = (TextView) findViewById(R.id.train_order_detail_applyNo);
        costCenter = (TextView) findViewById(R.id.train_order_detail_costCenter);
        reason = (EditText) findViewById(R.id.train_order_detail_reason);
        weiItem = (TextView) findViewById(R.id.train_order_detail_weiItem);
        weiReason = (TextView) findViewById(R.id.train_order_detail_weiReason);
        item = (TextView) findViewById(R.id.train_order_detail_item);
        totalprice = (TextView) findViewById(R.id.train_order_detail_priceall);
        commit = (TextView) findViewById(R.id.train_order_detail_bt1);
        cancle = (TextView) findViewById(R.id.train_order_detail_bt2);
        priceDetail = findViewById(R.id.train_order_detail_click_bottom);
        arrow_costCenter = findViewById(R.id.train_order_detail_costCenter_arrow);
        arrow_item = findViewById(R.id.train_order_detail_item_arrow);
        arrow_reason = findViewById(R.id.train_order_detail_reason_arrow);
        arrow_weiReason = findViewById(R.id.train_order_detail_weiReason_arrow);
        item_project = findViewById(R.id.train_order_detail_item_project);
        item_costCenter = findViewById(R.id.train_order_detail_item_costCenter);
        item_approveinfo = findViewById(R.id.train_order_detail_approveinfo);
        item_approveState = findViewById(R.id.train_order_detail_item_approveStatus);
        item_approve_chose = findViewById(R.id.layout_approve_chose);
        item_weiItem = findViewById(R.id.train_order_detail_item_weiItem);
        item_weiReason = findViewById(R.id.train_order_detail_item_weiReason);

        psgs_lv = (ListView) findViewById(R.id.train_order_detail_psgs_lv);
        approve_lv = (ListView) findViewById(R.id.train_order_detail_approve_lv);
        approveLevel_lv = (ListView) findViewById(R.id.approve_chose_lv);
    }

    @Override
    protected void initView() {
        seattype.setTextColor(Color.BLACK);
        seattype.setClickable(false);
        /*
        控制成本中心跟项目的显示与否
         */
        setCostCenterAndProjectVisibility(item_costCenter, item_project);
    }

    @Override
    protected void initListener() {
        notice.setOnClickListener(this);
        commit.setOnClickListener(this);
        cancle.setOnClickListener(this);
        priceDetail.setOnClickListener(this);
    }

    @Override
    protected void setViews() {
    }

    @Override
    protected void getData() {
        pTrainOrderDetail.getOrderDetail();
    }

    @Override
    public void updateViews(TrainOrderDetailBean b, TrainOrderDetailPsgAdapter adapter_psg,
                            ApproveStateAdapter adapter_approve_state) {
        TrainOrderDetailBean.RouteBean route = b.getRoute();
        List<TrainOrderDetailBean.UsersBean> users = b.getUsers();
        orderNo.setText(String.format("订单号:%s", b.getOrderNo()));//订单号
        ticketNo.setText(String.format("取票单号:%s", b.getOutTicketBillno()));//取票单号
        totalprice.setText(AppUtils.keepNSecimal(b.getTotalprice() + "", 1));//底部的总价
        contact.setText(b.getLinkName());
        applyNo.setText(b.getShenqingno());
        if (TextUtils.isEmpty(b.getLinkPhone())) {
            tel.setVisibility(View.GONE);
        } else {
            tel.setText(b.getLinkPhone());
        }
        if (TextUtils.isEmpty(b.getLinkEmail())) {
            email.setVisibility(View.GONE);
        } else {
            email.setText(b.getLinkEmail());
        }
        /*
         * 车次信息
         */
        traincode.setText(route.getTrainCode());
        start_station.setText(route.getFromStation());
        start_date.setText(route.getTravelTime().substring(5) + " " + TimeUtils.getTomorrowWeekDay(route.getTravelTime()));
        start_time.setText(route.getFromTime());
        end_date.setText(TimeUtils.caculateDate(route.getTravelTime(), route.getCosttime(), "MM-dd")
                + " " + TimeUtils.getTomorrowWeekDay((TimeUtils.caculateDate(route.getTravelTime(),
                route.getCosttime(), "yyyy-MM-dd"))));
        end_time.setText(route.getArriveTime());
        end_station.setText(route.getArriveStation());
        seattype.setText(users.get(0).getSeatType());
        String runTime = route.getRunTime();
        run_time.setText(runTime == null ? "--" : (runTime.replace(":", "时") + "分"));
        String bookpolicy = b.getBookpolicy();
        weiItem.setText(bookpolicy == null ? "" : bookpolicy.endsWith(",") ? bookpolicy.substring(0,
                bookpolicy.length() - 1) : bookpolicy);

        psgs_lv.setAdapter(adapter_psg);
        approve_lv.setAdapter(adapter_approve_state);
        weiReason.setText(b.getWbreason());
        costCenter.setText(b.getCostname());

        if (TextUtils.isEmpty(b.getLinkAddress())) {
            addr.setVisibility(View.GONE);
        } else {
            addr.setVisibility(View.VISIBLE);
            addr.setContent(b.getLinkAddress());
        }
//        applyNo.setText(b.getShenqingno());
        item.setText(b.getProname());
        reason.setText(b.getChailvitem());
        int approvestatus = b.getApprovestatus();
        int status = b.getStatus();
        if (approvestatus != 0 || status != TICKET_STATUS_YIDINGZUO) {
            weiItem.setHint("");
            item.setHint("");
            weiReason.setHint("");
            costCenter.setHint("");
            reason.setHint("");
        }
    }

    private void setEditable(boolean b) {
        if (b) {
            item_approveinfo.setVisibility(View.VISIBLE);
            arrow_costCenter.setVisibility(View.VISIBLE);
            arrow_item.setVisibility(View.VISIBLE);
            arrow_weiReason.setVisibility(View.VISIBLE);
            reason.setFocusable(true);
//            applyNo.setFocusable(true);
//            applyNo.setFocusableInTouchMode(true);
            reason.setFocusableInTouchMode(true);
        } else {
            arrow_costCenter.setVisibility(View.GONE);
            arrow_item.setVisibility(View.GONE);
            arrow_weiReason.setVisibility(View.GONE);
            reason.setFocusable(false);
//            applyNo.setFocusable(false);
        }
    }

    @Override
    public void setWeiReason(String name) {
        weiReason.setText(name);
    }

    @Override
    public void setProject(String name) {
        item.setText(name);
    }


    @Override
    public void setVisibility(TrainOrderDetailBean b) {
        setEditable(false);
        /**
         * 根据状态，对显示进行改变
         */
        int status = b.getStatus();//票的状态
        int approveStatus = b.getApprovestatus();//审批状态
        boolean isMonthPay = b.getPayType().equals("1");
        /*
        switch中，处理的是状态的显示、按钮的隐现
         */
        switch (status) {
            case TICKET_STATUS_WEIDINGZUO:
                commit.setVisibility(View.GONE);
                cancle.setVisibility(View.GONE);

                String s = "未订座";
                if (approveStatus == APPROVE_STATUS_DAISONGSHEN) {
                    s += "|待审批";
                }
                if (approveStatus == APPROVE_STATUS_WUXUSHENPI) {
                    s += "|无需审批";
                }
                commit.setVisibility(View.GONE);
                cancle.setVisibility(View.GONE);
                state.setText(s);
                break;
            case TICKET_STATUS_YIDINGZUO:
                if (approveStatus == APPROVE_STATUS_DAISONGSHEN) {//待提交审批
                    setEditable(true);
                    commit.setText("送审");
                    cancle.setText("取消");
                    state.setText("已订座|待送审");
                    pTrainOrderDetail.setmOrderStatus(PTrainOrderDetail.STATUS_DAITIJIAO);
                }
                if (approveStatus == APPROVE_STATUS_SHENPITONGGUO || approveStatus == APPROVE_STATUS_WUXUSHENPI) {//审批通过或者不需要审批
                    commit.setText(isMonthPay ? "确认出票" : "支付");
                    cancle.setText("取消订单");
                    state.setText("待支付");
//                    if (approveStatus == APPROVE_STATUS_SHENPITONGGUO) {
//                        state.setText("已订座|已审批");
//                    } else {
//                        state.setText("已订座|无需审批");
//                    }
                    pTrainOrderDetail.setmOrderStatus(STATUS_DAICHUPIAO);
                }
                if (approveStatus == APPROVE_STATUS_SHENPIFOUJUE || approveStatus == APPROVE_STATUS_SHENPIZHONG) {
                    commit.setVisibility(View.GONE);
                    cancle.setText("取消订单");
                    if (approveStatus == APPROVE_STATUS_SHENPIFOUJUE) {
                        state.setText("已订座|审批否决");
                    } else {
                        state.setText("已订座|审批中");
                    }
                    pTrainOrderDetail.setmOrderStatus(PTrainOrderDetail.STATUS_YIZHANZUO);
                }
                break;
            case TICKET_STATUS_YICHUPIAO:
                commit.setText("退票");
                cancle.setText("改签");
                state.setText("已出票");
                pTrainOrderDetail.setmOrderStatus(PTrainOrderDetail.STATUS_YICHUPIAO);
                break;
            case TICKET_STATUS_YIQUXIAO:
                commit.setVisibility(View.GONE);
                cancle.setVisibility(View.GONE);
                state.setText("已取消");
                pTrainOrderDetail.setmOrderStatus(PTrainOrderDetail.STATUS_YIQUXIAO);
                break;
            case TICKET_STATUS_DINGZUOSHIBAI:
                commit.setVisibility(View.GONE);
                cancle.setVisibility(View.GONE);
                state.setText("订座失败");
                break;
            case TICKET_STATUS_CHUPIAOSHIBAI:
                commit.setVisibility(View.GONE);
                cancle.setVisibility(View.GONE);
                state.setText("出票失败");
                break;
            case TICKET_STATUS_CHUPIAOZHONG:
                state.setText("出票中");
                commit.setVisibility(View.GONE);
                cancle.setVisibility(View.GONE);
                break;
        }
        if (b.getWeibeiflag() == 0) {//没有违背
            item_weiItem.setVisibility(View.GONE);
            item_weiReason.setVisibility(View.GONE);
        } else {
            item_weiItem.setVisibility(View.VISIBLE);
            item_weiReason.setVisibility(View.VISIBLE);
        }
        //根据状态，进行设置点击事件
        if (approveStatus == APPROVE_STATUS_DAISONGSHEN && status == TICKET_STATUS_YIDINGZUO) {//已订座且待审批的状态
            costCenter.setOnClickListener(this);
            item.setOnClickListener(this);
            weiReason.setOnClickListener(this);
        } else {
            costCenter.setOnClickListener(null);
            item.setOnClickListener(null);
            weiReason.setOnClickListener(null);
        }
        //设置审批状态的显示情况
        if (approveStatus == APPROVE_STATUS_SHENPITONGGUO || approveStatus == APPROVE_STATUS_SHENPIZHONG
                || approveStatus == APPROVE_STATUS_SHENPIFOUJUE) {
            item_approveState.setVisibility(View.VISIBLE);
        } else {
            item_approveState.setVisibility(View.GONE);
        }
        if (approveStatus == APPROVE_STATUS_DAISONGSHEN && status == TICKET_STATUS_YIDINGZUO) {
            item_approve_chose.setVisibility(View.VISIBLE);
        } else {
            item_approve_chose.setVisibility(View.GONE);
        }

        /*
        如果还在占座，或者无需审批，审批的信息就不用显示
         */
        if (status == TICKET_STATUS_DINGZUOZHONG || approveStatus == APPROVE_STATUS_WUXUSHENPI) {
            item_approveinfo.setVisibility(View.GONE);
            setApproveVisibilityByComSetting();
        } else {
            item_approveinfo.setVisibility(View.VISIBLE);
        }
        /*
        如果已经取消，或者订票失败了，让下面不可以编辑
         */
        if (status == TICKET_STATUS_YIQUXIAO || status == TICKET_STATUS_DINGZUOSHIBAI) {
            setEditable(false);
        }
        ///////////////////////////////////////
        if (TextUtils.isEmpty(pTrainOrderDetail.getmBean().getShenqingno())) {
            item_applyNo.setVisibility(View.GONE);
        }
    }

    private void setApproveVisibilityByComSetting() {
//        MyApplication.mComSettingBean
    }

    @Override
    public void setAdapter(final ChoseApproveLevelAdapter adapter_approve_chose) {
        approveLevel_lv.setAdapter(adapter_approve_chose);
        approveLevel_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<ApproveLevelBean> list = adapter_approve_chose.getList();
                for (int i = 0; i < list.size(); i++) {
                    if (i == position) {
                        list.get(i).setCheck(true);
                    } else {
                        list.get(i).setCheck(false);
                    }
                }
                pTrainOrderDetail.setmCurrentPosition_approve(position);
                adapter_approve_chose.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plane_detail_food:
                pTrainOrderDetail.toNotice();
                break;
            case R.id.train_order_detail_costCenter:
                pTrainOrderDetail.showPop(PTrainOrderDetail.TRAIN_ORDER_DETAIL_POP_COSTCENTER);
                break;
            case R.id.train_order_detail_item:
                pTrainOrderDetail.showPop(PTrainOrderDetail.TRAIN_ORDER_DETAIL_POP_ITEM);
                break;
            case R.id.train_order_detail_weiReason:
                pTrainOrderDetail.showPop(PTrainOrderDetail.TRAIN_ORDER_DETAIL_POP_WEIREASON);
                break;
            case R.id.train_order_detail_bt1:
                switch (pTrainOrderDetail.getmOrderStatus()) {
                    case PTrainOrderDetail.STATUS_DAITIJIAO:
                        prepareToCommit();//提交审批
                        break;
                    case PTrainOrderDetail.STATUS_DAICHUPIAO:
                        pTrainOrderDetail.payToGetTicket();
                        break;
                    case PTrainOrderDetail.STATUS_YICHUPIAO:
                        pTrainOrderDetail.returnTicket();
                        break;
                }
                break;
            case R.id.train_order_detail_bt2:
                if (pTrainOrderDetail.getmBean().getStatus() == TICKET_STATUS_YIDINGZUO) {
                    pTrainOrderDetail.cancleOrder();
                }
                if (pTrainOrderDetail.getmBean().getStatus() == TICKET_STATUS_YICHUPIAO) {
                    pTrainOrderDetail.alterTicket();
                }
                break;
            case R.id.train_order_detail_click_bottom:
                pTrainOrderDetail.showPriceDialog(priceDetail.getMeasuredHeight());
                LogFactory.d("getMeasuredHeight" + priceDetail.getMeasuredHeight());
                break;
        }
    }

    /**
     * 提交审批
     */
    private void prepareToCommit() {
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

    //读取填写信息，并提交审批
    private int getConfig() {
        MyList<ComSettingBean.ProductSetBean.ProconfvalueBean> settings = MyApplication.mComSettingBean.getProductSet().getProconfValue();
//        String applyNoStr = applyNo.getText().toString().trim();
        String reasonStr = reason.getText().toString().trim();
        String weiReasonStr = weiReason.getText().toString().trim();
//        //出差单号必填但是没填
//        if (settings.get("travelorder").equals("1") && TextUtils.isEmpty(applyNoStr)) {
//            return -1;
//        }
        //成本中心必填但是没填
        if (settings.get("costcenter").equals("1")
                && TextUtils.isEmpty(costCenter.getText().toString().trim())) {
            return -2;
        }
        //项目信息必填但是没填
        if (settings.get("projectinfo").equals("1") && TextUtils.isEmpty(item.getText().toString().trim())) {
            return -3;
        }
        //出差事由必填但是没填
        if (settings.get("travelreason").equals("1") && TextUtils.isEmpty(reasonStr)) {
            return -4;
        }
        //是违背的单子，但是没有填写违背的原因
        if (pTrainOrderDetail.getmBean().getWeibeiflag() == 1
                && TextUtils.isEmpty(weiReason.getText().toString().trim())) {
            return -5;
        }
        if (pTrainOrderDetail.getmBean().getApprovestatus() == APPROVE_STATUS_DAISONGSHEN
                && pTrainOrderDetail.getmCurrentPosition_approve() == -1) {
            return -6;
        }

        pTrainOrderDetail.commit(reasonStr, weiReasonStr);
        return 1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MyApplication.getApplication().getmMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                KeyBoardUtils.closeKeybord(TrainOrderDetailActivity.this);
            }
        }, 100);
        if (requestCode == Constant.ACTIVITY_TRAIN_ORDER_DETAIL_FLAG &&//成本中心返回
                resultCode == Constant.ACTIVITY_COSTCENTERSELECT_FLAG) {
            costCenter.setText(data.getStringExtra("name"));
            pTrainOrderDetail.setCostCenterId(data.getIntExtra("id", 0));
            pTrainOrderDetail.setCostCenterName(data.getStringExtra("name"));
        }
        if (requestCode == Constant.ACTIVITY_TRAIN_ORDER_DETAIL_FLAG &&//项目中心返回
                resultCode == Constant.ACTIVITY_PROJECTSELECT_FLAG) {
            item.setText(data.getStringExtra("name"));
            pTrainOrderDetail.setProjectId(data.getIntExtra("id", 0));
            pTrainOrderDetail.setProjectName(data.getStringExtra("name"));
        }
    }
}
