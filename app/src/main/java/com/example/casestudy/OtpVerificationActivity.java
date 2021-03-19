package com.example.casestudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class OtpVerificationActivity extends AppCompatActivity {

    MaterialTextView otpVerification,otpTimer;
    TextInputEditText etotp;
    String phonenumber;
    String otpid;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    String TAG = "OtpVerificationActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        getSupportActionBar().hide();

        otpVerification=findViewById(R.id.txtVerify);
        otpTimer=findViewById(R.id.txtTime);
        etotp=findViewById(R.id.etOTP);
        phonenumber = getIntent().getStringExtra("mobile");
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(OtpVerificationActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Login to your account");

        //Non editable otp
        etotp.setEnabled(false);


        otpVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etotp.getText().toString().isEmpty() || etotp.getText().toString().length()!=6){
                    Toast.makeText(OtpVerificationActivity.this, "Wrong OTP", Toast.LENGTH_SHORT).show();
                }else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpid, etotp.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

        new CountDownTimer(60000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                    otpTimer.setText(""+millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {

            }
        }.start();


        initiateOtp("+91"+phonenumber);
        etotp.setEnabled(true);

        if (mAuth.getCurrentUser()!=null){
            Intent intent = new Intent(OtpVerificationActivity.this, FirstUserDetail.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }




    private void initiateOtp(String phoneNumber) {
        progressDialog.show();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(OtpVerificationActivity.this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                otpid = s;
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(OtpVerificationActivity.this, TAG + " : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.show();
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(OtpVerificationActivity.this, "Login Success", Toast.LENGTH_SHORT).show();

                            //Add value in database

//                            Users user = new Users();
//                            user.setUserName(binding.etName.getText().toString());
//                            String id = task.getResult().getUser().getUid();
//                            database.getReference().child("Users").child(id).setValue(user);

                            startActivity(new Intent(OtpVerificationActivity.this, FirstUserDetail.class));
                            finish();
                            progressDialog.dismiss();

                            //FirebaseUser user = task.getResult().getUser();
                            // ...
                        } else {

                            Toast.makeText(OtpVerificationActivity.this, TAG + " : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
}