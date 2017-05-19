package com.auvgo.tmc.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.adapter.TimeAdapter;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.hotel.bean.HotelDetailBean;
import com.auvgo.tmc.hotel.bean.HotelPolicyBean;
import com.auvgo.tmc.hotel.interfaces.IUserZhiWei;
import com.auvgo.tmc.p.PPlaneBook;
import com.auvgo.tmc.plane.bean.PlaneListBean;
import com.auvgo.tmc.plane.bean.PlaneOrderDetailBean;
import com.auvgo.tmc.plane.bean.PlanePolicyBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.TrainPolicyBean;
import com.auvgo.tmc.train.bean.TrainTimeBean;
import com.auvgo.tmc.train.bean.UserBean;
import com.auvgo.tmc.train.bean.WbReasonBean;
import com.auvgo.tmc.train.bean.requestbean.RequestOfPolicy;
import com.auvgo.tmc.views.MyPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.auvgo.tmc.constants.Constant.AirAlterStatus.*;
import static com.auvgo.tmc.constants.Constant.AirReturnStatus.*;
import static com.auvgo.tmc.constants.Constant.AirTicketStatus.*;
import static com.auvgo.tmc.constants.Constant.ApproveStatus.*;

/**
 * Created by lc on 2016/12/6
 */

public class MUtils {
    public static String getSeatTypeByCode(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("9", "商务座");
        map.put("P", "特等座");
        map.put("M", "一等座");
        map.put("O", "二等座");
        map.put("6", "高级软卧");
        map.put("4", "软卧");
        map.put("3", "硬卧");
        map.put("2", "软座");
        map.put("1", "硬座");
        return map.get(code);
    }

    public static String getAirSeatTypeByCode(String code) {
        switch (code) {
            case "Y":
                return "经济舱";
            case "C":
                return "公务舱";
            case "F":
                return "头等舱";
        }
        return code;
    }

    public static String getWeibeiItemByTrainCode(String code) {
        TrainPolicyBean rpb = MyApplication.mTrainPolicy;
        if (rpb == null) return "";
        String gaotiePolicy = getTrainStrFromField(rpb.getGaotie() + "");
        String dongchePolicy = getTrainStrFromField(rpb.getDonche() + "");
        String pukuaiPolicy = getTrainStrFromField(rpb.getPukuai() + "");
        StringBuilder sb = new StringBuilder();

        if ("GC".contains(code)) {
            if (!TextUtils.isEmpty(gaotiePolicy)) {
                sb.append("高铁/城际：允许乘坐");
                sb.append(gaotiePolicy);
            } else {
                sb.append("不允许乘坐高铁/城际");
            }

        } else if ("D".contains(code)) {
            if (!TextUtils.isEmpty(dongchePolicy)) {
                sb.append("动车：允许乘坐");
                sb.append(dongchePolicy);
            } else
                sb.append("不允许乘坐动车");

        } else {
            if (!TextUtils.isEmpty(pukuaiPolicy)) {
                sb.append("普通列车：允许乘坐");
                sb.append(pukuaiPolicy);
            } else
                sb.append("不允许乘坐普通列车");
        }
        return sb.toString();

    }

    /**
     * 匹配火车票 状态
     *
     * @param statu
     * @return
     */
    public static String getOrgTicketStateByCode(int statu) {
        switch (statu) {
            case TICKET_STATUS_WEIDINGZUO:
                return "未订座";      //未订座
            case TICKET_STATUS_YIDINGZUO:
                return "已订座";
            case TICKET_STATUS_YICHUPIAO:
                return "已出票";
            case TICKET_STATUS_YIQUXIAO:
                return "已取消";
            case TICKET_STATUS_DINGZUOSHIBAI:
                return "订座失败";
            case TICKET_STATUS_CHUPIAOSHIBAI:
                return "出票失败";
            case TICKET_STATUS_CHUPIAOZHONG:
                return "出票中";
            case TICKET_STATUS_DINGZUOZHONG:
                return "订座中";//订座中
        }

        return "未匹配";
    }

