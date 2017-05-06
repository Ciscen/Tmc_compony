package com.auvgo.tmc.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.train.bean.TrainBean;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by lc on 2016/11/15
 */

public class TrainListAdapter extends Baseadapter<TrainBean.DBean, TrainListAdapter.ViewHolder> {


    public TrainListAdapter(Context context, List<TrainBean.DBean> list, int resourceId) {
        super(context, list, resourceId);
    }

    @Override
    protected ViewHolder getViewHolder(View convertView) {
        TextView code = (TextView) convertView.findViewById(R.id.item_train_list_traincode);
        TextView starttime = (TextView) convertView.findViewById(R.id.item_train_list_start_time);
        TextView arrivetime = (TextView) convertView.findViewById(R.id.item_train_list_arrive_time);
        TextView runtime = (TextView) convertView.findViewById(R.id.item_train_list_run_time);
        TextView from = (TextView) convertView.findViewById(R.id.item_train_list_from);
        TextView to = (TextView) convertView.findViewById(R.id.item_train_list_to);
        TextView price = (TextView) convertView.findViewById(R.id.item_train_list_price);
        TextView seattype = (TextView) convertView.findViewById(R.id.item_train_list_seat_type);
        TextView lastnum = (TextView) convertView.findViewById(R.id.item_train_list_last_num);
        ImageView wei = (ImageView) convertView.findViewById(R.id.item_train_list_wei);
        View c1 = convertView.findViewById(R.id.item_train_list_c1);
        View c2 = convertView.findViewById(R.id.item_train_list_c2);
        return new ViewHolder(code, starttime, arrivetime,
                runtime, from, to, price, seattype, lastnum, wei, c1, c2);
    }

    @Override
    protected void bindViews(ViewHolder viewHolder, int position) {
        TrainBean.DBean dBean = list.get(position);
        setViews(viewHolder, dBean);
    }

    private void setViews(ViewHolder viewHolder, TrainBean.DBean dBean) {
        viewHolder.code.setText(dBean.getTrain_code());
        viewHolder.starttime.setText(dBean.getStart_time());
        viewHolder.arrivetime.setText(dBean.getArrive_time());
        viewHolder.runtime.setText(dBean.getRun_time().replace(":", "时") + "分");
        viewHolder.from.setText(dBean.getFrom_station_name());
        viewHolder.to.setText(dBean.getTo_station_name());
        List<List<String>> canBook = dBean.getCanBook();
        if (canBook.size() == 0) return;
        viewHolder.price.setText(canBook.get(0).get(2));
        viewHolder.seattype.setText(canBook.get(0).get(0));
        viewHolder.starttime.setText(dBean.getStart_time());
        String s = canBook.get(0).get(1);
        if (s.equals("有")) {
            viewHolder.lastnum.setText("充足");
            viewHolder.lastnum.setTextColor(context.getResources().getColor(R.color.color_333));
        } else {
            viewHolder.lastnum.setText(s);
            viewHolder.lastnum.setTextColor(context.getResources().getColor(R.color.appTheme2));
        }
        if (isTrainWei(canBook)) {
            viewHolder.wei.setVisibility(View.VISIBLE);
        } else {
            viewHolder.wei.setVisibility(View.INVISIBLE);
        }
        if (dBean.getStart_station_code().equals(dBean.getFrom_station_code())) {
            viewHolder.c1.setBackgroundResource(R.drawable.shape_circle_oval);
        } else {
            viewHolder.c1.setBackgroundResource(R.drawable.shape_circle_ring);
        }
        if (dBean.getEnd_station_code().equals(dBean.getTo_station_code())) {
            viewHolder.c2.setBackgroundResource(R.drawable.shape_circle_oval);
        } else {
            viewHolder.c2.setBackgroundResource(R.drawable.shape_circle_ring);
        }

    }

    private boolean isTrainWei(List<List<String>> canBook) {
        boolean b = true;
        for (List<String> strings : canBook) {
            if (!isSeatWei(strings.get(3))) {
                b = false;
            }
        }
        return b;
    }

    private boolean isSeatWei(String s) {
        //  判断传入的s是否在规则给定的坐席里面，在false，不在true

        if (MyApplication.mTrainPolicy == null) {
            return false;
        }
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

    static class ViewHolder {
        TextView code, starttime, arrivetime, runtime, from, to, price, seattype, lastnum;
        ImageView wei;
        View c1, c2;

        public ViewHolder(TextView code, TextView starttime, TextView arrivetime,
                          TextView runtime, TextView from, TextView to, TextView price,
                          TextView seattype, TextView lastnum, ImageView wei, View c1, View c2) {
            this.code = code;
            this.starttime = starttime;
            this.arrivetime = arrivetime;
            this.runtime = runtime;
            this.from = from;
            this.to = to;
            this.price = price;
            this.seattype = seattype;
            this.lastnum = lastnum;
            this.wei = wei;
            this.c1 = c1;
            this.c2 = c2;
        }
    }
}
