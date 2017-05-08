package com.auvgo.tmc.plane.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.PlaneOrderDetailPsgAdapter;
import com.auvgo.tmc.plane.bean.PlaneOrderDetailBean;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.views.ItemView;
import com.auvgo.tmc.views.PlaneDetailCardView;

public class PlaneSendDetailActivity extends AppCompatActivity {
    private TextView orderNo, contact, tel, email;
    private PlaneDetailCardView cv;
    private ItemView ensurance;
    private ListView psgs_lv;
    private PlaneOrderDetailBean mBean;
    private PlaneOrderDetailPsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plane_send_detail);
        init();
    }

    private void init() {
        initData();
        findViews();
        initViews();
    }


    private void initData() {
        mBean = getIntent().getParcelableExtra("bean");
        adapter = new PlaneOrderDetailPsgAdapter(this, mBean.getPassengers());
    }

    private void findViews() {
        orderNo = (TextView) findViewById(R.id.plane_send_detail2_orderNo);
        contact = (TextView) findViewById(R.id.plane_send_detail2_contact);
        tel = (TextView) findViewById(R.id.plane_send_detail2_tel);
        email = (TextView) findViewById(R.id.plane_send_detail2_email);
        cv = (PlaneDetailCardView) findViewById(R.id.plane_send_detail2_cv);
        ensurance = (ItemView) findViewById(R.id.plane_send_detail2_ensurance);
        psgs_lv = (ListView) findViewById(R.id.plane_send_detail2_psg_lv);
    }

    private void initViews() {
        orderNo.setText(String.format("订单号:%s", mBean.getOrderno()));
        contact.setText(mBean.getLinkName());
        tel.setText(mBean.getLinkPhone());
        String linkEmail = mBean.getLinkEmail();
        if (TextUtils.isEmpty(linkEmail)) {
            email.setVisibility(View.GONE);
        } else {
            email.setText(linkEmail);
        }
        ensurance.setContent(String.valueOf(mBean.getPassengers().get(0).getBxName()));
        psgs_lv.setAdapter(adapter);
        PlaneOrderDetailBean.RoutesBean rb = mBean.getRoutes().get(0);
        cv.setAirline(rb.getCarriername() + rb.getAirline() + "|" + rb.getPlanestyle());
        cv.setStart_date(rb.getDeptdate().substring(5) + " " + TimeUtils.getTomorrowWeekDay(rb.getDeptdate()));
        cv.setEnd_date(rb.getArridate().substring(5) + " " + TimeUtils.getTomorrowWeekDay(rb.getArridate()));
        cv.setStart_time(rb.getDepttime());
        cv.setEnd_time(rb.getArritime());
        cv.setOrgname(rb.getOrgname());
        cv.setArriname(rb.getArriname());
        cv.setBottom2(rb.getCodeDes());
        cv.setGaiStr(rb.getChangerule());
        cv.setTuiStr(rb.getRefundrule());
    }
}
