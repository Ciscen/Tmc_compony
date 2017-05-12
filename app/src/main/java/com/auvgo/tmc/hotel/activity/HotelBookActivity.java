package com.auvgo.tmc.hotel.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.common.AddLsPsgActivity;
import com.auvgo.tmc.common.ApplyNoChoseActivity;
import com.auvgo.tmc.common.PassengerListActivity;
import com.auvgo.tmc.hotel.adapter.HotelBookPsgAdapter;
import com.auvgo.tmc.hotel.bean.HotelBookResultBean;
import com.auvgo.tmc.hotel.bean.HotelCheckResultBean;
import com.auvgo.tmc.hotel.bean.HotelDetailBean;
import com.auvgo.tmc.hotel.bean.HotelPolicyBean;
import com.auvgo.tmc.hotel.bean.RequestCreateHotelOrderBean;
import com.auvgo.tmc.plane.bean.RequestHotelValidateBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.SelectionBean;
import com.auvgo.tmc.train.bean.UserBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DeviceUtils;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.LogFactory;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.ItemView;
import com.auvgo.tmc.views.MyDialog;
import com.auvgo.tmc.views.MyPickerView;
import com.auvgo.tmc.views.TitleView;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HotelBookActivity extends BaseActivity implements HotelBookPsgAdapter.OnPsgClickListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.hotel_book_name)
    TextView hotel_name;
    @BindView(R.id.hotel_book_roominfo)
    TextView room_info;
    @BindView(R.id.hotel_book_date)
    TextView book_date;
    @BindView(R.id.hotel_book_cancel_rule)
    TextView rule;
    @BindView(R.id.hotel_book_price)
    TextView price_pay;
    @BindView(R.id.hotel_book_price_guarantee)
    TextView price_guarantee;
    @BindView(R.id.hotel_book_price_name)
    TextView price_name;
    @BindView(R.id.hotel_book_guarantee_price_ll)
    View guarantee_ll;
    @BindView(R.id.hotel_book_room_num)
    ItemView room_num;
    @BindView(R.id.hotel_book_contact)
    ItemView contact;
    @BindView(R.id.hotel_book_tel)
    ItemView tel;
    @BindView(R.id.hotel_book_room_keep)
    ItemView timeKeep;
    @BindView(R.id.item_hotel_book_room_keep_ll)
    View timeKeep_ll;
    @BindView(R.id.hotel_book_email)
    ItemView email;
    @BindView(R.id.hotel_book_lv)
    ListView lv;
    @BindView(R.id.hotel_book_title)
    TitleView title;
    @BindView(R.id.hotel_book_applyNo)
    ItemView applyNo;
    @BindView(R.id.hotel_book_item_applyNo)
    View item_applyNo;
    @BindView(R.id.hotel_book_applyNo_logo)
    View applyNo_logo;
    @BindView(R.id.hotel_book_click_addLSpsg)
    View add_ls;
    @BindView(R.id.hotel_book_paytype)
    View item_paytype;
    @BindView(R.id.hotel_book_rg)
    RadioGroup rg;
    RadioButton[] rbs;

    private ArrayList<UserBean> psgs;
    private List<SelectionBean> roomNums;
    private HotelBookPsgAdapter adapter;
    private int mRoomNum = -1;
    private int mCurrentOperateItem;
    private final int MAX_ROOM_NUM = 10;
    /*
    页面显示及数据需要的数据
     */
    private HotelDetailBean mHotelBean;
    private HotelDetailBean.RoomListBean mRoomBean;
    private HotelDetailBean.RoomListBean.RatePlanListBean mRateBean;
    private int roomPos;
    private int ratePos;
    /*
    提交订单需要的一些数据
     */
    private String lastArriveTime = "";/*最晚到点时间*/
    private HotelPolicyBean mHotelPolicy;//政策
    private boolean isWei;//是否是违背订单
    private String applyNoStr = "";
    /*
    房型检测得到的结果、包括获取到的时间显示list
     */
    private HotelCheckResultBean mCheckResultBean;
    /*
    控制变量
     */
    private int mTimeKeep;
    private boolean isGuarantee;//是否担保
    private int nights;
    private String payType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hotel_book;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        Intent intent = getIntent();
        psgs = (ArrayList<UserBean>) intent.getSerializableExtra("psgs");
        mRoomNum = psgs.size();
        adapter = new HotelBookPsgAdapter(this, psgs);
        adapter.setRoomNum(mRoomNum);
        roomNums = initRoomNumsData();
        mHotelBean = intent.getParcelableExtra("bean");
        roomPos = intent.getIntExtra("roomPos", 0);
        ratePos = intent.getIntExtra("ratePos", 0);
        mRoomBean = mHotelBean.getRoomList().get(roomPos);
        mRateBean = mRoomBean.getRatePlanList().get(ratePos);
        nights = TimeUtils.compareDay(mHotelBean.getArrivalDate(), mHotelBean.getDepartureDate());///夜数

        /*
        初始化政策及违规情况
         */
        mHotelPolicy = MyApplication.mHotelPolicy;
        String fukuankemu = MUtils.getFukuankemu();
        payType = fukuankemu.equals("3") ? "2" : fukuankemu;
    }

    private List<SelectionBean> initRoomNumsData() {
        List<SelectionBean> list = new ArrayList<>();
        for (int i = 1; i <= MAX_ROOM_NUM; i++) {
            SelectionBean sb = new SelectionBean("" + i, i == mRoomNum);
            list.add(sb);
        }
        return list;
    }

    @Override
    protected void findViews() {
        initRbs();
    }

    private void initRbs() {
        int childCount = rg.getChildCount();
        rbs = new RadioButton[childCount];
        for (int i = 0; i < childCount; i++) {
            rbs[i] = (RadioButton) rg.getChildAt(i);
        }
    }

    @Override
    protected void initView() {
        lv.setAdapter(adapter);
        hotel_name.setText(mHotelBean.getHotelName());
        boolean selfPay = mRateBean.getPaymentType().equals("SelfPay");
        room_info.setText(mRoomBean.getBedType() + "|" + mRateBean.getRatePlanName()
                + "|" + (selfPay ? "现付" : "预付"));
        book_date.setText(mHotelBean.getArrivalDate().substring(5).replace("-", "月") + "日" + "-" +
                mHotelBean.getDepartureDate().substring(5).replace("-", "月") + "日" +
                "  共" + TimeUtils.compareDay(mHotelBean.getArrivalDate(), mHotelBean.getDepartureDate()) + "晚");
        contact.setContent(MyApplication.mUserInfoBean.getName());
        tel.setContent(MyApplication.mUserInfoBean.getMobile());
        email.setContent(MyApplication.mUserInfoBean.getEmail());
        timeKeep_ll.setVisibility(selfPay ? View.VISIBLE : View.GONE);
        room_num.setContent(psgs.size() + "");
        setGuaranteeReferenceViews();
        setApplyNoVisibility(item_applyNo);
        if (selfPay) {
            payType = "2";
        }
        item_paytype.setVisibility(selfPay ?
                View.GONE : MUtils.getFukuankemu().equals("3") ? View.VISIBLE : View.GONE);
        rbs[0].setChecked(true);
    }

    @Override
    protected void initListener() {
        adapter.setListener(this);
        title.setTitleClickListener(this);
        rg.setOnCheckedChangeListener(this);
    }

    @Override
    protected void setViews() {
        setAddLsPsgVisibility(add_ls);
        findViewById(R.id.hotel_book_click_addpsg).setVisibility(isAllowBook() ? View.VISIBLE : View.GONE);
        lv.setClickable(isAllowBook());
        room_num.setEnabled(isAllowBook());
    }

    @Override
    protected void getData() {
        super.getData();
        /*
        获取验证
         */
        checkPrice();
    }

    private void checkPrice() {
        RequestHotelValidateBean rb = new RequestHotelValidateBean();
        rb.setRoomTypeId(mRateBean.getRoomTypeId());
        rb.setArrivalDate(mHotelBean.getArrivalDate());
        rb.setDepartureDate(mHotelBean.getDepartureDate());
        rb.setHotelId(mHotelBean.getHotelId());
        rb.setRatePlanId(String.valueOf(mRateBean.getRatePlanId()));
        rb.setTotalPrice(mRateBean.getTotalRate());
        RetrofitUtil.checkHotelValidate(this, AppUtils.getJson(rb), HotelCheckResultBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    mCheckResultBean = (HotelCheckResultBean) o;
                    setKeepTime(0);
                    setRoomNumsAndPriceStr();
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
    根据担保与否，设置view
     */
    private void setGuaranteeReferenceViews() {
        boolean gurantee = mRateBean.isGurantee();
        String str = "";
        if (gurantee) {
            List<String> guaranteeRuleDesc = mRateBean.getGuaranteeRuleDesc();
            if (guaranteeRuleDesc != null && guaranteeRuleDesc.size() > 0) {
                str = guaranteeRuleDesc.get(0);
            }
            guarantee_ll.setVisibility(View.VISIBLE);
        } else {
            List<String> prepayrule = mRateBean.getPrepayRuleDesc();
            if (prepayrule != null && prepayrule.size() > 0) {
                str = prepayrule.get(0);
            }
            guarantee_ll.setVisibility(View.GONE);
        }
        if (str.isEmpty()) rule.setVisibility(View.GONE);
        rule.setText(str);
    }


    @OnClick({R.id.hotel_book_room_detail,
            R.id.hotel_book_click_pricedetail,
            R.id.hotel_book_commit, R.id.hotel_book_room_num,
            R.id.hotel_book_room_keep, R.id.hotel_book_applyNo_logo,
            R.id.hotel_book_click_addpsg, R.id.hotel_book_click_addLSpsg})
    void onViewClick(View view) {
        switch (view.getId()) {
            /* 点击房间详情 */
            case R.id.hotel_book_room_detail:
                jumpToRoomDetail();
                break;
            /* 点击价格详情 */
            case R.id.hotel_book_click_pricedetail:
                showPriceDialog();
                break;
            /* 点击提交按钮 */
            case R.id.hotel_book_commit:
                prepareToCommit();
                break;
            /* 点击出差申请单号按钮 */
            case R.id.hotel_book_applyNo_logo:
                Intent intent = new Intent(this, ApplyNoChoseActivity.class);
                intent.putExtra("fromdate", mHotelBean.getArrivalDate());
                intent.putExtra("backdate", mHotelBean.getDepartureDate());
                startActivityForResult(intent, Constant.ACTIVITY_HOTEL_BOOK_FLAG);
                break;
            /* 点击房间数 */
            case R.id.hotel_book_room_num:
                DialogUtil.showPickerPopWithSureHeight(this, "选择房间数", mRoomNum - 1, roomNums,
                        new MyPickerView.OnPickerViewSure() {
                            @Override
                            public void onSingleSureClick(int p) {
                                mRoomNum = p + 1;
                                initPsgListView();
                                setRoomNumsAndPriceStr();
                            }

                            @Override
                            public void onMultiSureClick(List<Integer> s) {

                            }
                        });
                break;
            /* 点击保留到 */
            case R.id.hotel_book_room_keep:
                if (mCheckResultBean == null || mCheckResultBean.getArrivalOptionTimes() == null)
                    return;
                DialogUtil.showPickerPopWithSureHeight(this, "", mTimeKeep, mCheckResultBean.getArrivalOptionTimes(),
                        new MyPickerView.OnPickerViewSure() {
                            @Override
                            public void onSingleSureClick(int p) {
                                mTimeKeep = p;
                                setKeepTime(p);
                                setRoomNumsAndPriceStr();
                            }

                            @Override
                            public void onMultiSureClick(List<Integer> s) {

                            }
                        });
                break;
            case R.id.hotel_book_click_addpsg:
                addPsg();
                break;
            case R.id.hotel_book_click_addLSpsg:
                addLsPsg();
                break;
        }
    }

    private void addLsPsg() {
        if (getLeftNum() == 0) {
            ToastUtils.showTextToast("人数不能超过房间数");
            return;
        }
        Intent intent = new Intent(context, AddLsPsgActivity.class);
        ((Activity) context).startActivityForResult(intent, Constant.ACTIVITY_HOTEL_BOOK_FLAG);
    }

    private int getLeftNum() {
        int count = 0;
        for (int i = 0; i < psgs.size(); i++) {
            if (psgs.get(i) == null) {
                count++;
            }
        }
        return count;
    }

    private void addPsg() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        ArrayList<UserBean> psgs = getPsgs();
        bundle.putSerializable("psgs", psgs);
        bundle.putString("from", Constant.HOTEL);
        bundle.putInt("nums", mRoomNum - getLsNums());
        intent.setClass(this, PassengerListActivity.class);
        intent.putExtra("bundle", bundle);
        startActivityForResult(intent, Constant.ACTIVITY_HOTEL_BOOK_FLAG);
    }

    private int getLsNums() {
        int count = 0;
        for (int i = 0; i < psgs.size(); i++) {
            UserBean userBean = psgs.get(i);
            if (userBean != null && userBean.getId() == 0) {
                count++;
            }
        }
        return count;
    }

    private void doNext() {
        RetrofitUtil.getHotelPolicy(this, MUtils.getRequestStringWithCityIdByPsg(psgs, MyApplication.mHotelCityId)
                , null, new RetrofitUtil.OnResponse() {
                    @Override
                    public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                        if (status == 200) {
                            if (bean.getData().length() != 3) {
                                MyApplication.mHotelPolicy = new Gson().fromJson(bean.getData(), HotelPolicyBean.class);
                            } else {
                                MyApplication.mHotelPolicy = null;
                            }
                            mHotelPolicy = MyApplication.mHotelPolicy;
                            isWei = mHotelPolicy != null && mRateBean.getAverageRate() > Double.parseDouble(mHotelPolicy.getPrice()) && !mHotelPolicy.getPrice().equals("-1");
                            ifShowDialog();
                        }
                        return false;
                    }

                    @Override
                    public boolean onFailed(Throwable e) {
                        return false;
                    }
                });
    }

    private void showPriceDialog() {
        double gurantee = getGuaranteeAmout();//担保费
        double totalRate = Double.parseDouble(AppUtils.keepNSecimal(String.valueOf(mRateBean.getTotalRate()), 2));//房价F
        double fuwufee = MyApplication.mComSettingBean.getFuwufei().getGnhotelapp();//服务费
        String fuwufeeType = MyApplication.mComSettingBean.getFuwufei().getGnhoteltype();//服务费收取方式

        if (fuwufeeType.equals("order")) {//根据不同的服务费收费规则计算
        } else if (fuwufeeType.equals("jianye")) {
            fuwufee = fuwufee * mRoomNum * nights;
        }
        if (!mRateBean.isPrepay()) fuwufee = 0;
        DialogUtil.showHotelPriceDialog(this, mRateBean.getPaymentType().equals("SelfPay"),
                totalRate * mRoomNum, isGuarantee ? gurantee : 0, fuwufee, mRoomNum
                , nights);
    }

    /* 房间详情 */
    private void jumpToRoomDetail() {

    }

    private void ifShowDialog() {
        String s = MUtils.getHotelWeiPolicyStr();
        if (isWei) {
            if (mHotelPolicy.getController().equals("1")) {//仅提醒
                DialogUtil.showDialog(this, "提示", "取消", "继续预订", s, new MyDialog.OnButtonClickListener() {
                    @Override
                    public void onLeftClick() {
                    }

                    @Override
                    public void onRightClick() {
                        doCommit();
                    }
                });
            } else {//不允许预订
                DialogUtil.showDialog(this, "提示", "确定", "", s + ",不可预订", null);
            }
        } else {/*没有违背*/
            doCommit();
        }

    }

    private void setKeepTime(int p) {
        HotelCheckResultBean.ArrivalOptionTimesBean b = mCheckResultBean.getArrivalOptionTimes().get(p);
        timeKeep.setContent(b.getShowTime());
        lastArriveTime = b.getValue();
    }

    private void prepareToCommit() {
        switch (checkOrderComplete()) {
            case -1:
                ToastUtils.showTextToast("请选择入住人");
                return;
            case -2:
                ToastUtils.showTextToast("请填写预订人姓名");
                return;
            case -3:
                ToastUtils.showTextToast("请填写手机号码");
                return;
            case -4:
                ToastUtils.showTextToast("房价校验错误，请返回重试");
                return;
            case -5:
                ToastUtils.showTextToast("请输入出差单号");
                return;
            case -6:
                ToastUtils.showTextToast("身份证号码不能相同！");
                return;
        }
        doNext();
    }

    private int checkOrderComplete() {
        if (MUtils.checkIdDulpicated(psgs)) {
            return -6;
        }
        if (mCheckResultBean == null) {
            return -4;
        }
        if (psgsHasNull())
            return -1;
        if (TextUtils.isEmpty(contact.getContent()))
            return -2;
        if (TextUtils.isEmpty(tel.getContent()))
            return -3;
        if (TextUtils.isEmpty(applyNo.getContent().trim()) && getApplyNoSettingCode().equals("1"))
            return -5;


        return 0;
    }

    private boolean psgsHasNull() {
        for (int i = 0; i < mRoomNum; i++) {
            if (psgs.get(i) == null) {
                return true;
            }
        }
        return false;
    }

    /*
    下单
     */
    private void doCommit() {
        if (mCheckResultBean == null) {
            finish();
            return;
        }
        RequestCreateHotelOrderBean requestBean = new RequestCreateHotelOrderBean();
        requestBean.setOrder(getOrder());
        requestBean.setUsers(getPsgList());
        RetrofitUtil.createHotelOrder(this, AppUtils.getJson(requestBean), HotelBookResultBean.class
                , new RetrofitUtil.OnResponse() {
                    @Override
                    public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                        if (status == 200) {
                            showDialog("", "确定", getString(R.string.order_committing), new MyDialog.OnButtonClickListener() {
                                @Override
                                public void onLeftClick() {
                                }

                                @Override
                                public void onRightClick() {
                                    jumpToOrderList(Constant.HOTEL);
                                    finish();
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
    填充order字段的数据
     */
    private RequestCreateHotelOrderBean.OrderBean getOrder() {

        RequestCreateHotelOrderBean.OrderBean ob = new RequestCreateHotelOrderBean.OrderBean();
        ob.setArrivalDate(mHotelBean.getArrivalDate());
        ob.setDepartureDate(mHotelBean.getDepartureDate());
        ob.setHotelId(mHotelBean.getHotelId());
        ob.setBookpolicy(mHotelPolicy == null ? "" : mHotelPolicy.getCityname() + "不超过" + mHotelPolicy.getPrice() + "元");
        ob.setBookUserId(MyApplication.mUserInfoBean.getId());
        ob.setBookUserName(MyApplication.mUserInfoBean.getName());
        ob.setChuchaitype(0);
        ob.setCityId(MyApplication.mHotelCityId);
        ob.setCityName(MyApplication.mHotelCityName);
        ob.setCompanyid(MyApplication.mUserInfoBean.getCompanyid());
        ob.setCurrencyCode(mRateBean.getCurrencyCode());
        ob.setCustomerIPAddress(DeviceUtils.getPhoneIpAddress(this));
        ob.setCustomerType(mRateBean.getGuestType());
        ob.setHotelAddress(mHotelBean.getAddress());
        ob.setHotelName(mHotelBean.getHotelName());
        ob.setHotelPhone(mHotelBean.getPhone());
        ob.setLatestArrivalTime(lastArriveTime);
        ob.setShenqingno(applyNo.getContent().trim());
        ob.setLinkAddress("");
        ob.setLinkEmail(email.getContent());
        ob.setLinkPhone(tel.getContent());
        ob.setLinkName(contact.getContent());
        ob.setNumberOfCustomers(mRoomNum);
        int weibeiflag = isWei ? 1 : 0;
        ob.setWeibeiflag(weibeiflag);
        ob.setWbreason(isWei ? MUtils.getHotelWeiPolicyStr() : "");
        ob.setTotalPrice(Double.valueOf(AppUtils.keepNSecimal(String.valueOf(mRateBean.getTotalRate()), 2)));
        ob.setRoomTypeId(mRateBean.getRoomTypeId());
        ob.setRatePlanId(mRateBean.getRatePlanId());
        ob.setPaymentType(mRateBean.getPaymentType());
        ob.setHotelfrom(mHotelBean.getType());
        ob.setOrderFrom(3);
        ob.setHoteltype(0);
        ob.setIntervalDay(nights);
        ob.setNumberOfRooms(mRoomNum);
        ob.setNeedGuarantee(isGuarantee ? "1" : "0");
        ob.setGuaranteeAmount(getGuaranteeAmout());
        ob.setPayType(payType);
        return ob;
    }

    private Double getGuaranteeAmout() {
        String guaranteeType = mRateBean.getGuaranteeType();
        if (guaranteeType == null) return 0D;
        if (guaranteeType.equals("FirstNightCost"))
            return mRateBean.getAverageRate() * mRoomNum;
        else
            return mRateBean.getAverageRate() * mRoomNum * nights;
    }

    /*
    填充psg的数据
     */
    private List<RequestCreateHotelOrderBean.PassengerBean> getPsgList() {
        List<RequestCreateHotelOrderBean.PassengerBean> list = new ArrayList<>();
        for (int i = 0; i < psgs.size(); i++) {
            UserBean ub = psgs.get(i);
            RequestCreateHotelOrderBean.PassengerBean pb = new RequestCreateHotelOrderBean.PassengerBean();
            if (ub == null) break;
            pb.setCertno(ub.getCertno());
            pb.setDepname(ub.getDeptname());
            pb.setDeptid(String.valueOf(ub.getDeptid()));
            pb.setEmployeeid(ub.getId());
            pb.setMobile(ub.getMobile());
            pb.setName(ub.getName());
            pb.setPasstype("AD");
            pb.setCerttype(ub.getCerttype());
            pb.setZhiwei(Integer.valueOf(ub.getZhiwei()));
            pb.setSaleprice(mRateBean.getAverageRate());
            list.add(pb);
        }
        return list;
    }

    private void setRoomNumsAndPriceStr() {
        checkIsGuarantee();
        double fuwufee = MyApplication.mComSettingBean.getFuwufei().getGnhotelapp();//服务费
        String fuwufeeType = MyApplication.mComSettingBean.getFuwufei().getGnhoteltype();//服务费收取方式

        if (fuwufeeType.equals("order")) {//根据不同的服务费收费规则计算
        } else if (fuwufeeType.equals("jianye")) {
            fuwufee = fuwufee * mRoomNum * nights;
        }
        if (mRateBean.getPaymentType().equals("SelfPay")) fuwufee = 0;
        double price = Double.valueOf(AppUtils.keepNSecimal(String.valueOf(mRateBean.getTotalRate()), 2)) * mRoomNum;
        price_guarantee.setText(String.format("￥%s", AppUtils.keepNSecimal(String.valueOf(getGuaranteeAmout()), 2)));
        price_pay.setText(String.format("￥%s", AppUtils.keepNSecimal(price + fuwufee + "", 2)));
        if (mRateBean.isPrepay()) {
            price_name.setText("在线预付");
        } else {
            price_name.setText("到店付款");
        }
        room_num.setContent(mRoomNum + "");
        if (isGuarantee) {
            guarantee_ll.setVisibility(View.VISIBLE);
        } else {
            guarantee_ll.setVisibility(View.GONE);
        }
    }

    private void initPsgListView() {
        adapter.setRoomNum(mRoomNum);
        if (mRoomNum > psgs.size()) {
            int size = psgs.size();
            for (int i = 0; i < mRoomNum - size; i++) {
                psgs.add(null);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDelClick(int roomnum) {
        if (isAllowBook()) {
            mRoomNum = roomnum;
            setRoomNumsAndPriceStr();
        }
    }

    /**
     * 判断是否担保，修改isGuarantee的状态
     */
    private void checkIsGuarantee() {
        boolean isGuarantee = mRateBean.isGurantee();
        boolean isTimeGuarantee = mRateBean.isTimeGuarantee();
        boolean isAmountGuarantee = mRateBean.isAmountGuarantee();

        /*如果不需要担保*/
        if (!isGuarantee) {
            this.isGuarantee = false;
             /*无条件担保*/
        } else if (!isTimeGuarantee && !isAmountGuarantee) {
            this.isGuarantee = true;
            /*根据数量判断*/
        } else if (isAmountGuarantee && !isTimeGuarantee) {
            this.isGuarantee = isAmount();
            /*根据时间判断*/
        } else if (!isAmountGuarantee && isTimeGuarantee) {
            this.isGuarantee = isTime();
            /*两者取并集*/
        } else {
            this.isGuarantee = isTime() || isAmount();
        }

    }

    /*
     当前所选时间是否需要担保
     只需要比较当前时间跟限制时间
     */
    private boolean isTime() {
        String startTime = mRateBean.getStartTime();
        String endTime = mRateBean.getEndTime();
        boolean isTomorrow = mRateBean.isTomorrow();
        int minutesOfStartTimes = hhmm2minute(startTime);
        int minutesOfEndTimes = hhmm2minute(endTime);
        String s = lastArriveTime.trim();
        int minutesOfSelectedLastArriveTime = hhmm2minute(s.substring(s.length() - 8, s.length() - 3));
        int twelve = 60 * 12;
        /*如果范围是到跨夜的*/
        if (isTomorrow) {
            /*
            所选时间>=starttime或者<=06:00
             */
            return minutesOfSelectedLastArriveTime >= minutesOfStartTimes
                    || minutesOfSelectedLastArriveTime <= twelve / 2;
            /*如果是在当天的*/
        } else {
            /*
            所选时间在start~end范围内
             */
            return minutesOfSelectedLastArriveTime >= minutesOfStartTimes &&
                    minutesOfSelectedLastArriveTime <= minutesOfEndTimes;
        }
    }

    private int hhmm2minute(String startTime) {
        String[] split = startTime.split(":");
        int minutes = 0;
        if (split.length == 2) {
            try {
                minutes = Integer.parseInt(split[0]) * 60 + Integer.parseInt(split[1]);
            } catch (NumberFormatException e) {
                LogFactory.e("------返回的startTime或者endTime格式不对啊");
            }
        }
        return minutes;
    }

    /*
    当前房间数量是否需要担保
    只需要比较当前数量跟限制数量
     */
    private boolean isAmount() {
        int amount = mRateBean.getAmount();//几间以上需要预订
        return mRoomNum >= amount;
    }

    @Override
    public void onHeadClick(int position) {
        mCurrentOperateItem = position;
        Intent intent = new Intent(this, PassengerListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isSingle", true);
        bundle.putCharSequenceArray("selectedIds", getSelectedIds());
        intent.putExtra("bundle", bundle);
        startActivityForResult(intent, Constant.ACTIVITY_HOTEL_BOOK_FLAG);
    }

    private String[] getSelectedIds() {
        StringBuilder sb = new StringBuilder();
        for (UserBean psg : psgs) {
            if (psg != null)
                sb.append(psg.getId()).append(",");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString().split(",");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != Constant.ACTIVITY_HOTEL_BOOK_FLAG) return;
        if (resultCode == Constant.ACTIVITY_PASSENGER_FLAG) {
            ArrayList<UserBean> users = (ArrayList<UserBean>) data.getSerializableExtra("psgs");
//            psgs.set(mCurrentOperateItem, users.get(0));
            addToPsgList(users);
            adapter.notifyDataSetChanged();
        } else if (resultCode == Constant.ACTIVITY_APPLY_NO_CHOSE) {
            applyNo.setContent(data.getStringExtra("no"));
        } else if (resultCode == Constant.ACTIVITY_ADD_LS_PSG_FLAG) {
            UserBean bean = (UserBean) data.getSerializableExtra("bean");
            addToPsgList(bean);
            adapter.notifyDataSetChanged();
        }
    }

    private void addToPsgList(UserBean bean) {
        for (int i = 0; i < psgs.size(); i++) {
            UserBean userBean = psgs.get(i);
            if (userBean == null) {
                psgs.set(i, bean);
            }
        }
    }

    private void addToPsgList(ArrayList<UserBean> users) {
        ArrayList<UserBean> ar = new ArrayList<>();
        int n = psgs.size();
        for (int i = 0; i < n; i++) {
            UserBean userBean = psgs.get(i);
            if (userBean != null && userBean.getId() == 0) {
                ar.add(userBean);
            }
        }
        psgs.clear();
        psgs.addAll(users);
        psgs.addAll(ar);
        int size = n - psgs.size();
        for (int i = 0; i < size; i++) {
            psgs.add(null);
        }
    }

    @Override
    public void onClick(View v) {
        DialogUtil.showDialog(this, "提示", "取消", "确定", "订单尚未提交成功，您确定要离开当前页面吗？"
                , new MyDialog.OnButtonClickListener() {
                    @Override
                    public void onLeftClick() {

                    }

                    @Override
                    public void onRightClick() {
                        finish();
                    }
                });
    }

    public ArrayList<UserBean> getPsgs() {
        ArrayList<UserBean> ubs = new ArrayList<>();
        for (int i = 0; i < psgs.size(); i++) {
            UserBean ub = psgs.get(i);
            if (ub != null && ub.getId() != 0) {
                ubs.add(ub);
            }
        }
        return ubs;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        for (int i = 0; i < rbs.length; i++) {
            if (checkedId == rbs[i].getId()) {
                payType = i == 0 ? "2" : "1";
            }
        }
    }
}

