package com.sample.kakao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class MainActivity extends AppCompatActivity {
    private static final String TAG= "MainActivity";


    private View btn_logIn,logoutButton, btn_next, btn_logout ;
    private TextView nickName;
    private ImageView proflieImage;

    // https://www.youtube.com/watch?v=8hKczn0-Hkw
    // 위의 링크를 참고하여 만들었습니다.
    // 코드를 보시고 이해가 안되신다면 위 링크를 확인 부탁드립니다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_logIn = findViewById(R.id.login); //로그인 버튼 아이디
        logoutButton = findViewById(R.id.logout); // 로그아웃 버튼 아이디
        btn_logout = findViewById(R.id.logout);
        btn_next = findViewById(R.id.btn_next); // 다음페이지
        nickName = findViewById(R.id.nickName); // 프로필 네임 아이디
        proflieImage = findViewById(R.id.profileImage); // 프로필 이미지 아이디

        Function2<OAuthToken, Throwable, Unit> callback = new  Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if (oAuthToken != null) {
                    //TBO
                }
                if (throwable != null) {
                    //TBO
                }
                updateKakaoLoginUi();
                return null;
            }
        };
        btn_next.setOnClickListener(new View.OnClickListener() {           //선택화면으로 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Choice.class);
                startActivity(intent);
            }
        });

        // 카카오톡 설치 유무 확인 후 설치인 경우 카카오톡으로, 미설치인 경우 웹으로 로그인화면을 띄움
       btn_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(MainActivity.this)){
                    UserApiClient.getInstance().loginWithKakaoTalk(MainActivity.this,callback);
                }else {
                    UserApiClient.getInstance().loginWithKakaoAccount(MainActivity.this, callback);
                }
            }
        });



        logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        updateKakaoLoginUi();
                        return null;
                    }
                });
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        updateKakaoLoginUi();
                        return null;
                    }
                });
            }
        });

        updateKakaoLoginUi();

    }
    // 로그인이 되어있다면 프로필사진과 닉네임표시
    // 카카오톡 계정으로 로그인이 되어있는지 확인
    // 로그인이 안되어있다면 로그인 버튼 표시
    private void updateKakaoLoginUi(){
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if(user != null){
                    //로그인상태 확인 후 로그인이 되어있다면 정보들을 가져옴(아이디 이메일 등등)
                    Log.i(TAG, "invoke: id=" + user.getId());
                    Log.i(TAG, "invoke: email=" + user.getKakaoAccount().getEmail());
                    Log.i(TAG, "invoke: nickname=" + user.getKakaoAccount().getProfile().getNickname());
                    Log.i(TAG, "invoke: gender=" + user.getKakaoAccount().getGender());
                    Log.i(TAG, "invoke: age=" + user.getKakaoAccount().getAgeRange());

                    //닉네임표시
                    nickName.setText(user.getKakaoAccount().getProfile().getNickname());
                    //프로필사진을 동그랗게 짤라서 보여주기
                    Glide.with(proflieImage).load(user.getKakaoAccount().getProfile().getThumbnailImageUrl()).circleCrop().into(proflieImage);


                    //로그인되어있다면 로그아웃버튼만 활성화
                    btn_logIn.setVisibility(View.GONE);
                    logoutButton.setVisibility(View.VISIBLE);
                    btn_next.setVisibility(View.VISIBLE);

                }else{ //로그인이 안되있다면 로그인버튼만 활성화
                    nickName.setText(null);
                    proflieImage.setImageBitmap(null);
                    btn_logIn.setVisibility(View.VISIBLE);
                    logoutButton.setVisibility(View.GONE);
                    btn_next.setVisibility(View.GONE);

                }
                return null;
            }
        });
    }
}