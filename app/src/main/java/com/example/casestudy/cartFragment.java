package com.example.casestudy;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;
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
import java.util.List;
import java.util.Locale;


public class cartFragment extends Fragment implements com.example.casestudy.cartdisplayadapter.totalCalling, LocationListener {
    private RecyclerView cartDisplayRecycler;
    cartdisplayadapter cartdisplayadapter;
    private TextView GrandTotalTv;
    String TempTotal;
    MaterialButton addressChoice;
    LocationManager locationManager;
    EditText perfectAddress;
    String l1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);


        //never used
        perfectAddress = view.findViewById(R.id.UserAddress);
        //never used

        GrandTotalTv = view.findViewById(R.id.GrandTotal);
        addressChoice = view.findViewById(R.id.SelectAddressBtn);

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
                dialog.getWindow().getAttributes().windowAnimations =
                        android.R.style.Animation_Dialog;

                ImageView crossDialog = dialog.findViewById(R.id.CloseAddress);
                EditText addressEdit = dialog.findViewById(R.id.LocationAddress);
                MaterialButton submitAddress = dialog.findViewById(R.id.SubmitAddressET);
                TextView separator = dialog.findViewById(R.id.separatorTv);
                MaterialButton LocationBtn = dialog.findViewById(R.id.MapLocation);

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
                        if (!addressEdit.getText().toString().isEmpty()) {
                            Toast.makeText(getContext(), "Address Successfully Added ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Please Enter Address", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getContext(), "Please Press Button One more time for getting current location", Toast.LENGTH_SHORT).show();

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
                TempTotal = String.valueOf(countTotal);

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
            //  perfectAddress.setText(l1);


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


}