    /**
     * @param context
     * @param clazz
     * @param flag    如果不设置的话   传入-1
     */
    public static void jumpToPage(Context context, Class<? extends BaseActivity> clazz, int flag) {
        Intent intent = new Intent(context, clazz);
        if (flag > 0) {
            intent.setFlags(flag);
        }
        context.startActivity(intent);
    }

    /**
     * 匹配退票状态
     *
     * @param statu
     * @return
     */
    public static String getReturnStateByCode(int statu) {
        switch (statu) {
            case AIR_TP_WEITUIPIAO:
                return "未退票";
            case AIR_TP_QUERENZHONG:
                return "确认中";
            case AIR_TP_YITUIPIAO:
                return "已退票";
            case AIR_TP_TUIPIAOSHIBAI:
                return "退票失败";
            case AIR_TP_TUIPIAOZHONG:
                return "退票中";
            case AIR_TP_YIQUXIAO:
                return "已取消";
        }
        return "未匹配";
    }

    /**
     * 匹配改签状态
     *
     * @param statu
     * @return
     */
    public static String getAirAlterStateByCode(int statu) {
        switch (statu) {
            case AIR_GQ_WEIGAIQIAN:
                return "未改签";
            case AIR_GQ_ING:
                return "改签中";
            case AIR_GQ_SUCCESS:
                return "已改签";
            case AIR_GQ_FAILED:
                return "改签失败";
            case AIR_GQ_CANCELED:
                return "已取消";
            case AIR_GQ_CONFIRMED:
                return "已确认";
            case AIR_GQ_COMMITTED://已提交，显示待确认
                return "确认中";
        }
        return "未匹配";
    }

    /**
     * 匹配改签状态
     *
     * @param statu
     * @return
     */
    public static String getTrainAlterStateByCode(int statu) {
        switch (statu) {
            case Constant.TrainAlterStatus.TRAIN_GQ_WEIGAIQIAN://未改签:
                return "未改签";
            case Constant.TrainAlterStatus.TRAIN_GQ_GAIQIANZHONG: //改签中:
                return "改签中";
            case Constant.TrainAlterStatus.TRAIN_GQ_GAIQIANCHENGGONG:  //改签成功:
                return "已改签";
            case Constant.TrainAlterStatus.TRAIN_GQ_GAIQIANSHIBAI:  //改签失败:
                return "改签失败";
            case Constant.TrainAlterStatus.TRAIN_GQ_WEIQUEREN://未确认，待支付:
                return "待支付";
            case Constant.TrainAlterStatus.TRAIN_GQ_YIQUXIAO://已取消:
                return "已取消";
            case Constant.TrainAlterStatus.TRAIN_GQ_YIQUEREN://已确认://已提交，显示待确认
                return "已确认";
        }
        return "未匹配";
    }

    /**
     * 判断某席别是否是违背标准的席别
     *
     * @param s
     * @return
     */
    public static boolean isSeatWei(String s) {
        //  判断传入的s是否在规则给定的坐席里面，在false，不在true
        return MyApplication.mTrainPolicy != null &&
                !MyApplication.mTrainPolicy.getDonche().contains(s) &&
                !MyApplication.mTrainPolicy.getGaotie().contains(s) &&
                !MyApplication.mTrainPolicy.getPukuai().contains(s);

    }

    /**
     * 判断某席别是否可以预订
     *
     * @param seateTypeCode 某席别的代码
     * @param trainCode     G205
     * @return
     */
    public static boolean isCanbook(String seateTypeCode, String trainCode) {
        if (MyApplication.mTrainPolicy == null) return true;
        String controltype = MyApplication.mTrainPolicy.getControltype();
        boolean isWei = isSeatWei(seateTypeCode);
        if (!isWei) return true;
        else {
            if (trainCode.startsWith("G")) {
                return !controltype.substring(0, 1).equals("0");
            } else if (trainCode.startsWith("D")) {
                return !controltype.substring(1, 2).equals("0");
            } else {
                return !controltype.substring(4, 5).equals("0");
            }
        }
    }

