package com.example.mamman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mamman.Fragments.OrdersFragment;
import com.example.mamman.Model.User;
import com.example.mamman.PhoneLoginRegister.PhoneLoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Verify_otp_Activity extends AppCompatActivity {

    private EditText inputcode1,inputcode2,inputcode3,inputcode4,inputcode5,inputcode6;
    private String verificationId;
    DatabaseReference databaseReference;
    private String userID;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp_);

        inputcode1= findViewById(R.id.inputCode1);
        inputcode2= findViewById(R.id.inputCode2);
        inputcode3= findViewById(R.id.inputCode3);
        inputcode4= findViewById(R.id.inputCode4);
        inputcode5= findViewById(R.id.inputCode5);
        inputcode6= findViewById(R.id.inputCode6);

        setupOTPInput();

        final ProgressBar progressBar=findViewById(R.id.progressBar2);
        final Button buttonVerify=findViewById(R.id.button2);

        verificationId = getIntent().getStringExtra("verificationId");

        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputcode1.getText().toString().trim().isEmpty()
                || inputcode2.getText().toString().trim().isEmpty()
                || inputcode3.getText().toString().trim().isEmpty()
                || inputcode4.getText().toString().trim().isEmpty()
                || inputcode5.getText().toString().trim().isEmpty()
                || inputcode6.getText().toString().trim().isEmpty()){
                    Toast.makeText(Verify_otp_Activity.this,"Nhap Code",Toast.LENGTH_SHORT).show();
                    return;
                }
                String code= inputcode1.getText().toString() +
                        inputcode2.getText().toString() +
                        inputcode3.getText().toString() +
                        inputcode4.getText().toString() +
                        inputcode5.getText().toString() +
                        inputcode6.getText().toString();

                if (verificationId !=null){
                    progressBar.setVisibility(View.VISIBLE);
                    buttonVerify.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationId,
                            code
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    buttonVerify.setVisibility(View.VISIBLE);
                                    if(task.isSuccessful()){
                                        //Toast.makeText(Verify_otp_Activity.this,getIntent().getStringExtra("layout"),Toast.LENGTH_SHORT).show();

                                        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                                        databaseReference= FirebaseDatabase.getInstance().getReference("User");
                                        userID=firebaseUser.getUid();
                                        //Toast.makeText(getApplicationContext(),userID,Toast.LENGTH_LONG).show();

                                        if(getIntent().getStringExtra("layout").equals("register")){
                                            ThemUser();
                                        }



                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.putExtra("mobile",userID);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(Verify_otp_Activity.this,"code sai",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });

        findViewById(R.id.textResendOTP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        getIntent().getStringExtra("mobile"),60, TimeUnit.SECONDS,
                        Verify_otp_Activity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                            }

                            @Override
                            public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                verificationId = newVerificationId;
                                Toast.makeText(Verify_otp_Activity.this, "OTP send", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

        });

    }

    private void ThemUser(){
        databaseReference= FirebaseDatabase.getInstance().getReference("User");
        User user = new User(null,null,null,null,getIntent().getStringExtra("mobile"),null,null);
        databaseReference.child(userID).setValue(user);
    }


    private void setupOTPInput(){
        inputcode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputcode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputcode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputcode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputcode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputcode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputcode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}