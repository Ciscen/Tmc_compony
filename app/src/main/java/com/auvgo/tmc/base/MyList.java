package com.auvgo.tmc.base;

import com.auvgo.tmc.train.bean.ComSettingBean;

import java.util.ArrayList;

/**
 * Created by lc on 2016/11/21
 */

public class MyList<T> extends ArrayList<T>{
    public String get(String key) {
        for (int i = 0; i < size(); i++) {
            if (((ComSettingBean.ProductSetBean.ProconfvalueBean) get(i)).getName().equals(key)) {
                return ((ComSettingBean.ProductSetBean.ProconfvalueBean) get(i)).getValue();
            }
        }
        return null;
    }
}
