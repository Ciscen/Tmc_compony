package com.auvgo.tmc.hotel.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.hotel.adapter.HotelRoomDetailListAdapter;
import com.auvgo.tmc.hotel.bean.HotelDetailBean;
import com.auvgo.tmc.hotel.bean.HotelPolicyBean;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.views.MyDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class HotelRoomDetailActivity extends BaseActivity {

    @BindView(R.id.hotel_room_name)
    TextView name_tv;
    @BindView(R.id.hotel_room_imgnum)
    TextView imgNum_tv;
    @BindView(R.id.hotel_room_img)
    ImageView icon;
    @BindView(R.id.hotel_room_gv)
    GridView gv;
    @BindView(R.id.hotel_room_lv)
    ListView lv;
    @BindView(R.id.window)
    TextView window;
    @BindView(R.id.wifi)
    TextView wifi;
    @BindView(R.id.louceng)
    TextView louceng;
    @BindView(R.id.pingmi)
    TextView pingmi;
    @BindView(R.id.bed)
    TextView bed;
    @BindView(R.id.renshu)
    TextView renshu;
    @BindView(R.id.hotel_room_info_ll)
    View roomInfo;
    @BindView(R.id.hotel_room_notice)
    TextView notice;
    /*
    房间详情实体
     */
    private HotelDetailBean.RoomListBean mRoomBean;
    /*
    酒店详情实体
     */
    private HotelDetailBean mHotelBean;
    private HotelRoomDetailListAdapter adapter;
    /*
    该房间的图片
     */
    private ArrayList<HotelDetailBean.HotelImageListBean> imgs;
    /*
    房间在酒店房间列表中的索引位置
     */
    private int roomPos;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hotel_room_detail;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mHotelBean = intent.getParcelableExtra("bean");
        roomPos = intent.getIntExtra("roomPos", 0);
        ArrayList<HotelDetailBean.HotelImageListBean> imgs = intent.getParcelableArrayListExtra("imgs");
        this.imgs = (imgs == null ? new ArrayList<HotelDetailBean.HotelImageListBean>() : imgs);
        mRoomBean = mHotelBean.getRoomList().get(roomPos);
        adapter = new HotelRoomDetailListAdapter(this, mRoomBean.getRatePlanList());
    }

    @Override
    protected void initView() {
        name_tv.setText(mRoomBean.getName());
        if (imgs != null)
            imgNum_tv.setText(String.format(Locale.CHINESE, "%d张", imgs.size()));

        lv.setAdapter(adapter);
        lv.setFocusable(false);
        if (mRoomBean.getName().contains("无窗")) {
            window.setText("无窗");
            window.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.nowindow), null, null, null);
        } else {
            window.setText("有窗");
        }
        if (mRoomBean.getBroadnetAccess() == null) {
            wifi.setText("--");
            wifi.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.nowifi), null, null, null);
        } else {
            wifi.setText("无线");
        }
        if (mRoomBean.getFloor() == null) {
            louceng.setText("--");
            louceng.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.nolouceng), null, null, null);
        } else {
            louceng.setText(mRoomBean.getFloor());
        }
        if (mRoomBean.getArea() == null) {
            pingmi.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.nopingmi), null, null, null);
            pingmi.setText("--");
        } else {
            pingmi.setText(mRoomBean.getArea());
        }
        if (mRoomBean.getBedType() == null) {
            bed.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.nobed), null, null, null);
            bed.setText("--");
        } else {
            bed.setText(mRoomBean.getBedType());
        }
        if (mRoomBean.getCapacity() == null) {
            renshu.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.norenshu), null, null, null);
            renshu.setText("--");
        } else {
            renshu.setText(mRoomBean.getCapacity());
        }
    }

    @Override
    protected void initListener() {
        adapter.setmListener(new HotelRoomDetailListAdapter.OnHanZaoClickListener() {
            @Override
            public void onHanzaoClick(int position) {
                Intent intent = new Intent(HotelRoomDetailActivity.this, HotelBreakActivity.class);
                intent.putExtra("roomName", mRoomBean.getName());
                intent.putExtra("bean", mRoomBean.getRatePlanList().get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setViews() {
        MUtils.loadImg(this, imgs.size() > 0 ? imgs.get(0).getUrl() : "", icon, R.mipmap.hotel_room_list_icon_default);
        String guaranteeType = mRoomBean.getRatePlanList().get(0).getGuaranteeType();
        if (TextUtils.isEmpty(guaranteeType)) return;
        if (guaranteeType.equals("Chinese")) {
            notice.setText("须持大陆身份证入住");
        } else if (guaranteeType.equals("OtherForeign")) {
            notice.setText("须持国外护照入住”");
        } else if (guaranteeType.equals("HongKong")) {
            notice.setText("须持港澳台身份证入住");
        } else if (guaranteeType.equals("Japanese")) {
            notice.setText("须持日本护照入住");
        } else if (guaranteeType.equals("All")) {
            notice.setText("");
        }
    }

    @OnClick({R.id.hotel_room_back, R.id.hotel_room_img})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.hotel_room_back:
                finish();
                break;
            case R.id.hotel_room_img:
                if (imgs != null && imgs.size() > 0) {
                    Intent intent = new Intent(this, HotelBigPicActivity.class);
                    intent.putExtra("imgs", imgs);
                    startActivity(intent);
                }
                break;
        }
    }

    @OnItemClick(R.id.hotel_room_lv)
    void onItemClick(final int position) {
        HotelDetailBean.RoomListBean.RatePlanListBean ratePlanListBean = mRoomBean.getRatePlanList().get(position);
        String s = MUtils.getHotelWeiPolicyStr();
        HotelPolicyBean mHotelPolicy = MyApplication.mHotelPolicy;
        double v = mHotelPolicy == null ? Integer.MAX_VALUE >> 2 : Double.parseDouble(mHotelPolicy.getPrice());
        if (ratePlanListBean.getAverageRate() > v && v != -1) {
            if (mHotelPolicy.getController().equals("1")) {//仅提醒
                DialogUtil.showDialog(this, "提示", "取消", "继续预订", s, new MyDialog.OnButtonClickListener() {
                    @Override
                    public void onLeftClick() {
                    }

                    @Override
                    public void onRightClick() {
                        jump(position);
                    }
                });
            } else {//不允许预订
                DialogUtil.showDialog(this, "提示", "确定", "", s + ",不可预订", null);
            }
        } else {//没有违背
            jump(position);
        }

    }

    private void jump(int position) {
        Intent intent = new Intent(this, HotelBookActivity.class);
        intent.putExtra("bean", mHotelBean);
        intent.putExtra("roomPos", roomPos);
        intent.putExtra("ratePos", position);
        intent.putExtra("psgs", (Serializable) MyApplication.getApplication().selectedPsgs);
        startActivity(intent);
    }
}
