package com.example.team_5;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
    private Long uid;
    private FragmentOwnerRegistrationBinding fragmentOwnerRegistrationBinding;
    private FirebaseFirestore db;
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://qrmenu-2139c.appspot.com/");
    private StorageReference storageRef;


    public OwnerRegistrationFragment() {
        // Required empty public constructor
    }

    public static OwnerRegistrationFragment newInstance(Long uid) {
        OwnerRegistrationFragment fragment = new OwnerRegistrationFragment();
        Bundle args = new Bundle();
        args.putLong("uid",uid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            uid = getArguments().getLong("uid");
        }
        db = FirebaseFirestore.getInstance();
        storageRef = storage.getReference();

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
                fragmentOwnerRegistrationBinding.saveButton.setEnabled(false);
                fragmentOwnerRegistrationBinding.addStoreView.setVisibility(View.GONE);
                fragmentOwnerRegistrationBinding.addStoreLoading.setVisibility(View.VISIBLE);
                HashMap<String, Object> store = new HashMap<>();
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


    private void makeQR(String id){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="https://chart.apis.google.com/chart?cht=qr&chs=300x300&chl="+id;

        // Request a string response from the provided URL.
        ImageRequest imageRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>(){
                    @Override
                    public void onResponse(Bitmap response) {
                        String path = "store/" + id + "/qr.jpg";
                        saveQRImage(path, response);
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

    private void saveQRImage(String path, Bitmap bitmap){
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
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                fragmentOwnerRegistrationBinding.addStoreView.setVisibility(View.VISIBLE);
                fragmentOwnerRegistrationBinding.addStoreLoading.setVisibility(View.GONE);
                Toast.makeText(getContext(),"QR성공",Toast.LENGTH_SHORT).show();
            }
        });

    }

}