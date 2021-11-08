package com.example.mamman.EmailLoginRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mamman.HomeActivity;
import com.example.mamman.MainActivity;
import com.example.mamman.Model.User;
import com.example.mamman.R;
import com.example.mamman.ReusableCodeForAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;


public class EmailRegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText name,email,password;
    private Button signup;

    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    private String userID;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_register);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAuth= FirebaseAuth.getInstance();

        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        signup=(Button)findViewById(R.id.button2);
        signup.setOnClickListener(this);


    }

    public void goToLogin(View view) {

        Intent intent = new Intent(EmailRegisterActivity.this, EmailLoginActivity.class);
        startActivity(intent);
        Animatoo.animateSwipeLeft(this);
        finish();
    }

    public void backToManPage(View view) {

        Intent intent = new Intent(EmailRegisterActivity.this, MainActivity.class);
        startActivity(intent);
        Animatoo.animateSwipeRight(this);
        finish();
    }

    public void goToHomePage() {
        Intent intent=new Intent(EmailRegisterActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
        Animatoo.animateSwipeLeft(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button2:
                registerUser();
                break;
        }
    }


    private void registerUser() {
        String sname=name.getText().toString().trim();
        String semail=email.getText().toString().trim();
        String spassword=password.getText().toString().trim();

        if(sname.isEmpty()){
            name.setError("name is requied");
            name.requestFocus();
            return;
        }
        if(semail.isEmpty()){
            email.setError("email is requied");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(semail).matches()){
            email.setError("please provide valid email");
            email.requestFocus();
            return;
        }

        if(spassword.isEmpty()){
            password.setError("password is requied");
            password.requestFocus();
            return;
        }
        if(spassword.length()<6){
            password.setError("toi thieu 6 ki tu");
            password.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(semail,spassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    User user = new User(null,sname,semail,spassword,null,null,null);

                    FirebaseDatabase.getInstance().getReference("User")
                            .child(FirebaseAuth.getInstance().getCurrentUser()
                                    .getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(EmailRegisterActivity.this,"Dang ky thanh cong",Toast.LENGTH_SHORT).show();
                                firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                                databaseReference= FirebaseDatabase.getInstance().getReference("User");
                                userID=firebaseUser.getUid();
                                //Toast.makeText(getApplicationContext(),userID,Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("mobile",userID);
                                startActivity(intent);
                            }else {
                                Toast.makeText(EmailRegisterActivity.this,"Dang ky that bai",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(EmailRegisterActivity.this,"Dang ky khong thanh cong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}