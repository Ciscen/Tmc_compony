package com.auvgo.tmc.train.interfaces;


import com.auvgo.tmc.train.bean.ResponseOuterBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by lc on 2016/11/8
 */

public interface RetrofitServer {
    @FormUrlEncoded
    @POST("common/login")
    Observable<ResponseOuterBean> doLogin(@Field("data") String data,
                                          @Field("appkey") String appkey,
                                          @Field("sign") String sign);

    @FormUrlEncoded
    @POST("common/city")
    Observable<ResponseOuterBean> getCities(@Field("data") String data,
                                            @Field("appkey") String appkey,
                                            @Field("sign") String sign);

    @FormUrlEncoded
    @POST("crm/getpolicy")
    Observable<ResponseOuterBean> getPolicy(@Field("data") String data,
                                            @Field("appkey") String appkey,
                                            @Field("sign") String sign);

    @FormUrlEncoded
    @POST("crm/getcompnyconf")
    Observable<ResponseOuterBean> getComSetting(@Field("data") String data,
                                                @Field("appkey") String appkey,
                                                @Field("sign") String sign);

    @FormUrlEncoded
    @POST("crm/getemployee")
    Observable<ResponseOuterBean> getPsgList(@Field("data") String data,
                                             @Field("appkey") String appkey,
                                             @Field("sign") String sign);

    @FormUrlEncoded
    @POST("train/query")
    Observable<ResponseOuterBean> getTrainList(@Field("data") String data,
                                               @Field("appkey") String appkey,
                                               @Field("sign") String sign);

    @FormUrlEncoded
    @POST("train/station")
    Observable<ResponseOuterBean> getTrainTime(@Field("data") String data,
                                               @Field("appkey") String appkey,
                                               @Field("sign") String sign);

    @FormUrlEncoded
    @POST("train/checkacc")
    Observable<ResponseOuterBean> login12306(@Field("data") String data,
                                             @Field("appkey") String appkey,
                                             @Field("sign") String sign);

    @FormUrlEncoded
    @POST("train/createOrder")
    Observable<ResponseOuterBean> createOrder(@Field("data") String data,
                                              @Field("appkey") String appkey,
                                              @Field("sign") String sign);

    @FormUrlEncoded
    @POST("train/getOrderByNo")
    Observable<ResponseOuterBean> getOrder(@Field("data") String data,
                                           @Field("appkey") String appkey,
                                           @Field("sign") String sign);

    @FormUrlEncoded
    @POST("crm/getcostcenter")
    Observable<ResponseOuterBean> getCostCenter(@Field("data") String data,
                                                @Field("appkey") String appkey,
                                                @Field("sign") String sign);

    @FormUrlEncoded
    @POST("crm/getproject")
    Observable<ResponseOuterBean> getProject(@Field("data") String data,
                                             @Field("appkey") String appkey,
                                             @Field("sign") String sign);

    @FormUrlEncoded
    @POST("crm/getwbreason")
    Observable<ResponseOuterBean> getWbReason(@Field("data") String data,
                                              @Field("appkey") String appkey,
                                              @Field("sign") String sign);

    /*
    获取审批级别
     */
    @FormUrlEncoded
    @POST("train/getTrainApprove")
    Observable<ResponseOuterBean> getTrainApprove(@Field("data") String data,
                                                  @Field("appkey") String appkey,
                                                  @Field("sign") String sign);

    @FormUrlEncoded
    @POST("train/sendOrderApprove")
    Observable<ResponseOuterBean> sendOrderApprove(@Field("data") String data,
                                                   @Field("appkey") String appkey,
                                                   @Field("sign") String sign);

    /**
     * 获取个人信息
     */
    @FormUrlEncoded
    @POST("crm/procenter")
    Observable<ResponseOuterBean> getPersonalInfo(@Field("data") String data,
                                                  @Field("appkey") String appkey,
                                                  @Field("sign") String sign);

    /**
     * 修改个人信息
     */
    @FormUrlEncoded
    @POST("crm/procenter/update")
    Observable<ResponseOuterBean> updatePCInfo(@Field("data") String data,
                                               @Field("appkey") String appkey,
                                               @Field("sign") String sign);

    /**
     * 修改密码
     */
    @FormUrlEncoded
    @POST("crm/procenter/updatepass")
    Observable<ResponseOuterBean> updatePassword(@Field("data") String data,
                                                 @Field("appkey") String appkey,
                                                 @Field("sign") String sign);

