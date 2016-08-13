package com.zbj.zbjplugins;

import android.app.Application;

import net.wequick.small.Small;

/**
 * Created by liuhongxia on 2016/7/31.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Small.preSetUp(this);
    }
}
