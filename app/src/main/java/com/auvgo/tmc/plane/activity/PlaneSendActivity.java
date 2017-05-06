package com.auvgo.tmc.plane.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.ChoseApproveLevelAdapter;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.common.CostCenterActivity;
import com.auvgo.tmc.common.ProjectAcitivity;
import com.auvgo.tmc.p.PPlanSend;
import com.auvgo.tmc.plane.bean.PlaneOrderDetailBean;
import com.auvgo.tmc.train.bean.ApproveLevelBean;
import com.auvgo.tmc.train.interfaces.ViewManager_planesend;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.KeyBoardUtils;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.views.ItemView;
import com.auvgo.tmc.views.MyDialog;

import java.util.List;

public class PlaneSendActivity extends BaseActivity implements View.OnClickListener, ViewManager_planesend {

    private TextView ticket_status, approve_status, order_detail, priceall, commit, cancel;
    private ItemView
//            applyNo,
            costCenter, project, reason, weiItem, weiReason;
    private ListView lv;
    private PPlanSend pPlanSend;
    private View item_costCenter, item_project, item_weiReason;
//            item_applyNo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_plane_send;
    }

    @Override
    protected void initData() {
        pPlanSend = new PPlanSend(this, this);
        pPlanSend.init(getIntent());
    }

    @Override
    protected void findViews() {
        ticket_status = (TextView) findViewById(R.id.plane_send_detail_ticket_status);
        approve_status = (TextView) findViewById(R.id.plane_send_detail_approve_status);
        order_detail = (TextView) findViewById(R.id.plane_send_detail_order_detail);
        priceall = (TextView) findViewById(R.id.plane_send_detail_priceall);
        commit = (TextView) findViewById(R.id.plane_send_detail_commit);
        cancel = (TextView) findViewById(R.id.plane_send_detail_cancle);
//        applyNo = (ItemView) findViewById(R.id.plane_send_detail_applyNo);
        costCenter = (ItemView) findViewById(R.id.plane_send_detail_costcenter);
        project = (ItemView) findViewById(R.id.plane_send_detail_project);
        reason = (ItemView) findViewById(R.id.plane_send_detail_reason);
        weiItem = (ItemView) findViewById(R.id.plane_send_detail_weiItem);
        weiReason = (ItemView) findViewById(R.id.plane_send_detail_weiReason);
        lv = (ListView) findViewById(R.id.approve_chose_lv);
        item_costCenter = findViewById(R.id.plane_send_detail_item_costcenter);
        item_project = findViewById(R.id.plane_send_detail_item_project);
        item_weiReason = findViewById(R.id.plane_send_detail_item_weiReason);
//        item_applyNo = findViewById(R.id.plane_send_detail_item_applyNo);
    }

    @Override
    protected void initView() {
        setCostCenterAndProjectVisibility(item_costCenter, item_project);
    }

    @Override
    protected void initListener() {
        order_detail.setOnClickListener(this);
        item_costCenter.setOnClickListener(this);
        item_project.setOnClickListener(this);
        item_weiReason.setOnClickListener(this);
//        item_applyNo.setOnClickListener(this);
        commit.setOnClickListener(this);
        cancel.setOnClickListener(this);
        priceall.setOnClickListener(this);
    }

    @Override
    protected void setViews() {

    }

    @Override
    protected void getData() {
        super.getData();
        pPlanSend.getApproveLevels();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plane_send_detail_order_detail://跳转到订单详情
                pPlanSend.jumpToDetail();
                break;
            case R.id.plane_send_detail_priceall://弹出价格详情的弹窗:
                AppUtils.closeSoftInput(this);
                pPlanSend.showPriceDetail();
                break;
            case R.id.plane_send_detail_commit://提交
                pPlanSend.commit();
                break;
            case R.id.plane_send_detail_cancle://取消订单
                showDialog("取消", "确定", "确定取消订单吗？", new MyDialog.OnButtonClickListener() {
                    @Override
                    public void onLeftClick() {
                    }

                    @Override
                    public void onRightClick() {
                        pPlanSend.cancle();
                    }
                });
                break;
            case R.id.plane_send_detail_item_costcenter:
                startActivityForResult(new Intent(this, CostCenterActivity.class),
                        Constant.ACTIVITY_PLANE_SEND_FLAG);
                break;
            case R.id.plane_send_detail_item_project:
                startActivityForResult(new Intent(this, ProjectAcitivity.class),
                        Constant.ACTIVITY_PLANE_SEND_FLAG);
                break;
            case R.id.plane_send_detail_item_weiReason:
                pPlanSend.chowWeiPop();
                break;
        }
    }

    @Override
    public String getApplyNoStr() {
//        return applyNo.getContent().trim();
        return "";
    }

    @Override
    public String getReasonStr() {
        return reason.getContent().trim();
    }

    @Override
    public String getWeiReasonStr() {
        return weiReason.getContent().trim();
    }

    @Override
    public CharSequence getCostCenterStr() {
        return costCenter.getContent().trim();
    }

    @Override
    public CharSequence getProjectStr() {
        return project.getContent().trim();
    }


    @Override
    public void updateViews(final ChoseApproveLevelAdapter adapter, PlaneOrderDetailBean mBean) {
        /*
        审批级别的显示
         */
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<ApproveLevelBean> list = adapter.getList();
                for (int i = 0; i < list.size(); i++) {
                    if (i == position) {
                        list.get(i).setCheck(true);
                    } else {
                        list.get(i).setCheck(false);
                    }
                }
                pPlanSend.setmCurrentPosition_approve(position);
                adapter.notifyDataSetChanged();
            }
        });
        /*
        违背状态的判断，并控制控件的显示与否
         */
        if (mBean.getWeibeiflag() == 0) {
            weiItem.setVisibility(View.GONE);
            item_weiReason.setVisibility(View.GONE);
        }
        ticket_status.setText(MUtils.getOrgTicketStateByCode(mBean.getStatus()));
        approve_status.setText(MUtils.getApproveStateByCode(mBean.getApprovestatus()));
        weiItem.setContent(mBean.getBookpolicy());
        pPlanSend.caculatePrice();
    }

    @Override
    public void setTotalPrice(double totalPrice) {
        priceall.setText(pPlanSend.getmBean().getTotalprice() + "");
    }

    @Override
    public void setWbReason(String wbReason) {
        weiReason.setContent(wbReason);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MyApplication.getApplication().getmMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                KeyBoardUtils.closeKeybord(PlaneSendActivity.this);
            }
        }, 100);
        if (requestCode == Constant.ACTIVITY_PLANE_SEND_FLAG &&//成本中心返回
                resultCode == Constant.ACTIVITY_COSTCENTERSELECT_FLAG) {
            costCenter.setContent(data.getStringExtra("name"));
            pPlanSend.setCostCenterId(data.getIntExtra("id", 0));
            pPlanSend.setCostCenterName(data.getStringExtra("name"));
        }
        if (requestCode == Constant.ACTIVITY_PLANE_SEND_FLAG &&//项目中心返回
                resultCode == Constant.ACTIVITY_PROJECTSELECT_FLAG) {
            project.setContent(data.getStringExtra("name"));
            pPlanSend.setProjectId(data.getIntExtra("id", 0));
            pPlanSend.setProjectName(data.getStringExtra("name"));
        }
    }
}
