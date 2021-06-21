package com.heaven.foodie;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileFragment extends Fragment {


    private TextView UserNameTv, UserNumberTv, UserEmailTv;
    private MaterialButton UpdateDetailBtn, LogoutUserBtn;
    FirebaseDatabase userDb;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    //ProgressDialog progressDialog=new ProgressDialog(getActivity().getApplicationContext());


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);


        UserNumberTv = view.findViewById(R.id.tvUserNumber);
        UserEmailTv = view.findViewById(R.id.tvUserEmail);
        UserNameTv = view.findViewById(R.id.tvUserName);
        UpdateDetailBtn = view.findViewById(R.id.UpdateExistDetail);
        LogoutUserBtn = view.findViewById(R.id.UserLogout);


        userDb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        String Userid = auth.getCurrentUser().getUid();
        databaseReference = userDb.getReference().child("UserDetail").child(Userid);

        //Show Login User Detail
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Email = snapshot.child("email").getValue().toString();
                String Name = snapshot.child("name").getValue().toString();
                String Mobile = snapshot.child("mobile").getValue().toString();
                UserEmailTv.setText(Email);
                UserNameTv.setText(Name);
                UserNumberTv.setText(Mobile);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //end Show Login User Detail


        //this code is part of notification

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        //end this code is part of notification


        //update current user detail
        UpdateDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String updatedEmail = UserEmailTv.getText().toString().trim();
                String updatedName = UserNameTv.getText().toString().trim();
                String updatedNumber = UserNumberTv.getText().toString();


                if (!updatedEmail.isEmpty() && !updatedName.isEmpty() && !updatedNumber.isEmpty()) {
                    databaseReference.child("email").setValue(updatedEmail);
                    databaseReference.child("name").setValue(updatedName);
                    databaseReference.child("mobile").setValue(updatedNumber);
                    UpdateDetailBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                    Toast.makeText(getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();

                    //Notification code
                    Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "My Notification");
                    builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
                    builder.setSound(soundUri);
                    builder.setContentTitle("The Foodie Heaven Team");
                    builder.setContentText("Dear " + updatedName + " Your Information Updated Successfully");
                    builder.setSmallIcon(R.drawable.notificationcircle);
                    builder.setAutoCancel(true);
                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getContext());
                    managerCompat.notify(1, builder.build());
                    //end Notification code


                } else {
                    Toast.makeText(getContext(), "Empty Field Not Valid", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //end update current user detail


        //Logout user
        LogoutUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are your sure want to logout?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        auth.signOut();
                        Intent intent = new Intent(getContext(), SplashScreen.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();


            }
        });
        //end Logout user


        return view;


    }


}