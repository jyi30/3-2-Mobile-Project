package com.example.team_5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

public class Owner_manager extends AppCompatActivity {

    private ImageView rightIcon, rightIcon2;
    private Button btn_menu, btn_qr, btn_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_manager);

        rightIcon = findViewById(R.id.right_icon);
        rightIcon2 = findViewById(R.id.right_icon2);
        registerForContextMenu(rightIcon);
        registerForContextMenu(rightIcon2);
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
                Intent intent = new Intent(Owner_manager.this, Owner_registration.class);
                startActivity(intent);
            }
        });

        leftIcon.setOnClickListener(new View.OnClickListener() {                  //선택 화면으로 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Owner_manager.this, Choice.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,              //상단 메뉴
                                    ContextMenu.ContextMenuInfo menuInfo) {
        //view는 롱클릭한 위젯
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mi = getMenuInflater();
        if(v == rightIcon){
            mi.inflate(R.menu.main_menu1, menu);
        }
        if (v == rightIcon2){
            mi.inflate(R.menu.menu_call, menu);
        }
    }//end of ContextMenu()

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.two:     //고객화면 전환
                Intent intent = new Intent(Owner_manager.this, Guest.class);
                startActivity(intent);
                return true;
            case R.id.btn_logout:   //로그아웃
                return true;
            case R.id.call:     //알림
                return true;
            case R.id.call1:    //알림
                return true;
            case R.id.call2:    //알림
                return true;
        }
        return false;
    }
}