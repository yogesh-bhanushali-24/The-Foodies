package com.example.casestudy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.casestudy.model.FoodModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class fooddisplayadapter extends FirebaseRecyclerAdapter<FoodModel, fooddisplayadapter.foodviewholder> {
    public fooddisplayadapter(@NonNull FirebaseRecyclerOptions<FoodModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull foodviewholder holder, int position, @NonNull FoodModel model) {
        holder.mainFoodName.setText(model.getName());
        holder.mainFoodPrice.setText(model.getPrice() + "₹");
        holder.mainFoodCategory.setText(model.getCategories().toUpperCase());
        holder.mainFoodDescription.setText(model.getDescription());
        Glide.with(holder.mainFoodImage.getContext()).load(model.getImage()).into(holder.mainFoodImage);
    }

    @NonNull
    @Override
    public foodviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homefoodcardview, parent, false);
        return new foodviewholder(view);
    }

    class foodviewholder extends RecyclerView.ViewHolder {
        ImageView mainFoodImage;
        TextView mainFoodName, mainFoodPrice, mainFoodDescription, mainFoodCategory;

        public foodviewholder(@NonNull View itemView) {
            super(itemView);
            mainFoodImage = itemView.findViewById(R.id.foodimage);
            mainFoodName = itemView.findViewById(R.id.foodname);
            mainFoodPrice = itemView.findViewById(R.id.foodprice);
            mainFoodDescription = itemView.findViewById(R.id.fooddescription);
            mainFoodCategory = itemView.findViewById(R.id.foodcategory);
        }
    }
}
