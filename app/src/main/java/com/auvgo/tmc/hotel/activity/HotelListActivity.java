package com.auvgo.tmc.hotel.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.hotel.bean.HotelListBean;
import com.auvgo.tmc.hotel.bean.RequestHotelQueryBean;
import com.auvgo.tmc.hotel.interfaces.ViewManager_hotellist;
import com.auvgo.tmc.p.PHotelList;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DensityUtils;
import com.auvgo.tmc.utils.DeviceUtils;
import com.auvgo.tmc.views.RefreshListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HotelListActivity extends BaseActivity implements ViewManager_hotellist,
        AdapterView.OnItemClickListener, RefreshListView.OnRefreshListener {

    @BindView(R.id.hotel_list_checkin_tv)
    TextView checkInDate;
    @BindView(R.id.hotel_list_checkout_tv)
    TextView checkOutDate;
    @BindView(R.id.hotel_list_keyword_tv)
    TextView keyword;
    @BindView(R.id.hotel_list_del)
    View del;
    @BindView(R.id.hotel_list_notice)
    TextView notice;
    @BindView(R.id.hotel_list_lv)
    RefreshListView lv;
    @BindView(R.id.hotel_list_ll)
    LinearLayout bottom_ll;
    @BindView(R.id.empty_view)
    View empty_view;

    private PHotelList pHotelList;
    private int MAX_NOTICE_VALUE = 50;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hotel_list;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        pHotelList = new PHotelList(this, this);
        pHotelList.init(getIntent());
        if (HotelFiltActivity.mBean != null) {
            HotelFiltActivity.mBean = null;
        }
    }

    @Override
    protected void initView() {
        lv.setEmptyView(empty_view);
        empty_view.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initListener() {
        lv.setOnItemClickListener(this);
        lv.setOnRefreshListener(this);
    }

    @Override
    protected void setViews() {
        RequestHotelQueryBean bean = pHotelList.getBean();
        keyword.setText(bean.getQueryText());
        checkInDate.setText(bean.getArrivalDate().substring(5));
        checkOutDate.setText(bean.getDepartureDate().substring(5));
    }

    @Override
    protected void getData() {
        super.getData();
        pHotelList.getHotelList();
    }

    @OnClick({R.id.hotel_list_bottom_filt, R.id.hotel_list_bottom_level,
            R.id.hotel_list_bottom_location, R.id.hotel_list_bottom_sort})
    void onBottomClick(View view) {
        switch (view.getId()) {
            case R.id.hotel_list_bottom_sort:
                pHotelList.onBottomClick(0);
                break;
            case R.id.hotel_list_bottom_level:
                pHotelList.onBottomClick(1);
                break;
            case R.id.hotel_list_bottom_location:
                pHotelList.onBottomClick(2);
                break;
            case R.id.hotel_list_bottom_filt:
                pHotelList.onBottomClick(3);
                break;
        }
    }

    @OnClick(R.id.hotel_list_back)
    void onBackClick() {
        finish();
    }

    @OnClick({R.id.hotel_list_cld, R.id.hotel_list_keyword_tv})
    void onCalendarClick(View view) {
        switch (view.getId()) {
            case R.id.hotel_list_cld:
                pHotelList.jumpTo(Constant.ACTIVITY_CALENDAR_FLAG);
                break;
            case R.id.hotel_list_keyword_tv:
                pHotelList.jumpTo(Constant.ACTIVITY_HOTEL_KEYWORD_FLAG);
                break;
        }
    }

    @OnClick(R.id.hotel_list_del)
    void onDelClick() {
        keyword.setText("");
        pHotelList.getBean().setQueryText("");
        pHotelList.getHotelList();
        setDelVisibility();
        pHotelList.checkBottomState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != Constant.ACTIVITY_HOTEL_LIST_FLAG) {
            return;
        }
        if (resultCode == Constant.ACTIVITY_CALENDAR_FLAG) {
            pHotelList.receiveCldData(data);
        } else if (resultCode == Constant.ACTIVITY_HOTEL_KEYWORD_FLAG) {
            pHotelList.receiveKeywordData(data);
        } else if (resultCode == Constant.ACTIVITY_HOTEL_LOCATION_FLAG) {
            pHotelList.receiveLocation(data);
        } else if (resultCode == Constant.ACTIVITY_HOTEL_FILT_FLAG) {
            pHotelList.receiveFiltData(data);
        }

    }

    @Override
    public void setDate() {
        checkInDate.setText(pHotelList.getBean().getArrivalDate().substring(5));
        checkOutDate.setText(pHotelList.getBean().getDepartureDate().substring(5));
    }

    @Override
    public void setKeyword() {
        keyword.setText(pHotelList.getBean().getQueryText());
        setDelVisibility();
    }

    @Override
    public void setAdapter() {
        lv.setAdapter(pHotelList.getAdapter());
    }

    @Override
    public void onLoadFinished() {
        lv.onRefreshComplete(true);
        if (pHotelList.getmBean() != null && !pHotelList.isLoadMore()) {
            int count = pHotelList.getmBean().getTotalCount();
            if (count >= MAX_NOTICE_VALUE) {
                notice.setVisibility(View.VISIBLE);
                notice.setText("共" + count + "条，建议您使用条件查询");
                MyApplication.getApplication().getmMainThreadHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideNotice();
                    }
                }, 1000);
            }
        }
    }

    private void hideNotice() {
        final int height = notice.getMeasuredHeight();
        ObjectAnimator va = ObjectAnimator.ofFloat(lv, "translationY", 0, -height);
        va.setDuration(1000);
        va.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                notice.setVisibility(View.GONE);
//                lv.setY(DeviceUtils.getStatusHeight(context) + findViewById(R.id.hotel_list_title).getMeasuredHeight() );
                lv.setY(DeviceUtils.getScreenHeight(context) -
                        bottom_ll.getMeasuredHeight() - lv.getMeasuredHeight() - DeviceUtils.getStatusHeight(context));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        va.start();
    }

    @Override
    public void setDelVisibility() {
        if (pHotelList.getBean().getQueryText().isEmpty()) {
            del.setVisibility(View.GONE);
        } else {
            del.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setSortState(boolean b) {
        TextView tv = (TextView) bottom_ll.getChildAt(0);
        tv.setCompoundDrawablesWithIntrinsicBounds(0,
                !b ? R.mipmap.hotel_list_bottom_sort : R.mipmap.hotel_list_bottom_sort_checked, 0, 0);
        setTextState(tv, b);
    }

    private void setTextState(TextView tv, boolean b) {
        tv.setTextColor(b ? getResources().getColor(R.color.appTheme1) : getResources().getColor(R.color.color_666));
    }

    @Override
    public void setStarState(boolean b) {
        TextView tv = (TextView) bottom_ll.getChildAt(1);
        tv.setCompoundDrawablesWithIntrinsicBounds(0,
                !b ? R.mipmap.order_price_unchecked : R.mipmap.hotel_list_bottom_star_checked, 0, 0);
        setTextState(tv, b);
    }

    @Override
    public void setLocationState(boolean b) {
        TextView tv = (TextView) bottom_ll.getChildAt(2);
        tv.setCompoundDrawablesWithIntrinsicBounds(0,
                !b ? R.mipmap.hotel_list_bottom_location : R.mipmap.hotel_list_bottom_location_checked, 0, 0);
        setTextState(tv, b);
    }

    @Override
    public void setFiltState(boolean b) {
        TextView tv = (TextView) bottom_ll.getChildAt(3);
        tv.setCompoundDrawablesWithIntrinsicBounds(0,
                !b ? R.mipmap.hotel_list_bottom_filt : R.mipmap.hotel_list_bottom_filt_checked, 0, 0);
        setTextState(tv, b);
    }

    @Override
    public void setEmptyView() {
        List<HotelListBean.ListBean> list = pHotelList.getList();
        empty_view.setVisibility(list == null
                || list.size() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pHotelList.onItemClick(position);
    }

    @Override
    public void onRefresh() {
        pHotelList.onRefresh();
    }

    @Override
    public void onLoadMore() {
//        lv.onRefreshComplete(true);
        pHotelList.onLoadMore();
    }
}
