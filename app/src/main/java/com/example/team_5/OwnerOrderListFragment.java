package com.example.team_5;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.team_5.databinding.FragmentOwnerMenuAddBinding;
import com.example.team_5.databinding.FragmentOwnerOrderListBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OwnerOrderListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OwnerOrderListFragment extends Fragment {

    private FragmentOwnerOrderListBinding fragmentOwnerOrderListBinding;
    private StoreViewModel storeViewModel;
    private FirebaseFirestore db;

    public OwnerOrderListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static OwnerOrderListFragment newInstance() {
        OwnerOrderListFragment fragment = new OwnerOrderListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        storeViewModel = new ViewModelProvider(requireActivity())
                .get(StoreViewModel.class);
        db = FirebaseFirestore.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentOwnerOrderListBinding = FragmentOwnerOrderListBinding.inflate(inflater, container, false);
        return fragmentOwnerOrderListBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        OrderListAdapter orderListAdapter = new OrderListAdapter();
        storeViewModel.store.observe(getViewLifecycleOwner(), new Observer<HashMap<String, Object>>() {
            @Override
            public void onChanged(HashMap<String, Object> store) {
                db.collection("Order").whereEqualTo("store_id", store.get("id"))
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot doc : queryDocumentSnapshots){
                            HashMap<String,Object> order = (HashMap<String,Object>) doc.getData();
                            orderListAdapter.addOder(order);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"네트워크 오류",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        fragmentOwnerOrderListBinding.orderList.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentOwnerOrderListBinding.orderList.setAdapter(orderListAdapter);
    }
}