package com.example.team_5;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

public class GuestActivity extends AppCompatActivity implements
        GuestScanFragment.GuestFragmentListener {

    public final int REQUEST_CODE_PERMISSIONS = 100; //카메라 권한설정
    private StoreViewModel storeViewModel;

    private ImageView rightIcon, rightIcon2;
    private Button qrcodeScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        rightIcon = findViewById(R.id.right_icon);
        qrcodeScan = findViewById(R.id.btn_qr_scan);

        ImageView leftIcon = findViewById(R.id.left_icon);    //뒤로가기 버튼
        ImageView rightIcon = findViewById(R.id.right_icon);  //메뉴버튼
        TextView title = findViewById(R.id.toolbar_title);
        title.setText("QR코드 스캔");   //툴바 제목

        registerForContextMenu(rightIcon);

        storeViewModel = new ViewModelProvider(this).get(StoreViewModel.class);

        if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            startMain();
        }else {
            requestPermissions(new String[] {Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSIONS);
        }

        leftIcon.setOnClickListener(new View.OnClickListener()    {                  //선택 화면으로 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuestActivity.this, Choice.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startMain();
            } else {
                Toast.makeText(this,
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBarcode(String storeId) {
        storeViewModel.setStore(storeId);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, GuestMenuFragment.newInstance())
                .addToBackStack("menu")
                .commit();
    }

    private void startMain(){
        qrcodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuestScanFragment guestScanFragment = GuestScanFragment.newInstance();
                guestScanFragment.setGuestFragmentListener(GuestActivity.this);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, guestScanFragment)
                        .commit();
                qrcodeScan.setVisibility(View.GONE);
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
            mi.inflate(R.menu.main_menu2, menu);
        }
    }//end of ContextMenu()


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.one:
                Intent intent = new Intent(GuestActivity.this, Owner_manager.class);
                startActivity(intent);
                return true;
            case R.id.btn_logout:
                return true;
        }
        return false;
    }
}