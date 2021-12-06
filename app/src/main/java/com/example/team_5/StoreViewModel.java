package com.example.team_5;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

public class StoreViewModel extends ViewModel {
    public MutableLiveData<HashMap<String, Object>> store = new MutableLiveData<>();
    public MutableLiveData<String> storeId = new MutableLiveData<>();


    public void setStore(String storeId) { DBRequest.getStoreDB(storeId, store); }  //스토어ID 연결 (DBRequest.class)

    public void setStoreUId(Long uid) { DBRequest.getStoreDB2Uid(uid, store); } //user id 연결 (DBRequest.class)

    public void setStoreId(String storeId) {
        this.storeId.setValue(storeId);
    } //DB에 스토어 ID 값 추가

}
