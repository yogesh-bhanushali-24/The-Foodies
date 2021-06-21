package com.heaven.foodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.heaven.foodie.model.UserModel;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class FirstUserDetail extends AppCompatActivity {

    EditText etName, etNumber, etEmail;
    MaterialButton SubDetail;
    FirebaseDatabase userDb = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = userDb.getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference referenceCheckUser = userDb.getReference().child("UserDetail");
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_user_detail);
        getSupportActionBar().hide();

        progressDialog = new ProgressDialog(FirstUserDetail.this);
        progressDialog.setTitle("Fetching Detail");
        progressDialog.setMessage("Redirect to Welcome page");

//      the Existing user value Focus false
        etName = findViewById(R.id.Username);
        etNumber = findViewById(R.id.existingNumber);
        etEmail = findViewById(R.id.UserEmail);
        SubDetail = findViewById(R.id.btnGoOn);

        String mobile = getIntent().getStringExtra("mobile");
        etNumber.setText(mobile);

        //this code for session and this code is used when user is already create account on this app


        referenceCheckUser.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String alreadyExistNumber = etNumber.getText().toString();
                String Email = snapshot.child("email").getValue().toString();
                String Name = snapshot.child("name").getValue().toString();

                if (alreadyExistNumber.equals(etNumber.getText().toString())) {
                    progressDialog.show();
                    etName.setText(Name);
                    etEmail.setText(Email);
                }
                Intent intent = new Intent(FirstUserDetail.this, MainActivity.class);
                startActivity(intent);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //end session


        SubDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String NamePattern = "^[a-zA-Z ]*$";
                // String EmailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]";

                if (etName.getText().toString().trim().isEmpty()) {
                    etName.setError("Enter Your Name");
                    return;

                }
                if (etEmail.getText().toString().isEmpty()) {
                    etEmail.setError("Enter Your Email");
                    return;
                } else if (!etName.getText().toString().isEmpty() && !etEmail.getText().toString().isEmpty()) {
                    if (etName.getText().toString().trim().matches(NamePattern) &&
                            Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
                        String uName = etName.getText().toString().trim();
                        String uNumber = etNumber.getText().toString();
                        String uEmail = etEmail.getText().toString().trim();
                        HashMap<String, String> userMap = new HashMap<>();
                        userMap.put("Name", uName);
                        userMap.put("Mobile", uNumber);
                        userMap.put("Email", uEmail);
//                        databaseReference.push().setValue(userMap);
                        UserModel userModel = new UserModel(uName, uNumber, uEmail);
                        databaseReference.child("UserDetail").child(auth.getUid()).setValue(userModel);

                        Intent intent = new Intent(FirstUserDetail.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(FirstUserDetail.this, "Invalid", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });

    }


}