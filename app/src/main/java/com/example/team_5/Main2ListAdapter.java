package com.example.team_5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class Main2ListAdapter extends RecyclerView.Adapter {

    private ArrayList<HashMap<String,Object>> itemList;
    private Main2ListListener main2ListListener;

    public interface Main2ListListener{
        void itemClick(String storeId);
    }

    public void setMain2ListListener(Main2ListListener main2ListListener) {
        this.main2ListListener = main2ListListener;
    }

    public Main2ListAdapter(){
        itemList = new ArrayList<>();
    }

    public Main2ListAdapter(ArrayList<HashMap<String,Object>> list) {
        itemList = list;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main2_list_item, parent, false);
        return new Main2ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Main2ViewHolder main2ViewHolder = (Main2ViewHolder) holder;
        HashMap<String, Object> item = itemList.get(position);
        main2ViewHolder.textView.setText((String) item.get("text"));
        main2ViewHolder.titleView.setText((String) item.get("name"));

        main2ViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main2ListListener.itemClick((String) item.get("id"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class Main2ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleView;
        TextView textView;

        public Main2ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.main2_item_image);
            titleView = itemView.findViewById(R.id.main2_item_title);
            textView = itemView.findViewById(R.id.main2_item_text);

        }
    }

    public void setDataSet(ArrayList<HashMap<String, Object>> itemList){
        this.itemList = itemList;
        notifyDataSetChanged();
    }
}
