package com.example.casestudy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.casestudy.model.CartModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class cartdisplayadapter extends FirebaseRecyclerAdapter<CartModel, cartdisplayadapter.cartViewHolder> {

    private totalCalling TCalling;

    public cartdisplayadapter(@NonNull FirebaseRecyclerOptions<CartModel> options, totalCalling calling) {
        super(options);
        //this variable store totalCalling interface
        TCalling = calling;
        //end this variable store totalCalling interface
    }


    @Override
    protected void onBindViewHolder(@NonNull final cartViewHolder holder, final int position, @NonNull final CartModel model) {
        //show cart food
        holder.mainCartFoodName.setText(model.getName());
        holder.mainCartFoodItem.setText(model.getQuantity());
        holder.mainCartFoodTotal.setText(model.getTotal() + "₹");
        holder.mainCartFoodCategory.setText(model.getCategory());
        holder.mainCartFoodDescription.setText(model.getDescription());
        holder.mainCartFoodPrice.setText(model.getPrice());
        Glide.with(holder.mainCartFoodImage.getContext()).load(model.getImage()).into(holder.mainCartFoodImage);
        //end show cart food


        //increment and decrement in cart
        holder.mainCartIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String existQuantity = model.getQuantity();
                int count = Integer.parseInt(existQuantity);
                count++;
                if (count >= 10) {
                    Toast.makeText(holder.mainCartIncrement.getContext(), "You Can't insert more than 10", Toast.LENGTH_SHORT).show();
                    count = 10;
                }
                holder.mainCartFoodItem.setText("" + count);
                String updatedQuantity = String.valueOf(count);
                int priceInt = Integer.parseInt(model.getPrice());
                int calculation = priceInt * count;
                String Total = String.valueOf(calculation);
                Map<String, Object> map = new HashMap<>();
                map.put("quantity", updatedQuantity);
                map.put("total", Total);
                FirebaseDatabase.getInstance().getReference().child("cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child(getRef(position).getKey()).updateChildren(map);

                //calling bill() from cartFragment
                TCalling.bill();
                //end calling bill() from cartFragment

            }
        });

        holder.mainCartDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String existQuantity = model.getQuantity();
                int count = Integer.parseInt(existQuantity);
                if (count <= 0) {
                    count = 0;
                } else count--;
                holder.mainCartFoodItem.setText("" + count);

                String updatedQuantity = String.valueOf(count);
                int priceInt = Integer.parseInt(model.getPrice());
                int calculation = priceInt * count;
                String Total = String.valueOf(calculation);
                Map<String, Object> map = new HashMap<>();
                map.put("quantity", updatedQuantity);
                map.put("total", Total);
                FirebaseDatabase.getInstance().getReference().child("cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child(getRef(position).getKey()).updateChildren(map);

                //calling bill() from cartFragment
                TCalling.bill();
                //end calling bill() from cartFragment

            }
        });
        //end increment and decrement in cart


        //delete cart food item
        holder.mainCartFoodDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    FirebaseDatabase.getInstance().getReference().child("cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child(getRef(position).getKey()).removeValue();

                    Toast.makeText(holder.mainCartFoodDelete.getContext(), "Delete food successfully", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //calling bill() from cartFragment
                TCalling.bill();
                //end calling bill() from cartFragment
            }
        });
        //end delete cart food item


    }

    @NonNull
    @Override
    public cartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartfoodcardview, parent, false);
        return new cartViewHolder(view);
    }

    class cartViewHolder extends RecyclerView.ViewHolder {
        TextView mainCartFoodName, mainCartFoodPrice, mainCartFoodDescription, mainCartFoodCategory, mainCartFoodTotal;
        TextView mainCartFoodItem;
        ImageView mainCartFoodImage;
        ImageView mainCartFoodDelete;
        MaterialButton mainCartIncrement, mainCartDecrement;
        public cartViewHolder(@NonNull View itemView) {
            super(itemView);
            mainCartFoodName = itemView.findViewById(R.id.cartfoodname);
            mainCartFoodPrice = itemView.findViewById(R.id.cartfoodprice);
            mainCartFoodCategory = itemView.findViewById(R.id.cartfoodcategory);
            mainCartFoodDescription = itemView.findViewById(R.id.cartfooddescription);
            mainCartFoodTotal = itemView.findViewById(R.id.cartfoodtotal);
            mainCartFoodImage = itemView.findViewById(R.id.cartfoodimage);
            mainCartFoodItem = itemView.findViewById(R.id.cartitemNumber);
            mainCartFoodDelete = itemView.findViewById(R.id.cartfooddelete);
            mainCartIncrement = itemView.findViewById(R.id.cartincrementItem);
            mainCartDecrement = itemView.findViewById(R.id.cartdecrementItem);
        }
    }

    public interface totalCalling {
        //this function belonging to cartFragment bill()
        void bill();
        //end this function belonging to cartFragment bill()
    }

}
