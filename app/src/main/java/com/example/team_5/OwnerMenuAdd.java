package com.example.team_5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.team_5.databinding.ActivityOwnerMenuAddBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class OwnerMenuAdd extends AppCompatActivity {

    private ActivityOwnerMenuAddBinding activityOwnerMenuAddBinding;

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
    }
}

        /*readUser();

            @Override
            public void onClick(View v) {
                String getUserName = et_user_name.getText().toString();
                String getUserEmail = et_user_email.getText().toString();

                //hashmap 만들기
                HashMap result = new HashMap<>();
                result.put("name", getUserName);
                result.put("email", getUserEmail);

                writeNewUser("1",getUserName,getUserEmail);

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        activityOwnerMenuAddBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityOwnerMenuAddBinding.btnSave.setEnabled(false);
                activityOwnerMenuAddBinding.addMenuName.setVisibility(View.GONE);
                activityOwnerMenuAddBinding.addStoreLoading.setVisibility(View.VISIBLE);
                HashMap<String, Object> store = new HashMap<>();

                /*
                String sName = fragmentOwnerRegistrationBinding.addStoreSname.getEditableText().toString();

                store.put("name", sName);
                store.put("user_id", uid);
                store.put("menus", new ArrayList<>());
                db.runTransaction(new Transaction.Function<String>() {
                    @Override
                    public String apply(Transaction transaction) throws FirebaseFirestoreException {
                        DocumentReference storeRef = db.collection("Store").document();
                        store.put("id", storeRef.getId());
                        transaction.set(storeRef,store);
                        return storeRef.getId();
                    }
                }).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(@NonNull String s) {
                        Toast.makeText(getContext(),"qr코드를 생성 중입니다",Toast.LENGTH_LONG).show();
                        makeQR(s);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }
        });
    }


    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("users").child(userId).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        Toast.makeText(MainActivity.this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        Toast.makeText(MainActivity.this, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void readUser(){
        mDatabase.child("users").child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.getValue(User.class) != null){
                    User post = dataSnapshot.getValue(User.class);
                    Log.w("FireBaseData", "getData" + post.toString());
                } else {
                    Toast.makeText(MainActivity.this, "데이터 없음...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
            }
        });

    }

          */