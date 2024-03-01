package com.example.nexcab;

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
import android.widget.Toast;

import com.example.nexcab.databinding.FragmentDropoffLocationBinding;

public class DropoffLocation extends Fragment {

    FragmentDropoffLocationBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDropoffLocationBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.dropoffLocationString.getText().toString().equals("")){
                    // create bundle to pass object to next fragment
                    Bundle bundle = getArguments();
                    bundle.putString("dropoffLocation",binding.dropoffLocationString.getText().toString());

                    Fragment fragment = new ConfirmRide();
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