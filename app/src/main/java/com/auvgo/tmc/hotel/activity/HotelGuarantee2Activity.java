package com.auvgo.tmc.hotel.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.base.BaseActivity;
import com.auvgo.tmc.home.HomeActivity;
import com.auvgo.tmc.hotel.bean.RequestGuaranteeBean;
import com.auvgo.tmc.personalcenter.activity.OrderListActivity;
import com.auvgo.tmc.train.bean.ResponseOuterBean;
import com.auvgo.tmc.train.bean.SelectionBean;
import com.auvgo.tmc.utils.AppUtils;
import com.auvgo.tmc.constants.Constant;
import com.auvgo.tmc.utils.DialogUtil;
import com.auvgo.tmc.utils.MUtils;
import com.auvgo.tmc.utils.RetrofitUtil;
import com.auvgo.tmc.utils.ToastUtils;
import com.auvgo.tmc.views.ItemView;
import com.auvgo.tmc.views.MyDialog;
import com.auvgo.tmc.views.MyPickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HotelGuarantee2Activity extends BaseActivity {

    @BindView(R.id.guarantee2_card_number)
    ItemView card_num;
    @BindView(R.id.guarantee2_name)
    ItemView name;
    @BindView(R.id.guarantee2_id_num)
    TextView id_num;
    @BindView(R.id.guarantee2_id_tv)
    TextView id_type;
    @BindView(R.id.guarantee2_valid_date)
    ItemView valid_date;
    @BindView(R.id.guarantee2_cvv)
    ItemView cvv;
    @BindView(R.id.guarantee2_cvv_rl)
    View cvv_rl;
    private String orderNo;
    private String card_num_str;
    private boolean isCvv;
    private String idType = "IdentityCard";
    private List<SelectionBean> list;
    private int mCardTypePosition;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hotel_guarantee2;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        orderNo = getIntent().getStringExtra("orderNo");
        card_num_str = getIntent().getStringExtra("cardNo");
        isCvv = getIntent().getBooleanExtra("isCvv", false);
        list = getList();
    }

    private List<SelectionBean> getList() {
        List<SelectionBean> l = new ArrayList<>();
        SelectionBean sb1 = new SelectionBean("身份证", "IdentityCard");
        SelectionBean sb2 = new SelectionBean("护照", "Passport");
        SelectionBean sb3 = new SelectionBean("其他", "Other");
        l.add(sb1);
        l.add(sb2);
        l.add(sb3);
        return l;
    }

    @Override
    protected void initView() {
        if (!isCvv) {
            cvv_rl.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setViews() {

    }

    @OnClick({R.id.guarantee2_commit, R.id.guarantee2_id_tv,
            R.id.guarantee2_valid_wenhao, R.id.guarantee2_valid_wenhao2})
    void onCommit(View view) {
        switch (view.getId()) {
            case R.id.guarantee2_commit:
                gatherData();
                break;
            case R.id.guarantee2_valid_wenhao:
                MUtils.jumpToPage(this, HotelCreditCardActivity.class, -1);
                break;
            case R.id.guarantee2_id_tv:
                DialogUtil.showExpandablePickDialog(this, "证件类型", mCardTypePosition, list,
                        new MyPickerView.OnPickerViewSure() {
                            @Override
                            public void onSingleSureClick(int p) {
                                mCardTypePosition = p;
                                idType = list.get(p).getExtra();
                                id_type.setText(list.get(p).getName());
                            }

                            @Override
                            public void onMultiSureClick(List<Integer> s) {

                            }
                        });
                break;
            case R.id.guarantee2_valid_wenhao2:
                Intent intent = new Intent(this, HotelCreditCardActivity.class);
                intent.putExtra("isCvv", true);
                startActivity(intent);
                break;
        }

    }

    private void gatherData() {
        String name_str = name.getContent();
        String id_num_str = id_num.getText().toString();
        String valid_date_str = valid_date.getContent();
        if (valid_date_str.length() != 5) {
            ToastUtils.showTextToast("请输入正确的有效期");
            return;
        }
        final RequestGuaranteeBean rgb = new RequestGuaranteeBean();
//        rgb.setCardno(card_num_str);
        rgb.setCardno("4033910000000000");
        rgb.setCvv(isCvv ? cvv.getContent() : "");
        rgb.setHolderName(name_str);
        rgb.setIdtype(idType);
        rgb.setExpiration(20 + valid_date_str.substring(0, 2) + valid_date_str.substring(valid_date_str.length() - 2));
        rgb.setOrderno(orderNo);
        rgb.setIdno(id_num_str);
        DialogUtil.showDialog(this, "提示", "取消", "确定", "确定担保吗？", new MyDialog.OnButtonClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                commit(rgb);
            }
        });
    }

    private void commit(RequestGuaranteeBean rgb) {
        RetrofitUtil.guaranteeHotel(this, AppUtils.getJson(rgb), null, new RetrofitUtil.OnResponse() {
            @Override
            public boolean onSuccess(ResponseOuterBean bean, int status, String msg, Object o) {
                if (status == 200) {
                    showDialog("担保成功");
                } else {
                    showDialog(msg);
                    return true;
                }
                return false;
            }

            @Override
            public boolean onFailed(Throwable e) {
                return false;
            }
        });
    }

    private void showDialog(String msg) {
        DialogUtil.showDialog(HotelGuarantee2Activity.this, "提示", "确定", "", msg, new MyDialog.OnButtonClickListener() {
            @Override
            public void onLeftClick() {
                jumpToOrderList(Constant.HOTEL);
            }

            @Override
            public void onRightClick() {

            }
        });
    }
}
