package com.sample.kakao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Guest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);


        ImageView leftIcon = findViewById(R.id.left_icon);    //뒤로가기 버튼
        ImageView rightIcon = findViewById(R.id.right_icon);  //메뉴버튼
        TextView title = findViewById(R.id.toolbar_title);
        title.setText("QR코드 스캔");   //툴바 제목



        leftIcon.setOnClickListener(new View.OnClickListener()    {                  //선택 화면으로 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Guest.this, Choice.class);
                startActivity(intent);
            }
        });


    }
}