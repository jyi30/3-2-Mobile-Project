package com.example.team_5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.team_5.databinding.FragmentMenuBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class GuestMenuFragment extends Fragment {

    private StoreViewModel storeViewModel;
    private FragmentMenuBinding fragmentMenuBinding; //id 불러오기 위한 바인딩(findViewById 없이 id 사용가능)
    private GuestMenuListAdapter guestMenuListAdapter;
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://qrmenu-2139c.appspot.com");
    private StorageReference storageRef;

    public GuestMenuFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static GuestMenuFragment newInstance() {
        GuestMenuFragment fragment = new GuestMenuFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        storeViewModel = new ViewModelProvider(requireActivity()).get(StoreViewModel.class);
        guestMenuListAdapter = new GuestMenuListAdapter();
        storageRef = storage.getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMenuBinding = FragmentMenuBinding.inflate(inflater,container,false); //바인딩
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
                String id = (String) stringObjectHashMap.get("id");
                String path = "store/" + id;
                StorageReference storeRef = storageRef.child(path);
                guestMenuListAdapter.setStorageReference(storeRef);

            }
        });
    }
}