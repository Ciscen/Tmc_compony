package com.auvgo.tmc.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.PickerListAdapter;
import com.auvgo.tmc.adapter.PickerListAdapter2;
import com.auvgo.tmc.train.bean.SelectionBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lc on 2016/12/21
 */

public class PlaneFilterView extends LinearLayout implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemClickListener {
    private Context context;
    private TextView cancle, sure, title;
    private RadioGroup rg;
    private ListView lv;
    private OnPlaneFilterViewListener listener;
    private List<SelectionBean> sa, sb, sc;
    /*
     *回调的位置
     */
    private List<Integer> a, c;
    private int b;
    /*
    当前的筛选类别
     */
    private int mType;

    private PickerListAdapter2 adapter;

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int childCount = rg.getChildCount();
        for (int i = 0; i < childCount; i++) {
            RadioButton childAt = (RadioButton) rg.getChildAt(i);
            if (childAt.getId() == checkedId) {
                if (mType != i) {
                    setListContent(i);
                }
                mType = i;
                childAt.setChecked(true);
            } else {
                childAt.setChecked(false);
            }
        }
    }

    private void setListContent(int i) {
        switch (i) {
            case 0:
                adapter = new PickerListAdapter2(context, sa, R.layout.item_filter_lv);
                break;
            case 1:
                adapter = new PickerListAdapter2(context, sb, R.layout.item_filter_lv);
                break;
            case 2:
                adapter = new PickerListAdapter2(context, sc, R.layout.item_filter_lv);
                break;
        }
        lv.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (mType) {
            case 0:
                if (position == 0) {
                    setUnckecked(sa, 0);
                } else {
                    sa.get(0).setChecked(false);
                    sa.get(position).setChecked(!sa.get(position).isChecked());
                }
                break;
            case 1:
                setUnckecked(sb, position);
                break;
            case 2:
                if (position == 0) {
                    setUnckecked(sc, 0);
                } else {
                    sc.get(0).setChecked(false);
                    sc.get(position).setChecked(!sc.get(position).isChecked());
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    private void setUnckecked(List<SelectionBean> sa, int p) {
        for (int i = 0; i < sa.size(); i++) {
            sa.get(i).setChecked(false);
        }
        if (p >= 0) {
            if (sa.size() >= p + 1)
                sa.get(p).setChecked(true);
        }
    }

    public interface OnPlaneFilterViewListener {
        /**
         * @param a 起飞时间  的选择位置（多选）
         * @param b 舱位类型  的选择位置（单选）
         * @param c 航空公司  的选择位置（多选）
         */
        void onSure(List<Integer> a, int b, List<Integer> c);

        void onCancle();
    }

    public PlaneFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View.inflate(context, R.layout.layout_plane_filter, this);
        initData();
        initView();
        initListener();
    }

    public PlaneFilterView(Context context) {
        super(context);
        this.context = context;
        View.inflate(context, R.layout.layout_plane_filter, this);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        a = new ArrayList<>();
        c = new ArrayList<>();
    }

    private void initView() {
        cancle = (TextView) findViewById(R.id.plane_filter_cancle);
        sure = (TextView) findViewById(R.id.plane_filter_sure);
        title = (TextView) findViewById(R.id.plane_filter_title);
        rg = (RadioGroup) findViewById(R.id.plane_filter_rg);
        lv = (ListView) findViewById(R.id.plane_filter_lv);
        RadioButton childAt = (RadioButton) rg.getChildAt(0);
        childAt.setChecked(true);
    }

    private void initListener() {
        cancle.setOnClickListener(this);
        sure.setOnClickListener(this);
        rg.setOnCheckedChangeListener(this);
        lv.setOnItemClickListener(this);
    }

    public PlaneFilterView setOnPlaneFilterViewListener(OnPlaneFilterViewListener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * 设置选项的list
     * @param a
     * @param b
     * @param c
     * @return
     */
    public PlaneFilterView setSelections(List<SelectionBean> a, List<SelectionBean> b, List<SelectionBean> c) {
        sa = a;
        sb = b;
        sc = c;
        getSelectedPositions();
        adapter = new PickerListAdapter2(context, sa, R.layout.item_filter_lv);
        lv.setAdapter(adapter);
        return this;
    }

    /**
     * 通过sa,sb,sc得到选中的位置信息
     */
    private void getSelectedPositions() {
        a.clear();
        c.clear();
        b = 0;
        for (int i = 0; i < sa.size(); i++) {
            if (sa.get(i).isChecked()) {
                a.add(i);
            }
        }
        for (int i = 0; i < sc.size(); i++) {
            if (sc.get(i).isChecked()) {
                c.add(i);
            }
        }
        for (int i = 0; i < sb.size(); i++) {
            if (sb.get(i).isChecked()) {
                b = i;
                break;
            }
        }
    }

    /**
     * 重新恢复刚进来时的选择状态位置
     */
    private void restorePosition() {
        setUnckecked(sa, -1);
        setUnckecked(sb, b);
        setUnckecked(sc, -1);
        for (int i = 0; i < a.size(); i++) {
            sa.get(a.get(i)).setChecked(true);
        }
        for (int i = 0; i < c.size(); i++) {
            sc.get(c.get(i)).setChecked(true);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plane_filter_cancle:
                restorePosition();
                if (listener != null) listener.onCancle();
                break;
            case R.id.plane_filter_sure:
                getSelectedPositions();
                if (listener != null) listener.onSure(a, b, c);
                break;
        }
    }
}
