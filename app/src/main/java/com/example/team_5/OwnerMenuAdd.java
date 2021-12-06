package com.example.team_5;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.bumptech.glide.Glide;
import com.example.team_5.databinding.ActivityOwnerMenuAddBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kakao.sdk.user.UserApiClient;

import java.io.File;

public class OwnerMenuAdd extends AppCompatActivity {

    private ActivityOwnerMenuAddBinding activityOwnerMenuAddBinding;

    private ImageButton back;
    private FirebaseFirestore db;
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://qrmenu-2139c.appspot.com/");
    private StorageReference storageRef;

    EditText et_user_name, et_user_email;
    // 임시 EditText menu_name, menu_detail, menu_price; // 메뉴이름 , 설명, 가격 선언만하고 아직 설정x
    Button btn_save,btn_picture; // ----- 추가부분 -------
    ImageView picture; // ----- 추가부분 -------
    private Object Uripath; // 임시

    // uri 절대경로 가져오기
    public String getPath(Uri uri){

        String [] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this,uri,proj,null,null,null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOwnerMenuAddBinding = ActivityOwnerMenuAddBinding.inflate(getLayoutInflater());
        View view = activityOwnerMenuAddBinding.getRoot();
        setContentView(view);
        // ----- 추가부분 -------
        picture = findViewById(R.id.add_menu_image); //사진표시 아이디
        btn_picture = findViewById(R.id.btn_picture); //사진선택 버튼 아이디
        btn_picture.setOnClickListener(new View.OnClickListener(){

            @Override // 앨범띄워서 사진선택
            public void onClick(View view){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,0);
            }
        });
        // ----- 추가부분 -------
        btn_save =  findViewById(R.id.btn_save);
        ///====== 이미지 추가부분임 아직 텍스트 넣지 않음
        btn_save.setOnClickListener(new View.OnClickListener() {
            private static final String TAG = ".jpg";

            @Override
            public void onClick(View v) {
                StorageReference storageRef = storage.getReference();
                Uri file = Uri.fromFile(new File((String) Uripath)); // 절대경로uri를 file에 할당
                Log.d(TAG, "photo file : " + file);

                // stroage images에 절대경로파일 저장
                StorageReference riversRef = storageRef.child("images/" + file.getLastPathSegment());
                UploadTask uploadTask = riversRef.putFile(file);
                Log.d(TAG, "uploadTask : " + uploadTask);
            }
        });///====== 이미지 추가부분임 아직 텍스트 넣지 않음

        //firebase 정의
        db = FirebaseFirestore.getInstance();
        storageRef = storage.getReference();

        back = findViewById(R.id.back);
        //뒤로가기 버튼
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnerMenuAdd.this, Owner_menu.class);
                startActivity(intent);
            }
        });

    }

    @Override // 여기는 좌상단에 음식 이미지 추가부분임
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0) {
            Glide.with(getApplicationContext()).load(data.getData()).override(500,500).into(picture);
        }
    }

    ///임시로 짠것 사진을 String 형식으로 했었을때 참고하려고
//    @IgnoreExtraProperties
//    public class Menu {
//        public String getMenu_name;
//        public String getMenu_detail;
//        public String getMenu_price;
//        public String getMenu_picture;
//
//        public Menu() { }
//        public Menu(String getMenu_name, String getMenu_detail, String getMenu_price, String getMenu_picture) {
//            this.getMenu_name = getMenu_name;
//            this.getMenu_detail = getMenu_detail;
//            this.getMenu_price = getMenu_price;
//            this.getMenu_picture = getMenu_picture;
//        }
//
//        public void setName(String getMenu_name) {
//            this.getMenu_name = getMenu_name;
//        }
//
//        public void setDetail(String getMenu_detail) {
//            this.getMenu_detail = getMenu_detail;
//        }
//
//        public void setPrice(String getMenu_price) {
//            this.getMenu_price = getMenu_price;
//        }
//
//        public void setPicture(String getMenu_picture) {
//            this.getMenu_picture = getMenu_picture;
//        }
//
//        public String getName() {
//            return getMenu_name;
//        }
//
//        public String getDetaul() {
//            return getMenu_detail;
//        }
//
//        public String getPrice() {
//            return getMenu_price;
//        }
//
//        public String getPicture() {
//            return getMenu_picture;
//        }
//
//        @Override
//        public String toString() {
//            return "Menu{" +
//                    "Menu name='" + getMenu_name + '\'' +
//                    ", Menu detail='" + getMenu_detail + '\'' +
//                    ", Menu price=" + getMenu_price + '\'' +
//                    ", Menu picture=" + picture +
//                    '}' ;
//        }
//    }
    // ----- 추가부분 -------
}
