package com.example.team_5;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        KakaoSdk.init(this,"d8f303c3b6e638f4ea2d4429e7469663");

    }
}