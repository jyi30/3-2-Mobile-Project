package com.sample.kakao;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        KakaoSdk.init(this,"a0fe005898b3c89cdc92bd19263eff6a");

    }
}