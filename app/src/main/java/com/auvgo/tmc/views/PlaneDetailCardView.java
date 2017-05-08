package com.auvgo.tmc.views;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.plane.activity.PlanTuiGaiQianActivity;

/**
 * Created by lc on 2016/12/24
 */

public class PlaneDetailCardView extends LinearLayout {

    private TextView airline, cangwei, cangwei2, food, food2,
            start_time, start_date, end_time,
            end_date, price,
            orgname, arriname, jijian, tuigaiqian,
            run_time, run_time2, routeDes, cangwei_bottom;
    private View bottom, bottom2, tuigaiqian2;
    private String tuiStr;
    private String gaiStr;
    private Context context;

    public String getGaiStr() {
        return gaiStr;
    }

    public void setGaiStr(String gaiStr) {
        this.gaiStr = gaiStr;
    }

    public String getTuiStr() {
        return tuiStr;
    }

    public void setTuiStr(String tuiStr) {
        this.tuiStr = tuiStr;
    }

    private int type = 0;

    public PlaneDetailCardView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View.inflate(context, R.layout.layout_plane_detail_card, this);
        TypedArray a = context.getResources().obtainAttributes(attrs, R.styleable.PlaneDetailCardView);
        type = a.getInt(R.styleable.PlaneDetailCardView_type, 0);

        routeDes = (TextView) findViewById(R.id.plane_detail_route_des);
        airline = (TextView) findViewById(R.id.plane_detail_airline);
        cangwei = (TextView) findViewById(R.id.plane_detail_cangwei);
        cangwei2 = (TextView) findViewById(R.id.plane_detail_cangwei2);
        food = (TextView) findViewById(R.id.plane_detail_food);
        food2 = (TextView) findViewById(R.id.plane_detail_food2);
        start_time = (TextView) findViewById(R.id.plane_detail_depttime);
        start_date = (TextView) findViewById(R.id.plane_detail_deptdate);
        end_time = (TextView) findViewById(R.id.plane_detail_arritime);
        end_date = (TextView) findViewById(R.id.plane_detail_arridate);
        price = (TextView) findViewById(R.id.plane_detail_price);
        orgname = (TextView) findViewById(R.id.plane_detail_orgname);
        arriname = (TextView) findViewById(R.id.plane_detail_arriname);
        jijian = (TextView) findViewById(R.id.plane_detail_jijianfei);
        tuigaiqian = (TextView) findViewById(R.id.plane_detail_tuigaiqian);
        run_time = (TextView) findViewById(R.id.plane_detail_costtime);
        run_time2 = (TextView) findViewById(R.id.plane_detail_runtime2);
        bottom = findViewById(R.id.plane_detail_bottom);
        bottom2 = findViewById(R.id.plane_detail_bottom2);
        tuigaiqian2 = findViewById(R.id.plane_detail_tuigaiqian2);
        cangwei_bottom = (TextView) findViewById(R.id.plane_detail_cangwei_bottom);
        if (type == 0) {//右下角 无餐  ，航班详情页面

        } else if (type == 1) {//右下角  退改签，订单填写页面
            price.setVisibility(VISIBLE);
            run_time2.setVisibility(GONE);
            food2.setVisibility(GONE);
            tuigaiqian.setVisibility(VISIBLE);
        } else if (type == 2) {//带有航班动态的，订单详情页面
//            run_time2.setText("航班动态");
//            food2.setVisibility(GONE);
//            tuigaiqian.setVisibility(VISIBLE);
            bottom.setVisibility(GONE);
            bottom2.setVisibility(VISIBLE);
        } else if (type == 3) {//底部不显示
            findViewById(R.id.plane_detail_bottom).setVisibility(GONE);
        }
        tuigaiqian.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToTuigaiqian(context);
            }
        });
        tuigaiqian2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToTuigaiqian(context);
            }
        });
        a.recycle();
    }

    private void jumpToTuigaiqian(Context context) {
        Intent intent = new Intent(context, PlanTuiGaiQianActivity.class);
        intent.putExtra("tuipiao", tuiStr);
        intent.putExtra("gaiqian", gaiStr);
        context.startActivity(intent);
    }


    public void setAirline(String airline) {
        this.airline.setText(airline);
    }

    public void setCangwei(String cangwei) {
        if (type == 2) {
            cangwei_bottom.setText(cangwei);
        } else {
            this.cangwei.setText(cangwei);
        }
    }

    public void setCangwei2(String cangwei2) {
        this.cangwei2.setText(cangwei2);
    }

    public void setBottom2(String s) {
        cangwei_bottom.setText(s);
    }

    public void setFood(String food) {
        this.food.setText(food);
    }

    public void setFood2(String food2) {
        this.food2.setText(food2);
    }

    public void setStart_time(String start_time) {
        this.start_time.setText(start_time);
    }

    public void setStart_date(String start_date) {
        this.start_date.setText(start_date);
    }

    public void setEnd_time(String end_time) {
        this.end_time.setText(end_time);
    }

    public void setEnd_date(String end_date) {
        this.end_date.setText(end_date);
    }

    public void setPrice(String price) {
        this.price.setText(price);
    }

    public void setOrgname(String orgname) {
        this.orgname.setText(orgname);
    }

    public void setArriname(String arriname) {
        this.arriname.setText(arriname);
    }

    public void setJijian(String jijian) {
        this.jijian.setText(jijian);
        this.cangwei2.setText("机建/燃油：");
    }

    public void setTuigaiqian(String tuigaiqian) {
        this.tuigaiqian.setText(tuigaiqian);
    }

    public void setRun_time(String run_time) {
        this.run_time.setText(run_time);
    }

    public void setRun_time2(String run_time2) {
        this.run_time2.setText(run_time2);
    }

    public void setRouteDes(String routeDes) {
        this.routeDes.setText(routeDes);
    }
}
