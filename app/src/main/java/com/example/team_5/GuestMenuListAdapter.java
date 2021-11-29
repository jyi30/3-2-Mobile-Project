package com.example.team_5;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class GuestMenuListAdapter extends RecyclerView.Adapter<GuestMenuListAdapter.MenuItemViewHolder> {
    private final String TAG = "menuList";
    private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
    private ArrayList<HashMap<String, Integer>> cartList = new ArrayList<>();
    private StorageReference storageReference;
    private Button minbtn;
    private Button maxbtn;
    private String menuname;
    private int menuamount;
    int payamount = 0;
    GuestMenuFragment gmf = new GuestMenuFragment();
    HashMap<String, Integer> pushmap = new HashMap<String, Integer> ();

    public GuestMenuListAdapter() {
    }

    //리스트뷰 터치 시 반응
    public interface OnItemClickListener{
        void onitemClick(View v, int pos);
    }

    private OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    //아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_view, parent, false);

        MenuItemViewHolder menuItemViewHolder = new MenuItemViewHolder(view);
        return menuItemViewHolder;
    }

    //position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull MenuItemViewHolder holder, int position) {
        int pos = position;
        HashMap<String, Object> hashMap = arrayList.get(position);
        String image = (String) hashMap.get("image"); //DB 메뉴 이미지

        //DB 메뉴 이미지 업데이트하는 방법 (하위 노드 포함 모든 데이터를 덮어씀 -전체 객체 다시 사용하지 않아도 됨)
        StorageReference imageRef = storageReference.child("/menus/" + image);
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) { //glide로 메뉴 이미지 핸드폰에 저장하고 불러오기
                Glide.with(holder.itemView) //(메뉴 이미지 한 번 저장되면 로딩 시간 줄여줌)
                        .load(uri)
                        .centerCrop()
                        .into(holder.getImageView());
            }
        });
        holder.getTitleTextView().setText((String)hashMap.get("name")); //DB 메뉴이름 뷰홀더 text에 할당
        holder.getInformationTextView().setText((String)hashMap.get("detail")); //DB 메뉴설명 뷰홀더 text에 할당
        holder.getPriceTextView().setText((String)hashMap.get("price"));  //DB 메뉴가격 뷰홀더 text에 할당
        //-버튼 눌렀을시
        holder.getminbtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Debug", "메뉴" + arrayList.get(pos));
                menuname = hashMap.get("name").toString();
                if(Integer.parseInt(holder.getamount().getText().toString()) > 1) {
                    menuamount = Integer.parseInt(holder.getamount().getText().toString());
                    menuamount--;
                    payamount -= Integer.parseInt(hashMap.get("price").toString().replaceAll("\\,",""));
                    Log.e("Debug","총가격 : "+ payamount);
                    pushmap.put(menuname, menuamount);
                }
                else {
                    if(Integer.parseInt(holder.getamount().getText().toString()) == 1)
                        payamount -= Integer.parseInt(hashMap.get("price").toString().replaceAll("\\,",""));
                    pushmap.remove(menuname);
                    menuamount = 0;
                }
                Log.e("Debug", menuname+"은 "+pushmap.get(menuname));
                holder.setamount(menuamount);
                Log.e("Debug","이름 : " + menuname + " 개수 : " + menuamount);
                Log.e("Debug","OO외  "+ (pushmap.size() > 0 ? (pushmap.size()-1) : 0));
            }
        });
        //+버튼 눌렀을 시
        holder.getmaxbtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Debug", "메뉴" + arrayList.get(pos));
                menuname = hashMap.get("name").toString();
                if(Integer.parseInt(holder.getamount().getText().toString()) >= 0) {
                    menuamount = Integer.parseInt(holder.getamount().getText().toString());
                    payamount += Integer.parseInt(hashMap.get("price").toString().replaceAll("\\,",""));
                    menuamount++;
                }
                holder.setamount(menuamount);
                pushmap.put(menuname, menuamount);
                Log.e("Debug", menuname+"은 "+pushmap.get(menuname));
                Log.e("Debug","총가격 : "+ payamount);
                Log.e("Debug","OO외  "+pushmap.size());
                Log.e("Debug","이름 : " + menuname + " 개수 : " + menuamount);
            }
        });
    }
    @NonNull

    // 전체 데이터 갯수 리턴
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public String getname() {
        cartList.add(pushmap);
        return cartList.get(0).toString();
    }

    public String getprice() {return String.valueOf(payamount);}

    public int getamount() {
        return (pushmap.size() > 0 ? (pushmap.size()-1) : 0);
    }

    public int getPayamount() {return payamount;}

    public void setMenu(ArrayList<HashMap<String, Object>> hashMaps) {
        arrayList = hashMaps;
        notifyDataSetChanged();
    }

    public void setStorageReference(StorageReference storageReference) {
        this.storageReference = storageReference;
    }

    //아이템 뷰를 저장하는 뷰홀더 클래스 -adapt
    class MenuItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView titleTextView;
        private TextView informationTextView;
        private TextView priceTextView;
        private TextView amount;

        public MenuItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.menu_image);
            titleTextView = itemView.findViewById(R.id.menu_name);
            informationTextView = itemView.findViewById(R.id.menu_information);
            priceTextView = itemView.findViewById(R.id.menu_price);
            minbtn = itemView.findViewById(R.id.menu_min);
            maxbtn = itemView.findViewById(R.id.menu_max);
            amount = itemView.findViewById(R.id.menu_amount);

            //아이템뷰 클릭시 이벤트트
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onitemClick(v, pos);
                        }
                    }
                }
            });
        }

        public Button getmaxbtn() { return maxbtn; }

        public Button getminbtn() { return minbtn; }

        public TextView getamount() { return amount; }

        public void setamount(int amount) {this.amount.setText(amount+"");}

        public ImageView getImageView() { return imageView; }

        public void setImageView(ImageView imageView) { this.imageView = imageView; }

        public TextView getTitleTextView() { return titleTextView; }

        public void setTitleTextView(TextView titleTextView) { this.titleTextView = titleTextView; }

        public TextView getInformationTextView() { return informationTextView; }

        public void setInformationTextView(TextView informationTextView) { this.informationTextView = informationTextView; }

        public TextView getPriceTextView() { return priceTextView; }

        public void setPriceTextView(TextView priceTextView) { this.priceTextView = priceTextView; }
    }
}
