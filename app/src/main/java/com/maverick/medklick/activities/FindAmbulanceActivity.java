package com.maverick.medklick.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.maverick.medklick.R;

public class FindAmbulanceActivity extends AppCompatActivity {

    private FloatingActionButton ambulanceControlFab, aryanAmbulanceFab, cityHospitalAmbulanceFab,
            dootAmbulanceFab, emergencyAmbulanceFab, ighAmbulanceFab, jeevanRekhaMobileCenterFab,
            rajasthanSevaSadanAmbulanceFab, rghAmbulanceFab, sudhaNursingHomeFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_ambulance);

        // Ambulance Control Unit
        ambulanceControlFab = findViewById(R.id.fab_call_ambulance_control);
        ambulanceControlFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:102"));  //opens a Dialer app with the number already typed

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        // Aryan Ambulance
        aryanAmbulanceFab = findViewById(R.id.fab_call_aryan_ambulance);
        aryanAmbulanceFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+919437202495"));  //opens a Dialer app with the number already typed

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        // City Hospital Ambulance
        cityHospitalAmbulanceFab = findViewById(R.id.fab_call_city_hospital_ambulance);
        cityHospitalAmbulanceFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+919861188432"));  //opens a Dialer app with the number already typed

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        // Doot Ambulance
        dootAmbulanceFab = findViewById(R.id.fab_call_doot_ambulance);
        dootAmbulanceFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+919438095627"));  //opens a Dialer app with the number already typed

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        // Emergency Ambulance
        emergencyAmbulanceFab = findViewById(R.id.fab_call_emergency_ambulance);
        emergencyAmbulanceFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:108"));  //opens a Dialer app with the number already typed

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        // IGH Ambulance
        ighAmbulanceFab = findViewById(R.id.fab_call_igh_ambulance);
        ighAmbulanceFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+916612646202"));  //opens a Dialer app with the number already typed

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        // Jeevan Rekha Mobile Center
        jeevanRekhaMobileCenterFab = findViewById(R.id.fab_call_jeevan_rekha_mobile_center);
        jeevanRekhaMobileCenterFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+916612480139"));  //opens a Dialer app with the number already typed

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        // Rajasthan Seva Sadan Ambulance
        rajasthanSevaSadanAmbulanceFab = findViewById(R.id.fab_call_rajasthan_seva_sadan_ambulance);
        rajasthanSevaSadanAmbulanceFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+916612420879"));  //opens a Dialer app with the number already typed

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        // RGH Ambulance
        rghAmbulanceFab = findViewById(R.id.fab_call_rgh_ambulance);
        rghAmbulanceFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+916612507939"));  //opens a Dialer app with the number already typed

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        // Sudha Nursing Home Ambulance
        sudhaNursingHomeFab = findViewById(R.id.fab_call_sudha_nursing_home_ambulance);
        sudhaNursingHomeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+919437043554"));  //opens a Dialer app with the number already typed

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

    }
}
