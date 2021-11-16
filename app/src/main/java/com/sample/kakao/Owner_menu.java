package com.sample.kakao;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Owner_menu extends AppCompatActivity {


    private ListView mListView;   //리스트뷰
    private Button btn_add,btn_modify,btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_menu);

        mListView = (ListView)findViewById(R.id.listView);   //리스트뷰
        dataSetting();          //메뉴추가
        TextView title = findViewById(R.id.toolbar_title);
        title.setText("메뉴판");   //툴바 제목
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
        rightIcon.setOnClickListener(new View.OnClickListener()    {                  // 상단메뉴 (화면전환, 로그아웃 )
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {     //리스트 클릭시 수정, 삭제버튼
        getMenuInflater().inflate(R.menu.menu_listview, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int index= info.position;
        return true;
    }
    //swhitch
   //삭제, 수정버튼 구현



    private void dataSetting(){
        MyAdapter mMyAdapter = new MyAdapter();
         mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.food), "떡볶이"  ,"6000원 " );
         mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.food1), "치즈 떡볶이"  ,"6000원" );
         mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.food2), "로제 떡볶이"  ,"6000원" );
        mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.food3), "크림 떡볶이"  ,"6000원" );

        //리스트뷰에 어댑터 등록
        mListView.setAdapter(mMyAdapter);
    }

    private void showMenu(View v){                             // 상단메뉴 (화면전환, 로그아웃 )
        PopupMenu popupMenu = new PopupMenu(Owner_menu.this,v);
        popupMenu.getMenuInflater().inflate(R.menu.main_menu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.one) {
                    Intent intent = new Intent(Owner_menu.this,Owner_manager.class);
                    startActivity(intent); }
                if(item.getItemId() == R.id.two) {
                    Intent intent = new Intent(Owner_menu.this,Guest.class);
                    startActivity(intent); }
                if(item.getItemId() == R.id.btn_logout)
                {
                    //로그아웃 구현
                    Intent intent = new Intent(Owner_menu.this,MainActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        popupMenu.show();
    }



}
