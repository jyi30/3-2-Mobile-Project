package com.example.team_5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Owner_qr extends AppCompatActivity {
    private ImageView rightIcon, qrView;
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://qrmenu-2139c.appspot.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StorageReference storageRef = storage.getReference(); //db 이미지
        setContentView(R.layout.activity_owner_qr);
        rightIcon = findViewById(R.id.right_icon);
        qrView = findViewById(R.id.qr_image);
        registerForContextMenu(rightIcon);

        ImageView leftIcon = findViewById(R.id.left_icon);    //뒤로가기 버튼
        ImageView rightIcon = findViewById(R.id.right_icon);  //메뉴버튼
        TextView title = findViewById(R.id.toolbar_title);
        title.setText("QR코드 생성");   //툴바 제목

        String path = getIntent().getStringExtra("path");

        storageRef.child(path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(@NonNull Uri uri) {
                Glide.with(Owner_qr.this)
                        .load(uri)
                        .centerCrop()
                        .into(qrView);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"qr코드를 불러올 수 없습니다.",Toast.LENGTH_LONG).show();
                    }
                });

        leftIcon.setOnClickListener(new View.OnClickListener()    {                  //뒤로가기 버튼튼
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Owner_qr.this, Owner_manager.class);
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