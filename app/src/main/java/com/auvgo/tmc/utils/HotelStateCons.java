package com.auvgo.tmc.utils;

/**
 * Created by lc on 2017/2/28
 */

public class HotelStateCons {
    // 酒店订单状态
//    /**
//     * ------------到店付---------------
//     **/
//    public static final int HOTEL_ORDER_STATUS_DANBAO = 0;// 等待担保 （需要去支付费用）
//    public static final int HOTEL_ORDER_STATUS_DANBAO_FAIL = 1;// 担保失败
//    public static final int HOTEL_ORDER_STATUS_DANBAO_ING = 8;// 担保中
//    /**
//     * ------------预付---------------
//     **/

    public static class OrderStatus {
        public static final int HOTEL_ORDER_STATUS_CANCEL = 6;// 已取消
        public static final int HOTEL_ORDER_STATUS_COMMITTED = 7;// 已提交
        public static final int HOTEL_ORDER_STATUS_DENGDAIQUEREN = 2;// 等待确认
        public static final int HOTEL_ORDER_STATUS_QUEREN_ING = 3;// 确认中
        public static final int HOTEL_ORDER_STATUS_QUEREN_SUCCESS = 4;// 等待入住
        public static final int HOTEL_ORDER_STATUS_QUEREN_FAIL = 5;// 确认失败 酒店拒绝
        public static final int HOTEL_ORDER_STATUS_YIWANCHENG = 8;// 已完成
    }

    public static class PayStatus {
        //酒店支付状态
        public static final int HOTEL_PAY_STATUS_WEIZHIFU = 0;// 未支付
        public static final int HOTEL_PAY_STATUS_DAIZHIFU = 3;// 等待支付
        public static final int HOTEL_PAY_STATUS_ZHIFUCHENGGONG = 1;// 支付成功
        public static final int HOTEL_PAY_STATUS_ZHIFUSHIBAI = 8192;// 支付失败
        public static final int HOTEL_PAY_STATUS_ZHIFUZHONG = 4096;// 支付中
        public static final int HOTEL_PAY_STATUS_WEIDANBAO = 2;// 未担保
        public static final int HOTEL_PAY_STATUS_DAIDANBAO = 4;// 待担保
        public static final int HOTEL_PAY_STATUS_DANBAOCHENGGONG = 5;// 担保成功
        public static final int HOTEL_PAY_STATUS_DANBAOSHIBAI = 7;// 担保失败
        public static final int HOTEL_PAY_STATUS_DANBAOZHONG = 6;// 担保中
        public static final int HOTEL_PAY_STATUS_DAITUIKUAN = 8;// 退款中
        public static final int HOTEL_PAY_STATUS_TUIKUANCHENGGONG = 9;//退款成功
        public static final int HOTEL_PAY_STATUS_TUIKUANSHIBAI = 10;// 退款失败
    }

}
