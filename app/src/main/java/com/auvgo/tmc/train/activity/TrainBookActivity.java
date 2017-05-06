package com.auvgo.tmc.train.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.p.PTrainBook;
import com.auvgo.tmc.train.bean.TrainBean;
import com.auvgo.tmc.train.bean.UserBean;
import com.auvgo.tmc.train.interfaces.ViewManager_trainbook;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.views.ItemView;
import com.auvgo.tmc.views.TitleView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrainBookActivity extends BaseActivity implements ViewManager_trainbook, View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.plane_detail_airline)
    TextView traincode;
    @BindView(R.id.plane_detail_food)
    TextView notice;
    @BindView(R.id.train_detail_start_station)
    TextView start_station;
    @BindView(R.id.train_detail_end_station)
    TextView end_station;
    @BindView(R.id.train_detail_start_time)
    TextView start_time;
    @BindView(R.id.train_detail_start_date)
    TextView start_date;
    @BindView(R.id.train_detail_end_time)
    TextView end_time;
    @BindView(R.id.train_detail_end_date)
    TextView end_date;
    @BindView(R.id.train_detail_time)
    TextView time;
    @BindView(R.id.train_book_seattype)
    TextView seattype;
    @BindView(R.id.train_book_price)
    TextView price;
    @BindView(R.id.train_book_allprice)
    TextView allprice;
    @BindView(R.id.train_detail_runtime)
    TextView run_time;
    @BindView(R.id.train_book_click_addpsg)
    View addpsg;
    @BindView(R.id.train_book_click_pricedetail)
    View pricedetail;
    @BindView(R.id.train_book_commit)
    View commit;
    @BindView(R.id.train_book_addr_ll)
    View addr_ll;
    @BindView(R.id.train_book_item_applyNo)
    View item_applyNo;
    @BindView(R.id.train_book_applyNo_logo)
    View applyNo_logo;
    @BindView(R.id.train_book_wei)
    ImageView wei;
    @BindView(R.id.train_book_cover)
    View cover;
    @BindView(R.id.train_book_contact_et)
    EditText contact;
    @BindView(R.id.train_book_tel_et)
    EditText tel;
    @BindView(R.id.train_book_email_et)
    EditText email;
    @BindView(R.id.train_book_addr_et)
    EditText addr;
    @BindView(R.id.train_book_lv)
    ListView lv;
    @BindView(R.id.train_book_title)
    TitleView titleView;
    @BindView(R.id.train_book_applyNo)
    ItemView applyNo;
    @BindView(R.id.train_book_click_addLSpsg)
    View add_ls;
    @BindView(R.id.train_book_rg)
    RadioGroup rg;
    @BindView(R.id.train_book_paytype)
    View item_paytype;
    RadioButton[] rbs;
    PTrainBook pTrainBook;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_train_book;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        pTrainBook = new PTrainBook(this, this);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
            pTrainBook.initDate(bundle);
        }
    }

    @Override
    protected void findViews() {
        int childCount = rg.getChildCount();
        rbs = new RadioButton[childCount];
        for (int i = 0; i < childCount; i++) {
            rbs[i] = (RadioButton) rg.getChildAt(i);
        }
    }

    @Override
    protected void initView() {
        cover.setVisibility(View.VISIBLE);
        TrainBean.DBean dBean = pTrainBook.getdBean();
        String date = dBean.getTrain_start_date();
        String startDate = TimeUtils.getDateByCostDays(date, 0, "MM-dd");
        String endDate = TimeUtils.getDateByCostDays(date, Integer.parseInt(dBean.getArrive_days()), "MM-dd");
        traincode.setText(dBean.getTrain_code());
        start_station.setText(dBean.getFrom_station_name());
        start_time.setText(dBean.getStart_time());
        end_station.setText(dBean.getTo_station_name());
        end_time.setText(dBean.getArrive_time());
        start_date.setText(startDate + "  " + TimeUtils.getTomorrowWeekDay(date));
        end_date.setText(endDate + "  " + TimeUtils.getTomorrowWeekDay(TimeUtils.getDateByCostDays(date,
                Integer.parseInt(dBean.getArrive_days()), "yyyy-MM-dd")));
        seattype.setText(dBean.getCanBook().get(pTrainBook.getSeattypeposition()).get(0));
        this.price.setText(pTrainBook.getPrice());
        lv.setAdapter(pTrainBook.getAdapter());
        run_time.setText(dBean.getRun_time().replace(":", "时") + "分钟");
        caculatePrice();
        UserBean infoBean = MyApplication.mUserInfoBean;
        contact.setText(infoBean.getName());
        tel.setText(infoBean.getMobile());
        email.setText(infoBean.getEmail());
        if (isSeatWei(pTrainBook.getdBean().getCanBook().get(pTrainBook.getSeattypeposition()).get(3))) {
            wei.setVisibility(View.INVISIBLE);
        } else {
            wei.setVisibility(View.INVISIBLE);
        }
        addr_ll.setVisibility(View.GONE);
        setApplyNoVisibility(item_applyNo);

        item_paytype.setVisibility(MUtils.getFukuankemu().equals("3") ? View.VISIBLE : View.GONE);
        rbs[0].setChecked(true);

        if (!isAllowBook()) {
            addpsg.setVisibility(View.GONE);
            lv.setClickable(false);
        }
    }

    private boolean isSeatWei(String s) {
        if (MyApplication.mTrainPolicy == null) return false;
        //  判断传入的s是否在规则给定的坐席里面，在false，不在true
        if (MyApplication.mTrainPolicy.getDonche().contains(s)) {
            return false;
        }
        if (MyApplication.mTrainPolicy.getGaotie().contains(s)) {
            return false;
        }
        if (MyApplication.mTrainPolicy.getPukuai().contains(s)) {
            return false;
        }

        return true;
    }


    @Override
    protected void initListener() {
        notice.setOnClickListener(this);
        time.setOnClickListener(this);
        addpsg.setOnClickListener(this);
        commit.setOnClickListener(this);
        pricedetail.setOnClickListener(this);
        titleView.setTitleClickListener(this);
        applyNo_logo.setOnClickListener(this);
        rg.setOnCheckedChangeListener(this);
    }

    @Override
    protected void setViews() {
        setAddLsPsgVisibility(add_ls);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.train_detail_time:
                pTrainBook.getTrainTime();
                break;
            case R.id.plane_detail_food:
                pTrainBook.jumpNotice();
                break;
            case R.id.train_book_click_addpsg:
                pTrainBook.jumpToAddPsg();
                break;
            case R.id.train_book_commit:
                pTrainBook.createOrder();
                break;
            case R.id.train_book_click_pricedetail:
                pTrainBook.showPriceDialog(cover, pricedetail.getHeight());
                break;
            case R.id.title_back:
                pTrainBook.back();
                break;
            case R.id.train_book_applyNo_logo:
                pTrainBook.jumpToApplyNo();
                break;
        }
    }

    @OnClick(R.id.train_book_click_addLSpsg)
    public void onAddLsPsg(View view) {
        pTrainBook.jumpToAddLsPsg();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != Constant.ACTIVITY_TRAIN_BOOK_FLAG) return;
        if (resultCode == Constant.ACTIVITY_PASSENGER_FLAG) {
            pTrainBook.setPsgList((List<UserBean>) data.getSerializableExtra("psgs"));
            caculatePrice();
        } else if (resultCode == Constant.ACTIVITY_APPLY_NO_CHOSE) {
            applyNo.setContent(data.getStringExtra("no"));
        } else if (resultCode == Constant.ACTIVITY_ADD_LS_PSG_FLAG) {
            UserBean ub = (UserBean) data.getSerializableExtra("bean");
            pTrainBook.addPsg(ub);
            caculatePrice();
            pTrainBook.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void caculatePrice() {
        allprice.setText(pTrainBook.calculatePrice());
    }

    @Override
    public String getAddr() {
//        if (pTrainBook.getBooktypeposition() == 0) return addr.getText().toString();
        return "";
    }

    @Override
    public String getMobile() {
        return tel.getText().toString();
    }

    @Override
    public String getEmail() {
        return email.getText().toString();
    }

    @Override
    public String getContact() {
        return contact.getText().toString();
    }

    @Override
    public String getApplyNo() {
        return applyNo.getContent().trim();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        for (int i = 0; i < rbs.length; i++) {
            if (checkedId == rbs[i].getId()) {
                pTrainBook.setPayTypeByRadio(i);
            }
        }
    }
}
