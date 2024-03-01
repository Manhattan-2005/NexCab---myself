package com.example.nexcab;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.nexcab.databinding.FragmentPickupLocationBinding;
import com.example.nexcab.models.Ride;

public class PickupLocation extends Fragment {
FragmentPickupLocationBinding binding;
Intent intent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPickupLocationBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.pickupLocationString.getText().toString().equals("")){
                    // create bundle to pass object to next fragment
                    Bundle bundle = new Bundle();
                    bundle.putString("pickupLocation",binding.pickupLocationString.getText().toString());

                    Fragment fragment = new DropoffLocation();
                    // pass data to next fragment
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.location_frameLayout_id,fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    Log.d("onclick", "Fragment Succesfully replaced to DropoffLocation");
                }else{
                    Toast.makeText(getContext(), "Please select location!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}