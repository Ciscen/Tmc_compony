package com.auvgo.tmc.common.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.auvgo.tmc.R;

public class GuideFragment extends Fragment {

    private OnGuideFragmentButtonListener mListener;
    private int index;
    private View bt;
    private ImageView iv;
    private int[] imgs = {R.mipmap.guide1, R.mipmap.guide2, R.mipmap.guide3, R.mipmap.guide4};

    public GuideFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        iv = (ImageView) view.findViewById(R.id.guide_iv);
        bt = view.findViewById(R.id.guide_button);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        index = getArguments().getInt("index");
        initListener();
        initImage();
    }

    private void initImage() {
        iv.setImageResource(imgs[index]);
    }

    private void initListener() {
        if (index == 3) {
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonPressed();
                }
            });
        }
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGuideFragmentButtonListener) {
            mListener = (OnGuideFragmentButtonListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnGuideFragmentButtonListener {
        void onFragmentInteraction();
    }
}