    /**
     * 获取火车票政策描述
     *
     * @return
     */
    public static String getTrainPolicyString(TrainPolicyBean rpb) {
        if (rpb == null) return "";
        String gaotiePolicy = getTrainStrFromField(rpb.getGaotie());
        String dongchePolicy = getTrainStrFromField(rpb.getDonche());
        String pukuaiPolicy = getTrainStrFromField(rpb.getPukuai());
        String[] control = rpb.getControltype().split("/");//管控方式，0不允许预订，1只提醒
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(gaotiePolicy)) {
            sb.append("高铁/城际：允许乘坐");
            sb.append(gaotiePolicy);
        } else {
            sb.append("高铁/城际：").append(control[0].equals("1") ? "违背差旅标准" : "不允许预订");
        }
        sb.append("\n");

        if (!TextUtils.isEmpty(dongchePolicy)) {

            sb.append("动     车：允许乘坐");
            sb.append(dongchePolicy);
        } else {
            sb.append("动     车：").append(control[1].equals("1") ? "违背差旅标准" : "不允许预订");
        }
        sb.append("\n");

        if (!TextUtils.isEmpty(pukuaiPolicy)) {
            sb.append("普通 列车：允许乘坐");
            sb.append(pukuaiPolicy);
        } else {
            sb.append("普通 列车：").append(control[2].equals("1") ? "违背差旅标准" : "不允许预订");
        }
        sb.append("\n");
        return sb.toString().trim();
    }

    public static String getTrainStrFromField(String field) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(field)) {
            if (field.endsWith("/")) {
                field = field.substring(0, field.length() - 1);
            }
            String[] split = field.split("/");
            for (String aSplit : split) {
                sb.append(getSeatTypeByCode(aSplit));
                sb.append("、");
            }

        }
        if (sb.toString().endsWith("、")) {
            return sb.toString().substring(0, sb.length() - 1);
        }
        return sb.toString();

    }

    /**
     * 匹配审批状态
     *
     * @param approvestatus
     * @return
     */
    public static String getApproveStateByCode(int approvestatus) {
        switch (approvestatus) {
            case APPROVE_STATUS_WEISONGSHEN:
                return "未送审";
            case APPROVE_STATUS_SHENPITONGGUO:
                return "已审批";
            case APPROVE_STATUS_SHENPIFOUJUE:
                return "已拒绝";
            case APPROVE_STATUS_WUXUSHENPI:
                return "无需审批";
            case APPROVE_STATUS_SHENPIZHONG:
                return "审批中";
            case APPROVE_STATUS_DAISONGSHEN:
                return "待送审";
        }

        return "未匹配";
    }

    /**
     * 匹配审批状态
     *
     * @param status
     * @return
     */
    public static String getAltertateByCode(int status) {
        switch (status) {
            case 0:
                return "未改签";
            case 1:
                return "改签中";
            case 2:
                return "改签成功";
            case 3:
                return "改签失败";
            case 4:
                return "取消改签";
            case 5:
                return "改签中";
            case 6:
                return "已提交";
        }

        return "未匹配";
    }

    public static String getHotelStateByCode(int status, int approvestatus, int paystatus) {

        if (status == HotelStateCons.OrderStatus.HOTEL_ORDER_STATUS_CANCEL) {
            return "已取消";
        }
        /*
        如果无需审批、或者审批通过
         */
        if (approvestatus == Constant.ApproveStatus.APPROVE_STATUS_WUXUSHENPI ||
                approvestatus == Constant.ApproveStatus.APPROVE_STATUS_SHENPITONGGUO) {
            /*
            审批步骤结束，进行订单状态的判断
             */
            return checkOrderState(status, paystatus);

        }
        /*
        如果审批否决
         */
        else if (approvestatus == Constant.ApproveStatus.APPROVE_STATUS_SHENPIFOUJUE) {
            return "审批否决";
        }
        /*
          如果是待审批（已经闭合）
         */
        else if (approvestatus == Constant.ApproveStatus.APPROVE_STATUS_DAISONGSHEN) {
            return "待审批";

        } else if (approvestatus == Constant.ApproveStatus.APPROVE_STATUS_SHENPIZHONG) {
            return "审批中";
        }
        return "";
    }

    private static String checkOrderState(int status, int paystatus) {
      /*
        现付，担保
         */
        if (paystatus == HotelStateCons.PayStatus.HOTEL_PAY_STATUS_DAIDANBAO) {//等待担保
            return "等待担保";
        } else if (paystatus == HotelStateCons.PayStatus.HOTEL_PAY_STATUS_DANBAOZHONG) {
            return "担保中";
        /*
        现付担保失败
         */
        } else if (paystatus == HotelStateCons.PayStatus.HOTEL_PAY_STATUS_DANBAOSHIBAI) {//担保失败
            return "担保失败";
        } else if (paystatus == HotelStateCons.PayStatus.HOTEL_PAY_STATUS_DAITUIKUAN) {
            return "待退款";
        } else if (paystatus == HotelStateCons.PayStatus.HOTEL_PAY_STATUS_TUIKUANCHENGGONG) {
            return "退款成功";
        } else if (paystatus == HotelStateCons.PayStatus.HOTEL_PAY_STATUS_TUIKUANSHIBAI) {
            return "退款失败";
        } else if (status == HotelStateCons.OrderStatus.HOTEL_ORDER_STATUS_DENGDAIQUEREN) {//等待确认
            return "等待确认";
        /*
        确认中
        */
        } else if (status == HotelStateCons.OrderStatus.HOTEL_ORDER_STATUS_QUEREN_ING) {
            return "确认中";
        /*
        确认失败
        */
        } else if (status == HotelStateCons.OrderStatus.HOTEL_ORDER_STATUS_QUEREN_FAIL) {
            return "确认失败";
        /*
        确认成功
         */
        } else if (status == HotelStateCons.OrderStatus.HOTEL_ORDER_STATUS_CANCEL) {
            return "已取消";
        } else if (status == HotelStateCons.OrderStatus.HOTEL_ORDER_STATUS_QUEREN_SUCCESS) {
            return "等待入住";
        } else if (status == HotelStateCons.OrderStatus.HOTEL_ORDER_STATUS_COMMITTED) {
            return checkPayStatus(paystatus);
        }
        return "";
    }

    private static String checkPayStatus(int paystatus) {
        if (paystatus == HotelStateCons.PayStatus.HOTEL_PAY_STATUS_DAIZHIFU) {//待支付
            return "待支付";
        } else if (paystatus == HotelStateCons.PayStatus.HOTEL_PAY_STATUS_ZHIFUSHIBAI) {//支付失败
            return "支付失败";
        } else if (paystatus == HotelStateCons.PayStatus.HOTEL_PAY_STATUS_ZHIFUZHONG) {//支付中
            return "支付中";
        } else if (paystatus == HotelStateCons.PayStatus.HOTEL_PAY_STATUS_ZHIFUCHENGGONG) {//支付成功
            return "支付成功";
        }
        return "";

    }

    private static Runnable runnable;
    private static boolean isRunning;

    /**
     * 播放文字小动画
     *
     * @param tv
     * @param str
     */
    public static void startTextAnim(final TextView tv, final String str) {
        if (isRunning) {
            return;
        }
        final int[] p = new int[1];
        runnable = new Runnable() {
            @Override
            public void run() {

                if (p[0] % 3 == 0) {
                    tv.setText(str + ".");
                }
                if (p[0] % 3 == 1) {
                    tv.setText(str + "..");
                }
                if (p[0] % 3 == 2) {
                    tv.setText(str + "...");
                }
                p[0]++;
                if (isRunning) {
                    AppUtils.getHandler().postDelayed(runnable, 500);
                }
            }
        };
        AppUtils.getHandler().post(runnable);
        isRunning = true;
    }

    /**
     * 暂停动画
     */
    public static void stopTextAnim() {
        isRunning = false;
        if (runnable != null) {
            AppUtils.getHandler().removeCallbacks(runnable);
        }
    }

    /**
     * 显示时刻表
     *
     * @param context
     * @param startDate
     * @param fromStationCode
     * @param toStationCode
     * @param trainNo
     * @param fromName
     * @param toName
     */
    public static void showTimePop(final Context context, String startDate,
                                   String fromStationCode, String toStationCode,
                                   String trainNo, final String fromName, final String toName) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("qd", startDate);
        map.put("fc", fromStationCode);
        map.put("tc", toStationCode);
        map.put("tn", trainNo);
        RetrofitUtil.getTrainTimeList(context, AppUtils.getJson(map), TrainTimeBean.class, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    TrainTimeBean ttb = (TrainTimeBean) o;
                    TimeAdapter ta = new TimeAdapter(context, ttb.getData(), fromName, toName);
                    DialogUtil.showTimeDialog(context, ta);
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {

                return false;
            }
        });

    }

    /**
     * 判断是不是卧铺
     *
     * @param s
     * @return
     */
    public static boolean isWoPu(String s) {
        return "346".contains(s);
    }

    /**
     * 通过选择的乘客得到请求的字符串（请求差旅政策用）
     *
     * @param lpb
     * @return
     */
    public static String getRequestStringByPsg(List<? extends IUserZhiWei> lpb) {
        return getRequestStringWithCityIdByPsg(lpb, "");
    }

    public static String getRequestStringWithCityIdByPsg(List<? extends IUserZhiWei> lpb, String cityid) {
        RequestOfPolicy rop = new RequestOfPolicy();
        rop.setCid(String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        List<String> level = new ArrayList<>();
        for (int i = 0; i < lpb.size(); i++) {
            IUserZhiWei passengersBean = lpb.get(i);
            if (passengersBean == null) continue;
            String zhiwei = passengersBean.getZhiWei() + "";
            if (!TextUtils.isEmpty(zhiwei)) {
                level.add(zhiwei);
            }
        }
        if (level.size() == 0) {
            level.add(MyApplication.mUserInfoBean.getZhiwei());
        }
        rop.setCityid(cityid);
        rop.setLevel(level);
        return AppUtils.getJson(rop);
    }

    public static String getRequestStringByPsg2(List<PlaneOrderDetailBean.PassengersBean> lpb) {
        RequestOfPolicy rop = new RequestOfPolicy();
        rop.setCid(String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        List<String> level = new ArrayList<>();
        for (int i = 0; i < lpb.size(); i++) {
            PlaneOrderDetailBean.PassengersBean passengersBean = lpb.get(i);
            if (passengersBean == null) break;
            String zhiwei = passengersBean.getZhiwei() + "";
            if (!TextUtils.isEmpty(zhiwei)) {
                level.add(zhiwei);
            }
        }
        rop.setLevel(level);
        return AppUtils.getJson(rop);
    }

    /**
     * 得到飞机政策描述
     *
     * @return
     */
    public static String getPlanePolicyString(PlanePolicyBean policyBean) {
        if (policyBean == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (policyBean.getAllowfly() == 0) {
            sb.append("不可乘坐飞机;");
        }
        if (policyBean.getCabinlimit() == 1) {
            sb.append("不得高于").append(policyBean.getCabinzhekou()).append("折;");
        }
        if (policyBean.getAllowbefore() == 1) {
            sb.append("需提前").append(policyBean.getBeforeday()).append("天预订;");
        }
        if (policyBean.getFlightlimit() == 1) {
            switch (policyBean.getFlightlowtype()) {
                case 0:
                    sb.append("航班最低价;");
                    break;
                case 1:
                    sb.append("全天最低价;");
                    break;
                case 2:
                    sb.append("前后").append(policyBean.getFlighthour()).append("小时最低价;");
                    break;
            }
        }
        String s = sb.toString();
        return s.endsWith(";") ? s.substring(0, s.length() - 1) : s;
    }

    /**
     * 查询当前航班是否违规
     *
     * @param bean
     * @return
     */
    public static boolean isCabinWei(PlaneListBean bean, StringBuilder sb) {
        PlanePolicyBean mpp = MyApplication.mPlanePolicy;
        if (mpp == null) {
            return false;
        }

        if (mpp.getAllowfly() == 1) {
            boolean b = matchLowPrice(bean);
            boolean b1 = matchCount(bean);
            boolean b2 = matchPreBook(bean);
            if (sb != null) {
                if (!b) {
                    switch (mpp.getFlightlowtype()) {
                        case 0:
                            sb.append("航班最低价、");
                            break;
                        case 1:
                            sb.append("全天最低价、");
                            break;
                        case 2:
                            sb.append("前后").append(mpp.getFlighthour()).append("小时最低价、");
                            break;
                    }
                }
                if (!b2) {
                    sb.append("需提前").append(mpp.getBeforeday()).append("天预订、");
                }
            }

            if (b && b1 && b2) {
                return false;
            }
        } else {
            if (sb != null) {
                sb.append("不可乘坐飞机、");
            }
        }
        return true;
    }

    private static boolean matchPreBook(PlaneListBean bean) {
        PlanePolicyBean mpp = MyApplication.mPlanePolicy;
        return mpp == null || !(mpp.getAllowbefore() == 1 && TimeUtils.getTimeStamp(TimeUtils.getToday(), "yyyy-MM-dd") + 1000 * 60 * 60 * 24 * mpp.getBeforeday() > TimeUtils.getTimeStamp(bean.getDeptdate(), "yyyy-MM-dd"));
    }

    public static boolean matchCount(PlaneListBean bean) {
        PlanePolicyBean mpp = MyApplication.mPlanePolicy;
        if (mpp == null) return true;
        if (mpp.getCabinlimit() == 1) {
            if (bean.getCangweis() == null) return true;
            for (int i = 0; i < bean.getCangweis().size(); i++) {
                if (bean.getCangweis().get(i).getDiscount() <= mpp.getCabinzhekou()) {
                    return true;
                }
            }
            return false;
        }
        return true;

    }

    public static boolean isLowestOfThisLine(PlaneListBean b, int position) {
        PlanePolicyBean mPlanePolicy = MyApplication.mPlanePolicy;
        if (mPlanePolicy == null) return true;
        if (mPlanePolicy.getFlightlimit() == 1) {//开启了价格限制
            for (int i = 0; i < b.getCangweis().size(); i++) {
                if (b.getCangweis().get(i).getPrice() < b.getCangweis().get(position).getPrice()) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean matchLowPrice(PlaneListBean bean) {
        PlanePolicyBean mpp = MyApplication.mPlanePolicy;
        if (mpp == null) return true;
        double price = bean.getLow().getPrice();
        return !(mpp.getFlightlimit() == 1 && mpp.getFlightlowtype() == 1) || price <= MyApplication.lowestPrice;
    }

    public static void killCurrentProcess() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 根据乘客的id去passRoute里拿到改签的状态值
     * 0 未改签 1 改签中  2已改签  3改签失败
     *
     * @param mBean
     * @param id
     * @return
     */
    public static int getGaiqianstatusByPsgId(PlaneOrderDetailBean mBean, int id) {
        if (mBean != null) {
            List<PlaneOrderDetailBean.RoutePassBean> routePass = mBean.getRoutePass();
            for (int i = 0; i < routePass.size(); i++) {
                PlaneOrderDetailBean.RoutePassBean routePassBean = routePass.get(i);
                if (routePassBean.getPassid() == id) {
                    return routePassBean.getGaiqianstatus();
                }
            }
        }
        return 0;
    }

    /**
     * 根据乘客的id去passRoute里拿到改签的状态值
     */
    public static int getTuipiaostatusByPsgId(PlaneOrderDetailBean mBean, int id) {
        if (mBean != null) {
            List<PlaneOrderDetailBean.RoutePassBean> routePass = mBean.getRoutePass();
            for (int i = 0; i < routePass.size(); i++) {
                PlaneOrderDetailBean.RoutePassBean routePassBean = routePass.get(i);
                if (routePassBean.getPassid() == id) {
                    return routePassBean.getTuipiaostatus();
                }
            }
        }
        return 0;
    }

    public static void loadImg(Context context, String url, ImageView imageView, int defaultImg) {
        Glide.with(context).load(url)
                .asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(defaultImg).into(imageView);
    }

    public static String getHotelPolicyStr(HotelPolicyBean hpb) {
        HotelPolicyBean mHotelPolicy = hpb == null ? MyApplication.mHotelPolicy : hpb;
        if (mHotelPolicy == null) return "未配置差旅政策";
        HotelPolicyBean.PolicyBean policy = mHotelPolicy.getPolicy();
        String[] cities = policy == null ? null : policy.getCitylevelname().split("/");
        String[] prices = policy == null ? null : policy.getPrice().split("/");
        StringBuilder sb = new StringBuilder();
        if (cities == null || prices == null) return "";
        for (int i = 0; i < cities.length; i++) {
            String price = prices[i];
            if (price.equals("-1")) {
                sb.append(cities[i]).append("不限制");
            } else {
                sb.append(cities[i]).append("不得高于").append(price).append("元\n");
            }
        }
        return sb.toString().trim();
    }

    public static void choseWeireason(final Context context, String tag, final int mCurrentPosition_weiReason
            , final OnWeibeiListener listener) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("cid", String.valueOf(MyApplication.mUserInfoBean.getCompanyid()));
        map.put("type", tag);

        String json = AppUtils.getJson(map);
        RetrofitUtil.getWbReason(context, json, null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (200 == status) {
                    LogFactory.d("getWbReason=============" + bean.getData());
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<WbReasonBean>>() {
                    }.getType();
                    final List<WbReasonBean> mWbReasons = gson.fromJson(bean.getData(), type);
                    if (mWbReasons != null) {
                        DialogUtil.showExpandablePickDialog(context, "选择违背原因", mCurrentPosition_weiReason,
                                mWbReasons, new MyPickerView.OnPickerViewSure() {
                                    @Override
                                    public void onSingleSureClick(int p) {
                                        if (p >= 0) {
                                            listener.onSureClick(mWbReasons.get(p), p);
                                        }
                                    }

                                    @Override
                                    public void onMultiSureClick(List<Integer> s) {

                                    }
                                });
                    }
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    public static String getHotelWeiPolicyStr() {
        HotelPolicyBean mHotelPolicy = MyApplication.mHotelPolicy;
        if (mHotelPolicy == null) {
            return "";
        }
        return mHotelPolicy.getCityname() + "不得高于" + mHotelPolicy.getPrice() + "元";
    }

    public static String getFacilitiesByCode(String s) {
        switch (s) {
            case "1":
                return "免费WIFI";
            case "2":
                return "收费WIFI";
            case "3":
                return "免费宽带";
            case "4":
                return "收费宽带";
            case "5":
                return "免费停车场";
            case "6":
                return "收费停车场";
            case "7":
                return "免费接机服务";
            case "8":
                return "收费接机服务";
            case "9":
                return "室内游泳池";
            case "10":
                return "室外游泳池";
            case "11":
                return "健身房";
            case "12":
                return "商务中心";
            case "13":
                return "会议室";
            case "14":
                return "酒店餐厅";
        }
        return "";
    }

    /**
     * 检查选择的users中，是否存在重复的id的现象。
     */
    public static boolean checkIdDulpicated(List<UserBean> psgs) {
        List<String> l = new ArrayList<>();
        if (psgs != null) {
            for (int i = 0; i < psgs.size(); i++) {
                UserBean userBean = psgs.get(i);
                if (userBean != null) {
                    String certno = userBean.getCertno();
                    if (!l.contains(certno)) {
                        l.add(certno);
                    } else {
                        l.clear();
                        return true;
                    }
                }
            }
        }
        l.clear();
        return false;
    }

    public static String getFukuankemu() {
        return MyApplication.getApplication().getmPayType().getFukuankemu();
    }

    public static <T> List<T> getListFromJson(String data, Class<T[]> aClass) {
        Gson gson = new Gson();
        T[] t = gson.fromJson(data, aClass);
        return Arrays.asList(t);
    }

    public interface OnWeibeiListener {
        void onSureClick(MyPickerView.Selection selection, int pos);

    }

    public static String getHotelPicCategary(HotelDetailBean.HotelImageListBean hilb) {
        if (hilb.getImageTitle() == null) {
            return "其他";
        } else if (hilb.getImageTitle().equals("外观")) {
            return "外观";
        } else if (hilb.getImageTitle().equals("客房")) {
            return "客房";
        } else {
            return "设施";
        }
    }

    public static int getWeibeiFlag(PlaneListBean frb, int cangwei) {//0表示没有违背,-1不许乘坐飞机，-1违反航班最低价，-2违反全天最低价，-3违反折扣，-4违反提前x天预订
        PlanePolicyBean mPlanePolicy = MyApplication.mPlanePolicy;
        if (frb == null || mPlanePolicy == null) {
            return 0;
        }
        boolean allowFly = mPlanePolicy.getAllowfly() == 1;
        if (allowFly) {
            /*
            预订时间的判断
             */
            if (mPlanePolicy.getAllowbefore() == 1 && TimeUtils.getTimeStamp(TimeUtils.getToday(), "yyyy-MM-dd") +
                    1000 * 60 * 60 * 24 * mPlanePolicy.getBeforeday() >
                    TimeUtils.getTimeStamp(frb.getDeptdate(), "yyyy-MM-dd")) {
                return -4;
            }
            /*
            折扣的判断
             */
            if (mPlanePolicy.getCabinlimit() == 1 && frb.getCangweis().get(cangwei)
                    .getDiscount() > mPlanePolicy.getCabinzhekou()) {
                return -3;
            }
            /*
            价格限制的判断
             */
            if (mPlanePolicy.getFlightlimit() == 1) {//如果开启了价格限制
                switch (mPlanePolicy.getFlightlowtype()) {
                    case 0:/*航班最低价*/
                        if (frb.getCangweis().get(cangwei).isLowestInThisFlight()) {
                            return -1;
                        }
                        break;
                    case 1:/*全天最低价*/
                        if (!frb.isContainsLowestCarbin() || !frb.getCangweis()
                                .get(cangwei).isLowestInThisFlight()) {
                            return -2;
                        }
                        break;
                    case 2:/*前后n小时最低价*/
                        return -5;
                }

            }
        } else {
            return -1;
        }
        return 0;
    }

    public static void checkNHour(Context context, String from, String to, final PlaneListBean plb,
                                  final int cangwei, final PPlaneBook.OnCheckResult l) {
        Map<String, String> map = new HashMap<>();
        map.put("from", from);
        map.put("to", to);
        map.put("startdate", plb.getDeptdate());
        map.put("airline", plb.getAirline());
        map.put("hour", String.valueOf(MyApplication.mPlanePolicy == null ? 0 : MyApplication.mPlanePolicy.getFlighthour()));
        map.put("price", String.valueOf(plb.getCangweis().get(cangwei).getPrice()));
        RetrofitUtil.getNHourLowest(context, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    Type token = new TypeToken<List<PlaneListBean>>() {
                    }.getType();
                    List<PlaneListBean> list = new Gson().fromJson(bean.getData(), token);
                    int savePrice = checkIsLow(list, plb.getCangweis().get(cangwei).getPrice());
                    if (savePrice > 0) {
                        l.onGotResultWei();
                    } else {
                        l.onGotResultNotWei();
                    }
                    l.onNext();
                }
                return false;
            }

            private int checkIsLow(List<PlaneListBean> list, double price) {
                for (int i = 0; i < list.size(); i++) {
                    double price1 = list.get(i).getLow().getPrice();
                    if (price1 < price) {
                        return (int) (price - price1);
                    }
                }
                return -1;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

}
