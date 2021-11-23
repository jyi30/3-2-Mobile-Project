package com.example.team_5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Owner_menu extends AppCompatActivity {

private ImageView rightIcon,rightIcon2;
    private ListView mListView;   //리스트뷰
    private Button btn_add,btn_modify,btn_delete,btn_plus,btn_minus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_menu);


        rightIcon = findViewById(R.id.right_icon);
        rightIcon2 = findViewById(R.id.right_icon2);
        registerForContextMenu(rightIcon);
        registerForContextMenu(rightIcon2);
        mListView = (ListView)findViewById(R.id.listView);   //리스트뷰
        dataSetting();          //메뉴추가
        TextView title = findViewById(R.id.toolbar_title);
        title.setText("메뉴 관리");   //툴바 제목
        ImageView leftIcon = findViewById(R.id.left_icon);    //뒤로가기 버튼
        ImageView rightIcon = findViewById(R.id.right_icon);  //메뉴버튼
        btn_add = findViewById(R.id.btn_menu);                            //추가버튼

        registerForContextMenu(mListView);
        leftIcon.setOnClickListener(new View.OnClickListener()    {                  //뒤로가기 버튼튼
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Owner_menu.this, Owner_manager.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {     //리스트 클릭시 수정, 삭제버튼
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mi = getMenuInflater();
        if(v == rightIcon){
            mi.inflate(R.menu.main_menu1, menu);
        }
        if (v == rightIcon2){
            mi.inflate(R.menu.menu_call, menu);
        }
        if (v == mListView){
            mi.inflate(R.menu.menu_listview, menu);
        }
    }
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.two:     //고객화면 전환
                Intent intent = new Intent(Owner_menu.this, GuestActivity.class);
                startActivity(intent);
                return true;
            case R.id.btn_logout:   //로그아웃
                return true;
            case R.id.modify:     //메뉴 리스트뷰 수정버튼
                return true;
            case R.id.delete:     //메뉴 리스트뷰 삭제버튼
                return true;
            case R.id.full:     //메뉴 리스트뷰 품절버튼
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


    private void dataSetting(){
        MyAdapter mMyAdapter = new MyAdapter();
         mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.food), "떡볶이"  ,"6000원 " );
         mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.food1), "치즈 떡볶이"  ,"6000원" );
         mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.food2), "로제 떡볶이"  ,"6000원" );
        mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.food3), "크림 떡볶이"  ,"6000원" );
        //리스트뷰에 어댑터 등록
        mListView.setAdapter(mMyAdapter);
    }





}
