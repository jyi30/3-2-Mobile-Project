package com.example.team_5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder>{
    private ArrayList<HashMap<String,Object>> orderList = new ArrayList<>();
    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_view, parent, false);

        OrderListAdapter.OrderListViewHolder itemViewHolder = new OrderListAdapter.OrderListViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListViewHolder holder, int position) {
        HashMap<String,Object> order = orderList.get(position);
        holder.orderId.setText("주문번호: "+order.get("order_id").toString());
        Timestamp timestamp = (Timestamp) order.get("order_time");
        holder.orderTime.setText("주문시간: "+timestamp.toDate().toLocaleString());
        holder.orderUid.setText("주문고객: "+order.get("user_id").toString());
        holder.orderPrice.setText("총가격: "+order.get("price").toString());
        OrderMenuAdapter orderMenuAdapter = new OrderMenuAdapter((HashMap<String,Object>) order.get("menu_map"));
        holder.menuListView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.menuListView.setAdapter(orderMenuAdapter);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void addOder(HashMap<String, Object> order){
        orderList.add(order);
        notifyDataSetChanged();
    }
    class OrderListViewHolder extends RecyclerView.ViewHolder{
        private TextView orderId;
        private TextView orderUid;
        private TextView orderTime;
        private TextView orderPrice;
        private RecyclerView menuListView;

        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.order_id);
            orderUid = itemView.findViewById(R.id.order_uid);
            orderTime = itemView.findViewById(R.id.order_time);
            orderPrice = itemView.findViewById(R.id.order_price);
            menuListView = itemView.findViewById(R.id.order_menu_list);
        }
    }


}
