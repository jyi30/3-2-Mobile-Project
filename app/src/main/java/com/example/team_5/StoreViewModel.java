package com.example.team_5;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

public class StoreViewModel extends ViewModel {
    public MutableLiveData<HashMap<String, Object>> store = new MutableLiveData<>();
    public MutableLiveData<String> storeId = new MutableLiveData<>();

    public void setStore(String storeId) {
        DBRequest.getStoreDB(storeId, store);
    }

    public void setStoreId(String storeId) {
        this.storeId.setValue(storeId);
    }

    public void setStoreUId(Long uid) {
        DBRequest.getStoreDB2Uid(uid, store);
    }
}

