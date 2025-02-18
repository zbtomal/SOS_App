package com.example.sosapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SaveContactsActivity extends AppCompatActivity {

    private EditText[] hospitalNames = new EditText[5];
    private EditText[] hospitalNumbers = new EditText[5];
    private EditText policeNumber, fireNumber, friendNumber;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_contacts);

        // Initialize hospital EditTexts
        hospitalNames[0] = findViewById(R.id.hospital_name_1);
        hospitalNames[1] = findViewById(R.id.hospital_name_2);
        hospitalNames[2] = findViewById(R.id.hospital_name_3);
        hospitalNames[3] = findViewById(R.id.hospital_name_4);
        hospitalNames[4] = findViewById(R.id.hospital_name_5);

        hospitalNumbers[0] = findViewById(R.id.hospital_number_1);
        hospitalNumbers[1] = findViewById(R.id.hospital_number_2);
        hospitalNumbers[2] = findViewById(R.id.hospital_number_3);
        hospitalNumbers[3] = findViewById(R.id.hospital_number_4);
        hospitalNumbers[4] = findViewById(R.id.hospital_number_5);

        policeNumber = findViewById(R.id.police_number);
        fireNumber = findViewById(R.id.fire_number);
        friendNumber = findViewById(R.id.friend_number);
        saveButton = findViewById(R.id.btn_save);

        loadSavedData(); // Load previously saved data

        saveButton.setOnClickListener(v -> saveData());
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("EmergencyContacts", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (int i = 1; i <= 5; i++) {
            String hospitalName = hospitalNames[i-1].getText().toString().trim();
            // Save the hospital name or a default value if the name is empty
            editor.putString("hospital_name_" + i, hospitalName.isEmpty() ? "<Empty>" : hospitalName);
            editor.putString("hospital_number_" + i, hospitalNumbers[i-1].getText().toString());
        }

        editor.putString("police_number", policeNumber.getText().toString());
        editor.putString("fire_number", fireNumber.getText().toString());
        editor.putString("friend_number", friendNumber.getText().toString());
        editor.apply();

        Toast.makeText(this, "Contacts Saved!", Toast.LENGTH_SHORT).show();
    }

    private void loadSavedData() {
        SharedPreferences sharedPreferences = getSharedPreferences("EmergencyContacts", Context.MODE_PRIVATE);

        for (int i = 1; i <=5; i++) {
            hospitalNames[i-1].setText(sharedPreferences.getString("hospital_name_" + i, "<Empty>"));
            hospitalNumbers[i-1].setText(sharedPreferences.getString("hospital_number_" + i, ""));
        }

        policeNumber.setText(sharedPreferences.getString("police_number", ""));
        fireNumber.setText(sharedPreferences.getString("fire_number", ""));
        friendNumber.setText(sharedPreferences.getString("friend_number", ""));
    }
}
