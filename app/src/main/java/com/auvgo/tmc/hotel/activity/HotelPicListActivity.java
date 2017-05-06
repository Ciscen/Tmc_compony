package com.auvgo.tmc.hotel.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.hotel.adapter.HotelPicListAdapter;
import com.auvgo.tmc.hotel.bean.HotelDetailBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HotelPicListActivity extends BaseActivity {

    @BindView(R.id.hotel_pic_list_lv)
    ListView lv;
    @BindView(R.id.hotel_pic_list_bottom_ll)
    LinearLayout bottom;
    private List<TextView> texts;

    private List<HotelDetailBean.HotelImageListBean> list_all;

    private List<ArrayList<HotelDetailBean.HotelImageListBean>> list_show;
    private List<ArrayList<HotelDetailBean.HotelImageListBean>> list_filted;
    private HotelPicListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hotel_pic_list;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        list_all = getIntent().getBundleExtra("bundle").getParcelableArrayList("imgs");
        list_show = initListShow();
        list_filted = new ArrayList<>();
        list_filted.addAll(list_show);
        texts = new ArrayList<>();
        adapter = new HotelPicListAdapter(this, list_filted);
    }

    private ArrayList<ArrayList<HotelDetailBean.HotelImageListBean>> initListShow() {
        if (list_all == null) return null;
        ArrayList<ArrayList<HotelDetailBean.HotelImageListBean>> l = new ArrayList<>();
        ArrayList<HotelDetailBean.HotelImageListBean> list_waiguan = new ArrayList<>();
        ArrayList<HotelDetailBean.HotelImageListBean> list_kefang = new ArrayList<>();
        ArrayList<HotelDetailBean.HotelImageListBean> list_sheshi = new ArrayList<>();
        ArrayList<HotelDetailBean.HotelImageListBean> list_qita = new ArrayList<>();
        for (int i = 0; i < list_all.size(); i++) {
            HotelDetailBean.HotelImageListBean hilb = list_all.get(i);
            if (hilb.getImageTitle() == null) {
                list_qita.add(hilb);
            } else if (hilb.getImageTitle().equals("外观")) {
                list_waiguan.add(hilb);
            } else if (hilb.getImageTitle().equals("客房")) {
                list_kefang.add(hilb);
            } else {
                list_sheshi.add(hilb);
            }
        }
        l.add(list_waiguan);
        l.add(list_kefang);
        l.add(list_sheshi);
        l.add(list_qita);
        return l;
    }

    @Override
    protected void initView() {
        lv.setAdapter(adapter);
        int childCount = bottom.getChildCount();
        for (int i = 0; i < childCount; i++) {
            texts.add((TextView) bottom.getChildAt(i));
        }
    }

    @Override
    protected void initListener() {
        for (int i = 0; i < texts.size(); i++) {
            final int finalI = i;
            texts.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list_filted.clear();
                    switch (finalI) {
                        case 0:
                            list_filted.addAll(list_show);
                            break;
                        case 1:
                            list_filted.add(list_show.get(0));
                            break;
                        case 2:
                            list_filted.add(list_show.get(1));
                            break;
                        case 3:
                            list_filted.add(list_show.get(2));
                            break;
                    }
                    changeColor(finalI);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    private void changeColor(int i) {
        for (int i1 = 0; i1 < texts.size(); i1++) {
            if (i1 == i) {
                texts.get(i1).setTextColor(getResources().getColor(R.color.appTheme1));
            } else {
                texts.get(i1).setTextColor(getResources().getColor(R.color.color_666));
            }
        }
    }

    @Override
    protected void setViews() {
        texts.get(0).setTextColor(getResources().getColor(R.color.appTheme1));
    }
}
