package com.example.mamman.EmailLoginRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mamman.HomeActivity;
import com.example.mamman.MainActivity;
import com.example.mamman.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EmailLoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText email,password;
    private Button signIn;

    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    private String userID;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        email= (EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        signIn=(Button)findViewById(R.id.button2);
        signIn.setOnClickListener(this);

        mAuth =FirebaseAuth.getInstance();
    }

    public void goToRegister(View view) {


        Intent intent= new Intent(EmailLoginActivity.this,EmailRegisterActivity.class);
        startActivity(intent);
        Animatoo.animateSwipeLeft(this);
        finish();

    }

    public void backToManPage(View view) {

        Intent intent=new Intent(EmailLoginActivity.this, MainActivity.class);
        startActivity(intent);
        Animatoo.animateSwipeRight(this);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button2:
                userLogin();
        }
    }

    private void userLogin() {
        String semail=email.getText().toString().trim();
        String spassword=password.getText().toString().trim();

        if(semail.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(semail).matches()){
            email.setError("please enter a valid email");
            email.requestFocus();
            return;
        }
        if(spassword.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        if(spassword.length()<6){
            password.setError("toi thieu 6 ki tu");
            password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(semail,spassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                    if(!user.isEmailVerified()){
                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EmailLoginActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();

                                firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                                databaseReference= FirebaseDatabase.getInstance().getReference("User");
                                userID=firebaseUser.getUid();
                                //Toast.makeText(getApplicationContext(),userID,Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("mobile",userID);
                                startActivity(intent);
                            }
                        });
                    }else {
                        Toast.makeText(EmailLoginActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("mobile",user);
                        startActivity(intent);
                    }




                }else {
                    Toast.makeText(EmailLoginActivity.this,"Dang nhap that bai",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}