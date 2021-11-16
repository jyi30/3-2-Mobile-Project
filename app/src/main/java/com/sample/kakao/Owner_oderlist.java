package com.sample.kakao;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Owner_oderlist extends AppCompatActivity {


    private ListView mListView;   //리스트뷰
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_oderlist);



        TextView title = findViewById(R.id.toolbar_title);
        title.setText("주문목록");   //툴바 제목
        ImageView leftIcon = findViewById(R.id.left_icon);    //뒤로가기 버튼
        ImageView rightIcon = findViewById(R.id.right_icon);  //메뉴버튼

        leftIcon.setOnClickListener(new View.OnClickListener()    {                  //뒤로가기 버튼튼
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Owner_oderlist.this, Owner_manager.class);
                startActivity(intent);
            }
        });
        rightIcon.setOnClickListener(new View.OnClickListener()    {                   // 상단메뉴 (화면전환, 로그아웃 )
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });

    }
    private void showMenu(View v){                               // 상단메뉴 (화면전환, 로그아웃 )
        PopupMenu popupMenu = new PopupMenu(Owner_oderlist.this,v);
        popupMenu.getMenuInflater().inflate(R.menu.main_menu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.one) {
                    Intent intent = new Intent(Owner_oderlist.this, Owner_manager.class);
                    startActivity(intent); }
                if(item.getItemId() == R.id.two) {
                    Intent intent = new Intent(Owner_oderlist.this,Guest.class);
                    startActivity(intent); }
                if(item.getItemId() == R.id.btn_logout)
                {
                    //로그아웃 구현
                    Intent intent = new Intent(Owner_oderlist.this,MainActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        popupMenu.show();
    }
}
