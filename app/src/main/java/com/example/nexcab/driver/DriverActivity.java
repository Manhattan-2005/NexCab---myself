package com.example.nexcab.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.nexcab.R;
import com.example.nexcab.databinding.ActivityDriverBinding;

public class DriverActivity extends AppCompatActivity {

    private ActivityDriverBinding binding;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDriverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupDriverNavigation();
    }

    private void setupDriverNavigation() {
        navController = Navigation.findNavController(this, R.id.nav_host_driver_dashboard);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.driver_dashboard, R.id.driver_rides, R.id.navigation_inbox, R.id.navigation_settings)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}