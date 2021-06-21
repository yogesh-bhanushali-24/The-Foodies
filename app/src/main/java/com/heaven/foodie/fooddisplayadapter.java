package com.heaven.foodie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.heaven.foodie.model.FoodModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;

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


        //image show dialog box
        holder.mainFoodImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPlus dialogPlus = DialogPlus.newDialog(holder.mainFoodImage.getContext())
                        .setContentHolder(new ViewHolder(R.layout.image_dialog))
                        .create();

                View view = dialogPlus.getHolderView();
                ImageView imgeDialog = view.findViewById(R.id.imageShow);
                Glide.with(imgeDialog.getContext()).load(model.getImage()).into(imgeDialog);
                dialogPlus.show();
            }
        });
        //end image show dialog box


        //increment decrement
        holder.mainIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.count++;
                if (holder.count >= 10) {
                    Toast.makeText(holder.mainIncrement.getContext(), "You Can't insert more than 10", Toast.LENGTH_SHORT).show();
                    holder.count = 10;
                }

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

        //end increment decrement


        //click on cart
        holder.mainFoodCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.mainFoodCart.getContext(), "Food got to cart", Toast.LENGTH_SHORT).show();

                if (holder.count == 0) {
                    Toast.makeText(holder.mainFoodCart.getContext(), "Insert Quantity", Toast.LENGTH_SHORT).show();
                } else {

                    //Insert Food in current user cart view
                    String cartName = model.getName();
                    String cartPrice = model.getPrice();
                    String cartCategory = model.getCategories();
                    String cartDescription = model.getDescription();
                    String cartQuantity = String.valueOf(holder.count);
                    String cartImage = model.getImage();

                    int cartFoodPrice = Integer.parseInt(model.getPrice());
                    int FoodQuantityPrise = cartFoodPrice * holder.count;
                    String cartTotal = String.valueOf(FoodQuantityPrise);

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("name", cartName);
                    hashMap.put("price", cartPrice);
                    hashMap.put("category", cartCategory);
                    hashMap.put("description", cartDescription);
                    hashMap.put("quantity", cartQuantity);
                    hashMap.put("image", cartImage);
                    hashMap.put("total", cartTotal);
                    holder.referenceCart.child(holder.auth.getCurrentUser().getUid()).push().setValue(hashMap);

                    //end Insert Food in current user cart view
                }

            }
        });
        //end click on cart
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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference referenceCart = database.getReference().child("cart");
        FirebaseAuth auth = FirebaseAuth.getInstance();


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


        }


    }


}
