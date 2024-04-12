package com.example.nexcab.temp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nexcab.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RideStartedActivity extends AppCompatActivity {

    private String receiverToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_started);

        Button button = findViewById(R.id.endRideButton);
//        button.setOnClickListener(v -> endRide());
    }

    @SuppressLint("StaticFieldLeak")
    public void endRide(){
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject jsonNotif = new JSONObject();
        JSONObject wholeObj = new JSONObject();
        try {
            jsonNotif.put("title","Ride Ended");
            jsonNotif.put("body","Ride has been Ended!");
            wholeObj.put("to",receiverToken);
            wholeObj.put("notification",jsonNotif);
        } catch (JSONException e) {
            Log.d("ConfirmRideActivity: SendNotification ", "Exception while sending");
            Toast.makeText(this, "Exception while sending notification!", Toast.LENGTH_SHORT).show();
        }

        RequestBody requestBody = RequestBody.create(mediaType,wholeObj.toString());
        Request request = new Request.Builder().url("https://fcm.googleapis.com/fcm/send")
                .post(requestBody)
                .addHeader("Authorization","key=AAAAcx0Yvnw:APA91bEQjo2hXkWqcsyaOTLvZO14C8REtJ6bgN7X4kk4vKx05TEL5ShNB4PXE-fQrxYzKVAhXLggJqoBHBIbdke469Pcril1Le98HuvqgTOnnMNcGnSS-ZNvNJ0YrbNtnasTswNZf4N5")
                .addHeader("Content-Type","application/json").build();

        AsyncTask<Void, Void, Response> execute;
        execute = new AsyncTask<Void, Void, Response>() {
            @Override
            protected Response doInBackground(Void... voids) {
                try {
                    return client.newCall(request).execute();
                } catch (IOException e) {
                    Log.e("RideStartedActivity", "Exception while sending response", e);
                    return null; // or handle the exception as needed
                }
            }

            @Override
            protected void onPostExecute(Response response) {
                if (response != null) {
                    // Handle the response here
                } else {
                    Toast.makeText(getApplicationContext(), "Exception while sending notification!", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
}