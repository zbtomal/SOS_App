package com.example.sosapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FireBottomSheet extends BottomSheetDialogFragment {

    private Button btnSendLocation;
    private FusedLocationProviderClient fusedLocationClient;
    private Location currentLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fire_bottom_sheet, container, false);

        btnSendLocation = view.findViewById(R.id.btn_send_location);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        btnSendLocation.setOnClickListener(v -> {
            getLastKnownLocation();
            sendSmsToFireService();
            sendSmsToFriend();
            callFireService();
        });

        return view;
    }

    private void sendSmsToFireService() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("EmergencyContacts", Context.MODE_PRIVATE);
        String firePhoneNumber = sharedPreferences.getString("fire_number", "");

        if (firePhoneNumber.isEmpty()) {
            Toast.makeText(getActivity(), "No phone number for the fire service.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentLocation == null) {
            Toast.makeText(getActivity(), "Location is not available, please try again.", Toast.LENGTH_SHORT).show();
            return;
        }

        double latitude = currentLocation.getLatitude();
        double longitude = currentLocation.getLongitude();
        String locationMessage = "Emergency! My location: http://maps.google.com/maps?q=" + latitude + "," + longitude;

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(firePhoneNumber, null, locationMessage, null, null);
        Toast.makeText(getActivity(), "SMS sent to the fire service.", Toast.LENGTH_SHORT).show();
    }

    private void sendSmsToFriend() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("EmergencyContacts", Context.MODE_PRIVATE);
        String friendPhoneNumber = sharedPreferences.getString("friend_number", "");

        if (friendPhoneNumber.isEmpty()) {
            Toast.makeText(getActivity(), "No phone number for the friend.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentLocation == null) {
            Toast.makeText(getActivity(), "Location is not available, please try again.", Toast.LENGTH_SHORT).show();
            return;
        }

        double latitude = currentLocation.getLatitude();
        double longitude = currentLocation.getLongitude();
        String locationMessage = "Emergency! My location: http://maps.google.com/maps?q=" + latitude + "," + longitude;

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(friendPhoneNumber, null, locationMessage, null, null);
        Toast.makeText(getActivity(), "SMS sent to the friend.", Toast.LENGTH_SHORT).show();
    }

    private void callFireService() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:999"));

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 102);
            return;
        }

        startActivity(callIntent);
    }

    private void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), location -> {
                    if (location != null) {
                        currentLocation = location;
                    } else {
                        Toast.makeText(getActivity(), "Location is null", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed to get location", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastKnownLocation();
            } else {
                Toast.makeText(getActivity(), "Permission denied. Cannot get location.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 102) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callFireService();
            } else {
                Toast.makeText(getActivity(), "Permission denied. Cannot make call.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
