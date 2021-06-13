package com.example.casestudy;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.casestudy.model.CartModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import in.dd4you.appsconfig.DD4YouConfig;


public class cartFragment extends Fragment implements com.example.casestudy.cartdisplayadapter.totalCalling, LocationListener {
    private RecyclerView cartDisplayRecycler;
    cartdisplayadapter cartdisplayadapter;
    private TextView GrandTotalTv;
    private DD4YouConfig dd4YouConfig;
    String StoredGrandTotal;
    MaterialButton addressChoice, PlaceOrder;
    LocationManager locationManager;
    String l1;
    String StoringAddress;
    String NameCounting, PriceCounting, QuantityCounting, TotalCounting;
    String CustomerAddress;
    ProgressDialog progressDialog;
    ProgressDialog OrderPlaceProgressDialog;
    String RandomId;
    String CustomerName, CustomerEmail, CustomerMobile;
    // TextView CheckingValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Address");
        progressDialog.setMessage("Storing Address");

        OrderPlaceProgressDialog = new ProgressDialog(getContext());
        OrderPlaceProgressDialog.setTitle("Place Order");
        OrderPlaceProgressDialog.setMessage("Order Ready..");

        GrandTotalTv = view.findViewById(R.id.GrandTotal);
        addressChoice = view.findViewById(R.id.SelectAddressBtn);
        PlaceOrder = view.findViewById(R.id.placeOrder);

        dd4YouConfig = new DD4YouConfig(getContext());
        // CheckingValue=view.findViewById(R.id.checkingValue);


        //recycler view
        cartDisplayRecycler = view.findViewById(R.id.cartRecycler);
        cartDisplayRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        //end recycler view

