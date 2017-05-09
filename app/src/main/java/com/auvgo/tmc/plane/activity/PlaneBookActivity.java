package com.auvgo.tmc.plane.activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.common.bean.PeisonListBean;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.p.PPlaneBook;
import com.auvgo.tmc.plane.bean.PlaneListBean;
import com.auvgo.tmc.plane.bean.PlaneRouteBeanWF;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.UserBean;
import com.auvgo.tmc.train.interfaces.RetrofitServer;
import com.auvgo.tmc.train.interfaces.ViewManager_planebook;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.views.ItemView;
import com.auvgo.tmc.views.MyDialog;
import com.auvgo.tmc.views.PlaneDetailCardView;
import com.auvgo.tmc.views.TitleView;
import com.google.common.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaneBookActivity extends BaseActivity implements ViewManager_planebook, View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private PPlaneBook pPlaneBook;
    private PlaneDetailCardView cv1, cv2;
    private ItemView ensurance, contact, tel, email, applyNo, peisong_addr;
    private ListView psgs_lv;
    private View add, add_ls, click_total;
    private TextView total_tv, commit_tv;
    private TitleView titleView;
    private View item_applyNo, applyNo_logo, peisong_logo, item_paytype;
    private RadioGroup rg;
    private RadioButton[] rbs;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_plane_book;
    }

    @Override
    protected void initData() {
        pPlaneBook = new PPlaneBook(this, this);
        pPlaneBook.init(getIntent());

    }

    @Override
    protected void findViews() {
        titleView = (TitleView) findViewById(R.id.plane_book_title);
        cv1 = (PlaneDetailCardView) findViewById(R.id.plane_book_cv1);
        cv2 = (PlaneDetailCardView) findViewById(R.id.plane_book_cv2);
        ensurance = (ItemView) findViewById(R.id.plane_book_insurance);
        contact = (ItemView) findViewById(R.id.plane_book_contact);
        tel = (ItemView) findViewById(R.id.plane_book_tel);
        email = (ItemView) findViewById(R.id.plane_book_email);
        psgs_lv = (ListView) findViewById(R.id.plane_book_lv);
        add = findViewById(R.id.plane_book_click_addpsg);
        total_tv = (TextView) findViewById(R.id.plane_book_allprice);
        commit_tv = (TextView) findViewById(R.id.plane_book_commit);
        click_total = findViewById(R.id.plane_book_click_pricedetail);
        applyNo = (ItemView) findViewById(R.id.plane_book_applyNo);
        item_applyNo = findViewById(R.id.plane_book_item_applyNo);
        applyNo_logo = findViewById(R.id.plane_book_applyNo_logo);
        add_ls = findViewById(R.id.plane_book_click_addLSpsg);
        peisong_addr = (ItemView) findViewById(R.id.plane_book_peisong_addr);
        peisong_logo = findViewById(R.id.plane_book_peisong_logo);
        rg = (RadioGroup) findViewById(R.id.plane_book_rg);
        item_paytype = findViewById(R.id.plane_book_paytype);
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
        PlaneRouteBeanWF firstRoute = pPlaneBook.getFirstRoute();
        PlaneListBean mBean = firstRoute.getBean();
        PlaneRouteBeanWF secondRoute = pPlaneBook.getSecondRoute();
        if (secondRoute == null) {
            cv2.setVisibility(View.GONE);
        } else {
            PlaneListBean bean = secondRoute.getBean();
            cv2.setAirline(bean.getCarriername() + bean.getAirline() + "|" + bean.getPlanestyle());
            cv2.setStart_date(TimeUtils.getMMdd(bean.getDeptdate()) + " " + TimeUtils.getTomorrowWeekDay(bean.getDeptdate()));
            cv2.setEnd_date(TimeUtils.getMMdd(bean.getArridate()) + " " + TimeUtils.getTomorrowWeekDay(bean.getArridate()));
            cv2.setStart_time(bean.getDepttime());
            cv2.setEnd_time(bean.getArritime());
            cv2.setOrgname(bean.getOrgname() + bean.getDeptterm());
            cv2.setArriname(bean.getArriname() + bean.getArriterm());
            cv2.setJijian("￥" + bean.getAirporttax());
            cv2.setRun_time(bean.getFlytime());
            PlaneListBean.CangweisBean cangweisBean = bean.getCangweis().get(secondRoute.getCangwei());
            cv2.setFood(bean.isMeal() ? "有餐" : "无餐");
            cv2.setCangwei(cangweisBean.getDisdes() + cangweisBean.getCodeDes());
            cv2.setPrice(String.valueOf(cangweisBean.getPrice()));
            cv2.setGaiStr(cangweisBean.getChangerule());
            cv2.setTuiStr(cangweisBean.getRefundrule());
            cv2.setRouteDes("返程");
        }
        cv1.setAirline(mBean.getCarriername() + mBean.getAirline() + "|" + mBean.getPlanestyle());
        cv1.setStart_date(TimeUtils.getMMdd(mBean.getDeptdate()) + " " + TimeUtils.getTomorrowWeekDay(mBean.getDeptdate()));
        cv1.setEnd_date(TimeUtils.getMMdd(mBean.getArridate()) + " " + TimeUtils.getTomorrowWeekDay(mBean.getArridate()));
        cv1.setStart_time(mBean.getDepttime());
        cv1.setEnd_time(mBean.getArritime());
        cv1.setOrgname(mBean.getOrgname() + mBean.getDeptterm());
        cv1.setArriname(mBean.getArriname() + mBean.getArriterm());
        cv1.setJijian("￥" + mBean.getAirporttax());
        cv1.setRun_time(mBean.getFlytime());
        PlaneListBean.CangweisBean cangweisBean = mBean.getCangweis().get(firstRoute.getCangwei());
        cv1.setCangwei(cangweisBean.getDisdes() + cangweisBean.getCodeDes());
        cv1.setFood(mBean.isMeal() ? "有餐" : "无餐");
        cv1.setPrice("￥" + String.valueOf(cangweisBean.getPrice()));
        cv1.setGaiStr(cangweisBean.getChangerule());
        cv1.setTuiStr(cangweisBean.getRefundrule());
        cv1.setRouteDes(secondRoute == null ? "" : "去程");
        psgs_lv.setAdapter(pPlaneBook.getAdapter());
        pPlaneBook.caculatePrice();
        setApplyNoVisibility(item_applyNo);
        item_paytype.setVisibility(MUtils.getFukuankemu().equals("3") ? View.VISIBLE : View.GONE);
        rbs[0].setChecked(true);
    }

    @Override
    protected void initListener() {
        add.setOnClickListener(this);
        commit_tv.setOnClickListener(this);
        click_total.setOnClickListener(this);
        ensurance.setOnClickListener(this);
        titleView.setTitleClickListener(this);
        applyNo_logo.setOnClickListener(this);
        add_ls.setOnClickListener(this);
        peisong_logo.setOnClickListener(this);
        rg.setOnCheckedChangeListener(this);
    }

    @Override
    protected void setViews() {
        contact.setContent(MyApplication.mUserInfoBean.getName());
        tel.setContent(MyApplication.mUserInfoBean.getMobile());
        email.setContent(MyApplication.mUserInfoBean.getEmail());
        setAddLsPsgVisibility(add_ls);
        add.setVisibility(isAllowBook() ? View.VISIBLE : View.GONE);
        psgs_lv.setClickable(false);
    }

    @Override
    protected void getData() {
        super.getData();
        Map<String, String> map = new HashMap<>();
        UserBean mUserInfoBean = MyApplication.mUserInfoBean;
        map.put("cid", String.valueOf(mUserInfoBean.getCompanyid()));
        map.put("empid", String.valueOf(mUserInfoBean.getId()));
        RetrofitUtil.getPeisongAddr(this, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    List<PeisonListBean> listFromJson = MUtils.getListFromJson(bean.getData(), PeisonListBean[].class);
                    if (listFromJson.size() > 0)
                        peisong_addr.setContent(listFromJson.get(0).getAddr());
                }
                return true;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plane_book_click_addpsg:
                pPlaneBook.jumpToAddPsg();
                break;
            case R.id.plane_book_click_pricedetail://价格详情:
                pPlaneBook.showPriceDetail();
                break;
            case R.id.plane_book_commit://提交订单
                pPlaneBook.createOrder();
                break;
            case R.id.plane_book_insurance:
                pPlaneBook.showEnsuranceDialog();
                break;
            case R.id.plane_book_applyNo_logo:
                pPlaneBook.jumpToApplyNo();
                break;
            case R.id.title_back:
                showDialog("取消", "确定", "订单尚未提交成功，您确定要离开当前页面吗？",
                        new MyDialog.OnButtonClickListener() {
                            @Override
                            public void onLeftClick() {
                            }

                            @Override
                            public void onRightClick() {
                                finish();
                            }
                        });
                break;
            case R.id.plane_book_click_addLSpsg:
                pPlaneBook.jumpToAddLsPsg();
                break;
            case R.id.plane_book_peisong_logo:
                pPlaneBook.jumpToPeisong();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != Constant.ACTIVITY_PLANE_BOOK_FLAG) return;
        if (resultCode == Constant.ACTIVITY_PASSENGER_FLAG) {
            pPlaneBook.setPsgList((List<UserBean>) data.getSerializableExtra("psgs"));
            pPlaneBook.caculatePrice();
            pPlaneBook.getAdapter().notifyDataSetChanged();
        } else if (resultCode == Constant.ACTIVITY_APPLY_NO_CHOSE) {
            applyNo.setContent(data.getStringExtra("no"));
        } else if (resultCode == Constant.ACTIVITY_ADD_LS_PSG_FLAG) {
            UserBean ub = (UserBean) data.getSerializableExtra("bean");
            pPlaneBook.addPsg(ub);
            pPlaneBook.caculatePrice();
            pPlaneBook.getAdapter().notifyDataSetChanged();
        } else if (resultCode == Constant.ACTIVITY_PEISONG_ADDR_FLAG) {
            peisong_addr.setContent(data.getStringExtra("addr"));
        }
    }

    @Override
    public void setTotalPrice(double totalPrice) {
        total_tv.setText(String.valueOf(totalPrice));
    }

    @Override
    public void setEnsurance(int p, String name) {
        if (p == 0) {
            ensurance.setContent("未选择");
        } else {
            ensurance.setContent(name);
        }
    }

    @Override
    public String getContact() {
        return contact.getContent().trim();
    }

    @Override
    public String getMobile() {
        return tel.getContent().trim();
    }

    @Override
    public String getEmail() {
        return email.getContent().trim();
    }

    @Override
    public String getApplyNo() {
        return applyNo.getContent().trim();
    }

    @Override
    public String getPeisong() {
        return peisong_addr.getContent().trim();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        for (int i = 0; i < rbs.length; i++) {
            if (checkedId == rbs[i].getId()) {
                pPlaneBook.setPayTypeByRadio(i);
            }
        }
    }
}
