package com.example.mamman.PhoneLoginRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mamman.EmailLoginRegister.EmailLoginActivity;
import com.example.mamman.HomeActivity;
import com.example.mamman.MainActivity;
import com.example.mamman.R;
import com.example.mamman.Verify_otp_Activity;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class PhoneLoginActivity extends AppCompatActivity {
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        //hide status bar start
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //hide status end

        final EditText inputMobile = findViewById(R.id.phonenumber);
        inputMobile.setText("+84 ");
        Button signIn = findViewById(R.id.button2);
        final ProgressBar progressBar= findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputMobile.getText().toString().trim().isEmpty()) {
                    Toast.makeText(PhoneLoginActivity.this, "Nhập SĐT", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                signIn.setVisibility(View.INVISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        inputMobile.getText().toString(),60, TimeUnit.SECONDS,
                        PhoneLoginActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.GONE);
                                signIn.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressBar.setVisibility(View.GONE);
                                signIn.setVisibility(View.VISIBLE);
                                Toast.makeText(PhoneLoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                progressBar.setVisibility(View.GONE);
                                signIn.setVisibility(View.VISIBLE);
                                //Toast.makeText(PhoneLoginActivity.this,s,Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(PhoneLoginActivity.this, Verify_otp_Activity.class);
                                intent.putExtra("mobile",inputMobile.getText().toString());
                                intent.putExtra("verificationId",s);
                                intent.putExtra("layout","login");
                                startActivity(intent);
                            }
                        }
                );


            }
        });
    }

    public void goToRegister(View view) {

        Intent intent = new Intent(PhoneLoginActivity.this,PhoneRegisterActivity.class);
        startActivity(intent);
        Animatoo.animateSlideDown(this);
        finish();

    }
    public void backToManPage(View view) {

        Intent intent=new Intent(PhoneLoginActivity.this, MainActivity.class);
        startActivity(intent);
        Animatoo.animateSwipeRight(this);
        finish();
    }
}