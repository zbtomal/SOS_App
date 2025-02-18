package com.example.sosapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class HospitalBottomSheet extends BottomSheetDialogFragment {

    private Button btnHospital1, btnHospital2, btnHospital3, btnHospital4, btnHospital5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_hospitals, container, false);

        // Initialize buttons
        btnHospital1 = view.findViewById(R.id.btn_hospital_1);
        btnHospital2 = view.findViewById(R.id.btn_hospital_2);
        btnHospital3 = view.findViewById(R.id.btn_hospital_3);
        btnHospital4 = view.findViewById(R.id.btn_hospital_4);
        btnHospital5 = view.findViewById(R.id.btn_hospital_5);

        // Load hospital names and set them to buttons
        loadHospitalNames();

        // Set click listeners for each hospital button
        setupHospitalButtons();

        return view;
    }

    private void loadHospitalNames() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("EmergencyContacts", Context.MODE_PRIVATE);

        // Get hospital names from SharedPreferences
        String hospital1Name = sharedPreferences.getString("hospital_name_1", "Hospital 1");
        String hospital2Name = sharedPreferences.getString("hospital_name_2", "Hospital 2");
        String hospital3Name = sharedPreferences.getString("hospital_name_3", "Hospital 3");
        String hospital4Name = sharedPreferences.getString("hospital_name_4", "Hospital 4");
        String hospital5Name = sharedPreferences.getString("hospital_name_5", "Hospital 5");

        // Set the buttons with hospital names
        btnHospital1.setText(hospital1Name);
        btnHospital2.setText(hospital2Name);
        btnHospital3.setText(hospital3Name);
        btnHospital4.setText(hospital4Name);
        btnHospital5.setText(hospital5Name);
    }

    private void setupHospitalButtons() {
        btnHospital1.setOnClickListener(v -> sendSms(getHospitalPhoneNumber(1)));
        btnHospital2.setOnClickListener(v -> sendSms(getHospitalPhoneNumber(2)));
        btnHospital3.setOnClickListener(v -> sendSms(getHospitalPhoneNumber(3)));
        btnHospital4.setOnClickListener(v -> sendSms(getHospitalPhoneNumber(4)));
        btnHospital5.setOnClickListener(v -> sendSms(getHospitalPhoneNumber(5)));
    }

    private String getHospitalPhoneNumber(int hospitalIndex) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("EmergencyContacts", Context.MODE_PRIVATE);
        return sharedPreferences.getString("hospital_number_" + hospitalIndex, "");
    }

    private void sendSms(String hospitalPhoneNumber) {
        if (hospitalPhoneNumber.isEmpty()) {
            Toast.makeText(getActivity(), "No phone number for this hospital.", Toast.LENGTH_SHORT).show();
            return;
        }

        String message = "Emergency! I need an ambulance. My location: [your location here]";
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(hospitalPhoneNumber, null, message, null, null);
        Toast.makeText(getActivity(), "SMS sent to " + hospitalPhoneNumber, Toast.LENGTH_SHORT).show();
    }
}
