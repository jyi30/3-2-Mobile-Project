package com.example.team_5;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MenuItemViewHolder> {
    private final String TAG = "menuList";
    private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
    private StorageReference storageReference;


    public MenuListAdapter() {
    }

    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_view, parent, false);
        MenuItemViewHolder menuItemViewHolder = new MenuItemViewHolder(view);
        return menuItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemViewHolder holder, int position) {
        HashMap<String, Object> hashMap = arrayList.get(position);
        String image = (String) hashMap.get("image");

        StorageReference imageRef = storageReference.child("/menus/" + image);
        Log.d(TAG,imageRef.getPath());
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(holder.itemView)
                        .load(uri)
                        .centerCrop()
                        .into(holder.getImageView());
            }
        });
        holder.getTitleTextView().setText((String)hashMap.get("name"));
        holder.getInformationTextView().setText((String)hashMap.get("detail"));
        holder.getPriceTextView().setText((String)hashMap.get("price"));
    }
    @NonNull

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setMenu(ArrayList<HashMap<String, Object>> hashMaps) {
        arrayList = hashMaps;
        notifyDataSetChanged();
    }

    public void setStorageReference(StorageReference storageReference) {
        this.storageReference = storageReference;
    }


    class MenuItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView titleTextView;
        private TextView informationTextView;
        private TextView priceTextView;

        public MenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.menu_image);
            titleTextView = itemView.findViewById(R.id.menu_name);
            informationTextView = itemView.findViewById(R.id.menu_information);
            priceTextView = itemView.findViewById(R.id.menu_price);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }

        public void setTitleTextView(TextView titleTextView) {
            this.titleTextView = titleTextView;
        }

        public TextView getInformationTextView() {
            return informationTextView;
        }

        public void setInformationTextView(TextView informationTextView) {
            this.informationTextView = informationTextView;
        }

        public TextView getPriceTextView() {
            return priceTextView;
        }

        public void setPriceTextView(TextView priceTextView) {
            this.priceTextView = priceTextView;
        }
    }
}
