package com.auvgo.tmc.hotel.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.auvgo.tmc.R;
import com.auvgo.tmc.hotel.bean.HotelDetailBean;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HotelDetailReviewFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    @BindView(R.id.hotel_detail_review_goodRate)
    TextView goodRate;
    @BindView(R.id.hotel_detail_review_goodCount)
    TextView goodCount;
    @BindView(R.id.hotel_detail_review_count)
    TextView count;
    @BindView(R.id.hotel_detail_review_badCount)
    TextView badCount;


    public HotelDetailReviewFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static HotelDetailReviewFragment newInstance() {
        HotelDetailReviewFragment fragment = new HotelDetailReviewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel_detail_review, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }

    public void setContent(HotelDetailBean.ReviewBean reviewBean) {
        goodRate.setText(reviewBean.getScore());
        goodCount.setText(String.format(Locale.CHINESE, "%d条", reviewBean.getGood()));
        badCount.setText(String.format(Locale.CHINESE, "%d条", reviewBean.getPoor()));
        count.setText(String.format(Locale.CHINESE, "%d条", reviewBean.getCount()));
    }
}
