package com.auvgo.tmc.plane.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.auvgo.tmc.MyApplication;
import com.auvgo.tmc.R;
import com.auvgo.tmc.adapter.PlaneDetailAdapter;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.plane.bean.PlaneListBean;
import com.auvgo.tmc.plane.bean.PlanePolicyBean;
import com.auvgo.tmc.plane.bean.PlaneRouteBeanWF;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.TimeUtils;
import com.auvgo.tmc.views.NoticeDialog;
import com.auvgo.tmc.views.PlaneDetailCardView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaneDetailActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private PlaneDetailCardView cv;
    private ListView lv;
    private ArrayList<PlaneListBean> list_all;
    private PlaneListBean mBean;
    private PlaneDetailAdapter adapter;
    private PlaneRouteBeanWF firstRoute;
    private boolean isReturn = false;
    private ArrayList<PlaneListBean> recommentList;
    private boolean isAlter;//是否是改签的流程

    @Override
    protected int getLayoutId() {
        return R.layout.activity_plane_detail;
    }

    @Override
    protected void initData() {
        mBean = (PlaneListBean) getIntent().getSerializableExtra("bean");
        // list_all = (ArrayList<PlaneListBean>) getIntent().getSerializableExtra("recommentList");
        adapter = new PlaneDetailAdapter(this, mBean);
        isReturn = getIntent().getBooleanExtra("isReturn", false);
        isAlter = getIntent().getBooleanExtra("isAlter", false);
        if (isReturn)
            firstRoute = (PlaneRouteBeanWF) getIntent().getSerializableExtra("firstRoute");
    }

    @Override
    protected void findViews() {
        cv = (PlaneDetailCardView) findViewById(R.id.plane_detail_cardview);
        lv = (ListView) findViewById(R.id.plane_detail_lv);
    }

    @Override
    protected void initView() {
        lv.setFocusable(false);
        cv.setAirline(mBean.getCarriername() + mBean.getAirline() + "|" +
                mBean.getPlanestyle());
        cv.setStart_date(TimeUtils.getMMdd(mBean.getDeptdate()) + " " +
                TimeUtils.getTomorrowWeekDay(mBean.getDeptdate()));
        cv.setEnd_date(TimeUtils.getMMdd(mBean.getArridate()) + " " +
                TimeUtils.getTomorrowWeekDay(mBean.getArridate()));
        cv.setStart_time(mBean.getDepttime());
        cv.setEnd_time(mBean.getArritime());
        cv.setOrgname(mBean.getOrgname() + mBean.getDeptterm());
        cv.setArriname(mBean.getArriname() + mBean.getArriterm());
        cv.setJijian("￥" + mBean.getAirporttax());
        cv.setRun_time2(mBean.getFlytime());
        cv.setFood2(mBean.isMeal() ? "有餐" : "无餐");
        lv.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        lv.setOnItemClickListener(this);
    }

    @Override
    protected void setViews() {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        doNext(position);
    }

    /**
     * @param position
     */
    private void doNext(final int position) {
        final boolean wei = mBean.getCangweis().get(position).isWei();
        if (!wei && !isSetNhour()) {// 如果没有违规且没有设置两小时最低价
            jumpToNext(position);
        } else {//如果违规了，或者设置最低价了
            if (isSetNhour()) {//如果设置了最n小时低价的限制
                Map<String, String> map = new HashMap<>();
                map.put("from", isReturn ? MyApplication.toCityCode : MyApplication.fromCityCode);
                map.put("to", isReturn ? MyApplication.fromCityCode : MyApplication.toCityCode);
                map.put("startdate", mBean.getDeptdate());
                map.put("airline", mBean.getAirline());
                map.put("hour", String.valueOf(MyApplication.mPlanePolicy == null ? 0 : MyApplication.mPlanePolicy.getFlighthour()));
                map.put("price", String.valueOf(mBean.getCangweis().get(position).getPrice()));

                RetrofitUtil.getNHourLowest(this, AppUtils.getJson(map), null, new RetrofitUtil.OnResponse() {
                    @Override
                    public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                        if (status == 200) {
                            Type token = new TypeToken<List<PlaneListBean>>() {
                            }.getType();
                            recommentList = new Gson().fromJson(bean.getData(), token);
                            int savePrice = checkIsLow(recommentList, mBean.getCangweis().get(position).getPrice());
                            String saveMoney = savePrice > 0 ? savePrice + "" : "";
                            if (MyApplication.mPlanePolicy == null) {
                                jumpToNext(position);
                                return true;
                            }
                            if (savePrice < 0) {//如果是最低价
                                if (wei) {//是最低价但是违规
                                    gatherWeiStr(position, "");
                                } else {//是最低价且不违规
                                    jumpToNext(position);
                                }
                            } else {//如果不是最低价
//                                if (wei) {//不是最低价，且违规
//                                    gatherWeiStr(position, saveMoney);
//                                } else {//不是最低价，且不违规
                                gatherWeiStr(position, saveMoney);
//                                }
                            }
                        }
                        return false;
                    }

                    @Override
                    public boolean onFailed(Throwable e) {
                        return false;
                    }
                });
            } else {
                gatherWeiStr(position, "");
            }
        }
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

    private void gatherWeiStr(int position, String s) {
        PlanePolicyBean mPlanePolicy = MyApplication.mPlanePolicy;

        if (mPlanePolicy.getController() == 0) {
            DialogUtil.showDialog(this, "提示", "确定", "", "不允许预订该违背航班", null);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("您选择的航班违背了“");
        sb.append(mBean.getWeiItemStr());
        if (mPlanePolicy.getCabinlimit() == 1) {
            List<PlaneListBean.CangweisBean> cangweis = mBean.getCangweis();
            int cabinzhekou = mPlanePolicy.getCabinzhekou();
            if (cangweis.get(position).getDiscount() > cabinzhekou) {
                sb.append("不得高于").append(cabinzhekou).append("折、");
            }
        }
        boolean b = MUtils.isLowestOfThisLine(mBean, position);
        if (mPlanePolicy.getFlightlimit() == 1) {
            switch (mPlanePolicy.getFlightlowtype()) {
                case 0:
                    if (!b) sb.append("航班最低价、");
                    break;
                case 1://全天最低价  已经判断过了，但是没有具体到每个舱位
                    if (!b && !sb.toString().contains("全天")) sb.append("全天最低价、");
                    break;
                case 2:
                    if (!b) sb.append("前后").append(mPlanePolicy.getFlighthour()).append("小时最低价、");
                    break;
            }
        }
        String s1 = sb.toString();
        if (s1.endsWith("、")) {
            sb.deleteCharAt(s1.length() - 1);
        }
        sb.append("”的差旅标准，是否继续预订？");
        showPop(position, sb, s);
    }

    //是否有前后两小时最低价的限制
    private boolean isSetNhour() {
        PlanePolicyBean mPlanePolicy = MyApplication.mPlanePolicy;
        return mPlanePolicy != null && mPlanePolicy.getFlightlimit() == 1 && mPlanePolicy.getFlightlowtype() == 2;
    }

    private void showPop(final int position, StringBuilder sb, String saveMoney) {
        new NoticeDialog(this, sb.toString(), saveMoney, new NoticeDialog.OnButtonClickListener() {
            @Override
            public void onLeftClick() {
                jumpToNext(position);
            }

            @Override
            public void onRightClick() {
                Intent intent = new Intent(PlaneDetailActivity.this, RecommentPlaneActivity.class);
                intent.putExtra("list", recommentList);
                startActivity(intent);
            }
        }).show();
    }

    private void jumpToNext(int position) {
        Intent intent = new Intent();
        PlaneRouteBeanWF b = new PlaneRouteBeanWF();
        b.setBean(mBean);
        b.setCangwei(position);
        if (MyApplication.isWF) {
            if (!isReturn) {
                intent.putExtra("isReturn", true);
                intent.putExtra("firstRoute", b);
                intent.putExtra("fromCode", MyApplication.toCityCode);
                intent.putExtra("toCode", MyApplication.fromCityCode);
                intent.putExtra("fromName", MyApplication.toCityName);
                intent.putExtra("toName", MyApplication.fromCityName);
                intent.putExtra("returnDate", MyApplication.returnDate);
//                intent.putExtra("startDate", mBean.getDeptdate());
                intent.putExtra("startDate", MyApplication.returnDate);
                MyApplication.firstRoute = b;
                intent.setClass(PlaneDetailActivity.this, PlaneListActivity.class);
            } else {
                intent.putExtra("firstRoute", firstRoute);
                intent.putExtra("secondRoute", b);
                intent.setClass(PlaneDetailActivity.this, PlaneBookActivity.class);
            }
        } else {
            intent.putExtra("firstRoute", b);
            if (isAlter) {
                intent.setClass(PlaneDetailActivity.this, PlaneAlterConfirmActivity.class);
            } else {
                intent.setClass(PlaneDetailActivity.this, PlaneBookActivity.class);
            }
        }
        startActivity(intent);
    }

    private boolean isWei(int position) {
        PlanePolicyBean mPlanePolicy = MyApplication.mPlanePolicy;
        return mPlanePolicy != null &&
                (mBean.isAirlineWei() ||
                        !MUtils.matchCount(mBean) ||
                        !MUtils.isLowestOfThisLine(mBean, position));
    }
}
