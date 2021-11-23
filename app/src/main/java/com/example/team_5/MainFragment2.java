package com.example.team_5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.team_5.databinding.FragmentMain2Binding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;


public class MainFragment2 extends Fragment {

    private FragmentMain2Binding fragmentMain2Binding;
    private FirebaseFirestore db;
    private StoreViewModel storeViewModel;
    private Main2ListAdapter main2ListAdapter;

    public MainFragment2() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MainFragment2 newInstance() {
        MainFragment2 fragment = new MainFragment2();
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
        storeViewModel = new ViewModelProvider(requireActivity()).get(StoreViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMain2Binding = FragmentMain2Binding.inflate(inflater,container,false);
        return fragmentMain2Binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        fragmentMain2Binding.main2List.setLayoutManager(new LinearLayoutManager(getContext()));
        main2ListAdapter = new Main2ListAdapter();
        fragmentMain2Binding.main2List.setAdapter(main2ListAdapter);

        db.collection("Store")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<HashMap<String, Object>> main2List = new ArrayList<>();
                for(DocumentSnapshot doc: queryDocumentSnapshots){
                    HashMap<String, Object> store = (HashMap<String, Object>) doc.getData();
                    main2List.add(store);
                }
                main2ListAdapter.setDataSet(main2List);
            }
        });
    }
}