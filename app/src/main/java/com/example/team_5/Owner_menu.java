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

public class Owner_menu extends AppCompatActivity {

private ImageView rightIcon;
    private Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_menu);


        rightIcon = findViewById(R.id.right_icon);
        registerForContextMenu(rightIcon);

        TextView title = findViewById(R.id.toolbar_title);
        title.setText("메뉴 관리");   //툴바 제목
        ImageView leftIcon = findViewById(R.id.left_icon);    //뒤로가기 버튼
        ImageView rightIcon = findViewById(R.id.right_icon);  //메뉴버튼
        btn_add = findViewById(R.id.btn_add);                            //추가버튼

        btn_add.setOnClickListener(new View.OnClickListener() {           //메뉴추가화면
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Owner_menu.this, OwnerMenuAdd.class);
                startActivity(intent);
            }
        });

        leftIcon.setOnClickListener(new View.OnClickListener()    {                  //뒤로가기 버튼
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
        //
    }
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.two:     //고객화면 전환
                Intent intent = new Intent(Owner_menu.this, GuestActivity.class);
                startActivity(intent);
                return true;
            case R.id.btn_logout:   //로그아웃
                return true;
           // case R.id.modify:     //메뉴 리스트뷰 수정버튼
              //  return true;
        //    case R.id.delete:     //메뉴 리스트뷰 삭제버튼
              //  return true;
           //     case R.id.full:     //메뉴 리스트뷰 수정버튼
              //  return true;
        }
        return false;
    }
    

}
