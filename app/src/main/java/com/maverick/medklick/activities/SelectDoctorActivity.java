package com.maverick.medklick.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.maverick.medklick.R;
import com.maverick.medklick.others.DoctorList;
import com.maverick.medklick.others.DoctorListAdapter;

import java.util.ArrayList;

public class SelectDoctorActivity extends AppCompatActivity {

    private String doctorName, doctorFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_doctor);

        final ArrayList<DoctorList> doctorListsArrayList = new ArrayList<>();

        doctorListsArrayList.add(new DoctorList(R.string.doctor_a, R.string.fee_a));
        doctorListsArrayList.add(new DoctorList(R.string.doctor_b, R.string.fee_b));
        doctorListsArrayList.add(new DoctorList(R.string.doctor_c, R.string.fee_c));
        doctorListsArrayList.add(new DoctorList(R.string.doctor_d, R.string.fee_d));

        DoctorListAdapter doctorListAdapter = new DoctorListAdapter(SelectDoctorActivity.this, doctorListsArrayList);

        ListView selectDoctorListView = findViewById(R.id.select_doctor_ListView);

        selectDoctorListView.setAdapter(doctorListAdapter);

        selectDoctorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DoctorList doctorList = doctorListsArrayList.get(position);

                Toast.makeText(SelectDoctorActivity.this, "Appointment Booked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SelectDoctorActivity.this, MainActivity.class));
            }
        });
    }
}
