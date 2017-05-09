package com.auvgo.tmc;

import android.view.ViewGroup;

import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.plane.bean.PlanePolicyBean;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.interfaces.RetrofitServer;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.utils.DeviceUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.Url;
import com.auvgo.tmc.views.TuyaView;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class TestActivity extends BaseActivity {

    private ViewGroup ll;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ll = (ViewGroup) findViewById(R.id.test_root);
        TuyaView tuyaView = new TuyaView(this, DeviceUtils.getScreenWidth(this), DeviceUtils.getScreenHeight(this));
        ll.addView(tuyaView);
    }

    @Override
    protected void setViews() {

    }

    @Override
    protected void getData() {
        super.getData();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Url.getUrl(0))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();
        final RetrofitServer server = retrofit.create(RetrofitServer.class);
        Map<String, String> map = new HashMap<>();
        map.put("cid", String.valueOf(MyApplication.mUserInfoBean.getId()));
        System.out.println("11111");
        map.put("keyword", "");
        map.put("pagenum", 1 + "");
        map.put("pagesize", "");
        map.put("userid", String.valueOf(MyApplication.mUserInfoBean.getId()));
        Observable.just(AppUtils.getJson(map)).flatMap(new Func1<String, Observable<ResponseOuterBean>>() {
            @Override
            public Observable<ResponseOuterBean> call(String s) {
                System.out.println("flatMap-----" + Thread.currentThread().getName());
                String sign = AppUtils.getMD5(AppUtils.getMD5(Constant.APPSECRIT).toUpperCase() + s).toUpperCase();//签名
                return server.getPsgList(s, Constant.APPKEY, sign);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<ResponseOuterBean>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted------" + Thread.currentThread().getName());

            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError------" + e.toString());

            }

            @Override
            public void onNext(ResponseOuterBean responseOuterBean) {
                String data = responseOuterBean.getData();
                System.out.println(data);
            }
        });

    }
}
