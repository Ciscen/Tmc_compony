package com.auvgo.tmc.constants;

/**
 * Created by admin on 2016/11/8
 * 需要使用静态内部类，对常量进行分类
 */

public class Constant {
    /*
     *appKey 固定值，用于服务端统计android跟ios的表示
     */
    public static final String APPKEY = "xinglvand";
    /*
     *appSecrit 固定值，用于加密的时候混淆使用
     */
    public static final String APPSECRIT = "8098615395fae44857ac080fc7c9b59e";

    /*
     * 用于公用模块调用时区分来源
     */
    public static final String TRAIN = "train";
    public static final String HOTEL = "hotel";
    public static final String PLANE = "air";

    /*
    微信支付的APP_ID
     */
    public static final String APP_ID_WX = "wxf7c591d3fd7f6070";

    /*
       用于标记车次类别的字符串
      */
    public static class TrainType {
        public static final String TRAIN_TYPE_ALL = "GCDZTKYL0123456789";
        public static final String TRAIN_TYPE_GT = "GCD";
        public static final String TRAIN_TYPE_PT = "ZTKYL0123456789";
    }

    /**
     * 用于标记是审批还是详情的动作
     */
    public static class PushDirectionType {
        public static final int PUSH_DIRECTION_APPROVE = 0;
        public static final int PUSH_DIRECTION_DETAIL = 1;
        public static final int PUSH_DIRECTION_HOME = 2;
    }

    public static class AirTicketStatus {
        public static final int TICKET_STATUS_WEIDINGZUO = 0;//未订座
        public static final int TICKET_STATUS_YIDINGZUO = 1;//已定座
        public static final int TICKET_STATUS_YICHUPIAO = 2;//已出票
        public static final int TICKET_STATUS_YIQUXIAO = 3;//已取消
        public static final int TICKET_STATUS_DINGZUOSHIBAI = 4;//订座失败
        public static final int TICKET_STATUS_CHUPIAOSHIBAI = 5;//出票失败
        public static final int TICKET_STATUS_CHUPIAOZHONG = 6;//出票中
        public static final int TICKET_STATUS_DINGZUOZHONG = 7;//订座中
    }
    public static class TrainTicketStatus {
        // 火车票订单状态
        public static final int TICKET_STATUS_WEIDINGZUO = 0;//未订座
        public static final int TICKET_STATUS_YIDINGZUO = 1;//已定座
        public static final int TICKET_STATUS_YICHUPIAO = 2;//已出票
        public static final int TICKET_STATUS_YIQUXIAO = 3;//已取消
        public static final int TICKET_STATUS_DINGZUOSHIBAI = 4;//订座失败
        public static final int TICKET_STATUS_CHUPIAOSHIBAI = 5;//出票失败
        public static final int TICKET_STATUS_CHUPIAOZHONG = 6;//出票中
        public static final int TICKET_STATUS_DINGZUOZHONG = 7;//订座中
    }

    /**
     * 改签状态
     */
    public static class AirAlterStatus {
        public static final int AIR_GQ_WEIGAIQIAN = 0;//未改签
        public static final int AIR_GQ_ING = 1;//改签中
        public static final int AIR_GQ_SUCCESS = 2;//改签成功
        public static final int AIR_GQ_FAILED = 3;//改签失败
        public static final int AIR_GQ_CANCELED = 4;//改签取消
        public static final int AIR_GQ_CONFIRMED = 5;//已确认
        public static final int AIR_GQ_COMMITTED = 6;//已提交
    }
    /**
     * 改签状态
     */
    public static class TrainAlterStatus {
        public static final int TRAIN_GQ_WEIGAIQIAN = 0;//未改签
        public static final int TRAIN_GQ_GAIQIANZHONG = 1;//改签中
        public static final int TRAIN_GQ_GAIQIANCHENGGONG = 2;//改签成功
        public static final int TRAIN_GQ_GAIQIANSHIBAI = 3;//改签失败
        public static final int TRAIN_GQ_WEIQUEREN= 4;//未确认，待支付
        public static final int TRAIN_GQ_YIQUXIAO= 5;//已取消
        public static final int TRAIN_GQ_YIQUEREN = 6;//已确认
    }

    public static class AirReturnStatus {
        public static final int AIR_TP_WEITUIPIAO = 0;//未退票
        public static final int AIR_TP_QUERENZHONG = 1;//确认中
        public static final int AIR_TP_YITUIPIAO = 2;//已退票
        public static final int AIR_TP_TUIPIAOSHIBAI = 3;//退票失败
        public static final int AIR_TP_YIQUXIAO = 4;//已取消
        public static final int AIR_TP_TUIPIAOZHONG = 5;//退票中
    }
    public static class TrainReturnStatus {
        public static final int TRAIN_TP_WEITUIPIAO = 0;//未退票
        public static final int TRAIN_TP_TUIPIAOZHONG= 1;//退票中
        public static final int TRAIN_TP_YITUIPIAO = 2;//已退票
        public static final int TRAIN_TP_TUIPIAOSHIBAI = 3;//退票失败
    }

    /**
     * 审批状态
     */
    public static class ApproveStatus {
        public static final int APPROVE_STATUS_SHENPITONGGUO = 1;
        public static final int APPROVE_STATUS_DAISONGSHEN = 5;
        public static final int APPROVE_STATUS_SHENPIFOUJUE = 2;
        public static final int APPROVE_STATUS_WUXUSHENPI = 3;
        public static final int APPROVE_STATUS_SHENPIZHONG = 4;
        public static final int APPROVE_STATUS_WEISONGSHEN = 0;

    }

