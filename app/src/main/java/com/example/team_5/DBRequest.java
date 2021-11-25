package com.example.team_5;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class DBRequest {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance(); //파이어베이스 접근 위한 인스턴스 생성


    //가게 데이터
    public static void getStoreDB(String storeId, MutableLiveData<HashMap<String,Object>> data){
        db.collection("Store").document(storeId)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                data.setValue((HashMap<String, Object>) documentSnapshot.getData());
            }
        });
    }

    //user_id 데이터
    public static void getStoreDB2Uid(Long uid, MutableLiveData<HashMap<String,Object>> data){
        db.collection("Store").whereEqualTo("user_id",uid)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.isEmpty()){
                    data.setValue(null);
                }else {
                    DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(0);
                    data.setValue((HashMap<String, Object>) doc.getData());

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("DB", "fail");
            }
        });
    }
}
