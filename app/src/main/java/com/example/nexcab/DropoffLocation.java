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

import java.util.Objects;

public class DropoffLocation extends Fragment {
    Bundle bundle;
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
                    bundle = getArguments();
                    String parentFragment = bundle.getString("ParentFragment");

                    bundle.putString("dropoffLocation",binding.dropoffLocationString.getText().toString());
                    if(Objects.equals(parentFragment, "PickupLocation")) {
                        bundle.putString("ParentFragment","DropoffLocation");
                    } else if(Objects.equals(parentFragment, "PickupLocationPreebook")) {
                        bundle.putString("ParentFragment","DropoffLocationPreebook");
                    }

                    Fragment fragment = new ConfirmRide();
                    // pass data to next fragment
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    if(Objects.equals(parentFragment, "PickupLocation")) {
                        fragmentTransaction.replace(R.id.location_frameLayout_id, fragment);
                    } else if(Objects.equals(parentFragment, "PickupLocationPreebook")) {
                        fragmentTransaction.replace(R.id.prebook_container,fragment);
                    }

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