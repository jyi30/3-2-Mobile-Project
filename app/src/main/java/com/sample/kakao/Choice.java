package com.sample.kakao;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

import static android.content.ContentValues.TAG;

public class Choice extends AppCompatActivity {
    private Button btn_owner, btn_client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        btn_owner = findViewById(R.id.btn_owner);     //점주버튼
        btn_client = findViewById(R.id.btn_client);    //고객버튼
        ImageView leftIcon = findViewById(R.id.left_icon);    //뒤로가기 버튼
        ImageView rightIcon = findViewById(R.id.right_icon);  //메뉴버튼



        btn_owner.setOnClickListener(new View.OnClickListener() {           //점주 화면으로 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choice.this, Owner_manager.class);
                startActivity(intent);
            }
        });
        btn_client.setOnClickListener(new View.OnClickListener() {           //고객 화면으로 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choice.this, Guest.class);
                startActivity(intent);
            }
        });

        leftIcon.setOnClickListener(new View.OnClickListener()    {                  //뒤로가기 버튼튼
           @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choice.this, MainActivity.class);
                startActivity(intent);
            }
        });
        rightIcon.setOnClickListener(new View.OnClickListener()    {                  // 상단메뉴 (화면전환, 로그아웃 )
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });
        TextView title = findViewById(R.id.toolbar_title);
        title.setText("선택 화면");   //툴바 제목
    }

    private void showMenu(View v){                                              // 상단메뉴 (화면전환, 로그아웃 )
        PopupMenu popupMenu = new PopupMenu(Choice.this,v);
        popupMenu.getMenuInflater().inflate(R.menu.main_menu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.one) {
                Intent intent = new Intent(Choice.this, Owner_manager.class);
                startActivity(intent); }
                if(item.getItemId() == R.id.two) {
                    Intent intent = new Intent(Choice.this,Guest.class);
                    startActivity(intent); }
                if(item.getItemId() == R.id.btn_logout)
                {
                    //로그아웃 구현
                    Intent intent = new Intent(Choice.this,MainActivity.class);
                    startActivity(intent);
//                    updateKakaoLoginUi();

                }
                return false;
            }
        });
        popupMenu.show();
    }
//
//    private void updateKakaoLoginUi(){
//        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
//            @Override
//            public Unit invoke(User user, Throwable throwable) {
//                if(user != null){
//                    //로그인상태 확인 후 로그인이 되어있다면 정보들을 가져옴(아이디 이메일 등등)
//                    Log.i(TAG, "invoke: id=" + user.getId());
//                    Log.i(TAG, "invoke: email=" + user.getKakaoAccount().getEmail());
//                    Log.i(TAG, "invoke: nickname=" + user.getKakaoAccount().getProfile().getNickname());
//                    Log.i(TAG, "invoke: gender=" + user.getKakaoAccount().getGender());
//                    Log.i(TAG, "invoke: age=" + user.getKakaoAccount().getAgeRange());
//
//                    Log.e("logout ", "22222222222222222222");
//                    //로그인되어있다면 로그아웃버튼만 활성화
//
//
//                }else{ //로그인이 안되있다면 로그인버튼만 활성화
//                    Log.e("logout ", "로그아웃 동작");
//
//                }
//                return null;
//            }
//        });
//    }

}