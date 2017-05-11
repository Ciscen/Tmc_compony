package com.auvgo.tmc.hotel.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.ChoseApproveLevelAdapter;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.base.MyList;
import com.auvgo.tmc.common.CostCenterActivity;
import com.auvgo.tmc.common.ProjectAcitivity;
import com.auvgo.tmc.hotel.bean.HotelOrderDetailBean;
import com.auvgo.tmc.train.bean.ApproveLevelBean;
import com.auvgo.tmc.train.bean.ComSettingBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.requestbean.RequestSendApproveBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.ItemView;
import com.auvgo.tmc.views.MyDialog;
import com.auvgo.tmc.views.MyPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class HotelSendActivity extends BaseActivity {
    @BindView(R.id.hotel_send_detail_state)
    TextView state;
    @BindView(R.id.hotel_send_detail_waitingtosend)
    TextView waitingtosend;
    @BindView(R.id.hotel_little_detail_orderno_top)
    TextView order_tv;
    @BindView(R.id.hotel_send_price_guarantee)
    TextView price_guarantee;
    @BindView(R.id.hotel_send_price_pay)
    TextView price_pay;
    @BindView(R.id.hotel_send_price_name)
    TextView price_name;
    //    @BindView(R.id.hotel_send_detail_applyNo)
//    ItemView applyNo;
    @BindView(R.id.hotel_send_detail_costcenter)
    ItemView costCenter;
    @BindView(R.id.hotel_send_detail_project)
    ItemView projectCenter;
    @BindView(R.id.hotel_send_detail_reason)
    ItemView chuchaiReason;
    @BindView(R.id.hotel_send_detail_weiItem)
    ItemView weiItem;
    @BindView(R.id.hotel_send_detail_weiReason)
    ItemView weiReason;
    @BindView(R.id.hotel_send_guarantee_price_ll)
    View guarantee_ll;//担保的价格
    @BindView(R.id.approve_chose_lv)
    ListView lv;
    @BindView(R.id.hotel_send_detail_item_project)
    View item_project;
    @BindView(R.id.hotel_send_detail_item_costcenter)
    View item_costCenter;
    private String orderNo;
    private HotelOrderDetailBean mBean;
    private int mCurrentPosition = -1;//当前选择的违背原因
    private ChoseApproveLevelAdapter adapter;
    /*
    送审需要的数据
     */
    private String costCenterName = "";
    private String costCenterId = "";
    private String projectName = "";
    private String projectId = "";
    private String approveId = "-1";//审批级别的id

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hotel_send;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        orderNo = getIntent().getStringExtra("orderNo");
    }

    @Override
    protected void initView() {
        setCostCenterAndProjectVisibility(item_costCenter, item_project);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setViews() {

    }

    @Override
    protected void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("orderno", orderNo);
        RetrofitUtil.getHotelOrderDetail(this, AppUtils.getJson(map), HotelOrderDetailBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    mBean = (HotelOrderDetailBean) o;
                    getApproves();
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    /*
    获取审批级别
     */
    private void getApproves() {
        Map<String, String> map = new HashMap<>();
        map.put("orderno", mBean.getOrderno());
        map.put("type", "book");
        RetrofitUtil.getHotelApprove(this, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    if (bean.getData() != null) {
                        Type typeToken = new TypeToken<List<ApproveLevelBean>>() {
                        }.getType();
                        Gson gson = new Gson();
                        List<ApproveLevelBean> albs = gson.fromJson(bean.getData(), typeToken);
                        adapter = new ChoseApproveLevelAdapter(HotelSendActivity.this, albs);
                        updateViews();
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
        state.setText(MUtils.getApproveStateByCode(mBean.getApprovestatus()));
//        order_tv.setText("订单号：" + mBean.getApprovalno());
        weiItem.setContent(mBean.getWbreason() + "");
        price_pay.setText("￥" + String.valueOf(mBean.getTotalPrice()));
        price_guarantee.setText("￥" + String.valueOf(mBean.getGuaranteeAmount()));
        price_name.setText(mBean.getPaymentType().equals("SelfPay") ? "到店付款" : "在线预付");
        guarantee_ll.setVisibility(mBean.getIsNeedGuarantee().equals("false") ? View.GONE : View.VISIBLE);
        lv.setAdapter(adapter);
        if (mBean.getWeibeiflag() == 0) {
            weiItem.setVisibility(View.GONE);
            weiReason.setVisibility(View.GONE);
        }
    }

    /*
    审批级别选择的时候的点击
     */
    @OnItemClick(R.id.approve_chose_lv)
    void onItemClick(int position) {
        approveId = String.valueOf(adapter.getList().get(position).getId());
        List<ApproveLevelBean> list = adapter.getList();
        for (int i = 0; i < list.size(); i++) {
            if (i == position) {
                list.get(i).setCheck(true);
            } else {
                list.get(i).setCheck(false);
            }
        }
        adapter.notifyDataSetChanged();
    }

    /*
    点击事件
     */
    @OnClick({R.id.hotel_send_detail_commit, R.id.hotel_send_detail_cancle
            , R.id.hotel_send_detail_project, R.id.hotel_send_detail_costcenter
            , R.id.hotel_send_detail_weiReason, R.id.hotel_send_detail_state_rl
            , R.id.hotel_book_click_pricedetail, R.id.hotel_little_detail_orderno_top})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.hotel_send_detail_project:
                startActivityForResult(new Intent(this, ProjectAcitivity.class),
                        Constant.ACTIVITY_HOTEL_SEND_FLAG);
                break;
            case R.id.hotel_send_detail_costcenter:
                startActivityForResult(new Intent(this, CostCenterActivity.class),
                        Constant.ACTIVITY_HOTEL_SEND_FLAG);
                break;
            case R.id.hotel_send_detail_weiReason:
                MUtils.choseWeireason(this, "hotel", mCurrentPosition, new MUtils.OnWeibeiListener() {
                    @Override
                    public void onSureClick(MyPickerView.Selection selection, int pos) {
                        mCurrentPosition = pos;
                        weiReason.setContent(selection.getName());
                    }
                });
                break;
            case R.id.hotel_send_detail_state_rl:
                Intent intent = new Intent(this, HotelLittleOrderDetailActivity.class);
                intent.putExtra("bean", mBean);
                startActivity(intent);
                break;
            case R.id.hotel_send_detail_commit:
                prepareToSendApprove();
                break;
            case R.id.hotel_send_detail_cancle:
                showDialog("取消", "确定", "确定取消订单？", new MyDialog.OnButtonClickListener() {
                    @Override
                    public void onLeftClick() {

                    }

                    @Override
                    public void onRightClick() {
                        cancel();
                    }
                });
                break;
            case R.id.hotel_book_click_pricedetail://显示价格详情的弹窗
                DialogUtil.showHotelPriceDialog(this, mBean);
                break;
            case R.id.hotel_little_detail_orderno_top:
                Intent i = new Intent(this, HotelLittleOrderDetailActivity.class);
                i.putExtra("bean", mBean);
                startActivity(i);
                break;
        }
    }

    /*
    订单取消
     */
    private void cancel() {
        Map<String, String> map = new HashMap<>();
        map.put("orderno", mBean.getOrderno());
        RetrofitUtil.cancelHotelOrder(this, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    DialogUtil.showDialog(HotelSendActivity.this, "提示", "确定", "", "取消成功", new MyDialog.OnButtonClickListener() {
                        @Override
                        public void onLeftClick() {
                            jumpToOrderList(Constant.HOTEL);
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

    /*
    送审
     */
    private void prepareToSendApprove() {
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

    private void sendApprove() {
        RequestSendApproveBean sab = new RequestSendApproveBean();
        sab.setApproveid(approveId);
        sab.setChailvitem(chuchaiReason.getContent());
        sab.setCostid(String.valueOf(costCenterId));
        sab.setCostname(costCenterName);
        sab.setOrderno(mBean.getOrderno());
        sab.setProid(String.valueOf(projectId));
        sab.setProname(projectName);
        sab.setWbreason(weiReason.getContent());
        RetrofitUtil.sendHotelOrderApprove(this, AppUtils.getJson(sab), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    ToastUtils.showTextToast("送审成功！");
                    jumpToOrderList(Constant.HOTEL);
                } else {
                    DialogUtil.showDialog(HotelSendActivity.this, "提示", "确定", "", "送审失败\n" + msg, null);
                }
                return true;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    private int getConfig() {
        MyList<ComSettingBean.ProductSetBean.ProconfvalueBean> settings =
                MyApplication.mComSettingBean.getProductSet().getProconfValue();
        String reasonStr = chuchaiReason.getContent();
        String weiReasonStr = weiReason.getContent();
        //出差单号必填但是没填
//        if (settings.get("travelorder").equals("1") && TextUtils.isEmpty(applyNoStr)) {
//            return -1;
//        }
        //成本中心必填但是没填
        if (settings.get("costcenter").equals("1")
                && TextUtils.isEmpty(costCenter.getContent())) {
            return -2;
        }
        //项目信息必填但是没填
        if (settings.get("projectinfo").equals("1") && TextUtils.isEmpty(projectCenter.getContent())) {
            return -3;
        }
        //出差事由必填但是没填
        if (settings.get("travelreason").equals("1") && TextUtils.isEmpty(reasonStr)) {
            return -4;
        }
        //是违背的单子，但是没有填写违背的原因
        if (mBean.getWeibeiflag() == 1
                && TextUtils.isEmpty(weiReason.getContent())) {
            return -5;
        }
        if (approveId.equals("-1")) {
            return -6;
        }
        showDialog("取消", "确定", "确定送审吗？", new MyDialog.OnButtonClickListener() {
            @Override
            public void onLeftClick() {
            }

            @Override
            public void onRightClick() {
                sendApprove();
            }
        });
        return 1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.ACTIVITY_HOTEL_SEND_FLAG &&//成本中心返回
                resultCode == Constant.ACTIVITY_COSTCENTERSELECT_FLAG) {
            costCenter.setContent(data.getStringExtra("name"));
            costCenterId = String.valueOf(data.getIntExtra("id", 0));
            costCenterName = data.getStringExtra("name");
        }
        if (requestCode == Constant.ACTIVITY_HOTEL_SEND_FLAG &&//项目中心返回
                resultCode == Constant.ACTIVITY_PROJECTSELECT_FLAG) {
            projectCenter.setContent(data.getStringExtra("name"));
            projectId = String.valueOf(data.getIntExtra("id", 0));
            projectName = data.getStringExtra("name");
        }
    }

}

