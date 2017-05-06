package com.auvgo.tmc.utils;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.Baseadapter;
import com.auvgo.tmc.adapter.FilterStationAdapter;
import com.auvgo.tmc.adapter.TimeAdapter;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.hotel.bean.HotelOrderDetailBean;
import com.auvgo.tmc.plane.bean.PlaneListBean;
import com.auvgo.tmc.plane.bean.PlaneOrderDetailBean;
import com.auvgo.tmc.plane.bean.PlaneRouteBeanWF;
import com.auvgo.tmc.train.bean.SelectionBean;
import com.auvgo.tmc.views.MDialog;
import com.auvgo.tmc.views.MyCheckbox;
import com.auvgo.tmc.views.MyCustomDialog;
import com.auvgo.tmc.views.MyDialog;
import com.auvgo.tmc.views.MyGridView;
import com.auvgo.tmc.views.MyPickerView;
import com.auvgo.tmc.views.NoticeDialog;
import com.auvgo.tmc.views.PlaneFilterView;
import com.auvgo.tmc.views.StarPopView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by lc on 2016/11/9
 */

public class DialogUtil {

    private static MyDialog dialog;

    public static MyDialog showDialog(Context context, String title, String left, String right, String content, MyDialog.OnButtonClickListener listener) {

        if (dialog == null || dialog.getmContext() != context) {
            dialog = new MyDialog(context, title, left, right, content, listener);
        } else {
            if (dialog.isShowing()) {
                dialog.dismiss();
                dialog = new MyDialog(context, title, left, right, content, listener);
            }
            dialog.setContent(content);
            dialog.setContext(context);
            dialog.setTitle(title);
            dialog.setLeft(left);
            dialog.setRight(right);
            dialog.setListener(listener);
            dialog.invalicade();
        }
        dialog.show();
        return dialog;
    }