    /**
     * 获取订单列表
     */
    @FormUrlEncoded
    @POST("train/order/queryorder")
    Observable<ResponseOuterBean> getOrderList(@Field("data") String data,
                                               @Field("appkey") String appkey,
                                               @Field("sign") String sign);

    /**
     * 获取审批订单列表
     */
    @FormUrlEncoded
    @POST("common/approveorder")
    Observable<ResponseOuterBean> getApproveOrder(@Field("data") String data,
                                                  @Field("appkey") String appkey,
                                                  @Field("sign") String sign);

    /**
     * 提交审批
     */
    @FormUrlEncoded
    @POST("train/order/approvesave")
    Observable<ResponseOuterBean> doTrainCommitApprove(@Field("data") String data,
                                                       @Field("appkey") String appkey,
                                                       @Field("sign") String sign);

    /**
     * 提交退票
     * 输入参数
     * {
     * "cid":"1",
     * "empid":"2",
     * "orderno":"MDW111841887430600",
     * "userids":"1/2/3",
     * "orderfrom":"来自安卓2还是ios3"
     * }
     */
    @FormUrlEncoded
    @POST("train/order/createtuipiao")
    Observable<ResponseOuterBean> doReturnTicket(@Field("data") String data,
                                                 @Field("appkey") String appkey,
                                                 @Field("sign") String sign);

    /**
     * 取消订单
     * {
     * "orderno":"MDW111841887430600",
     * }
     */
    @FormUrlEncoded
    @POST("train/cancel")
    Observable<ResponseOuterBean> cancelOrder(@Field("data") String data,
                                              @Field("appkey") String appkey,
                                              @Field("sign") String sign);

    /**
     * 批量获取员工职级
     * {
     * "cid":1,
     * "empids":"[1,2,3]"
     * }
     */
    @FormUrlEncoded
    @POST("crm/getEmpLevel")
    Observable<ResponseOuterBean> getEmpLevel(@Field("data") String data,
                                              @Field("appkey") String appkey,
                                              @Field("sign") String sign);

    /**
     * 改签
     */
    @FormUrlEncoded
    @POST("train/order/creategaiqian")
    Observable<ResponseOuterBean> doAlter(@Field("data") String data,
                                          @Field("appkey") String appkey,
                                          @Field("sign") String sign);

    /**
     * 改签详情单
     * <p>
     * {"cid":"1","gqorderno":"改签订单号"}
     */
    @FormUrlEncoded
    @POST("train/order/getGaiqianDetail")
    Observable<ResponseOuterBean> getAlterOrderDetail(@Field("data") String data,
                                                      @Field("appkey") String appkey,
                                                      @Field("sign") String sign);

    /**
     * 退票详情单
     * <p>
     * {"cid":"1","tporderno":"退票订单号"}
     */
    @FormUrlEncoded
    @POST("train/order/getTuipiaoDetail")
    Observable<ResponseOuterBean> getTrainReturnOrderDetail(@Field("data") String data,
                                                            @Field("appkey") String appkey,
                                                            @Field("sign") String sign);

    /**
     * 订座中检查状态
     */
    @FormUrlEncoded
    @POST("train/checksatus")
    Observable<ResponseOuterBean> checkState(@Field("data") String data,
                                             @Field("appkey") String appkey,
                                             @Field("sign") String sign);

    /**
     * 确认出票
     */
    @FormUrlEncoded
    @POST("train/confirm")
    Observable<ResponseOuterBean> confirmTrainTicket(@Field("data") String data,
                                                     @Field("appkey") String appkey,
                                                     @Field("sign") String sign);

    /**
     * 机票查询
     * 增加字段carrier：默认all
     */
    @FormUrlEncoded
    @POST("air/query")
    Observable<ResponseOuterBean> getPlaneList(@Field("data") String data,
                                               @Field("appkey") String appkey,
                                               @Field("sign") String sign);

    /**
     * 机票政策
     */
    @FormUrlEncoded
    @POST("crm/getairpolicy")
    Observable<ResponseOuterBean> getPlanePolicy(@Field("data") String data,
                                                 @Field("appkey") String appkey,
                                                 @Field("sign") String sign);

    /**
     * 获取前后n小时最低价
     */
    @FormUrlEncoded
    @POST("air/gethourflight")
    Observable<ResponseOuterBean> getNHourLowest(@Field("data") String data,
                                                 @Field("appkey") String appkey,
                                                 @Field("sign") String sign);

