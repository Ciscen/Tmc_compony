package com.auvgo.tmc.hotel.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.hotel.adapter.HotelDetailVPAdapter;
import com.auvgo.tmc.hotel.bean.HotelDetailBean;
import com.auvgo.tmc.hotel.fragment.HotelDetailFragment;
import com.auvgo.tmc.hotel.fragment.HotelDetailReviewFragment;
import com.auvgo.tmc.hotel.interfaces.ViewManager_hoteldetail;
import com.auvgo.tmc.p.PHotelDetail;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DeviceUtils;
import com.auvgo.tmc.utils.LogFactory;
import com.auvgo.tmc.utils.TimeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class HotelDetailActivity extends BaseActivity implements ViewManager_hoteldetail,
        ViewPager.OnPageChangeListener, HotelDetailFragment.OnFragmentInteractionListener {

    @BindView(R.id.hotel_detail_img)
    ImageView img;
    @BindView(R.id.hotel_detail_name)
    TextView name;
    @BindView(R.id.hotel_detail_imgnum)
    TextView imgNum;
    @BindView(R.id.hotel_detail_level)
    TextView level;
    @BindView(R.id.hotel_detail_addr)
    TextView addr;
    @BindView(R.id.hotel_detail_detail)
    TextView detail;
    @BindView(R.id.hotel_detail_review)
    TextView review;
    @BindView(R.id.hotel_detail_inDate_tv)
    TextView inDate;
    @BindView(R.id.hotel_detail_outDate_tv)
    TextView outDate;
    @BindView(R.id.hotel_detail_inDay_tv)
    TextView inDay;
    @BindView(R.id.hotel_detail_outDay_tv)
    TextView outDay;
    @BindView(R.id.hotel_detail_total_tv)
    TextView total;
    @BindView(R.id.hotel_detail_notice1)
    TextView notice1;
    @BindView(R.id.hotel_detail_notice2)
    TextView notice2;
    @BindView(R.id.hotel_detail_lv)
    ListView lv;
    @BindView(R.id.hotel_detail_vp)
    ViewPager vp;
    @BindView(R.id.hotel_detail_indicator)
    View indicator;
    private PHotelDetail pHotelDetail;
    private List<Fragment> fragments;
    private boolean firstIn = true;
    private int delt, screenWidth;
    private int startPosition;

    @Override
    protected int getLayoutId() {
        AppUtils.translucentStatus(this);
        return R.layout.activity_hotel_detail;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        fragments = new ArrayList<>();
        pHotelDetail = new PHotelDetail(this, this);
        pHotelDetail.initData(getIntent());
        fragments.add(HotelDetailReviewFragment.newInstance());
        fragments.add(new HotelDetailFragment());
    }

    @Override
    protected void initView() {
        lv.setFocusable(false);
        vp.setAdapter(new HotelDetailVPAdapter(getSupportFragmentManager(), fragments));
    }

    @Override
    protected void initListener() {
        vp.setOnPageChangeListener(this);
    }

    @Override
    protected void setViews() {
    }

    @Override
    protected void getData() {
        super.getData();
        pHotelDetail.getData();
    }

    @OnClick({R.id.hotel_detail_img, R.id.hotel_detail_addr_click_ll, R.id.hotel_detail_detail
            , R.id.hotel_detail_review, R.id.hotel_detail_date_rl, R.id.hotel_detail_back})
    void onViewClick(View view) {
        switch (view.getId()) {
            //点击进入大图
            case R.id.hotel_detail_img:
                pHotelDetail.jumpTo(Constant.ACTIVITY_HOTEL_PIC_LIST_FLAG);
                break;
            //点击进入位置详情
            case R.id.hotel_detail_addr_click_ll:
                pHotelDetail.jumpTo(Constant.ACTIVITY_HOTEL_MAP_FLAG);
                break;
            //点击切换vp
            case R.id.hotel_detail_detail:
                vp.setCurrentItem(1);
                break;
            //点击切换vp
            case R.id.hotel_detail_review:
                vp.setCurrentItem(0);
                break;
            //点击选择时间
            case R.id.hotel_detail_date_rl:
                pHotelDetail.jumpTo(Constant.ACTIVITY_CALENDAR_FLAG);
                break;
            case R.id.hotel_detail_back:
                finish();
                break;
        }
    }

    @OnItemClick(R.id.hotel_detail_lv)
    void onItemClick(int position) {
        pHotelDetail.jumpToRoomDetail(position);
    }

    @Override
    public void updateView() {
        HotelDetailBean mBean = pHotelDetail.getmBean();
        Glide.with(this).load(mBean.getHotelImageList().get(0).getUrl())
                .asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.hotel_detail_imgdefault).into(img);
        name.setText(mBean.getHotelName());
        level.setText(mBean.getStarRateName());
        imgNum.setText(String.format(Locale.CHINA, "%d张", mBean.getHotelImageList().size()));
        addr.setText(mBean.getAddress());
        inDate.setText(mBean.getArrivalDate().substring(5));
        inDay.setText(TimeUtils.getWeekDay(mBean.getArrivalDate()));
        outDate.setText(mBean.getDepartureDate().substring(5));
        outDay.setText(TimeUtils.getWeekDay(mBean.getDepartureDate()));
        total.setText("共" + TimeUtils.compareDay(mBean.getArrivalDate(), mBean.getDepartureDate()) + "晚");
        lv.setAdapter(pHotelDetail.getAdapter());
        ((HotelDetailReviewFragment) fragments.get(0)).setContent(mBean.getReview());
        ((HotelDetailFragment) fragments.get(1)).setContent(mBean.getFacilities());
    }

    @Override
    public void setData() {
        HotelDetailBean mBean = pHotelDetail.getmBean();
        inDate.setText(mBean.getArrivalDate().substring(5));
        inDay.setText(TimeUtils.getWeekDay(mBean.getArrivalDate()));
        outDate.setText(mBean.getDepartureDate().substring(5));
        outDay.setText(TimeUtils.getWeekDay(mBean.getDepartureDate()));
        total.setText("共" + TimeUtils.compareDay(mBean.getArrivalDate(), mBean.getDepartureDate()) + "晚");
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (firstIn) {
            firstIn = false;
            int reviewCenterPosition = review.getLeft() + review.getWidth() / 2;
            int detailCenterPosition = detail.getLeft() + detail.getWidth() / 2;
            delt = detailCenterPosition - reviewCenterPosition;
            screenWidth = DeviceUtils.deviceWidth();
            ViewGroup.LayoutParams lp = indicator.getLayoutParams();
            lp.width = delt;
            indicator.setLayoutParams(lp);
            startPosition = reviewCenterPosition - delt / 2;
            indicator.setX(startPosition);
        }
        indicator.setX(startPosition + delt * position + positionOffsetPixels * delt / screenWidth);
    }

    @Override
    public void onPageSelected(int position) {
        LogFactory.d("onPageSelected");
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        LogFactory.d("onPageScrollStateChanged");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.ACTIVITY_HOTEL_DETAIL_FLAG) return;
        if (resultCode == Constant.ACTIVITY_CALENDAR_FLAG) {
            pHotelDetail.receiveCld(data);
        }
    }

    @Override
    public void onMoreClick() {
        pHotelDetail.jumpTo(Constant.ACTIVITY_HOTEL_INFO_FLAG);
    }
}

