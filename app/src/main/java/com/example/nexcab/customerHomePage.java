package com.example.nexcab;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link customerHomePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class customerHomePage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public customerHomePage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment customerHomePage.
     */
    // TODO: Rename and change types and number of parameters
    public static customerHomePage newInstance(String param1, String param2) {
        customerHomePage fragment = new customerHomePage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_home_page, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // find both buttons
        Button instant_ride = view.findViewById(R.id.instant_ride_button);
        Button prebook = view.findViewById(R.id.prebook_button);
        // When the buttons are clicked
        instant_ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        prebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(view);
            }
        });
    }
    public void replaceFragment(View view){
        Fragment instant_ride = new CustomerInstantRide();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        // Remove all views before replacing the fragment
        FrameLayout frameLayout = view.findViewById(R.id.customer_home_page_frame_layout);
        frameLayout.removeAllViews();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.customer_home_page_frame_layout,instant_ride);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}