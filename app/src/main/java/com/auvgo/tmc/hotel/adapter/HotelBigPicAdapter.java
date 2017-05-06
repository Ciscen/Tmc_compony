package com.auvgo.tmc.hotel.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.hotel.bean.HotelDetailBean;
import com.auvgo.tmc.utils.LogFactory;
import com.auvgo.tmc.utils.MUtils;

import java.util.List;

/**
 * Created by lc on 2017/3/1
 */

public class HotelBigPicAdapter extends PagerAdapter {
    private List<HotelDetailBean.HotelImageListBean> list;
    private Context context;
    private LayoutInflater mInflater;

    public HotelBigPicAdapter(List<HotelDetailBean.HotelImageListBean> list, Context context) {
        this.list = list;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        ViewGroup.LayoutParams layoutParams = new ViewPager.LayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, context.getResources().getDisplayMetrics());
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        imageView.setLayoutParams(layoutParams);
//        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        MUtils.loadImg(context, list.get(position).getUrl(), imageView, R.mipmap.hotel_detail_imgdefault);
        LogFactory.d("instantiateItem------------执行");
        container.addView(imageView);
        return imageView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView((View) object);
        LogFactory.d("destroyItem------------执行");
    }
}
