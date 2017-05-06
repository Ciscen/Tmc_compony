package com.auvgo.tmc.plane.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;

public class PlanTuiGaiQianActivity extends BaseActivity {

    private String tuipiao;
    private String gaiqian;
    private TextView tui, gai;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_plan_tui_gai_qian;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        tuipiao = intent.getStringExtra("tuipiao");
        gaiqian = intent.getStringExtra("gaiqian");
    }

    @Override
    protected void findViews() {
        tui = (TextView) findViewById(R.id.tuipiao_tv);
        gai = (TextView) findViewById(R.id.gaiqian_tv);
    }

    @Override
    protected void initView() {
        tui.setText(tuipiao);
        gai.setText(gaiqian);
    }

    @Override
    protected void setViews() {

    }

    @Override
    protected void initListener() {
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
