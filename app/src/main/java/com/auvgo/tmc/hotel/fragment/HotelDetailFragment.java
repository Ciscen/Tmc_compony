package com.auvgo.tmc.hotel.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auvgo.tmc.R;
import com.auvgo.tmc.views.StateView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HotelDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class HotelDetailFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    @BindView(R.id.hotel_detail_detail_wifi)
    StateView wifi;
    @BindView(R.id.hotel_detail_detail_park)
    StateView park;
    @BindView(R.id.hotel_detail_detail_package)
    StateView pkg;
    @BindView(R.id.hotel_detail_detail_meettingroom)
    StateView meetingroom;


    public HotelDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel_detail, container, false);
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

    @OnClick(R.id.hotel_detail_detail_more)
    void onMoreClick() {
        mListener.onMoreClick();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onMoreClick();
    }

    public void setContent(String facility) {
        if (facility == null) return;
        String[] facilities = facility.split(",");
        List<String> list = Arrays.asList(facilities);
        if (list.contains("1") || list.contains("2")) {
            wifi.setOn(true);
        }
        if (list.contains("5")) {
            park.setOn(true);
            park.setTextStr("免费停车");
        } else if (list.contains("6")) {
            park.setOn(true);
            park.setTextStr("收费停车");
        }

        if (list.contains("7")) {
            pkg.setOn(true);
            pkg.setTextStr("免费接机");
        } else if (list.contains("8")) {
            pkg.setOn(true);
            pkg.setTextStr("收费接机");
        }
        if (list.contains("13")) {
            meetingroom.setOn(true);
        }
    }
}