    public static void closeDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public static void showPriceDialog(Context context, int h, String serFee, String serFeeTotal,
                                       String peisongfei, String peisongTotal,
                                       String ticketFee, String totalFee, MyCustomDialog.OnLoadData l) {
        View view = View.inflate(context, R.layout.layout_book_price_dialog, null);
        TextView ticketprice = (TextView) view.findViewById(R.id.price_dialog_ticketprice);
        TextView ticketprice_total = (TextView) view.findViewById(R.id.price_dialog_ticketprice_total);
        TextView servicefee = (TextView) view.findViewById(R.id.price_dialog_servicefee);
        TextView servicefee_total = (TextView) view.findViewById(R.id.price_dialog_servicefee_total);
        View peisongFeiItem = view.findViewById(R.id.price_dialog_peisongFei_item);
        TextView peisongFei = (TextView) view.findViewById(R.id.price_dialog_peisongFei);
        TextView peisongFei_total = (TextView) view.findViewById(R.id.price_dialog_peisongFei_total);
        if (!TextUtils.isEmpty(peisongfei)) {
            peisongFei.setText(peisongfei);
            peisongFei_total.setText(peisongTotal);
        } else {
            peisongFeiItem.setVisibility(View.GONE);
        }
        servicefee.setText(serFee);
        ticketprice_total.setText(totalFee);
        ticketprice.setText(ticketFee);
        servicefee_total.setText(serFeeTotal);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        int height = view.getMeasuredHeight();
        int yoff = DeviceUtils.getDisplayMetrics().heightPixels - h;
        final Window w = dimWindows(context);
        MyCustomDialog dialog = new MyCustomDialog(true, view, new MyCustomDialog.OndismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams attributes = w.getAttributes();
                attributes.alpha = 1;
                w.setAttributes(attributes);
            }
        });
        dialog.setYoff(yoff);
        dialog.show();
    }

    public static void showPlaneFilterPop(Context context, List<SelectionBean> a,
                                          List<SelectionBean> b, List<SelectionBean> c,
                                          final PlaneFilterView.OnPlaneFilterViewListener listener) {
        PlaneFilterView v = new PlaneFilterView(context);
        final MyCustomDialog dialog = new MyCustomDialog(v, null);
        final Window window = dimWindows(context);
        v.setSelections(a, b, c).setOnPlaneFilterViewListener(new PlaneFilterView.OnPlaneFilterViewListener() {
            @Override
            public void onSure(List<Integer> a, int b, List<Integer> c) {
                listener.onSure(a, b, c);
                dialog.dismiss();
            }

            @Override
            public void onCancle() {
                listener.onCancle();
                dialog.dismiss();
            }
        });
        dialog.setDismissListener(new MyCustomDialog.OndismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.alpha = 1;
                window.setAttributes(attributes);
            }
        });
        dialog.show();
    }

    public static void showPlanePriceDetailPop(Context context, PlaneRouteBeanWF firstRoute,
                                               PlaneRouteBeanWF secondRoute, int baoxian, int psgNum) {
        PlaneListBean firstRouteBean = firstRoute.getBean();
        View content = View.inflate(context, R.layout.layout_plane_price_detail, null);
        TextView piaojia1 = (TextView) content.findViewById(R.id.price_dialog_piaojia);
        TextView jijianfei1 = (TextView) content.findViewById(R.id.price_dialog_jijian);
        TextView baoxian1 = (TextView) content.findViewById(R.id.price_dialog_baoxian);
        TextView fuwufei1 = (TextView) content.findViewById(R.id.price_dialog_servicefee);
        TextView piaojiaall1 = (TextView) content.findViewById(R.id.price_dialog_piaojia_all);
        TextView jijianfeiall1 = (TextView) content.findViewById(R.id.price_dialog_jijian_all);
        TextView baoxianall1 = (TextView) content.findViewById(R.id.price_dialog_baoxian_all);
        TextView fuwufeiall1 = (TextView) content.findViewById(R.id.price_dialog_servicefee_all);
        TextView qucheng = (TextView) content.findViewById(R.id.price_dialog_qucheng);
        //票价
        double piaojia = firstRouteBean.getCangweis().get(firstRoute.getCangwei()).getPrice();
        //服务费
        double fuwufei = MyApplication.mComSettingBean.getFuwufei().getGnapp();
        //机建费、税费
        int jijianfei = firstRouteBean.getAirporttax() + firstRouteBean.getFueltax();
        piaojia1.setText(piaojia + "x" + psgNum);
        jijianfei1.setText(jijianfei + "x" + psgNum);
        baoxian1.setText(baoxian + "x" + psgNum);
        fuwufei1.setText(fuwufei + "x" + psgNum);
        piaojiaall1.setText(String.valueOf(piaojia * psgNum));
        jijianfeiall1.setText(String.valueOf(jijianfei * psgNum));
        baoxianall1.setText(String.valueOf(baoxian * psgNum));
        fuwufeiall1.setText(String.valueOf(fuwufei * psgNum));

        if (secondRoute != null) {
            View item_return = content.findViewById(R.id.item_dialog_fancheng);
            item_return.setVisibility(View.VISIBLE);
            TextView piaojia2 = (TextView) content.findViewById(R.id.price_dialog_piaojia2);
            TextView jijianfei2 = (TextView) content.findViewById(R.id.price_dialog_jijian2);
            TextView baoxian2 = (TextView) content.findViewById(R.id.price_dialog_baoxian2);
            TextView fuwufei2 = (TextView) content.findViewById(R.id.price_dialog_servicefee2);
            TextView piaojiaall2 = (TextView) content.findViewById(R.id.price_dialog_piaojia_all2);
            TextView jijianfeiall2 = (TextView) content.findViewById(R.id.price_dialog_jijian_all2);
            TextView baoxianall2 = (TextView) content.findViewById(R.id.price_dialog_baoxian_all2);
            TextView fuwufeiall2 = (TextView) content.findViewById(R.id.price_dialog_servicefee_all2);
            qucheng.setText("去程");
            PlaneListBean secondRouteBean = secondRoute.getBean();
            //票价
            double pj2 = secondRouteBean.getCangweis().get(secondRoute.getCangwei()).getPrice();
            //机建费、税费
            int jjf2 = secondRouteBean.getAirporttax() + secondRouteBean.getFueltax();
            piaojia2.setText(pj2 + "x" + psgNum);
            jijianfei2.setText(jjf2 + "x" + psgNum);
            baoxian2.setText(baoxian + "x" + psgNum);
            fuwufei2.setText(fuwufei + "x" + psgNum);
            piaojiaall2.setText(String.valueOf(pj2 * psgNum));
            jijianfeiall2.setText(String.valueOf(jjf2 * psgNum));
            baoxianall2.setText(String.valueOf(baoxian * psgNum));
            fuwufeiall2.setText(String.valueOf(fuwufei * psgNum));
        }
        MyCustomDialog dialog = new MyCustomDialog(false, content, null);
        final Window window = dimWindows(context);
        content.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int height = content.getMeasuredHeight();
        dialog.setYoff(DeviceUtils.deviceHeight() - height);
        dialog.setDismissListener(new MyCustomDialog.OndismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.alpha = 1;
                window.setAttributes(attributes);
            }
        });
        dialog.show();
    }

    public static void showPlanePriceDetailPop(Context context, PlaneOrderDetailBean mBean) {
        View content = View.inflate(context, R.layout.layout_plane_price_detail, null);
        TextView piaojia1 = (TextView) content.findViewById(R.id.price_dialog_piaojia);
        TextView jijianfei1 = (TextView) content.findViewById(R.id.price_dialog_jijian);
        TextView baoxian1 = (TextView) content.findViewById(R.id.price_dialog_baoxian);
        TextView fuwufei1 = (TextView) content.findViewById(R.id.price_dialog_servicefee);
        TextView piaojiaall1 = (TextView) content.findViewById(R.id.price_dialog_piaojia_all);
        TextView jijianfeiall1 = (TextView) content.findViewById(R.id.price_dialog_jijian_all);
        TextView baoxianall1 = (TextView) content.findViewById(R.id.price_dialog_baoxian_all);
        TextView fuwufeiall1 = (TextView) content.findViewById(R.id.price_dialog_servicefee_all);

        //乘客人数
        int psgNum = mBean.getPassengers().size();
        //票价
        double piaojia = mBean.getTotalticketprice() / psgNum;
        //服务费

        List<PlaneOrderDetailBean.RoutePassBean> routePass = mBean.getRoutePass();
        double fuwufei = routePass.get(0).getFuwufee();
        //机建费、税费
        PlaneOrderDetailBean.RoutesBean routesBean = mBean.getRoutes().get(0);
        double jijianfei = routesBean.getAirporttax() + routesBean.getFueltax();
        //保险费
        double baoxian = routePass.get(0).getBxPayMoney();

        piaojia1.setText(piaojia + "x" + psgNum);
        jijianfei1.setText(jijianfei + "x" + psgNum);
        baoxian1.setText(baoxian + "x" + psgNum);
        fuwufei1.setText(fuwufei + "x" + psgNum);
        piaojiaall1.setText(String.valueOf(piaojia * psgNum));
        jijianfeiall1.setText(String.valueOf(jijianfei * psgNum));
        baoxianall1.setText(String.valueOf(baoxian * psgNum));
        fuwufeiall1.setText(String.valueOf(fuwufei * psgNum));

        MyCustomDialog dialog = new MyCustomDialog(false, content, null);
        final Window window = dimWindows(context);
        content.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int height = content.getMeasuredHeight();
        dialog.setYoff(DeviceUtils.deviceHeight() - height);
        dialog.setDismissListener(new MyCustomDialog.OndismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.alpha = 1;
                window.setAttributes(attributes);
            }
        });
        dialog.show();
    }

    public static void showHotelPolicyDialog(Context context, String policyStr) {
        View view = View.inflate(context, R.layout.layout_dialog_policy, null);
        ((TextView) view.findViewById(R.id.dialog_policy_tv)).setText(policyStr);
        final NoticeDialog dialog = new NoticeDialog(context, view);
        view.findViewById(R.id.dialog_close_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public static void showStarPop(Context context, int[] a, boolean[] b, final StarPopView.StarPopListener listener) {
        StarPopView spv = new StarPopView(context, a, b);
        final MyCustomDialog dialog = new MyCustomDialog(spv);
        spv.setListener(new StarPopView.StarPopListener() {
            @Override
            public void onSureClick(int[] positions, boolean[] stars) {
                listener.onSureClick(positions, stars);
                dialog.dismiss();
            }

            @Override
            public void onCancel() {
                listener.onCancel();
            }
        });

        final Window window = dimWindows(context);
        dialog.show();
        dialog.setDismissListener(new MyCustomDialog.OndismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.alpha = 1;
                window.setAttributes(attributes);
            }
        });
    }

    public static void showHotelPriceDialog(Context context, HotelOrderDetailBean mBean) {
        showHotelPriceDialog(context, mBean.getPaymentType().equals("SelfPay"), mBean.getTotalPrice() - mBean.getFuwufee(), mBean.getGuaranteeAmount()
                , mBean.getPaymentType().equals("SelfPay") ? 0 : mBean.getFuwufee()
                , mBean.getNumberOfRooms(),
                TimeUtils.compareDay(
                        TimeUtils.date2Format(new Date(mBean.getArrivalDate()), "yyyy-MM-dd"),
                        TimeUtils.date2Format(new Date(mBean.getDepartureDate()), "yyyy-MM-dd")
                ));
//        View view = View.inflate(context, R.layout.layout_hotel_price_dialog, null);
//        View guarantee_rl = view.findViewById(R.id.dialog_hotel_price_guarantee_rl);
//        TextView price_guarantee = (TextView) view.findViewById(R.id.dialog_hotel_price_guarantee);
//        TextView price_pay = (TextView) view.findViewById(R.id.dialog_hotel_price_pay);
//        TextView count = (TextView) view.findViewById(R.id.dialog_hotel_price_count);
//        TextView fuwufei = (TextView) view.findViewById(R.id.dialog_hotel_price_fuwufei);
//        if (mBean.getIsNeedGuarantee().equals("false")) {
//            guarantee_rl.setVisibility(View.GONE);
//        } else {
//            price_guarantee.setText(String.valueOf(mBean.getGuaranteeAmount()));
//        }
//        fuwufei.setText(String.valueOf(mBean.getUsers().get(0).getFuwufee()));
//        price_pay.setText(String.valueOf(mBean.getTotalPrice()));
//        count.setText("房费(" + mBean.getNumberOfRooms() + "间*"
//                + TimeUtils.compareDay(
//                TimeUtils.date2Format(new Date(mBean.getArrivalDate()), "yyyy-MM-dd"),
//                TimeUtils.date2Format(new Date(mBean.getDepartureDate()), "yyyy-MM-dd")
//        ) + "晚)");
//        final Window w = dimWindows(context);
//        MyCustomDialog dialog = new MyCustomDialog(true, view, new MyCustomDialog.OndismissListener() {
//            @Override
//            public void onDismiss() {
//                WindowManager.LayoutParams attributes = w.getAttributes();
//                attributes.alpha = 1;
//                w.setAttributes(attributes);
//            }
//        });
//        dialog.show();
    }

    public static void showHotelPriceDialog(Context context, boolean isSelfPay, double totalRate, double guaranteeFee,
                                            double fuwufee, int mRoomNum, int night) {
        View view = View.inflate(context, R.layout.layout_hotel_price_dialog, null);
        View guarantee_rl = view.findViewById(R.id.dialog_hotel_price_guarantee_rl);
        TextView price_guarantee = (TextView) view.findViewById(R.id.dialog_hotel_price_guarantee);
        TextView price_pay = (TextView) view.findViewById(R.id.dialog_hotel_price_pay);
        TextView count = (TextView) view.findViewById(R.id.dialog_hotel_price_count);
        TextView fuwufei = (TextView) view.findViewById(R.id.dialog_hotel_price_fuwufei);
        TextView price_name = (TextView) view.findViewById(R.id.dialog_hotel_price_name);
        if (guaranteeFee == 0) {
            guarantee_rl.setVisibility(View.GONE);
        } else {
            price_guarantee.setText(AppUtils.keepNSecimal(guaranteeFee + "", 2));
        }
        price_name.setText(isSelfPay ? "到店付款" : "在线预付");
        fuwufei.setText(AppUtils.keepNSecimal(fuwufee + "", 2));
        price_pay.setText(AppUtils.keepNSecimal(totalRate + "", 2));
        count.setText("房费(" + mRoomNum + "间*"
                + night + "晚)");
        final Window w = dimWindows(context);
        MyCustomDialog dialog = new MyCustomDialog(true, view, new MyCustomDialog.OndismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams attributes = w.getAttributes();
                attributes.alpha = 1;
                w.setAttributes(attributes);
            }
        });
        dialog.show();
    }

    public static void showExpandablePickDialog(Context context, String title, int mPosition,
                                                List<? extends MyPickerView.Selection> list, MyPickerView.OnPickerViewSure onPickerViewSure) {
        showPickDialog(context, title, mPosition, list, MyPickerView.WRAP_HEIGHT, MyPickerView.SINGLE_CHOICE, onPickerViewSure);
    }

    public static void showCancelDialog(Context context, String info) {
        View view = View.inflate(context, R.layout.layout_dialog_cancel, null);
        ((TextView) view.findViewById(R.id.dialog_cancel_tv)).setText(info);
        final NoticeDialog dialog = new NoticeDialog(context, view);
        view.findViewById(R.id.dialog_close_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showDatePicker(Context context, String mStartDate, DatePickerDialog.OnDateSetListener mListener) {
        Calendar c = Calendar.getInstance();
        c.setTime(TimeUtils.string2Date(TextUtils.isEmpty(mStartDate) ? TimeUtils.getToday() : mStartDate, "yyyy-MM-dd"));
        DatePickerDialog dpd = new DatePickerDialog(context, mListener, c.get(Calendar.YEAR),
                c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }


    public interface OnPickerListener {
        void onSure();
    }

    /**
     * 选择的view,不好用，使用showPickerPop代替
     *
     * @param cover
     * @param adapter
     * @param listener
     * @param pListener
     */
    @Deprecated
    public static void showPickerView(Context context, View cover, Baseadapter adapter,
                                      final AdapterView.OnItemClickListener listener, final OnPickerListener pListener) {
        View contentView = View.inflate(context, R.layout.layout_picker_pop, null);
        ListView mlv = (ListView) contentView.findViewById(R.id.picker_pop_lv);
        ViewGroup.LayoutParams lp = mlv.getLayoutParams();
        lp.height = 100;
        mlv.setLayoutParams(lp);
        View cancle = contentView.findViewById(R.id.pop_cancle);
        View sure = contentView.findViewById(R.id.pop_sure);
        mlv.setAdapter(adapter);
        mlv.setOnItemClickListener(listener);
        final MyCustomDialog dialog = new MyCustomDialog(contentView, cover);
        dialog.show();
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pListener.onSure();
                dialog.dismiss();
            }
        });
    }

    /**
     * 多选的选择框
     *
     * @param context
     * @param title
     * @param selections 选项
     * @param mPositions List<Integer> 已经选择的位置集合
     * @param listener
     */
    public static void showMultiPickerView(Context context, String title, List<? extends MyPickerView.Selection> selections
            , List<Integer> mPositions, MyPickerView.OnPickerViewSure listener) {
        final Window window = dimWindows(context);
        MyPickerView mpv = new MyPickerView(context, null);
        mpv.setTitle(title)
                .setPosition(mPositions)
                .setSelections(selections)
                .setListener(listener)
                .setMyPickerViewDismiss(new MyCustomDialog.OndismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams attributes = window.getAttributes();
                        attributes.alpha = 1;
                        window.setAttributes(attributes);
                    }
                });
        mpv.show(MyPickerView.WRAP_HEIGHT, MyPickerView.MULTI_CHOICE);
    }

    public interface FilterPopListener {
        void onSureClick(String typeStr, List<Integer> offTimes, List<String> station_selected);
    }

    public static void showPickerPopWithSureHeight(final Context context, String title, int mPosition,
                                                   List<? extends MyPickerView.Selection> selectionBeens,
                                                   MyPickerView.OnPickerViewSure onsureL) {
        showPickDialog(context, title, mPosition, selectionBeens, MyPickerView.SURE_HEIGHT, MyPickerView.SINGLE_CHOICE, onsureL);
    }

    private static void showPickDialog(Context context, String title, int mPosition,
                                       List<? extends MyPickerView.Selection> selectionBeens,
                                       int heightMode, int choiceMode,
                                       MyPickerView.OnPickerViewSure onsureL) {
        final Window window = dimWindows(context);
        MyPickerView mpv = new MyPickerView(context, null);
        mpv.setTitle(title)
                .setPosition(mPosition)
                .setSelections(selectionBeens)
                .setListener(onsureL)
                .setMyPickerViewDismiss(new MyCustomDialog.OndismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams attributes = window.getAttributes();
                        attributes.alpha = 1;
                        window.setAttributes(attributes);
                    }
                });
        mpv.show(heightMode, choiceMode);
    }


    /**
     * 背景变暗
     *
     * @param context
     * @return
     */
    public static Window dimWindows(Context context) {
        final Activity a = (Activity) context;
        final Window window = a.getWindow();
        final WindowManager.LayoutParams attributes = window.getAttributes();
        ValueAnimator va = ValueAnimator.ofFloat(1, 0.5f);
        va.setDuration(500);
        window.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, 0);

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                float f = (float) animation.getAnimatedValue();
                attributes.alpha = f;
                attributes.dimAmount = f;
                window.setAttributes(attributes);
            }
        });
        va.start();


