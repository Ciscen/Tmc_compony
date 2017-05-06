package com.auvgo.tmc.personalcenter.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;

import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.ItemView;
import com.auvgo.tmc.views.MyPickerView;

import java.util.ArrayList;
import java.util.List;

public class OrderFilterActivity extends BaseActivity implements View.OnClickListener {

    private ItemView name_iv, start_iv, stop_iv, status_iv;
    private View query_tv;
    private CheckBox bookDate_cb, offDate_cb;
    private String mStartDate;//起始日期
    private String mEndDate;
    private String mName;//名字
    private String mDateType = "0";
    private int mOrderStatus = -1;//-1表示全部
    private int mCurrentPosition;//表示当前viewpager正在显示页面的position
    private View cover;
    private String clazz;
    private List<? extends MyPickerView.Selection> status;
    private int mSelectedStatus;//订单状态的选择位置
    public static final int FILT_APPROVE_STATE = 0;
    public static final int FILT_ORDER_STATE = 1;
    public static final int FILT_PAY_STATE = 2;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_filter;
    }

    @Override
    protected void initData() {
        mCurrentPosition = getIntent().getIntExtra("position", 0);
        clazz = getIntent().getStringExtra("from");
        status = getSelectBeans();
    }

    @Override
    protected void findViews() {
        cover = findViewById(R.id.order_filter_cover);
        name_iv = (ItemView) findViewById(R.id.order_filter_name);
        start_iv = (ItemView) findViewById(R.id.order_filter_start_date);
        stop_iv = (ItemView) findViewById(R.id.order_filter_end_date);
        status_iv = (ItemView) findViewById(R.id.order_filter_status);
        query_tv = findViewById(R.id.order_filter_query);
        bookDate_cb = (CheckBox) findViewById(R.id.order_filter_bookdate_cb);
        offDate_cb = (CheckBox) findViewById(R.id.order_filter_offdate_cb);
    }

    @Override
    protected void initView() {
//        start_iv.setContent(TimeUtils.getYestoday(TimeUtils.getToday()));
//        stop_iv.setContent(TimeUtils.getToday());
        cover.setVisibility(View.VISIBLE);
        switch (clazz) {

            case Constant.TRAIN:
                offDate_cb.setText("发车日期");
                break;
            case Constant.HOTEL:
                offDate_cb.setText("入住日期");
                break;
        }
    }

    @Override
    protected void initListener() {
        bookDate_cb.setOnClickListener(this);
        offDate_cb.setOnClickListener(this);
        start_iv.setOnClickListener(this);
        stop_iv.setOnClickListener(this);
        status_iv.setOnClickListener(this);
        query_tv.setOnClickListener(this);
    }

    @Override
    protected void setViews() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_filter_bookdate_cb:
                bookDate_cb.setChecked(true);
                mDateType = "0";
                setStartAndStopText("预订始", "预订止");
                offDate_cb.setChecked(false);
                break;
            case R.id.order_filter_offdate_cb:
                mDateType = "1";
                switch (clazz) {
                    case Constant.HOTEL:
                        setStartAndStopText("入住始", "入住止");
                        break;
                    case Constant.TRAIN:
                        setStartAndStopText("开车始", "开车止");
                        break;
                    case Constant.PLANE:
                        setStartAndStopText("起飞始", "起飞止");
                        break;
                }
                bookDate_cb.setChecked(false);
                offDate_cb.setChecked(true);
                break;
            case R.id.order_filter_start_date:
                DialogUtil.showDatePicker(this, mStartDate, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mStartDate = TimeUtils.getyyyy_MM_ddByDate(year, month, dayOfMonth);
                        if (!TextUtils.isEmpty(mEndDate) && TimeUtils.compareDay(mStartDate, mEndDate) < 0) {
                            ToastUtils.showTextToast("起始日期不能晚于出发日期");
                        } else {
                            start_iv.setContent(mStartDate + "  " + TimeUtils.getTomorrowWeekDay(mStartDate));
                        }
                    }
                });
                break;
            case R.id.order_filter_end_date:
                DialogUtil.showDatePicker(this, mEndDate, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mEndDate = TimeUtils.getyyyy_MM_ddByDate(year, month, dayOfMonth);
                        if (!TextUtils.isEmpty(mStartDate) && TimeUtils.compareDay(mStartDate, mEndDate) < 0) {
                            ToastUtils.showTextToast("出发日期不能晚于起始日期");
                        } else {
                            stop_iv.setContent(mEndDate + "  " + TimeUtils.getTomorrowWeekDay(mEndDate));
                        }
                    }
                });
                break;
            case R.id.order_filter_status:
                DialogUtil.showPickerPopWithSureHeight(this, "订单类型", mSelectedStatus, status,
                        new MyPickerView.OnPickerViewSure() {
                    @Override
                    public void onSingleSureClick(int p) {
                        mSelectedStatus = p;
                        status_iv.setContent(status.get(p).getName());
                    }

                            @Override
                            public void onMultiSureClick(List<Integer> s) {

                            }
                        });
