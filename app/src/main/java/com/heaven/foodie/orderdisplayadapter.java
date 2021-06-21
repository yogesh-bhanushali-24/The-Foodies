package com.heaven.foodie;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.heaven.foodie.model.OrderModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class orderdisplayadapter extends FirebaseRecyclerAdapter<OrderModel, orderdisplayadapter.OrderViewHolder> {
    public orderdisplayadapter(@NonNull FirebaseRecyclerOptions<OrderModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull OrderModel model) {
        holder.CustomerOrder.setText(model.getOrderID());
        holder.CustomerName.setText(model.getCustomerName());
        holder.CustomerEmail.setText(model.getCustomerEmail());
        holder.CustomerNumber.setText(model.getCustomerMobile());
        holder.CustomerAddress.setText(model.getAddress());
        holder.ItemName.setText(model.getItemNames());
        holder.ItemQuantity.setText(model.getItemQuantity());
        holder.ItemPrice.setText(model.getItemPrice());
        holder.ItemPriceTotal.setText(model.getItemTotal());
        holder.ItemGrandTotal.setText(model.getGrandTotal() + "₹");
        if (model.getStatus().equals("Pending")) {
            holder.Status.setText(model.getStatus());
            holder.Status.setTextColor(Color.parseColor("#FFFF00"));
            // holder.cardView.setCardBackgroundColor(Color.parseColor("#FFCE2F"));

            //  #FF7A51,#7885CB

        } else if (model.getStatus().equals("Delivered")) {
            holder.Status.setText(model.getStatus());
            holder.Status.setTextColor(Color.parseColor("#006400"));
            //  holder.cardView.setCardBackgroundColor(Color.parseColor("#43B64D"));
        } else if (model.getStatus().equals("Dispatch")) {
            holder.Status.setText(model.getStatus());
            holder.Status.setTextColor(Color.parseColor("#000080"));
            //  holder.cardView.setCardBackgroundColor(Color.parseColor("#35BCBA"));

        } else {
            holder.Status.setText(model.getStatus());
            holder.Status.setTextColor(Color.parseColor("#8B0000"));
            // holder.cardView.setCardBackgroundColor(Color.parseColor("#FD8302"));
        }


    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderscardview, parent, false);
        return new OrderViewHolder(view);
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView CustomerOrder, CustomerName, CustomerEmail, CustomerNumber, CustomerAddress, ItemName, ItemQuantity, ItemPrice, ItemPriceTotal, ItemGrandTotal, Status;


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            CustomerOrder = itemView.findViewById(R.id.tvCustomerOrder);
            CustomerName = itemView.findViewById(R.id.tvCustomerName);
            CustomerEmail = itemView.findViewById(R.id.tvCustomerEmail);
            CustomerNumber = itemView.findViewById(R.id.tvCustomerNumber);
            CustomerAddress = itemView.findViewById(R.id.tvCustomerAddress);
            ItemName = itemView.findViewById(R.id.tvItemName);
            ItemQuantity = itemView.findViewById(R.id.tvItemQuantity);
            ItemPrice = itemView.findViewById(R.id.tvItemPrice);
            ItemPriceTotal = itemView.findViewById(R.id.tvItemPriceTotal);
            ItemGrandTotal = itemView.findViewById(R.id.tvItemGrandTotal);
            Status = itemView.findViewById(R.id.tvStatus);
        }
    }
}
