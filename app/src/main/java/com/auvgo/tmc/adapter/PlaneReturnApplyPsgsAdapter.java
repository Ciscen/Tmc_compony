package com.auvgo.tmc.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.plane.bean.PlaneOrderDetailBean;
import com.auvgo.tmc.train.bean.TrainOrderDetailBean;
import com.auvgo.tmc.utils.AppUtils;

import java.util.List;

/**
 * Created by lc on 2016/12/5
 */

public class PlaneReturnApplyPsgsAdapter extends Baseadapter<PlaneOrderDetailBean.PassengersBean, PlaneReturnApplyPsgsAdapter.ViewHoler> {


    private boolean showPiaohao;
    private PlaneOrderDetailBean mbean;

    public PlaneReturnApplyPsgsAdapter(Context context, List<PlaneOrderDetailBean.PassengersBean> list) {
        super(context, list, R.layout.item_alter_psgs);
    }

    /**
     * @param showPiaoHao 是否显示票号
     */
    public PlaneReturnApplyPsgsAdapter(Context context, PlaneOrderDetailBean bean, boolean showPiaoHao) {
        super(context, bean.getPassengers(), R.layout.item_alter_psgs);
        this.showPiaohao = showPiaoHao;
        this.mbean = bean;
    }


    @Override
    protected ViewHoler getViewHolder(View convertView) {
        TextView name = (TextView) convertView.findViewById(R.id.item_alter_name);
        TextView idNo = (TextView) convertView.findViewById(R.id.item_alter_idNum);
        TextView ticketNo = (TextView) convertView.findViewById(R.id.item_alter_ticketNo);
        ImageView checked = (ImageView) convertView.findViewById(R.id.item_alter_checked);
        return new ViewHoler(name, idNo, ticketNo, checked);
    }

    @Override
    protected void bindViews(ViewHoler viewHolder, int position) {
        viewHolder.name.setText(list.get(position).getName());
        viewHolder.idNo.setText(list.get(position).getCertno());
        if (showPiaohao) viewHolder.ticketNo.setVisibility(View.VISIBLE);
        viewHolder.ticketNo.setText(getTicketNumber(position));
        if (list.get(position).isChecked()) {
            viewHolder.checked.setImageResource(R.mipmap.checked_icon);
        } else {
            viewHolder.checked.setImageResource(R.mipmap.unchecked_icon);
        }
    }

    private String getTicketNumber(int position) {
        PlaneOrderDetailBean.PassengersBean psgBean = list.get(position);
        return getTicketNumFromRoutes(psgBean.getId());
    }

    private String getTicketNumFromRoutes(int id) {
        List<PlaneOrderDetailBean.RoutePassBean> routePass = mbean.getRoutePass();
        if (routePass != null) {
            for (int i = 0; i < routePass.size(); i++) {
                if (routePass.get(i).getId() == id) {
                    return AppUtils.getNoNullStr(routePass.get(i).getPiaohao());
                }
            }
        }
        return "";
    }

    class ViewHoler {
        TextView name, idNo, ticketNo;
        ImageView checked;

        public ViewHoler(TextView name, TextView idNo, TextView ticketNo, ImageView checked) {
            this.name = name;
            this.idNo = idNo;
            this.ticketNo = ticketNo;
            this.checked = checked;
        }
    }

}
