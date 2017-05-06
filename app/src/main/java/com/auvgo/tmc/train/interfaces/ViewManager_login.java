package com.auvgo.tmc.train.interfaces;

/**
 * Created by admin on 2016/11/8.
 */

public interface ViewManager_login {
    String getUserName();

    String getPsw();

    String getCardNum();

    boolean isSavePsw();
    boolean isAutoLogin();

    boolean isSaveUserName();
}
