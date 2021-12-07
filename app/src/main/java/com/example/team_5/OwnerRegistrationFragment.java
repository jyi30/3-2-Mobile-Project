package com.example.team_5;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.team_5.databinding.FragmentOwnerRegistrationBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class OwnerRegistrationFragment extends Fragment {
    private FragmentOwnerRegistrationBinding fragmentOwnerRegistrationBinding; //xml id 사용을 위해
    private Long uid; //user id
    private FirebaseFirestore db; //db
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://qrmenu-2139c.appspot.com/"); //storage(db 이미지)
    private StorageReference storageRef;
    private StoreViewModel storeViewModel;
    private ORFragListener orFragListener;

    interface ORFragListener{
        void onRegister();
    }

    public void setOrFragListener (ORFragListener orFragListener) {
        this.orFragListener = orFragListener;
    }

    public OwnerRegistrationFragment() {
        // Required empty public constructor
    }

    public static OwnerRegistrationFragment newInstance(Long uid) {
        OwnerRegistrationFragment fragment = new OwnerRegistrationFragment();
        Bundle args = new Bundle();
        args.putLong("uid",uid); //uid
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            uid = getArguments().getLong("uid");
        }
        db = FirebaseFirestore.getInstance(); //firebase
        storageRef = storage.getReference(); //storage
        storeViewModel = new ViewModelProvider(requireActivity()).get(StoreViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentOwnerRegistrationBinding = FragmentOwnerRegistrationBinding.inflate(inflater, container, false);
        return fragmentOwnerRegistrationBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        fragmentOwnerRegistrationBinding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentOwnerRegistrationBinding.saveButton.setEnabled(false); //저장버튼
                fragmentOwnerRegistrationBinding.addStoreView.setVisibility(View.GONE); //storeView 사라지게
                fragmentOwnerRegistrationBinding.addStoreLoading.setVisibility(View.VISIBLE); // 로딩 이미지 보이게

                HashMap<String, Object> store = new HashMap<>(); //store HashMap
                String sName = fragmentOwnerRegistrationBinding.addStoreSname.getEditableText().toString(); //가게 이름 저장
                store.put("name", sName); //db에 가게이름 put
                store.put("user_id", uid); // db에 user id put
                store.put("menus", new ArrayList<>()); // db에 menu put

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

    //QR코드 생성
    private void makeQR(String id){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="https://chart.apis.google.com/chart?cht=qr&chs=300x300&chl="+id;

        // Request a string response from the provided URL.
        ImageRequest imageRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>(){
                    @Override
                    public void onResponse(Bitmap response) {
                        String path = "store/" + id + "/qr.jpg";
                        saveQRImage(path, response, id);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.ARGB_8888,
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        // Add the request to the RequestQueue.
        queue.add(imageRequest);
    }

    private void saveQRImage(String path, Bitmap bitmap, String sid){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = storageRef.child(path).putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fragmentOwnerRegistrationBinding.addStoreView.setVisibility(View.VISIBLE); //StoreView 보이게
                fragmentOwnerRegistrationBinding.addStoreLoading.setVisibility(View.GONE); //로딩 이미지 사라지게
                //Toast.makeText(getContext(),"QR성공",Toast.LENGTH_SHORT).show();
                storeViewModel.setStore(sid);
                orFragListener.onRegister();
            }
        });
    }
}