    /**
     * 下单
     */
    @FormUrlEncoded
    @POST("air/createOrder")
    Observable<ResponseOuterBean> createPlaneOrder(@Field("data") String data,
                                                   @Field("appkey") String appkey,
                                                   @Field("sign") String sign);

    /**
     * 订单详情
     */
    @FormUrlEncoded
    @POST("air/getOrderByNo")
    Observable<ResponseOuterBean> getPlaneOrderDetail(@Field("data") String data,
                                                      @Field("appkey") String appkey,
                                                      @Field("sign") String sign);

    /**
     * 获取审批级别
     */
    @FormUrlEncoded
    @POST("air/getAirApprove")
    Observable<ResponseOuterBean> getPlaneApproveLevel(@Field("data") String data,
                                                       @Field("appkey") String appkey,
                                                       @Field("sign") String sign);

    /**
     * 送审
     */
    @FormUrlEncoded
    @POST("air/sendOrderApprove")
    Observable<ResponseOuterBean> sendPlaneApprove(@Field("data") String data,
                                                   @Field("appkey") String appkey,
                                                   @Field("sign") String sign);

    /**
     * 飞机票订单列表
     */
    @FormUrlEncoded
    @POST("air/queryorder")
    Observable<ResponseOuterBean> getPlaneOrderList(@Field("data") String data,
                                                    @Field("appkey") String appkey,
                                                    @Field("sign") String sign);

    /**
     * 订单审批，同意拒绝
     */
    @FormUrlEncoded
    @POST("air/approvesave")
    Observable<ResponseOuterBean> doPlaneApproveCommit(@Field("data") String data,
                                                       @Field("appkey") String appkey,
                                                       @Field("sign") String sign);

    /**
     * 取消飞机票订单
     */
    @FormUrlEncoded
    @POST("air/cancel")
    Observable<ResponseOuterBean> canclePlaneOrder(@Field("data") String data,
                                                   @Field("appkey") String appkey,
                                                   @Field("sign") String sign);

    /**
     * 飞机票出票
     */
    @FormUrlEncoded
    @POST("air/confirm")
    Observable<ResponseOuterBean> comfirmOutPlaneTicket(@Field("data") String data,
                                                        @Field("appkey") String appkey,
                                                        @Field("sign") String sign);

    /**
     * 我的差旅信息 {"cid":"1","level":"员工职级"}
     */
    @FormUrlEncoded
    @POST("crm/procenter/mytravel")
    Observable<ResponseOuterBean> getMyTravelInfo(@Field("data") String data,
                                                  @Field("appkey") String appkey,
                                                  @Field("sign") String sign);

    /**
     * 机票退票
     */
    @FormUrlEncoded
    @POST("air/order/createtuipiao")
    Observable<ResponseOuterBean> returnPlaneTicket(@Field("data") String data,
                                                    @Field("appkey") String appkey,
                                                    @Field("sign") String sign);

    /**
     * 获取机票退票原因
     */
    @FormUrlEncoded
    @POST("air/order/gettpreason")
    Observable<ResponseOuterBean> getReturnReasons(@Field("data") String data,
                                                   @Field("appkey") String appkey,
                                                   @Field("sign") String sign);

    /**
     * 机票退票
     */
    @FormUrlEncoded
    @POST("air/order/getTuipiaoDetail")
    Observable<ResponseOuterBean> getPlaneTuipiaoDetail(@Field("data") String data,
                                                        @Field("appkey") String appkey,
                                                        @Field("sign") String sign);

    /**
     * 机票改签
     */
    @FormUrlEncoded
    @POST("air/order/creategaiqian")
    Observable<ResponseOuterBean> createPlaneAlter(@Field("data") String data,
                                                   @Field("appkey") String appkey,
                                                   @Field("sign") String sign);

    /**
     * 机票改签详情
     */
    @FormUrlEncoded
    @POST("air/order/getGaiqianDetail")
    Observable<ResponseOuterBean> getPlaneAlterDetail(@Field("data") String data,
                                                      @Field("appkey") String appkey,
                                                      @Field("sign") String sign);

    /**
     * 机票改签送审
     */
    @FormUrlEncoded
    @POST("air/order/sendGaiqianApprove")
    Observable<ResponseOuterBean> sendPlaneAlterApprove(@Field("data") String data,
                                                        @Field("appkey") String appkey,
                                                        @Field("sign") String sign);

