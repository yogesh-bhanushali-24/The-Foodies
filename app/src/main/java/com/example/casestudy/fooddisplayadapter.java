package com.example.casestudy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.casestudy.model.FoodModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;

public class fooddisplayadapter extends FirebaseRecyclerAdapter<FoodModel, fooddisplayadapter.foodviewholder> {
    public fooddisplayadapter(@NonNull FirebaseRecyclerOptions<FoodModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final foodviewholder holder, final int position, @NonNull final FoodModel model) {
        holder.mainFoodName.setText(model.getName());
        holder.mainFoodPrice.setText(model.getPrice() + "₹");
        holder.mainFoodCategory.setText(model.getCategories().toUpperCase());
        holder.mainFoodDescription.setText(model.getDescription());
        Glide.with(holder.mainFoodImage.getContext()).load(model.getImage()).into(holder.mainFoodImage);


        //increment decrement
        holder.mainIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.count++;
                holder.mainFoodItemNumber.setText("" + holder.count);
            }
        });

        holder.mainDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.count <= 0) {
                    holder.count = 0;
                } else holder.count--;
                holder.mainFoodItemNumber.setText("" + holder.count);

            }
        });

        //end


        //click on cart
        holder.mainFoodCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.mainFoodCart.getContext(), "Food got to cart", Toast.LENGTH_SHORT).show();
            }
        });
        //
    }

    @NonNull
    @Override
    public foodviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homefoodcardview, parent, false);
        return new foodviewholder(view);
    }

    class foodviewholder extends RecyclerView.ViewHolder {
        ImageView mainFoodImage, mainFoodCart;
        TextView mainFoodName, mainFoodPrice, mainFoodDescription, mainFoodCategory;
        TextView mainFoodItemNumber;
        MaterialButton mainIncrement, mainDecrement;
        int count = 0;


        public foodviewholder(@NonNull final View itemView) {
            super(itemView);
            mainFoodImage = itemView.findViewById(R.id.foodimage);
            mainFoodName = itemView.findViewById(R.id.foodname);
            mainFoodPrice = itemView.findViewById(R.id.foodprice);
            mainFoodDescription = itemView.findViewById(R.id.fooddescription);
            mainFoodCategory = itemView.findViewById(R.id.foodcategory);
            mainFoodCart = itemView.findViewById(R.id.foodCart);
            mainIncrement = itemView.findViewById(R.id.incrementItem);
            mainDecrement = itemView.findViewById(R.id.decrementItem);
            mainFoodItemNumber = itemView.findViewById(R.id.itemNumber);

            //increment decrement
//            mainIncrement.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(final View v) {
//                    count++;
//                    mainFoodItemNumber.setText(""+count);
//                }
//            });
//
//            mainDecrement.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(final View v) {
//                    if (count<=0)
//                    {
//                        count=0;
//                    }
//                    else count--;
//                    mainFoodItemNumber.setText(""+count);
//
//                }
//            });

            //end


        }


    }


}
