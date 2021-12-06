package com.example.team_5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.team_5.databinding.FragmentOwnerMenuBinding;


public class OwnerMenuFragment extends Fragment {


    private FragmentOwnerMenuBinding fragmentOwnerMenuBinding;
    private OMFragListener omFragListener;

    interface OMFragListener{
        void clickAddMenu();
    }

    public void setOMFragListener (OMFragListener listener) {
        this.omFragListener = listener;
    }

    public OwnerMenuFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static OwnerMenuFragment newInstance() {
        OwnerMenuFragment fragment = new OwnerMenuFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentOwnerMenuBinding = FragmentOwnerMenuBinding.inflate(inflater,container,false);
        return fragmentOwnerMenuBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        fragmentOwnerMenuBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                omFragListener.clickAddMenu();
            }
        });
    }
}