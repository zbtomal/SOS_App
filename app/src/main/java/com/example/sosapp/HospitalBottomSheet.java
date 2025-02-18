package com.example.sosapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class HospitalBottomSheet extends BottomSheetDialogFragment {

    private Button btnHospital1, btnHospital2, btnHospital3, btnHospital4, btnHospital5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_hospitals, container, false);

        // Initialize buttons
        btnHospital1 = view.findViewById(R.id.btn_hospital_1);
        btnHospital2 = view.findViewById(R.id.btn_hospital_2);
        btnHospital3 = view.findViewById(R.id.btn_hospital_3);
        btnHospital4 = view.findViewById(R.id.btn_hospital_4);
        btnHospital5 = view.findViewById(R.id.btn_hospital_5);

        // Load saved hospital names
        loadHospitalNames();

        return view;
    }

    private void loadHospitalNames() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("EmergencyContacts", Context.MODE_PRIVATE);

        // Get saved hospital names, show "<Empty>" if not saved
        btnHospital1.setText(getHospitalName(sharedPreferences, "hospital_name_1"));
        btnHospital2.setText(getHospitalName(sharedPreferences, "hospital_name_2"));
        btnHospital3.setText(getHospitalName(sharedPreferences, "hospital_name_3"));
        btnHospital4.setText(getHospitalName(sharedPreferences, "hospital_name_4"));
        btnHospital5.setText(getHospitalName(sharedPreferences, "hospital_name_5"));
    }

    private String getHospitalName(SharedPreferences sharedPreferences, String key) {
        String name = sharedPreferences.getString(key, "");
        return name.isEmpty() ? "<Empty>" : name;
    }
}
