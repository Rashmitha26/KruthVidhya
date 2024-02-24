package com.example.kruthvidhya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
    private TextView userlogin;
    private EditText userName,userPassword,userEmail,userage;
    private Button regbt;
    private FirebaseAuth firebaseAuth;
    String email,name,age,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
         firebaseAuth=FirebaseAuth.getInstance();
        userName=(EditText)findViewById(R.id.usernameet);
        userPassword=(EditText)findViewById(R.id.userpassword);
        userEmail=(EditText)findViewById(R.id.useremailet);
        regbt=(Button)findViewById(R.id.registerbt);
        userlogin=(TextView)findViewById(R.id.loginbacktv);
        userage=(EditText)findViewById(R.id.etage);

        regbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    String user_email=userEmail.getText().toString().trim();
                    String user_password=userPassword.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                                @Override
                                public void onComplete( @NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        sendUserData();
                                        //sendEmailVerification();
                                        firebaseAuth.signOut();
                                        Toast.makeText(register.this,"Registration successful",Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(new Intent(register.this,MainActivity.class));

                                    }
                                    else{
                                        Toast.makeText(register.this,"Registration failed",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                }
            }
        });


        userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotologin=new Intent(register.this,MainActivity.class);
                startActivity(gotologin);
            }
        });

    }
    private Boolean validate(){
        Boolean result=false;
        name=userName.getText().toString();
        password=userPassword.getText().toString();
        email=userEmail.getText().toString();
        age=userage.getText().toString().trim();

        if(name.isEmpty() || password.isEmpty() || email.isEmpty() || age.isEmpty()){
            Toast.makeText(this,"Please enter all the details", Toast.LENGTH_SHORT).show();
        }
        else{
            result=true;
            Toast.makeText(this,"Thanks for registering",Toast.LENGTH_SHORT).show();
        }
        return result;
    }
    private void sendEmailVerification(){
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        sendUserData();
                        Toast.makeText(register.this,"Successfully Registered.Verification mail has been sent",Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        //sendEmailVerification();
                        startActivity(new Intent(register.this,MainActivity.class));
                    }
                    else{
                        Toast.makeText(register.this,"Verification mail hasnt been sent",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());


        UserProfile userProfile = new UserProfile(age, email, name);
        myRef.setValue(userProfile);
    }


}
