package com.auvgo.tmc.common.interfaces;

import android.os.Parcelable;

/**
 * Created by lc on 2017/3/29
 */

public interface IApplyChose extends Parcelable {
    String getReason();

    String getNames();

    String getDate();

    String getNo();
}
