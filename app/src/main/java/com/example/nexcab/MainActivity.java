package com.example.nexcab;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.customview.widget.Openable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.nexcab.databinding.ActivityMainBinding;
import com.example.nexcab.driver.DriverActivity;
import com.example.nexcab.models.User;
import com.example.nexcab.temp.RideStartedActivity;
import com.example.nexcab.temp.StartRideMapsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;

    private static final int REQUEST_CODE_NOTIFICATION_ACCESS = 101;
    private static String DEVICE_REGISTRATION_TOKEN;
    private FirebaseDatabase database;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // hide the action bar
        getSupportActionBar().hide();

        getDeviceRegistrationToken();

        // get database and user reference
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        setupRiderNavigation();

        // Handle intent if called from booking activity
        Intent intent1 = getIntent();
        String source = intent1.getStringExtra("Source");
        if (source != null && source.equals("booking")) {
            navController.navigate(R.id.navigation_rides);
        }
    }

    @NonNull
    @Override
    public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            return new OnBackInvokedDispatcher() {
                @Override
                public void registerOnBackInvokedCallback(int priority, @NonNull OnBackInvokedCallback callback) {

                }

                @Override
                public void unregisterOnBackInvokedCallback(@NonNull OnBackInvokedCallback callback) {

                }
            };
        }
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //
        if(FirebaseMessageReceiver.callRideStartedActivity){
            Intent intent = new Intent(this, StartRideMapsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            FirebaseMessageReceiver.callRideStartedActivity = false;
            finish();
        }
    }

    // Set up navigation for driver
//    private void setupDriverNavigation() {
//        navController = Navigation.findNavController(this, R.id.nav_host_driver_dashboard);
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.driver_dashboard, R.id.driver_rides, R.id.navigation_inbox, R.id.navigation_settings)
//                .build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);
//    }

    // Set up navigation for rider
    private void setupRiderNavigation() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_customer_home_page);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_rides, R.id.navigation_inbox, R.id.navigation_settings)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    public void getDeviceRegistrationToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("App token: ", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        DEVICE_REGISTRATION_TOKEN = token;
                        database.getReference().child("Users").child(auth.getUid()).child("token").setValue(token);
                        Log.d("App token: ", token);
//                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
