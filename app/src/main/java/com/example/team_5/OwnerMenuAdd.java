package com.example.team_5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.team_5.databinding.ActivityOwnerMenuAddBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class OwnerMenuAdd extends AppCompatActivity {

    private ActivityOwnerMenuAddBinding activityOwnerMenuAddBinding;

    private ImageButton back;
    private FirebaseFirestore db;
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://qrmenu-2139c.appspot.com/");
    private StorageReference storageRef;

    EditText et_user_name, et_user_email;
    Button btn_save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOwnerMenuAddBinding = ActivityOwnerMenuAddBinding.inflate(getLayoutInflater());
        View view = activityOwnerMenuAddBinding.getRoot();
        setContentView(view);

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
}
