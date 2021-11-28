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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.team_5.databinding.ActivityOwnerManagerBinding;

public class Owner_manager extends AppCompatActivity {
    private StoreViewModel storeViewModel;
    private Long uid;
    private ActivityOwnerManagerBinding activityOwnerManagerBinding;
    private ImageView rightIcon;
    private Button btn_store_registration, btn_menu, btn_qr, btn_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOwnerManagerBinding = ActivityOwnerManagerBinding.inflate(getLayoutInflater());
        View view = activityOwnerManagerBinding.getRoot();
        setContentView(R.layout.activity_owner_manager);

        uid = getIntent().getLongExtra("uid", 0);

        storeViewModel = new ViewModelProvider(this).get(StoreViewModel.class);
        storeViewModel.setStoreUId(uid);


        rightIcon = findViewById(R.id.right_icon);
        registerForContextMenu(rightIcon);
        ImageView leftIcon = findViewById(R.id.left_icon);    //상단 뒤로가기 버튼
        ImageView rightIcon = findViewById(R.id.right_icon);  //상단 메뉴버튼

        btn_store_registration= findViewById(R.id.btn_store_registration); //가게등록 버튼
        btn_menu = findViewById(R.id.btn_menu);                            //메뉴관리 버튼
        btn_qr = findViewById(R.id.btn_qr);                                //QR코드확인 버튼
        btn_order = findViewById(R.id.btn_order);                          //주문관리 버튼
        TextView title = findViewById(R.id.toolbar_title);
        title.setText("관리자 화면");   //툴바 제목


        //가게 등록 화면으로 이동
        btn_store_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OwnerRegistrationFragment ownerRegistrationFragment = OwnerRegistrationFragment.newInstance(uid);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, ownerRegistrationFragment).commit();
                activityOwnerManagerBinding.btnQr.setVisibility(View.GONE);
                activityOwnerManagerBinding.btnOrder.setVisibility(View.GONE);
                activityOwnerManagerBinding.btnMenu.setVisibility(View.GONE);
                activityOwnerManagerBinding.btnStoreRegistration.setVisibility(View.GONE);

            }
        });

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
    }//end of ContextMenu()

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.two:     //고객화면 전환
                Intent intent = new Intent(Owner_manager.this, GuestActivity.class);
                startActivity(intent);
                return true;
            case R.id.btn_logout:   //로그아웃
                return true;
        }
        return false;
    }
}