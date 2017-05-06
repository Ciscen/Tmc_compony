package com.auvgo.tmc.views;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.train.activity.NoticeActivity;

/**
 * Created by lc on 2016/12/5
 */

public class TrainDetailCardView extends LinearLayout {
    //车次详情的控件
    private TextView traincode, notice,
            start_station, end_station,
            start_time, start_date, end_time,
            end_date, timeOrSeattype_tv, run_time;

    private int timeOrSeattype = 0;

    public TrainDetailCardView(Context context) {
        super(context);
        initViews(context);
    }


    public TrainDetailCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(R.styleable.TrainDetailCardView);
        timeOrSeattype = ta.getInt(R.styleable.TrainDetailCardView_timeOrSeattype, 0);
        ta.recycle();
        initViews(context);
    }

    private void initViews(final Context context) {
        View.inflate(context, R.layout.layout_train_detail, this);
        traincode = (TextView) findViewById(R.id.plane_detail_airline);
        notice = (TextView) findViewById(R.id.plane_detail_food);
        start_station = (TextView) findViewById(R.id.train_detail_start_station);
        end_station = (TextView) findViewById(R.id.train_detail_end_station);
        start_time = (TextView) findViewById(R.id.train_detail_start_time);
        start_date = (TextView) findViewById(R.id.train_detail_start_date);
        end_time = (TextView) findViewById(R.id.train_detail_end_time);
        end_date = (TextView) findViewById(R.id.train_detail_end_date);
        timeOrSeattype_tv = (TextView) findViewById(R.id.train_detail_time);
        run_time = (TextView) findViewById(R.id.train_detail_runtime);

        if (timeOrSeattype == 0) {
            timeOrSeattype_tv.setText("时刻表");
        } else {
            timeOrSeattype_tv.setText("");
            timeOrSeattype_tv.setTextColor(Color.BLACK);
        }
        notice.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, NoticeActivity.class));
            }
        });
    }

    public void setTraincode(String traincode) {
        this.traincode.setText(traincode);
    }

    public void setNotice(String notice) {
        this.notice.setText(notice);
    }

    public void setStart_station(String start_station) {
        this.start_station.setText(start_station);
    }

    public void setEnd_station(String end_station) {
        this.end_station.setText(end_station);
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

    public void setTimeOrSeattypeClickListener(OnClickListener listener) {
        if (listener != null)
            this.timeOrSeattype_tv.setOnClickListener(listener);
    }

    public void setSeatType(String seatType) {
        timeOrSeattype_tv.setText(seatType);
    }

    public void setRun_time(String run_time) {
        this.run_time.setText(run_time);
    }
}
