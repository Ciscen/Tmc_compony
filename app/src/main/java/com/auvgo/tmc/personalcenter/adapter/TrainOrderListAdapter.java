package com.auvgo.tmc.personalcenter.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.Baseadapter;
import com.auvgo.tmc.bean.OrderListBean;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.personalcenter.fragment.TrainOrderListFragment;
import com.auvgo.tmc.utils.MUtils;

import java.util.List;

/**
 * Created by lc on 2016/12/1
 */

public class TrainOrderListAdapter extends Baseadapter<OrderListBean.ListBean, TrainOrderListAdapter.ViewHolder> {

    private int ticketType;

    public TrainOrderListAdapter(Context context, List<OrderListBean.ListBean> list, int resourceId, int ticketType) {
        super(context, list, resourceId);
        this.ticketType = ticketType;
    }

    @Override
    protected ViewHolder getViewHolder(View convertView) {
        ViewHolder vh = new ViewHolder();
        vh.from = (TextView) convertView.findViewById(R.id.item_order_list_from);
        vh.to = (TextView) convertView.findViewById(R.id.item_order_list_to);
        vh.trainCode = (TextView) convertView.findViewById(R.id.item_order_list_no);
        vh.offTime = (TextView) convertView.findViewById(R.id.item_order_list_offtime);
        vh.name = (TextView) convertView.findViewById(R.id.item_order_list_name);
        vh.orderStatus = (TextView) convertView.findViewById(R.id.item_order_list_order_status);
        vh.approveStatus = (TextView) convertView.findViewById(R.id.item_order_list_approve_status);
        vh.price = (TextView) convertView.findViewById(R.id.item_order_list_price);
        vh.orderno = (TextView) convertView.findViewById(R.id.item_order_list_orderno);
        return vh;
    }

    @Override
    protected void bindViews(ViewHolder viewHolder, int position) {
        OrderListBean.ListBean listBean = list.get(position);
        viewHolder.from.setText(listBean.getFromStation());
        viewHolder.to.setText(listBean.getArriveStation());
        viewHolder.trainCode.setText(listBean.getTraincode());
        viewHolder.offTime.setText("出发:" + listBean.getTravelTime() + " " + listBean.getFromTime());
        viewHolder.name.setText(listBean.getChencheRen());
        viewHolder.orderno.setText(String.format("订单号:%s", listBean.getOrderno()));
        switch (ticketType) {
            case TrainOrderListFragment.NORMAL:
                viewHolder.orderStatus.setText(MUtils.getOrgTicketStateByCode(listBean.getStatu()));
                viewHolder.approveStatus.setText(MUtils.getApproveStateByCode(listBean.getApprovestatus()));
                viewHolder.price.setText(String.format("￥%s", listBean.getTotalprice()));
                if (listBean.getPaystatus() == Constant.PayStatus.PAY_STATUS_DAIZHIFU) {
                    viewHolder.approveStatus.setText("待支付");
                }
                break;
            case TrainOrderListFragment.RETURNL:
                viewHolder.orderStatus.setText(MUtils.getReturnStateByCode(listBean.getStatu()));
                viewHolder.approveStatus.setVisibility(View.GONE);
                viewHolder.price.setText(String.format("￥%s", listBean.getTotalprice()));
                break;
            case TrainOrderListFragment.ALTER:
                viewHolder.orderStatus.setText(MUtils.getAlterStateByCode(listBean.getStatu()));
                viewHolder.approveStatus.setText("");
                if (listBean.getPaystatus() == Constant.PayStatus.PAY_STATUS_DAIZHIFU) {
                    viewHolder.approveStatus.setText("待支付");
                }
                viewHolder.price.setText("");
                break;
        }
    }

    class ViewHolder {
        TextView from, to, trainCode, offTime, name, orderStatus, approveStatus, price, orderno;
    }
}
