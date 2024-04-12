package com.example.nexcab;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.nexcab.databinding.FragmentPickDateTimeBinding;
import com.example.nexcab.databinding.FragmentPickupLocationBinding;

import java.util.Calendar;
import java.util.Objects;


public class PickDateTimeFragment extends Fragment {

   FragmentPickDateTimeBinding binding;
    EditText pickDate;
    EditText pickTime;
    String date,time;
    Bundle bundle;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPickDateTimeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bundle = new Bundle();
        binding.pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDate(getContext());
            }
        });

        binding.pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTime(getContext());
            }
        });
        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.pickDate.getText().toString().equals("") && !binding.pickTime.getText().toString().equals("")) {
                    // set ParentFragment
                    bundle.putString("ParentFragment","PickDateTimeFragment");

                    //set bundle values
                    bundle.putString("Date",date);
                    bundle.putString("Time",time);

                    Fragment pickupLocation = new PickupLocation();
                    pickupLocation.setArguments(bundle);

                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.prebook_container, pickupLocation );
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else {
                    Toast.makeText(getContext(), "Please select date and time!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void handleDate(Context context){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date = year + "-" + month + "-" + day;
                binding.pickDate.setText(date);
            }
        },year,month,day);
        datePickerDialog.show();
    }

    public void handleTime(Context context){
        int hour = 0;
        int minute = 0;
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time = hourOfDay + ":" + minute;
                binding.pickTime.setText(time);
            }
        },hour,minute,true);
        timePickerDialog.show();
    }
}