//        alpha = 1;
//        final Timer timer = new Timer();
//        final TimerTask runnable = new TimerTask() {
//            @Override
//            public void run() {
//                a.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        alpha -= 0.02f;
//                        WindowManager.LayoutParams attributes = window.getAttributes();
//                        attributes.alpha = alpha;
//                        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//                        window.setAttributes(attributes);
//                        if (alpha <= 0.5) {
//                            timer.cancel();
//                        }
//                    }
//                });
//            }
//        };
//        timer.schedule(runnable, 0, 10);
        return window;
    }

    /**
     * @param context
     * @param cover            蒙板
     * @param typeStr          表示车次类型的字符串
     * @param offTimes         表示出发时间的数字集合
     * @param station_selected 表示已选择出发到达车站的集合
     * @param stations_all     表示出发到达车站的集合
     * @param listener         对于点击确定按钮的监听
     */
    private static String typeStr_copy;

    public static void showFilterPop(Context context, final String typeStr, List<Integer> offTimes,
                                     final List<String> station_selected,
                                     final List<String> stations_all, final FilterPopListener listener) {
        View contentView = View.inflate(context, R.layout.layout_filter_pp, null);

        final List<Integer> offTimes_copy = new ArrayList<>();

        if (offTimes.size() != 4) {
            offTimes_copy.addAll(offTimes);
        }

        final List<String> station_selected_copy = new ArrayList<>();
        if (station_selected.size() != stations_all.size())
            station_selected_copy.addAll(station_selected);

        if (typeStr.length() < Constant.TrainType.TRAIN_TYPE_ALL.length()) {
            typeStr_copy = typeStr;
        } else {
            typeStr_copy = "";
        }

        TextView title = (TextView) contentView.findViewById(R.id.pop_title);
        MyGridView gv = (MyGridView) contentView.findViewById(R.id.filter_pp_gv);
        final MyCheckbox typeBox0 = (MyCheckbox) contentView.findViewById(R.id.filter_pp_type0);
        final MyCheckbox typeBox1 = (MyCheckbox) contentView.findViewById(R.id.filter_pp_type1);
        final MyCheckbox typeBox2 = (MyCheckbox) contentView.findViewById(R.id.filter_pp_type2);
        final MyCheckbox typeBox3 = (MyCheckbox) contentView.findViewById(R.id.filter_pp_type3);
        final MyCheckbox offBox0 = (MyCheckbox) contentView.findViewById(R.id.filter_pp_offtime0);
        final MyCheckbox offBox1 = (MyCheckbox) contentView.findViewById(R.id.filter_pp_offtime1);
        final MyCheckbox offBox2 = (MyCheckbox) contentView.findViewById(R.id.filter_pp_offtime2);
        final MyCheckbox offBox3 = (MyCheckbox) contentView.findViewById(R.id.filter_pp_offtime3);
        View sure = contentView.findViewById(R.id.pop_sure);
        View cancle = contentView.findViewById(R.id.pop_cancle);
        title.setText("筛选");
        MyCheckbox.OnClickListener mclistener = new MyCheckbox.OnClickListener() {
            @Override
            public void onClick(View v, int position) {
                boolean checked = ((MyCheckbox) v).isChecked();
                switch (v.getId()) {
                    case R.id.filter_pp_type0:

                        if (!typeStr_copy.contains("GC")) {
                            typeStr_copy += "GC";
                        } else {
                            typeStr_copy = typeStr_copy.replace("GC", "");
                        }
                        break;
                    case R.id.filter_pp_type1:
                        if (!typeStr_copy.contains("D")) {
                            typeStr_copy += "D";
                        } else {
                            typeStr_copy = typeStr_copy.replace("D", "");
                        }
                        break;
                    case R.id.filter_pp_type2:
                        if (!typeStr_copy.contains("ZTK")) {
                            typeStr_copy += "ZTK";
                        } else {
                            typeStr_copy = typeStr_copy.replace("ZTK", "");
                        }
                        break;
                    case R.id.filter_pp_type3:
                        if (!typeStr_copy.contains("YL0123456789")) {
                            typeStr_copy += "YL0123456789";
                        } else {
                            typeStr_copy = typeStr_copy.replace("YL0123456789", "");
                        }
                        break;
                    case R.id.filter_pp_offtime0:
                        if (offTimes_copy.contains(0)) {
                            offTimes_copy.remove(offTimes_copy.indexOf(0));
                        } else {
                            offTimes_copy.add(0);
                        }
                        break;
                    case R.id.filter_pp_offtime1:
                        if (offTimes_copy.contains(1)) {
                            offTimes_copy.remove(offTimes_copy.indexOf(1));
                        } else {
                            offTimes_copy.add(1);
                        }
                        break;
                    case R.id.filter_pp_offtime2:
                        if (offTimes_copy.contains(2)) {
                            offTimes_copy.remove(offTimes_copy.indexOf(2));
                        } else {
                            offTimes_copy.add(2);
                        }
                        break;
                    case R.id.filter_pp_offtime3:
                        if (offTimes_copy.contains(3)) {
                            offTimes_copy.remove(offTimes_copy.indexOf(3));
                        } else {
                            offTimes_copy.add(3);
                        }
                        break;
                }
            }
        };
        typeBox0.setOnclickListener(mclistener);
        typeBox1.setOnclickListener(mclistener);
        typeBox2.setOnclickListener(mclistener);
        typeBox3.setOnclickListener(mclistener);
        offBox0.setOnclickListener(mclistener);
        offBox1.setOnclickListener(mclistener);
        offBox2.setOnclickListener(mclistener);
        offBox3.setOnclickListener(mclistener);
        if (typeStr_copy.contains("G")) typeBox0.setChecked(true);
        if (typeStr_copy.contains("D")) typeBox1.setChecked(true);
        if (typeStr_copy.contains("K")) typeBox2.setChecked(true);
        if (typeStr_copy.contains("L")) typeBox3.setChecked(true);
        if (offTimes_copy.contains(0)) offBox0.setChecked(true);
        if (offTimes_copy.contains(1)) offBox1.setChecked(true);
        if (offTimes_copy.contains(2)) offBox2.setChecked(true);
        if (offTimes_copy.contains(3)) offBox3.setChecked(true);

        /////////////////////////////////

        final FilterStationAdapter adapter = new FilterStationAdapter(context, stations_all, station_selected_copy);
        gv.setAdapter(adapter);

//        //////////////如果默认条件为全部车站，则全不选/////////////////
//        if (station_selected_copy.size() == stations_all.size()) {
//            station_selected_copy.clear();
//        }
//        adapter.notifyDataSetChanged();
        ////////////////////////////////

        adapter.setListener(new MyCheckbox.OnClickListener() {
            @Override
            public void onClick(View v, int position) {
                String s = stations_all.get(position);
                if (station_selected_copy.contains(s)) {
                    station_selected_copy.remove(s);
                } else {
                    station_selected_copy.add(s);
                }
                adapter.notifyDataSetChanged();
            }
        });
        final MyCustomDialog dialog = new MyCustomDialog(contentView, null);
        dialog.show();
        final Window window = dimWindows(context);
        dialog.setDismissListener(new MyCustomDialog.OndismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.alpha = 1;
                window.setAttributes(attributes);
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 如果某一项全为空，则为全选的状态
                 */
                if (!typeBox0.isChecked() && !typeBox1.isChecked() && !typeBox2.isChecked() && !typeBox3.isChecked()) {
                    typeStr_copy = Constant.TrainType.TRAIN_TYPE_ALL;
                }
                if (!offBox0.isChecked() && !offBox1.isChecked() && !offBox2.isChecked() && !offBox3.isChecked()) {
                    offTimes_copy.clear();
                    offTimes_copy.add(0);
                    offTimes_copy.add(1);
                    offTimes_copy.add(2);
                    offTimes_copy.add(3);
                }
                if (station_selected_copy.size() == 0) {
                    station_selected_copy.addAll(stations_all);
                }


                listener.onSureClick(typeStr_copy, offTimes_copy, station_selected_copy);

                dialog.dismiss();
            }
        });
    }

    public static void showTimeDialog(Context context, TimeAdapter adapter) {
        final MDialog dialog = new MDialog(context, R.layout.dialog_time, R.style.Dialog);
        View close = dialog.findViewById(R.id.dialog_time_close);
        ListView lv = (ListView) dialog.findViewById(R.id.dialog_time_lv);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        lv.setAdapter(adapter);
    }
}
