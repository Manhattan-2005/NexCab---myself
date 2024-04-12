package com.example.nexcab;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.nexcab.models.Ride;
import com.example.nexcab.temp.RideStartedActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FirebaseMessageReceiver extends FirebaseMessagingService {
    FirebaseAuth auth;
    FirebaseDatabase database;

    public static boolean callRideStartedActivity = false;
    String activityName, ride_id, user_id;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public FirebaseMessageReceiver(){
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        Log.d("FirebaseMessageReceiver", "Inside FirebaseMessageReceiver ");
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("FirebaseMessageReceiver", "From: " + remoteMessage.getFrom());

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("FirebaseMessageReceiver: ", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // start the ride

        if (remoteMessage.getData().size() > 0) {
            Log.d("FirebaseMessageReceiver", "inside message received");
            Map<String, String> data = remoteMessage.getData();
            if (data.containsKey("action") && data.get("action").equals("start_activity")) {
                Log.d("FirebaseMessageReceiver", "inside nested condition ");
                activityName = data.get("activity_name");
                ride_id = data.get("ride_id");

                Ride.id = ride_id;
                Ride.pickup = data.get("pickup_location");

                user_id = data.get("ride_user_id");
                moveRideToRecentRides(ride_id,user_id);
                startClientActivity(activityName);
            }
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
//        sendNotification(remoteMessage.getFrom(),remoteMessage.getNotification().getBody());
//        sendNotification(remoteMessage.getNotification().getBody());


    }

    private void moveRideToRecentRides(String ride_id,String user_id) {
        DatabaseReference pickupLocationRef = database.getReference().child("Users")
                .child(user_id).child("Rides");
        DatabaseReference bookedRidesRef = database.getReference().child("Users")
                .child(user_id).child("Rides").child("recent");


        // Get the ride details from pickup location
        pickupLocationRef.child(ride_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Check if the ride details exist
                if (dataSnapshot.exists()) {
                    // Get the ride details
                    Object rideDetails = dataSnapshot.getValue();

                    // Remove the ride details from pickup location
                    pickupLocationRef.child(ride_id).removeValue();

                    // Add the ride details to booked rides
                    bookedRidesRef.child(ride_id).setValue(rideDetails);
                } else {
                    // Handle if ride details do not exist
                    Log.d("MoveRide", "Ride details not found at pickup location");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
                Log.e("MoveRide", "Database error: " + databaseError.getMessage());
            }
        });
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        // Handle token refresh (e.g., update server with the new token)
        updateToken(token);
    }

    private void updateToken(String token) {
        database.getReference().child("Users").child(auth.getUid()).child("token").setValue(token);
    }

    private void sendNotification(String from, String body){
        new Handler(Looper.getMainLooper()).post(new Runnable(){

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),body,Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void startClientActivity(String activityName) {
        callRideStartedActivity = true;
    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_IMMUTABLE);

        String channelId = "Demo channel";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("FCM Message")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
