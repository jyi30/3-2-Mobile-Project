package com.sample.kakao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Guest_menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_menu);


        ImageView rightIcon2 = findViewById(R.id.right_icon2); //상단 알림


        TextView title = findViewById(R.id.toolbar_title);
        title.setText("메뉴판");   //툴바 제목
        ImageView leftIcon = findViewById(R.id.left_icon);    //뒤로가기 버튼
        ImageView rightIcon = findViewById(R.id.right_icon);  //메뉴버튼

        leftIcon.setOnClickListener(new View.OnClickListener() {                  //뒤로가기 버튼튼
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Guest_menu.this, Guest.class);
                startActivity(intent);
            }
        });


    }
 }