    /**
     * 支付状态
     */
    public static class PayStatus {
        public static final int PAY_STATUS_WEIZHIFU = 0;
        public static final int PAY_STATUS_SUCCESS = 1;
        public static final int PAY_STATUS_FAILED = 2;
        public static final int PAY_STATUS_DAIZHIFU = 3;
    }

    /*
     *activity的标记
     */
    public static final int ACTIVITY_HOME_FLAG = 0;
    public static final int ACTIVITY_PLANE_HOME_FLAG = 1;
    public static final int ACTIVITY_HOTEL_HOME_FLAG = 2;
    public static final int ACTIVITY_TRAIN_HOME_FLAG = 3;
    public static final int ACTIVITY_APPROVE_FLAG = 4;
    public static final int ACTIVITY_PERSONAL_FLAG = 5;
    public static final int ACTIVITY_CHECKONLINE_FLAG = 6;
    public static final int ACTIVITY_FLIGHTDYNAMIC_FLAG = 7;
    public static final int ACTIVITY_CITY_FLAG = 8;
    public static final int ACTIVITY_CALENDAR_FLAG = 9;
    public static final int ACTIVITY_PASSENGER_FLAG = 10;
    public static final int ACTIVITY_LOGIN_FLAG = 11;
    public static final int ACTIVITY_TRAIN_LIST_FLAG = 12;
    public static final int ACTIVITY_TRAIN_DETAIL_FLAG = 13;
    public static final int ACTIVITY_TRAIN_12306_FLAG = 14;
    public static final int ACTIVITY_TRAIN_BOOK_FLAG = 15;
    public static final int ACTIVITY_HOME_FILTER1_FLAG = 16;//订单列表的筛选页面1
    public static final int ACTIVITY_HOME_FILTER2_FLAG = 17;//订单列表的筛选页面2
    public static final int ACTIVITY_TRAIN_ORDER_DETAIL_FLAG = 18;
    public static final int ACTIVITY_PROJECTSELECT_FLAG = 19;
    public static final int ACTIVITY_COSTCENTERSELECT_FLAG = 20;
    public static final int ACTIVITY_ALTER_TRAIN_HOME_FLAG = 21;
    public static final int ACTIVITY_ALTER_TRAIN_DETAIL_FLAG = 22;
    public static final int ACTIVITY_TRAIN_ORDER_LIST_FLAG = 23;
    public static final int ACTIVITY_TRAIN_ORDER_FILTER_FLAG = 24;//订单列表的筛选页面
    public static final int ACTIVITY_PLANE_HOME_FLAG_WF = 25;//机票首页，往返的fragment标记
    public static final int ACTIVITY_PLANE_LIST_FLAG = 26;
    public static final int ACTIVITY_PLANE_SEND_FLAG = 27;//送审
    public static final int ACTIVITY_PLANE_ORDER_DETAIL_FLAG = 28;//订单详情
    public static final int ACTIVITY_PLANE_ALTER_QUERY_FLAG = 29;//机票改签查询页面
    public static final int ACTIVITY_HOTEL_KEYWORD_FLAG = 30;//酒店关键字页面
    public static final int ACTIVITY_HOTEL_LIST_FLAG = 31;//酒店列表
    public static final int ACTIVITY_HOTEL_LOCATION_FLAG = 32;//酒店位置区域页面
    public static final int ACTIVITY_HOTEL_DETAIL_FLAG = 33;//酒店位置区域页面
    public static final int ACTIVITY_HOTEL_INFO_FLAG = 34;//酒店位置区域页面
    public static final int ACTIVITY_HOTEL_ROOM_INFO_FLAG = 35;//酒店房型页面
    public static final int ACTIVITY_HOTEL_BOOK_FLAG = 36;//酒店下单
    public static final int ACTIVITY_HOTEL_SEND_FLAG = 37;//酒店送审
    public static final int ACTIVITY_HOTEL_PIC_LIST_FLAG = 38;//酒店图片列表
    public static final int ACTIVITY_HOTEL_MAP_FLAG = 39;//酒店图片列表
    public static final int ACTIVITY_HOTEL_FILT_FLAG = 40;//酒店筛选界面
    public static final int ACTIVITY_APPLY_NO_CHOSE = 41;//酒店筛选界面
    public static final int ACTIVITY_PLANE_BOOK_FLAG = 42;//机票预订页面
    public static final int ACTIVITY_ADD_LS_PSG_FLAG = 43;//添加临时乘客页面
    public static final int ACTIVITY_PEISONG_ADDR_FLAG = 44;//配送地址选择页面
    public static final int ACTIVITY_WX_ENTRY_FLAG = 45;//微信支付接受结果的页面



    /**
     * 手机号、邮箱的正则
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    public static final String KEY_PASSWORD = "com.auvgo.tmc.psw";
    public static final String KEY_USERNAME = "com.auvgo.tmc.username";
    public static final String KEY_AUTOLOGIN = "com.auvgo.tmc.autologin";
    public static final String KEY_CARDNUM = "com.auvgo.tmc.cardnum";
    /*
    规范存取值intent、bundle的key
     */
    public static final String INTENT_KEY_PSG = "intent.psgs";
    public static final String INTENT_KEY_ORDERDETAIL = "intent.orderdetail";
    public static final String INTENT_KEY_BEAN = "intent.BEAN";
    public static final String INTENT_KEY_FLAG = "intent.activity.flag";
    /*
        表示要改签
     */
    public static final int ACTION_ALTER = 0;
    public static final int ACTION_RETURN = 1;

    /*
    permission常量
     */
    public static final int PERMISSION_CODE_IMEI = 0X01;
}
