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
import com.example.mamman.MainActivity;
import com.example.mamman.Model.User;
import com.example.mamman.R;
import com.example.mamman.Verify_otp_Activity;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileReader;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class PhoneRegisterActivity extends AppCompatActivity {
    EditText phonenumber;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_register);

        //hide status bar start
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //hide status end

        final EditText inputMobile = findViewById(R.id.phonenumber);
        inputMobile.setText("+84 ");
        Button signUn = findViewById(R.id.button2);
        final ProgressBar progressBar= findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        signUn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputMobile.getText().toString().trim().isEmpty()) {
                    Toast.makeText(PhoneRegisterActivity.this, "Nhập SĐT", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                signUn.setVisibility(View.INVISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        inputMobile.getText().toString(),60, TimeUnit.SECONDS,
                        PhoneRegisterActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.GONE);
                                signUn.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressBar.setVisibility(View.GONE);
                                signUn.setVisibility(View.VISIBLE);
                                Toast.makeText(PhoneRegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                progressBar.setVisibility(View.GONE);
                                signUn.setVisibility(View.VISIBLE);


                                Intent intent = new Intent(PhoneRegisterActivity.this, Verify_otp_Activity.class);
                                intent.putExtra("mobile",inputMobile.getText().toString());
                                intent.putExtra("verificationId",s);
                                intent.putExtra("layout","register");
                                startActivity(intent);
                            }
                        }
                );


            }
        });
        /*
        phonenumber =(EditText)findViewById(R.id.phonenumber);
        databaseReference= FirebaseDatabase.getInstance().getReference("User");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Name","Hoa");
        hashMap.put("password","123");
        String note="011";

        databaseReference.child(note).push().setValue(hashMap);

         */

    }

    public void goToLogin(View view) {
        Intent intent = new Intent(PhoneRegisterActivity.this,PhoneLoginActivity.class);
        startActivity(intent);
        Animatoo.animateSlideDown(this);
        finish();
    }
    public void backToManPage(View view) {

        Intent intent=new Intent(PhoneRegisterActivity.this, MainActivity.class);
        startActivity(intent);
        Animatoo.animateSwipeRight(this);
        finish();
    }
}