    /**
     * 获取保险｛cid:1｝
     */
    @FormUrlEncoded
    @POST("common/baoxian")
    Observable<ResponseOuterBean> getInsurances(@Field("data") String data,
                                                @Field("appkey") String appkey,
                                                @Field("sign") String sign);

    /**
     * 酒店列表查询
     */
    @FormUrlEncoded
    @POST("hotel/query")
    Observable<ResponseOuterBean> getHotelList(@Field("data") String data,
                                               @Field("appkey") String appkey,
                                               @Field("sign") String sign);

    /**
     * 酒店关键字
     */
    @FormUrlEncoded
    @POST("hotel/keyword")
    Observable<ResponseOuterBean> getHotelKeyword(@Field("data") String data,
                                                  @Field("appkey") String appkey,
                                                  @Field("sign") String sign);


    /**
     * 酒店详情
     */
    @FormUrlEncoded
    @POST("hotel/gethoteldetail")
    Observable<ResponseOuterBean> getHotelDetail(@Field("data") String data,
                                                 @Field("appkey") String appkey,
                                                 @Field("sign") String sign);

    /**
     * 获取热门数据 商圈 品牌等
     */
    @FormUrlEncoded
    @POST("hotel/hotsuggest")
    Observable<ResponseOuterBean> getHotData(@Field("data") String data,
                                             @Field("appkey") String appkey,
                                             @Field("sign") String sign);

    /**
     * 列表位置筛选数据： 城市的商圈，行政区，车站标志物
     */
    @FormUrlEncoded
    @POST("hotel/filterdata")
    Observable<ResponseOuterBean> getFiltData(@Field("data") String data,
                                              @Field("appkey") String appkey,
                                              @Field("sign") String sign);

    /**
     * 列表位置筛选数据： 城市的商圈，行政区，车站标志物
     */
    @FormUrlEncoded
    @POST("crm/gethotelpolicy")
    Observable<ResponseOuterBean> getHotelPolicy(@Field("data") String data,
                                                 @Field("appkey") String appkey,
                                                 @Field("sign") String sign);

    /**
     * 城市定位  {\"cityname\":\"上海\"}
     */
    @FormUrlEncoded
    @POST("hotel/location")
    Observable<ResponseOuterBean> getCityIdByName(@Field("data") String data,
                                                  @Field("appkey") String appkey,
                                                  @Field("sign") String sign);

    /**
     * 酒店信息详情
     */
    @FormUrlEncoded
    @POST("hotel/hotelinfo")
    Observable<ResponseOuterBean> getHotelInfo(@Field("data") String data,
                                               @Field("appkey") String appkey,
                                               @Field("sign") String sign);

    /**
     * 房型库存验证
     */
    @FormUrlEncoded
    @POST("hotel/validator")
    Observable<ResponseOuterBean> checkHotelValidate(@Field("data") String data,
                                                     @Field("appkey") String appkey,
                                                     @Field("sign") String sign);

    /**
     * 酒店订单详情
     * orderno
     */
    @FormUrlEncoded
    @POST("hotel/getOrderByNo")
    Observable<ResponseOuterBean> getHotelOrderDetail(@Field("data") String data,
                                                      @Field("appkey") String appkey,
                                                      @Field("sign") String sign);

    /**
     * 获取火车票审批级别
     */
    @FormUrlEncoded
    @POST("hotel/getHotelApprove")
    Observable<ResponseOuterBean> getHotelApprove(@Field("data") String data,
                                                  @Field("appkey") String appkey,
                                                  @Field("sign") String sign);

    /**
     * 酒店送审
     */
    @FormUrlEncoded
    @POST("hotel/sendOrderApprove")
    Observable<ResponseOuterBean> sendHotelOrderApprove(@Field("data") String data,
                                                        @Field("appkey") String appkey,
                                                        @Field("sign") String sign);

    /**
     * 酒店审批
     */
    @FormUrlEncoded
    @POST("hotel/order/approvesave")
    Observable<ResponseOuterBean> doHotelCommitApprove(@Field("data") String data,
                                                       @Field("appkey") String appkey,
                                                       @Field("sign") String sign);

    /**
     * 酒店下单
     */
    @FormUrlEncoded
    @POST("hotel/createOrder")
    Observable<ResponseOuterBean> createHotelOrder(@Field("data") String data,
                                                   @Field("appkey") String appkey,
                                                   @Field("sign") String sign);

