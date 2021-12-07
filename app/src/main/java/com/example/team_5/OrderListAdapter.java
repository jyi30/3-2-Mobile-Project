package com.example.team_5;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        holder.ordState.setText("주문상태 : 대기");

        holder.menuReject.setText("주문거부");
        holder.menuReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ordState.setText("주문상태 : 거부");
                Intent intent = new Intent(v.getContext(), Cancel.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("tid", order.get("tid").toString());
                intent.putExtra("price", order.get("price").toString());
                v.getContext().startActivity(intent);
            }
        });
        holder.menuAccept.setText("주문접수");
        holder.menuAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.ordState.getText().equals("주문상태 : 대기")) {
                    holder.ordState.setText("주문상태 : 접수");
                    holder.menuAccept.setText("주문완료");
                    holder.menuReject.setVisibility(View.GONE);
                }
                else
                    holder.ordState.setText("주문상태 : 완료");
            }
        });
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
        private Button menuReject;
        private Button menuAccept;
        private TextView ordState;

        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.order_id);
            orderUid = itemView.findViewById(R.id.order_uid);
            orderTime = itemView.findViewById(R.id.order_time);
            orderPrice = itemView.findViewById(R.id.order_price);
            menuListView = itemView.findViewById(R.id.order_menu_list);
            menuReject = itemView.findViewById(R.id.rejectBtn);
            menuAccept = itemView.findViewById(R.id.acceptBtn);
            ordState = itemView.findViewById(R.id.ordState);
        }
    }


}
