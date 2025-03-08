package com.example.sosapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hospitalButton = findViewById<Button>(R.id.btn_hospital)
        hospitalButton.setOnClickListener { v: View? ->
            val bottomSheet = HospitalBottomSheet()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        val policeButton = findViewById<Button>(R.id.btn_police)
        policeButton.setOnClickListener { v: View? ->
            val bottomSheet = PoliceBottomSheet()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        val fireServiceButton = findViewById<Button>(R.id.btn_fireservice)
        fireServiceButton.setOnClickListener { v: View? ->
            val bottomSheet = FireBottomSheet()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }


        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { v: View? ->
            val intent =
                Intent(
                    this@MainActivity,
                    SaveContactsActivity::class.java
                )
            startActivity(intent)
        }
    }
}
