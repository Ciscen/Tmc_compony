package com.auvgo.tmc.utils;

/**
 * Created by lc on 2017/2/28
 */

public class HotelStateCons {
    // 酒店订单状态
    /**
     * ------------到店付---------------
     **/
    public static final int HOTEL_ORDER_STATUS_DANBAO = 0;// 等待担保 （需要去支付费用）
    public static final int HOTEL_ORDER_STATUS_DANBAO_FAIL = 1;// 担保失败
    public static final int HOTEL_ORDER_STATUS_DANBAO_ING = 8;// 担保中
    /**
     * ------------预付---------------
     **/
    public static final int HOTEL_ORDER_STATUS_QUEREN = 2;// 等待确认
    public static final int HOTEL_ORDER_STATUS_QUEREN_ING = 3;// 确认中
    public static final int HOTEL_ORDER_STATUS_QUEREN_SUCCESS = 4;// 已确认
    public static final int HOTEL_ORDER_STATUS_QUEREN_FAIL = 5;// 确认失败 酒店拒绝
    public static final int HOTEL_ORDER_STATUS_CANCEL = 6;// 已取消
    public static final int HOTEL_ORDER_STATUS_COMMITTED = 7;// 已提交
    //酒店支付状态
    public static final int HOTEL_PAY_STATUS = 0;// 等待支付
    public static final int HOTEL_PAY_STATUS_SUCCESS = 1;// 支付成功
    public static final int HOTEL_PAY_STATUS_FAIL = 2;// 支付失败
    public static final int HOTEL_PAY_STATUS_ING = 3;// 支付中

}
