package com.example.nexcab.userauthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.nexcab.R;
import com.example.nexcab.models.User;

public class UseIdentification extends AppCompatActivity {

    String role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_identification);

        // hide actionbar
        getSupportActionBar().hide();

        Button driver = findViewById(R.id.I_am_driver);
        Button rider = findViewById(R.id.I_am_rider);

        driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role = "Driver";
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
//                intent.putExtra("role",role);
                User.temprole = role;
                startActivity(intent);
            }
        });

        rider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role = "Rider";
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
//                intent.putExtra("role",role);
                User.temprole = role;
                startActivity(intent);
            }
        });
    }
}