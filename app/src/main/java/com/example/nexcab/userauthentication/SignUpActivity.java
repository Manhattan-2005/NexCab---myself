package com.example.nexcab.userauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.nexcab.R;
import com.example.nexcab.databinding.ActivitySignUpBinding;
import com.example.nexcab.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // get role of user (driver / rider)
//        String role = intent.getStringExtra("role");

        // hide the Action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        // redirect to login page when clicked
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),LoginActivity.class);
//                intent.putExtra("role",role);
                startActivity(intent);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkFields()){
                    if(binding.password.getText() == binding.passwordRepeat.getText())
                        addUser();
                    else
                        Toast.makeText(SignUpActivity.this, "Password doesn't match!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SignUpActivity.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addUser(){
        firebaseAuth.createUserWithEmailAndPassword
                        (binding.email.getText().toString(),binding.password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Add to real time database if task is successful
                        if(task.isSuccessful()){
                            User user = new User(binding.firstname.getText().toString(),binding.lastname.getText().toString(),
                                    binding.email.getText().toString(),binding.password.getText().toString(),User.temprole);
                            user.setHasUpcomingRide(false);
                            Toast.makeText(SignUpActivity
                                    .this, "User Created Successfully!", Toast.LENGTH_SHORT).show();
                            String id = task.getResult().getUser().getUid();
                            firebaseDatabase.getReference().child("Users").child(id).setValue(user);

                            // navigate to login page
                            intent = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(SignUpActivity
                                    .this, Objects.requireNonNull(task.getException())
                                    .getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean checkFields(){
        return !TextUtils.isEmpty(binding.firstname.getText()) && !TextUtils.isEmpty(binding.lastname.getText()) &&
                !TextUtils.isEmpty(binding.email.getText()) && !TextUtils.isEmpty(binding.password.getText()) &&
                !TextUtils.isEmpty(binding.passwordRepeat.getText());
    }
}