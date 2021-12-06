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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Choice extends AppCompatActivity {
    private Button btn_owner, btn_client;
    private ImageView rightIcon,rightIcon2;
    private Long uid; //user ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        uid = getIntent().getLongExtra("uid",0); //user id
        if(uid != null && uid != 0) {
            rightIcon = findViewById(R.id.right_icon);
            registerForContextMenu(rightIcon);

            btn_owner = findViewById(R.id.btn_owner);     //점주버튼
            btn_client = findViewById(R.id.btn_client);    //고객버튼
            ImageView leftIcon = findViewById(R.id.left_icon);    //뒤로가기 버튼
            ImageView rightIcon = findViewById(R.id.right_icon);  //메뉴버튼

            btn_owner.setOnClickListener(new View.OnClickListener() {           //점주 화면으로 이동
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Choice.this, Owner_manager.class);
                    intent.putExtra("uid", uid); //user id 받아오기
                    startActivity(intent);
                }
            });
            btn_client.setOnClickListener(new View.OnClickListener() {           //고객 화면으로 이동
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Choice.this, GuestActivity.class);
                    intent.putExtra("uid", uid); //user id 받아오기
                    startActivity(intent);
                }
            });

            leftIcon.setOnClickListener(new View.OnClickListener() {                  //뒤로가기 버튼튼
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(Choice.this, MainActivity.class);
//                    startActivity(intent);
                    finish();
                }
            });
            TextView title = findViewById(R.id.toolbar_title);
            title.setText("선택 화면");   //툴바 제목
        }else{
            Toast.makeText(getApplicationContext(),"로그인 오류" + uid,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,              //상단 메뉴
                                    ContextMenu.ContextMenuInfo menuInfo) {
        //view는 롱클릭한 위젯
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mi = getMenuInflater();
        if(v == rightIcon){
            mi.inflate(R.menu.main_menu2, menu);
        }
    }//end of ContextMenu()

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.btn_logout:   //로그아웃
                return true;
        }
        return false;
    }

}