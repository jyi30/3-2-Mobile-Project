package com.example.team_5;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Owner_qr extends AppCompatActivity {
private ImageView rightIcon;
private Button qrcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_qr);

        qrcode = findViewById(R.id.qrcode);            //qr코드 생성버튼
        rightIcon = findViewById(R.id.right_icon);
        registerForContextMenu(rightIcon);

        ImageView leftIcon = findViewById(R.id.left_icon);    //뒤로가기 버튼
        ImageView rightIcon = findViewById(R.id.right_icon);  //메뉴버튼
        TextView title = findViewById(R.id.toolbar_title);
        title.setText("QR코드 생성");   //툴바 제목



        leftIcon.setOnClickListener(new View.OnClickListener()    {                  //뒤로가기 버튼튼
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Owner_qr.this, Owner_registration.class);
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
    }//end of ContextMenu()

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.two:     //고객화면 전환
                Intent intent = new Intent(Owner_qr.this, GuestActivity.class);
                startActivity(intent);
                return true;
            case R.id.btn_logout:   //로그아웃
                return true;
        }
        return false;
    }
}