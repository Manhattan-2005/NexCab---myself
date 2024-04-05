package com.example.nexcab.ui.rides;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.nexcab.R;
import com.example.nexcab.adapters.RideAdapter;
import com.example.nexcab.databinding.FragmentRideBinding;
import com.example.nexcab.models.Ride;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RideFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ride, container, false);

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        // Set up TabLayout with custom text color
        tabLayout.setTabTextColors(getResources().getColor(R.color.white), getResources().getColor(R.color.white));


        // Set up ViewPager with adapter
        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    // Adapter for ViewPager
    private class PagerAdapter extends FragmentPagerAdapter {

        private final String[] tabTitles = {"Upcoming", "Recent"};

        public PagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            // Return the appropriate Fragment based on the position
            switch (position) {
                case 0:
                    return new UpcomingRidesFragment();
                case 1:
                    return new RecentRidesFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

}