    /**
     * 酒店订单列表
     */
    @FormUrlEncoded
    @POST("hotel/order/queryorder")
    Observable<ResponseOuterBean> getHotelOrderList(@Field("data") String data,
                                                    @Field("appkey") String appkey,
                                                    @Field("sign") String sign);

    /**
     * 酒店订单列表
     */
    @FormUrlEncoded
    @POST("hotel/order/cancel")
    Observable<ResponseOuterBean> cancelHotelOrder(@Field("data") String data,
                                                   @Field("appkey") String appkey,
                                                   @Field("sign") String sign);

    /**
     * 酒店担保
     */
    @FormUrlEncoded
    @POST("hotel/order/guarantee")
    Observable<ResponseOuterBean> guaranteeHotel(@Field("data") String data,
                                                 @Field("appkey") String appkey,
                                                 @Field("sign") String sign);

    /**
     * 酒店担保
     */
    @FormUrlEncoded
    @POST("hotel/order/cardvalidate")
    Observable<ResponseOuterBean> checkCreditCardValidate(@Field("data") String data,
                                                          @Field("appkey") String appkey,
                                                          @Field("sign") String sign);

    /**
     * 酒店担保
     */
    @FormUrlEncoded
    @POST("hotel/order/confirmpay")
    Observable<ResponseOuterBean> hotelPay(@Field("data") String data,
                                           @Field("appkey") String appkey,
                                           @Field("sign") String sign);

    /**
     * 获取申请单号列表
     */
    @FormUrlEncoded
    @POST("crm/getappform")
    Observable<ResponseOuterBean> getApplyNoList(@Field("data") String data,
                                                 @Field("appkey") String appkey,
                                                 @Field("sign") String sign);

    /**
     * 获取申请单号详情
     */
    @FormUrlEncoded
    @POST("crm/getAppformDetail")
    Observable<ResponseOuterBean> getApplyNoDetail(@Field("data") String data,
                                                   @Field("appkey") String appkey,
                                                   @Field("sign") String sign);

    /**
     * 获取支付方式
     */
    @FormUrlEncoded
    @POST("common/paytype")
    Observable<ResponseOuterBean> getPayType(@Field("data") String data,
                                             @Field("appkey") String appkey,
                                             @Field("sign") String sign);


    /**
     * 获取配送地址
     */
    @FormUrlEncoded
    @POST("crm/getpeison")
    Observable<ResponseOuterBean> getPeisongAddr(@Field("data") String data,
                                                 @Field("appkey") String appkey,
                                                 @Field("sign") String sign);

    /**
     * 获取支付信息
     */
    @FormUrlEncoded
    @POST("common/sendpay")
    Observable<ResponseOuterBean> getPayInfo(@Field("data") String data,
                                             @Field("appkey") String appkey,
                                             @Field("sign") String sign);

    /**
     * 设置默认地址
     */
    @FormUrlEncoded
    @POST("crm/setpeisonsort")
    Observable<ResponseOuterBean> setDefaultAddr(@Field("data") String data,
                                                 @Field("appkey") String appkey,
                                                 @Field("sign") String sign);

    /**
     * 取消改签
     */
    @FormUrlEncoded
    @POST("train/order/cancleGaiqian")
    Observable<ResponseOuterBean> cancleTrainGaiqian(@Field("data") String data,
                                                     @Field("appkey") String appkey,
                                                     @Field("sign") String sign);

    /**
     * 火车票确认改签
     */
    @FormUrlEncoded
    @POST("train/order/confirmGaiqian")
    Observable<ResponseOuterBean> confirmTrainGaiqian(@Field("data") String data,
                                                      @Field("appkey") String appkey,
                                                      @Field("sign") String sign);

    /**
     * 机票确认改签
     */
    @FormUrlEncoded
    @POST("air/order/confirmGaiqianPay")
    Observable<ResponseOuterBean> confirmPlaneGaiqian(@Field("data") String data,
                                                      @Field("appkey") String appkey,
                                                      @Field("sign") String sign);

    /**
     * 改签后退票
     */
    @FormUrlEncoded
    @POST("air/order/creategqtuipiao")
    Observable<ResponseOuterBean> tuipiaoInGaiqian(@Field("data") String data,
                                                   @Field("appkey") String appkey,
                                                   @Field("sign") String sign);

    /**
     * 改签后改签
     */
    @FormUrlEncoded
    @POST("air/order/moregaiqian")
    Observable<ResponseOuterBean> moregaiqian(@Field("data") String data,
                                              @Field("appkey") String appkey,
                                              @Field("sign") String sign);

}
