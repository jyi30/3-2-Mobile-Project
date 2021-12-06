package com.example.team_5;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.team_5.databinding.FragmentOwnerMenuAddBinding;
import com.example.team_5.databinding.FragmentOwnerRegistrationBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


public class OwnerMenuAddFragment extends Fragment {

    private FragmentOwnerMenuAddBinding fragmentOwnerMenuAddBinding;
    private ImageButton back;
    private FirebaseFirestore db;
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://qrmenu-2139c.appspot.com/");
    private StorageReference storageRef;
    private StoreViewModel storeViewModel;
    private Object Uripath; // 임시
    private Uri imgUri;
    private OMAFragListener omaFragListener;

    interface OMAFragListener{
        void clickSaveMenu(HashMap<String,Object> menu);
    }

    public void setOMAFragListener (OMAFragListener listener) {
        this.omaFragListener = listener;
    }
    public OwnerMenuAddFragment() {
        // Required empty public constructor
    }


    public static OwnerMenuAddFragment newInstance() {
        OwnerMenuAddFragment fragment = new OwnerMenuAddFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        db = FirebaseFirestore.getInstance();
        storageRef = storage.getReference();
        storeViewModel = new ViewModelProvider(requireActivity())
                .get(StoreViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentOwnerMenuAddBinding = FragmentOwnerMenuAddBinding.inflate(inflater, container, false);
        return fragmentOwnerMenuAddBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        fragmentOwnerMenuAddBinding.btnPicture.setOnClickListener(new View.OnClickListener(){

            @Override // 앨범띄워서 사진선택
            public void onClick(View view){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,0);
            }
        });

        storeViewModel.store.observe(getViewLifecycleOwner(), new Observer<HashMap<String, Object>>() {
            @Override
            public void onChanged(HashMap<String, Object> store) {
                fragmentOwnerMenuAddBinding.btnSave.setOnClickListener(new View.OnClickListener() {
                    private static final String TAG = ".jpg";
                    String name = fragmentOwnerMenuAddBinding.addMenuName.getText().toString();
                    @Override
                    public void onClick(View v) {
                    if (imgUri != null && name != ""){
                            HashMap<String,Object> menu = new HashMap<>();
                            int last = ((ArrayList<Object>) store.get("menus")).size();
                            String path = store.get("id").toString() + "/menus/"+name+".jpg";
                            menu.put("name", name);

                            save(path, store.get("id").toString(), menu);
                        }
//                StorageReference storageRef = storage.getReference();
//                Uri file = Uri.fromFile(new File((String) Uripath)); // 절대경로uri를 file에 할당
//                Log.d(TAG, "photo file : " + file);
//
//                // stroage images에 절대경로파일 저장
//                StorageReference riversRef = storageRef.child("images/" + file.getLastPathSegment());
//                UploadTask uploadTask = riversRef.putFile(file);
//                Log.d(TAG, "uploadTask : " + uploadTask);
                    }
                });///====== 이미지 추가부분임 아직 텍스트 넣지 않음

            }
        });


        //뒤로가기 버튼
        fragmentOwnerMenuAddBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


    }
    private void save(String path, String sid, HashMap<String, Object> menu){

        UploadTask uploadTask = storageRef.child(path).putFile(imgUri);
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
                db.collection("Store").document(sid).update("menus",  FieldValue.arrayUnion(menu))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(@NonNull Void aVoid) {
                                omaFragListener.clickSaveMenu(menu);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });

    }

    @Override // 여기는 좌상단에 음식 이미지 추가부분임
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0) {
            imgUri = data.getData();
            Glide.with(getContext()).load(data.getData()).override(500,500)
                    .into(fragmentOwnerMenuAddBinding.addMenuImage);
        }
    }


}