        //show cart view data
        FirebaseRecyclerOptions<CartModel> options =
                new FirebaseRecyclerOptions.Builder<CartModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()), CartModel.class)
                        .build();

        cartdisplayadapter = new cartdisplayadapter(options, () -> bill());
        cartDisplayRecycler.setAdapter(cartdisplayadapter);
        //end show cart view data

        //Grand Total Function calling
        bill();
        //end Grand Total Function calling

        //address choice By location or Manually
        addressChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog box for address
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.address_choice_dialog);
                dialog.setCancelable(false);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                ImageView crossDialog = dialog.findViewById(R.id.CloseAddress);
                EditText addressEdit = dialog.findViewById(R.id.LocationAddress);
                MaterialButton submitAddress = dialog.findViewById(R.id.SubmitAddressET);
                TextView separator = dialog.findViewById(R.id.separatorTv);
                MaterialButton LocationBtn = dialog.findViewById(R.id.MapLocation);

                DatabaseReference FetchAddressReference = FirebaseDatabase.getInstance().getReference().child("Address").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                FetchAddressReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            addressEdit.setText(snapshot.child("address").getValue().toString());
                            Toast.makeText(getContext(), "Already Stored Your Address", Toast.LENGTH_SHORT).show();
                            separator.setVisibility(View.INVISIBLE);
                            LocationBtn.setVisibility(View.GONE);
                        } else {
                            separator.setVisibility(View.VISIBLE);
                            LocationBtn.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

                //cross image for close or dismiss dialog box
                crossDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                //end cross image for close or dismiss dialog box

                //Address Submit button
                submitAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog.show();
                        if (!addressEdit.getText().toString().isEmpty()) {

                            StoringAddress = addressEdit.getText().toString();
                            DatabaseReference AddressReference = FirebaseDatabase.getInstance().getReference().child("Address").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            AddressReference.child("address").setValue(StoringAddress);
                            progressDialog.dismiss();
                            dialog.dismiss();
                            Toast.makeText(getContext(), "Address Successfully Added ", Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Please Enter Address", Toast.LENGTH_SHORT).show();
                            LocationBtn.setVisibility(View.VISIBLE);
                            separator.setVisibility(View.VISIBLE);
                        }
                    }
                });
                //end Address Submit button

                //Current Location Button click
                LocationBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Location Function Calling
                        getLocation();
                        grantPermission();
                        checkLocationIsEnableOrNot();
                        //end Location Function Calling
                        addressEdit.setText(l1);
                        Toast.makeText(getContext(), "If Location Address Is Not Shown Click Button Once Again", Toast.LENGTH_SHORT).show();
                        if (!addressEdit.getText().toString().isEmpty()) {
                            LocationBtn.setVisibility(View.GONE);
                            separator.setVisibility(View.GONE);

                        } else {

                        }
                    }
                });
                //end Current Location Button click
                dialog.show();
                //dialog box for address
            }
        });
        //end address choice

        fetchAddress();
        fetchUserDetail();
        fetchRandomId();
        fetchCartItems();


        //Place Order
        PlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (StoredGrandTotal.equals("0")) {
                    Toast.makeText(getContext(), "Cart Is Empty", Toast.LENGTH_SHORT).show();
                } else if (CustomerAddress == null) {
                    Toast.makeText(getContext(), "Insert Address", Toast.LENGTH_SHORT).show();
                } else {

                    OrderPlaceProgressDialog.show();
                    DatabaseReference FinalPlaceOrder = FirebaseDatabase.getInstance().getReference().child("Order");
                    HashMap<String, String> PlaceMap = new HashMap<>();
                    PlaceMap.put("OrderID", RandomId);
                    PlaceMap.put("Address", CustomerAddress);
                    PlaceMap.put("CustomerName", CustomerName);
                    PlaceMap.put("CustomerEmail", CustomerEmail);
                    PlaceMap.put("CustomerMobile", CustomerMobile);
                    PlaceMap.put("ItemNames", NameCounting.replace("null\n", ""));
                    PlaceMap.put("ItemPrice", PriceCounting.replace("null\n", ""));
                    PlaceMap.put("ItemQuantity", QuantityCounting.replace("null\n", ""));
                    PlaceMap.put("ItemTotal", TotalCounting.replace("null\n", ""));
                    PlaceMap.put("GrandTotal", StoredGrandTotal);
                    PlaceMap.put("Status", "Pending");
                    FinalPlaceOrder.push().setValue(PlaceMap);

                    //Notification code for order place
                    Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "My Notification");
                    builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
                    builder.setSound(soundUri);
                    builder.setContentTitle("The Foodies Team");
                    builder.setContentText("Dear " + CustomerName + " Your Order Place Successfully Worth of " + StoredGrandTotal + "₹");
                    builder.setSmallIcon(R.drawable.notificationcircle);
                    builder.setAutoCancel(true);
                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getContext());
                    managerCompat.notify(1, builder.build());
                    //end Notification code for order place

                    DatabaseReference ClearCart = FirebaseDatabase.getInstance().getReference().child("cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    ClearCart.removeValue();
                    bill();

                    OrderPlaceProgressDialog.dismiss();
                    Toast.makeText(getContext(), "Order Successfully placed", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //end Place Order

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        cartdisplayadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        cartdisplayadapter.stopListening();
    }

    //this function is for Grand total
    public void bill() {

        //total bill
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ValueEventListener valueEventListener = new ValueEventListener() {
            int countTotal = 0;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {

                    String total = ds.child("total").getValue().toString();
                    int total1 = Integer.parseInt(total);
                    countTotal = countTotal + total1;
                }
                StoredGrandTotal = String.valueOf(countTotal);

                GrandTotalTv.setText(String.valueOf(countTotal) + "₹");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        reference.addListenerForSingleValueEvent(valueEventListener);
        //end total bill
    }
    //end this function is for Grand total


    //Location Functions
    private void getLocation() {
        try {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5, (LocationListener) this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void checkLocationIsEnableOrNot() {
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = false;
        boolean networkEnabled = false;
        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!gpsEnabled && !networkEnabled) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Enable GPS Service")
                    .setCancelable(false)
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }).setNegativeButton("Cancel", null)
                    .show();
        }
    }

    private void grantPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext().getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            l1 = addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
    //end Location Functions


    //fetching UserDetails
    private void fetchUserDetail() {
        DatabaseReference PlaceUserReference = FirebaseDatabase.getInstance().getReference().child("UserDetail").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        PlaceUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CustomerName = snapshot.child("name").getValue().toString();
                CustomerEmail = snapshot.child("email").getValue().toString();
                CustomerMobile = snapshot.child("mobile").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //exit fetching UserDetails

    //fetching address
    private void fetchAddress() {
        DatabaseReference PlaceAddressReference = FirebaseDatabase.getInstance().getReference().child("Address").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        PlaceAddressReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    CustomerAddress = snapshot.child("address").getValue().toString();
                } else {
                    Toast.makeText(getContext(), "Click One more Time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    //exit fetching address

    //random id
    private void fetchRandomId() {
        RandomId = dd4YouConfig.generateRandomString(12).toUpperCase();
    }
    //exit random id

    //fetching cartItems
    private void fetchCartItems() {
        DatabaseReference PlaceCartReference = FirebaseDatabase.getInstance().getReference().child("cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        PlaceCartReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String CustomerItemName = ds.child("name").getValue().toString();
                    String CustomerItemPrice = ds.child("price").getValue().toString();
                    String CustomerItemQuantity = ds.child("quantity").getValue().toString();
                    String CustomerItemTotal = ds.child("total").getValue().toString();
                    NameCounting = NameCounting + "\n" + CustomerItemName;
                    PriceCounting = PriceCounting + "\n" + CustomerItemPrice;
                    QuantityCounting = QuantityCounting + "\n" + CustomerItemQuantity;
                    TotalCounting = TotalCounting + "\n" + CustomerItemTotal;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
    //exit fetching cartItems


}