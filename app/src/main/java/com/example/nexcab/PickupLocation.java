package com.example.nexcab;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.nexcab.adapters.PlaceAutocompleteAdapter;
import com.example.nexcab.databinding.FragmentPickupLocationBinding;
import com.example.nexcab.models.Ride;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;



import java.util.Objects;

public class PickupLocation extends Fragment implements OnMapReadyCallback {
    FragmentPickupLocationBinding binding;
    Fragment parentFragment;
    Intent intent;
    Bundle bundle;

    //map
    private GoogleMap mMap;
    private AutoCompleteTextView autoCompleteTextView;
    private PlacesClient placesClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPickupLocationBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), "AIzaSyA6Y2BX-uF7ttWvfucY47nRsIUuz_LpJJE");
        }
        placesClient = Places.createClient(requireContext());

        autoCompleteTextView = rootView.findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setAdapter(new PlaceAutocompleteAdapter(requireContext(), placesClient));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.pickupLocationString.getText().toString().equals("")){
                    sendPickupLocation();
                }else{
                    Toast.makeText(getContext(), "Please select location!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void sendPickupLocation(){
        // create bundle to pass object to next fragment
        bundle = getArguments();
        String parentFragment = "";

        // get ParentFragment and set it according to instant ride or preebook
        if(bundle == null) {
            bundle = new Bundle();
            bundle.putString("ParentFragment","PickupLocation");
        }
        else{
            // intent came from PickDateTimeFragment
            parentFragment = bundle.getString("ParentFragment");
            bundle.putString("ParentFragment","PickupLocationPreebook");
        }
        //set the bundle values
        bundle.putString("pickupLocation", binding.pickupLocationString.getText().toString());

        Fragment fragment = new DropoffLocation();
        // pass data to next fragment
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(Objects.equals(parentFragment, "")) {
            Log.d("Parent Fragment", "Parent Fragment is null ");
            fragmentTransaction.replace(R.id.location_frameLayout_id, fragment);
        }
        else
            fragmentTransaction.replace(R.id.prebook_container,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        Log.d("onclick", "Fragment Succesfully replaced to DropoffLocation");
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Enable the "My Location" layer if the user has granted permission.
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
    }
}