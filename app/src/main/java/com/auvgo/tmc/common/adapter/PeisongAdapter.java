package com.auvgo.tmc.common.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.Baseadapter;
import com.auvgo.tmc.common.bean.PeisonListBean;

import java.util.List;

/**
 * Created by lc on 2017/4/11
 */

public class PeisongAdapter extends Baseadapter<PeisongAdapter.IPeisong, PeisongAdapter.ViewHolder> {


    public PeisongAdapter(Context context, List<? extends PeisongAdapter.IPeisong> list) {
        super(context, list, R.layout.item_peisong);
    }

    @Override
    protected ViewHolder getViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    protected void bindViews(ViewHolder viewHolder, int position) {
        IPeisong pb = list.get(position);
        if (pb == null) return;
        viewHolder.addr.setText(pb.getAddr());
        viewHolder.name.setText(pb.getName());
        viewHolder.icon.setImageResource(pb.isDefault() ? R.mipmap.checked_peisong : R.mipmap.unchecked_peisong);
        viewHolder.tel.setText(pb.getTel());
    }

    public static class ViewHolder {
        TextView addr, name, tel;
        ImageView icon;

        public ViewHolder(View view) {
            this.addr = (TextView) view.findViewById(R.id.item_peisong_addr);
            this.name = (TextView) view.findViewById(R.id.item_peisong_name);
            this.tel = (TextView) view.findViewById(R.id.item_peisong_tel);
            this.icon = (ImageView) view.findViewById(R.id.item_peisong_icon);
        }
    }

    public interface IPeisong {
        String getName();

        String getTel();

        String getAddr();

        boolean isDefault();

        int getId();
    }
}
