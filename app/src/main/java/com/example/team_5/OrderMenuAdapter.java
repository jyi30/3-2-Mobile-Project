package com.example.team_5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Set;

public class OrderMenuAdapter extends RecyclerView.Adapter<OrderMenuAdapter.OrderMenuViewHolder> {
    private HashMap<String, Object> menuMap;
    private Object[] menuIds;
    public OrderMenuAdapter(HashMap<String, Object> menuMap){
        this.menuMap = menuMap;
        menuIds = menuMap.keySet().toArray();
    }
    @NonNull
    @Override
    public OrderMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_menu_view, parent, false);

        OrderMenuAdapter.OrderMenuViewHolder menuItemViewHolder = new OrderMenuAdapter.OrderMenuViewHolder(view);
        return menuItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderMenuViewHolder holder, int position) {
        String mid =(String) menuIds[position];
        HashMap<String,Object> menu = (HashMap<String,Object>) menuMap.get(mid);
        holder.menuName.setText(menu.get("name").toString());
        holder.menuPrice.setText("가격: "+menu.get("price").toString());
        holder.menuCount.setText("개수: "+menu.get("count").toString());
    }

    @Override
    public int getItemCount() {
        return menuIds.length;
    }

    class OrderMenuViewHolder extends RecyclerView.ViewHolder{

        private TextView menuName;
        private TextView menuCount;
        private TextView menuPrice;
        public OrderMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            menuName = itemView.findViewById(R.id.order_menu_name);
            menuCount = itemView.findViewById(R.id.order_menu_count);
            menuPrice = itemView.findViewById(R.id.order_menu_price);
        }
    }
}
