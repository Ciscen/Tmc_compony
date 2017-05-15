package com.auvgo.tmc.utils;

import android.content.Context;
import android.util.Log;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.interfaces.RetrofitServer;
import com.auvgo.tmc.views.MyDialog;
import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 2016/11/8
 */

public class RetrofitUtil {

    private static RetrofitServer server = new Retrofit.Builder()
            .baseUrl(Url.getUrl(0))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitServer.class);


    private RetrofitUtil() {
    }

    public static void setUrl(int flag) {
        server = new Retrofit.Builder()
                .baseUrl(Url.getUrl(flag))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitServer.class);
    }


    public interface OnResponse {
        /**
         * @param bean   返回的外层实体类对象
         * @param status 返回的状态吗
         * @param msg    返回的提示信息
         * @param o      data里的实体类对象
         * @return boolean 截获处理，true的话，封装的对话框就不会继续执行了。这时候可以自定义一些动作
         */
        boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o);

        boolean onFailed(Throwable e);
    }

    /**
     * @param context      上下文
     * @param observable   由retrofitServer请求后返回的observable对象，这样就针对不同的请求，实现公用
     * @param dataBeanClaz 返回的data的类型的class
     * @param listener     回调
     * @param canCancle    请求的进度框是否可以取消
     * @param desc         进度框里的文字提示
     * @param showProgress 是否显示进度框
     */
    private static void request(final Context context, final Observable<ResponseOuterBean> observable, boolean showProgress,
                                final Class dataBeanClaz, final OnResponse listener, boolean canCancle, String desc) {
        if (showProgress)
            MyApplication.getApplication().startProgressDialog(context, canCancle, desc);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseOuterBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            LogFactory.e("错误信息--------------》》》" + e.toString());
                            MyApplication.getApplication().stopProgressDialog();
                            boolean b = listener.onFailed(e);
                            if (b) return;
                            if (context == null) return;
                            DialogUtil.showDialog(context, "提示", "确定", "", context.getString(R.string.error_msg), new MyDialog.OnButtonClickListener() {
                                @Override
                                public void onLeftClick() {
                                }

                                @Override
                                public void onRightClick() {

                                }
                            });
                        } catch (Exception ex) {
                            Log.d("---------", "onError: " + ex.getMessage());
                        }
                    }

                    @Override
                    public void onNext(ResponseOuterBean responseBody) {
                        MyApplication.getApplication().stopProgressDialog();
                        Gson gson = new Gson();
                        LogFactory.d("返回值==============>" + responseBody.getStatus() + "返回的内容=============>" + responseBody.getData());
                        Object o = null;
                        if (context == null) return;
                        if (responseBody.getStatus() == 200 && responseBody.getData() != null) {
                            if (dataBeanClaz != null) {
                                o = gson.fromJson(responseBody.getData(), dataBeanClaz);
                            }
                        }
                        boolean b = listener.onSuccess(responseBody, responseBody.getStatus(), responseBody.getMsg(), o);
                        if (b) return;
                        if (responseBody.getStatus() != 200) {
                            DialogUtil.showDialog(context, "提示", "确定", "", responseBody.getMsg(), null);
                        }
                    }
                });
    }

    /**
     * @param context      上下文
     * @param dataJson     json格式的data请求参数
     * @param dataBeanClaz 请求下来的data里面的内容类型的class
     * @param showProgress 是否显示进度
     * @param listener     回调
     */
    public static void doLogin(Context context, String dataJson, final Class dataBeanClaz, boolean showProgress, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        request(context, server.doLogin(dataJson, Constant.APPKEY, sign), showProgress, dataBeanClaz, listener, true, "");
    }

    public static void getCities(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        LogFactory.d("----------提交的json------------" + dataJson);
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        request(context, server.getCities(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    public static void getPolicy(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        LogFactory.d("----------提交的json------------" + dataJson);
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        request(context, server.getPolicy(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, false, "");
    }

    public static void getComSetting(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        request(context, server.getComSetting(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, false, "");
    }

    public static void getPsgList(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        request(context, server.getPsgList(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    public static void getTrainList(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        LogFactory.d("----------提交的json------------" + dataJson);
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        request(context, server.getTrainList(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    public static void getTrainTimeList(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        LogFactory.d("----------提交的json------------" + dataJson);
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        request(context, server.getTrainTime(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    public static void login12306(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        LogFactory.d("----------提交的json------------" + dataJson);
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        request(context, server.login12306(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "正在验证");
    }

    public static void createOrder(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        LogFactory.d("----------提交的json------------" + dataJson);
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        request(context, server.createOrder(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, false, "正在下单");
    }

    public static void getOrder(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        LogFactory.d("----------提交的json------------" + dataJson);
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        request(context, server.getOrder(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "加载订单");
    }

    public static void getCostCenter(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        LogFactory.d("----------提交的json------------" + dataJson);
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        request(context, server.getCostCenter(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    public static void getProject(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        LogFactory.d("----------提交的json------------" + dataJson);
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        request(context, server.getProject(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    public static void getWbReason(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        LogFactory.d("----------提交的json------------" + dataJson);
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        request(context, server.getWbReason(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    public static void getTrainApprove(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        LogFactory.d("----------提交的json------------" + dataJson);
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        request(context, server.getTrainApprove(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    //送审
    public static void sendOrderApprove(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        LogFactory.d("----------提交的json------------" + dataJson);
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        request(context, server.sendOrderApprove(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "正在送审");
    }

    /**
     * 个人中心
     */
    public static void getPersonalInfo(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        LogFactory.d("----------提交的json------------" + dataJson);
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        request(context, server.getPersonalInfo(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "正在获取个人信息");
    }

    public static void updatePCInfo(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        request(context, server.updatePCInfo(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "正在更改个人信息");
    }

    public static void updatePassword(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        request(context, server.updatePassword(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 订单列表
     */
    public static void getTrainOrderList(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        LogFactory.d("----------提交的json------------" + dataJson);
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        request(context, server.getOrderList(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 审批订单列表
     */
    public static void getApproveOrder(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        LogFactory.d("----------提交的json------------" + dataJson);
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        request(context, server.getApproveOrder(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 审批
     */
    public static void doTrainCommitApprove(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.doTrainCommitApprove(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 退票
     */
    public static void doReturnTicket(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.doReturnTicket(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 获取员工职级
     */
    public static void getEmpLevel(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getEmpLevel(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 改签
     */
    public static void doAlter(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.doAlter(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 改签单详情
     */
    public static void getAlterOrderDetail(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getAlterOrderDetail(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 火车退票单详情
     */
    public static void getTrainReturnOrderDetail(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getTrainReturnOrderDetail(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 订座中检查状态
     */
    public static void checkState(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.checkState(dataJson, Constant.APPKEY, sign), false, dataBeanClaz, listener, true, "");
    }

    /**
     * 取消订单
     */
    public static void cancleOrder(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.cancelOrder(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 出票
     */
    public static void confirmTrainTicket(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.confirmTrainTicket(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 机票查询
     */
    public static void getPlaneList(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getPlaneList(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 机票政策
     */
    public static void getPlanePolicy(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getPlanePolicy(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 前后n小时最低价
     */
    public static void getNHourLowest(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getNHourLowest(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 提交飞机票订单
     */
    public static void createPlaneOrder(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.createPlaneOrder(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 获取飞机票订单详情
     */
    public static void getPlaneOrderDetail(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getPlaneOrderDetail(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 获取审批级别
     * {"orderno":"XLGJ111524446350044",
     * "type":"book,tuipiao,gaiqian"}
     */
    public static void getPlaneApproveLevel(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getPlaneApproveLevel(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }


    /**
     * 飞机票送审
     */
    public static void sendPlaneApprove(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.sendPlaneApprove(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "送审中");
    }

    /**
     * 飞机票订单列表
     */
    public static void getPlaneOrderList(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
//        String sign = Md5Sign.createSign(dataJson,Constant.APPSECRIT);//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getPlaneOrderList(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 飞机票审批接口，同意、取消
     */
    public static void doPlaneApproveCommit(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.doPlaneApproveCommit(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 飞机票取消订单
     */
    public static void canclePlaneOrder(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.canclePlaneOrder(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 飞机票确认出票
     */
    public static void confirmPlaneTicket(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.comfirmOutPlaneTicket(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "出票中");
    }

    /**
     * 我的差旅信息 {"cid":"1","level":"员工职级"}
     */
    public static void getMyTravelInfo(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getMyTravelInfo(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 机票退票
     * {"cid":"1",
     * "empid":"2",
     * "orderno":"MDW111841887430600",
     * "userids": "[1,2,3]",
     * "orderfrom":"来自安卓还是ios",
     * "tpreason":"退票原因"}
     */
    public static void returnPlaneTicket(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.returnPlaneTicket(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 获取退票原因 {"cid":""}
     */
    public static void getReturnReasons(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getReturnReasons(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 得到机票退票详情
     */
    public static void getPlaneReturnDetail(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getPlaneTuipiaoDetail(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 改签 机票
     * {"cid":"1",
     * "empid":"2",
     * "orderno":"MDW111841887430600",
     * "passids":"[1,2,3]",
     * "routeid":"[1,2]",
     * "orderfrom":"来自安卓还是ios",
     * route:[{....}]}
     */
    public static void createPlaneAlter(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.createPlaneAlter(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 机票改签详情
     * {"cid":"1","tporderno":"退票订单号"}
     */
    public static void getPlaneAlterDetail(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getPlaneAlterDetail(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 机票改签送审
     * {"orderno":"XLGJ111524446350044", "approveid":"" }
     */
    public static void sendPlaneAlterApprove(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.sendPlaneAlterApprove(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 获取保险
     */
    public static void getInsurance(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getInsurances(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 酒店查询
     * {"arrivalDate":"2017-02-15","departureDate":"2017-02-17","cityId":
     * "0011" ,"queryText":"查询关键词",
     * "sort":"Default艺龙默认排序 StarRankDesc推荐星级降序RateAsc价格升序RateDesc价格降序DistanceAsc距离升序",
     * "brandId":"品牌编码",
     * "lowRate":"最小价格",
     * "highRate":"最大价格" ,
     * "starRate":"推荐星级",
     * "longitude":"经度",
     * "latitude":"维度",
     * "radius":"半径",
     * "pageIndex":"","pageSize":""}
     */
    public static void getHotelList(final Context context, String dataJson, boolean showProgress, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getHotelList(dataJson, Constant.APPKEY, sign), showProgress, dataBeanClaz, listener, true, "");
    }

    /**
     * 酒店关键字
     * {
     * keyword:""
     * cityId:""
     * }
     */
    public static void getHotelKeyword(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getHotelKeyword(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 酒店详情
     */
    public static void getHotelDetail(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getHotelDetail(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }


    /**
     * 获取热门数据
     * cityId:"0101"
     */
    public static void getHotData(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getHotData(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 列表位置筛选数据： 城市的商圈，行政区，车站标志物
     * {
     * "cityid":"0101"
     * }
     */
    public static void getHotelLocations(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getFiltData(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 列表位置筛选数据： 城市的商圈，行政区，车站标志物
     * {
     * "cid":1
     * "level":[1,2,3]
     * }
     */
    public static void getHotelPolicy(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getHotelPolicy(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 根据名字，获取城市id
     * <p>
     * {
     * {\"cityname\":\"上海\"}
     * }
     */
    public static void getCityIdByName(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getCityIdByName(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 酒店信息详情
     * 需要传入  hotelId
     */
    public static void getHotelInfo(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getHotelInfo(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 房型库存验证
     * {
     * arrivelDate:"",departureDate:"",hotelId:"",roomTypeId:"",ratePlanId:"",totalPrice:""
     * }
     */
    public static void checkHotelValidate(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.checkHotelValidate(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 酒店审批
     * <p>
     * {"cid":"1",
     * "empid":"2",
     * "type":"0待审1已审",
     * "name":"出行人姓名",
     * "pgnum":1,
     * "ywtype" :"air/train/hotel"
     * }
     */
    public static void doHotelCommitApprove(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.doHotelCommitApprove(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 酒店订单详情
     * orderno
     */
    public static void getHotelOrderDetail(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getHotelOrderDetail(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 获取火车票审批级别
     * orderno
     */
    public static void getHotelApprove(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getHotelApprove(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 酒店送审
     */
    public static void sendHotelOrderApprove(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.sendHotelOrderApprove(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 酒店下单
     */
    public static void createHotelOrder(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.createHotelOrder(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 得到酒店订单列表
     */
    public static void getHotelOrderList(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getHotelOrderList(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 取消酒店订单
     */
    public static void cancelHotelOrder(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.cancelHotelOrder(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 酒店担保
     * {
     * "orderno:"订单号",
     * "cardno":"信用卡号",
     * "cvv":"",
     * "expiration":"4位年+2位月",
     * "holderName":"持卡人",
     * "idtype":"身份证 IdentityCard护照 Passport其他 Other",
     * "idno":"身份证号"
     * }
     */
    public static void guaranteeHotel(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.guaranteeHotel(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 检查信用卡有效与否
     * {"cardno:"信用卡卡号"}
     */
    public static void checkCreditCardValidate(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.checkCreditCardValidate(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 支付
     * {"orderno:"单号"}
     */
    public static void hotelPay(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.hotelPay(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    public static void getApplyNoList(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getApplyNoList(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    public static void getApplyNoDetail(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getApplyNoDetail(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 获取支付方式
     */
    public static void getPayType(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getPayType(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 获取配送地址
     * cid  pgnum
     */
    public static void getPeisongAddr(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getPeisongAddr(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 获取配送地址
     * cid  pgnum
     */
    public static void getPayInfo(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.getPayInfo(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 设置默认配送地址
     */
    public static void setDefaultAddr(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.setDefaultAddr(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 取消改签
     * gqorderno
     */
    public static void cancleTrainGaiqian(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.cancleTrainGaiqian(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 确认改签
     * gqorderno
     */
    public static void confirmTrainGaiqian(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.confirmTrainGaiqian(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 确认改签
     * gqorderno
     */
    public static void confirmPlaneGaiqian(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.confirmPlaneGaiqian(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 改签后退票
     * 所需参数:cid    gqorderno     empid    tpreason  passids  orderfrom
     */
    public static void tuipiaoInGaiqian(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.tuipiaoInGaiqian(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }

    /**
     * 改签后改签
     * 所需参数:cid    gqorderno     empid    route    orderfrom    weibeiflag    wbreason
     */
    public static void moregaiqian(final Context context, String dataJson, final Class dataBeanClaz, final OnResponse listener) {
        String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + dataJson).toUpperCase();//签名
        LogFactory.d("提交的json字符串-------------->", dataJson);
        request(context, server.moregaiqian(dataJson, Constant.APPKEY, sign), true, dataBeanClaz, listener, true, "");
    }
}
