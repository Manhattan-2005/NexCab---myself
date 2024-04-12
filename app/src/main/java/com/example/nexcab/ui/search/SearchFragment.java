package com.example.nexcab.ui.search;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nexcab.R;
import com.example.nexcab.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment {
    FragmentSearchBinding binding;
    String location;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.displaySearchResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = binding.locationEditText.getText().toString();

                if(!location.equals("")){
                    // Create an Intent to start SearchResultActivity
                    Intent intent = new Intent(getActivity(), SearchResultActivity.class);

                    // Pass any necessary data to the SearchResultActivity using Intent extras
                    intent.putExtra("location", location);

                    // Start the activity
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(), "Please enter location!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}