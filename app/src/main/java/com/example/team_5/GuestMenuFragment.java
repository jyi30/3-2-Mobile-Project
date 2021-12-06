package com.example.team_5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.team_5.databinding.FragmentMenuBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Nullable;

public class GuestMenuFragment extends Fragment {

    private StoreViewModel storeViewModel;
    private FragmentMenuBinding fragmentMenuBinding; //id 불러오기 위한 바인딩(findViewById 없이 id 사용가능)
    private GuestMenuListAdapter guestMenuListAdapter;
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://qrmenu-2139c.appspot.com");
    private StorageReference storageRef;
    private String sid;
    private long uid;

    GuestActivity activity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity = (GuestActivity)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        activity=null;
    }

    public GuestMenuFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static GuestMenuFragment newInstance(Long uid) {
        GuestMenuFragment fragment = new GuestMenuFragment();
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
        storeViewModel = new ViewModelProvider(requireActivity()).get(StoreViewModel.class);
        guestMenuListAdapter = new GuestMenuListAdapter();
        storageRef = storage.getReference();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentMenuBinding = FragmentMenuBinding.inflate(inflater,container,false); //바인딩

        fragmentMenuBinding.payAmount.setText("총 결제금액 : " + guestMenuListAdapter.getPayamount()+"원");

        fragmentMenuBinding.menuList.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                fragmentMenuBinding.payAmount.setText("총 결제금액 : " + guestMenuListAdapter.getPayamount()+"원");
                return false;
            }
        });

        fragmentMenuBinding.payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] split = guestMenuListAdapter.getname().replaceAll("\\{","").replaceAll("\\}","").split("=");
                String name = "" + split[0] + " 외 " + guestMenuListAdapter.getamount() + "개";
                String price = guestMenuListAdapter.getprice();
                HashMap<String, Object> oderMap = guestMenuListAdapter.getOderMap();

                oderMap.put("show_name", name);
                oderMap.put("price",price);
                oderMap.put("store_id",sid);
                oderMap.put("user_id",uid);
                oderMap.put("order_refuse", false);
                oderMap.put("refuse_text","");
                oderMap.put("get_order",false);

                Log.e("Debug","ddd"+name);
                Intent intent = new Intent(getActivity(), PayActivity.class);
                intent.putExtra("oder_map", oderMap);
//                intent.putExtra("name", name);
//                intent.putExtra("price", price);
                startActivityForResult(intent, GuestActivity.REQUEST_CODE_PAY);
            }
        });
        return fragmentMenuBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        fragmentMenuBinding.menuList.setAdapter(guestMenuListAdapter);
        fragmentMenuBinding.menuList.setLayoutManager(new LinearLayoutManager(getContext()));

        storeViewModel.store.observe(getViewLifecycleOwner(), new Observer<HashMap<String, Object>>() {
            @Override
            public void onChanged(HashMap<String, Object> stringObjectHashMap) {
                ArrayList<HashMap<String,Object>> menus =(ArrayList<HashMap<String,Object>>) stringObjectHashMap.get("menus");
                guestMenuListAdapter.setMenu(menus);
                sid = (String) stringObjectHashMap.get("id");
                String path = "store/" + sid;
                StorageReference storeRef = storageRef.child(path);
                guestMenuListAdapter.setStorageReference(storeRef);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}