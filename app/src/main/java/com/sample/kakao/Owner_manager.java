package com.sample.kakao;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Owner_manager extends AppCompatActivity {

    private Button btn_menu, btn_qr, btn_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_manager);


        ImageView leftIcon = findViewById(R.id.left_icon);    //상단 뒤로가기 버튼
        ImageView rightIcon = findViewById(R.id.right_icon);  //상단 메뉴버튼
        ImageView rightIcon2 = findViewById(R.id.right_icon2); //상단 알림

        btn_menu = findViewById(R.id.btn_menu);                            //메뉴관리 버튼
        btn_qr = findViewById(R.id.btn_qr);                                //QR코드확인 버튼
        btn_order = findViewById(R.id.btn_order);                          //주문관리 버튼
        TextView title = findViewById(R.id.toolbar_title);
        title.setText("관리자 화면");   //툴바 제목


        btn_menu.setOnClickListener(new View.OnClickListener() {           //메뉴관리 화면으로 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Owner_manager.this, Owner_menu.class);
                startActivity(intent);
            }
        });

        btn_order.setOnClickListener(new View.OnClickListener() {           //주문목록 화면으로 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Owner_manager.this, Owner_oderlist.class);
                startActivity(intent);
            }
        });

        btn_qr.setOnClickListener(new View.OnClickListener() {           //QR 코드 확인 화면으로 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Owner_manager.this, Owner_qr.class);
                startActivity(intent);
            }
        });

        leftIcon.setOnClickListener(new View.OnClickListener()    {                  //선택 화면으로 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Owner_manager.this, Choice.class);
                startActivity(intent);
            }
        });

        rightIcon2.setOnClickListener(new View.OnClickListener()    {                  // 상단 메뉴 설정
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });


        rightIcon.setOnClickListener(new View.OnClickListener()    {                  // 상단메뉴 (화면전환, 로그아웃 )
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });
    }
    private void showMenu(View v){                                                 // 상단메뉴 (화면전환, 로그아웃 )
        PopupMenu popupMenu = new PopupMenu(Owner_manager.this,v);
        popupMenu.getMenuInflater().inflate(R.menu.main_menu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.one) {
                    Intent intent = new Intent(Owner_manager.this, Owner_manager.class);
                    startActivity(intent); }
                if(item.getItemId() == R.id.two) {
                    Intent intent = new Intent(Owner_manager.this,Guest.class);
                    startActivity(intent); }
                if(item.getItemId() == R.id.btn_logout)
                {
                    //로그아웃 구현
                    Intent intent = new Intent(Owner_manager.this,MainActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        popupMenu.show();
    }

}