//
//
//                List<SelectionBean> sbs = getSelectBeans();
//                final PickerListAdapter adapter = new PickerListAdapter(this, sbs, R.layout.item_picker_lv);
//                DialogUtil.showPickerView(this, cover, adapter, new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        List<? extends SelectionBean> list = adapter.getList();
//                        for (int i = 0; i < list.size(); i++) {
//                            if (i == position) {
//                                list.get(i).setChecked(true);
//                            } else {
//                                list.get(i).setChecked(false);
//                            }
//                        }
//                        adapter.notifyDataSetChanged();
//                    }
//                }, new DialogUtil.OnPickerListener() {
//                    @Override
//                    public void onSure() {
//                        List<? extends SelectionBean> list = adapter.getList();
//                        for (int i = 0; i < list.size(); i++) {
//                            if (list.get(i).isChecked()) {
//                                mOrderStatus = i - 1;
//                                status_iv.setContent(list.get(i).getName());
//                                return;
//                            }
//                        }
//                    }
//                });
                break;
            case R.id.order_filter_query:
                mName = name_iv.getContent();
                Intent intent = new Intent();
                intent.putExtra("name", AppUtils.getNoNullStr(mName));
                intent.putExtra("dateType", AppUtils.getNoNullStr(mDateType));
                intent.putExtra("startDate", AppUtils.getNoNullStr(mStartDate));
                intent.putExtra("endDate", AppUtils.getNoNullStr(mEndDate));
//                intent.putExtra("orderStatus", mOrderStatus == -1 ? "" : mOrderStatus + "");
//                intent.putExtra("currentPosition", mCurrentPosition + "");
                intent.putExtra("filterBean", (FilterBean) status.get(mSelectedStatus));
                setResult(Constant.ACTIVITY_TRAIN_ORDER_FILTER_FLAG, intent);
                finish();
                break;
        }

    }

    private void setStartAndStopText(String s1, String s2) {
        start_iv.setTitle(s1);
        stop_iv.setTitle(s2);
    }

    private List<? extends MyPickerView.Selection> getSelectBeans() {
        List<MyPickerView.Selection> sbs = new ArrayList<>();
        if (clazz.equals(Constant.HOTEL)) {
            sbs.add(new FilterBean("全部", FILT_APPROVE_STATE, "", true));
            sbs.add(new FilterBean("待审批", FILT_APPROVE_STATE, "0", true));
            sbs.add(new FilterBean("审批通过", FILT_APPROVE_STATE, "1", false));
            sbs.add(new FilterBean("审批否决", FILT_APPROVE_STATE, "2", false));
//            sbs.add(new FilterBean("无需审批", FILT_APPROVE_STATE, "3", false));
            sbs.add(new FilterBean("审批中", FILT_APPROVE_STATE, "4", false));

            sbs.add(new FilterBean("待担保", FILT_ORDER_STATE, "0", false));
//            sbs.add(new FilterBean("担保失败", FILT_ORDER_STATE, "1", false));
            sbs.add(new FilterBean("待确认", FILT_ORDER_STATE, "2", false));
//            sbs.add(new FilterBean("确认中", FILT_ORDER_STATE, "3", false));
            sbs.add(new FilterBean("已确认", FILT_ORDER_STATE, "4", false));
//            sbs.add(new FilterBean("确认失败", FILT_ORDER_STATE, "5", false));
            sbs.add(new FilterBean("已取消", FILT_ORDER_STATE, "6", false));
//            sbs.add(new FilterBean("已提交", FILT_ORDER_STATE, "7", false));
//            sbs.add(new FilterBean("担保中", FILT_ORDER_STATE, "8", false));

            sbs.add(new FilterBean("待支付", FILT_PAY_STATE, "0", false));

//            sbs.add(new FilterBean("支付成功", FILT_PAY_STATE, "1", false));
//            sbs.add(new FilterBean("支付失败", FILT_PAY_STATE, "2", false));
//            sbs.add(new FilterBean("支付中", FILT_PAY_STATE, "3", false));

            return sbs;
        }
        if (mCurrentPosition == 0) {
            sbs.add(new FilterBean("全部", FILT_APPROVE_STATE, "", true));
            sbs.add(new FilterBean("待审批", FILT_APPROVE_STATE, "0", false));
            sbs.add(new FilterBean("审批通过", FILT_APPROVE_STATE, "1", false));
            sbs.add(new FilterBean("审批否决", FILT_APPROVE_STATE, "2", false));

            sbs.add(new FilterBean("订座中", FILT_ORDER_STATE, "0", true));
            sbs.add(new FilterBean("已订座", FILT_ORDER_STATE, "1", false));
            sbs.add(new FilterBean("已出票", FILT_ORDER_STATE, "2", false));
            sbs.add(new FilterBean("已取消", FILT_ORDER_STATE, "3", false));
            sbs.add(new FilterBean("订座失败", FILT_ORDER_STATE, "4", false));
            sbs.add(new FilterBean("出票失败", FILT_ORDER_STATE, "5", false));
            sbs.add(new FilterBean("出票中", FILT_ORDER_STATE, "6", false));
        } else if (mCurrentPosition == 1) {
            sbs.add(new FilterBean("全部", FILT_ORDER_STATE, "", true));
            sbs.add(new FilterBean("未退票", FILT_ORDER_STATE, "0", false));
            sbs.add(new FilterBean("退票中", FILT_ORDER_STATE, "1", false));
            sbs.add(new FilterBean("已退票", FILT_ORDER_STATE, "2", false));
            sbs.add(new FilterBean("退票失败", FILT_ORDER_STATE, "3", false));
        } else if (mCurrentPosition == 2) {
            sbs.add(new FilterBean("全部", FILT_ORDER_STATE, "", true));
            sbs.add(new FilterBean("未改签", FILT_ORDER_STATE, "0", false));
            sbs.add(new FilterBean("改签中", FILT_ORDER_STATE, "1", false));
            sbs.add(new FilterBean("已改签", FILT_ORDER_STATE, "2", false));
            sbs.add(new FilterBean("改签失败", FILT_ORDER_STATE, "3", false));
        }
        return sbs;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.ACTIVITY_HOME_FILTER1_FLAG &&
                resultCode == Constant.ACTIVITY_CALENDAR_FLAG) {
            mStartDate = data.getStringExtra("date");
            if (!TextUtils.isEmpty(mEndDate)) {
                if (TimeUtils.compareDay(mStartDate, mEndDate) < 0) {
                    mStartDate = mEndDate;
                    ToastUtils.showTextToast("起始日期不能晚于出发日期");
                }
            }
            start_iv.setContent(mStartDate + "  " + TimeUtils.getTomorrowWeekDay(mStartDate));
        }
        if (requestCode == Constant.ACTIVITY_HOME_FILTER2_FLAG &&
                resultCode == Constant.ACTIVITY_CALENDAR_FLAG) {
            mEndDate = data.getStringExtra("date");
            if (!TextUtils.isEmpty(mStartDate)) {
                int i = TimeUtils.compareDay(mStartDate, mEndDate);
                if (i < 0) {
                    ToastUtils.showTextToast("结束日期不能早于出发日期");
                    mEndDate = mStartDate;
                }
            }
            stop_iv.setContent(mEndDate + "  " + TimeUtils.getTomorrowWeekDay(mEndDate));
        }
    }

    public static class FilterBean extends MyPickerView.Selection implements Parcelable {
        private String name;
        private int type;
        private String code;

        public FilterBean(String name, int type, String code, boolean isChecked) {
            this.name = name;
            this.type = type;
            this.code = code;
            this.isChecked = isChecked;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeInt(this.type);
            dest.writeString(this.code);
            dest.writeByte(isChecked ? (byte) 1 : (byte) 0);
        }

        protected FilterBean(Parcel in) {
            this.name = in.readString();
            this.type = in.readInt();
            this.code = in.readString();
            this.isChecked = in.readByte() != 0;
        }

        public static final Parcelable.Creator<FilterBean> CREATOR = new Parcelable.Creator<FilterBean>() {
            @Override
            public FilterBean createFromParcel(Parcel source) {
                return new FilterBean(source);
            }

            @Override
            public FilterBean[] newArray(int size) {
                return new FilterBean[size];
            }
        };
    }
}
