package com.maverick.medklick.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.maverick.medklick.R;
import com.maverick.medklick.others.DoctorAppointments;

import java.util.Calendar;

public class FindDoctorActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Spinner hospitalSpinner, departmentSpinner;
    private TextView dateOfAppointment;
    private Button submitButton;
    private String hospitalName, departmentName, date;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        // If the user is not logged in then start LoginActivity
        if (auth.getCurrentUser() == null) {
            // close this activity
            finish();
            // start login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        firebaseDatabase = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        databaseReference = firebaseDatabase.getReference("appointments");

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(FindDoctorActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        dateOfAppointment = findViewById(R.id.fd_date_editText);
        hospitalSpinner = findViewById(R.id.fd_hospital_spinner);
        departmentSpinner = findViewById(R.id.fd_department_spinner);
        submitButton = findViewById(R.id.fd_submit_button);

        dateOfAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int fdYear = calendar.get(Calendar.YEAR);
                int fdMonth = calendar.get(Calendar.MONTH);
                int fdDay = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(FindDoctorActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateOfAppointment.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, fdYear, fdMonth, fdDay);
                datePickerDialog.show();
            }
        });

        hospitalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hospitalName = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        departmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                departmentName = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = dateOfAppointment.getText().toString().trim();

                if (hospitalName.equals("")) {
                    Toast.makeText(FindDoctorActivity.this, "Please select Hospital", Toast.LENGTH_SHORT).show();
                } else if (departmentName.equals("")) {
                    Toast.makeText(FindDoctorActivity.this, "Please select Department", Toast.LENGTH_SHORT).show();
                } else if (date.equals("")) {
                    Toast.makeText(FindDoctorActivity.this, "Please enter Date of Appointment", Toast.LENGTH_SHORT).show();
                } else {
                    displayAppointmentInformation();
                    startActivity(new Intent(FindDoctorActivity.this, SelectDoctorActivity.class));
                }
            }
        });
    }

    private void displayAppointmentInformation() {
        String dateAppointment = dateOfAppointment.getText().toString().trim();
        FirebaseUser user = auth.getCurrentUser();

        DoctorAppointments doctorAppointments = new DoctorAppointments(hospitalName, departmentName, dateAppointment);
        databaseReference.child(user.getUid()).setValue(doctorAppointments);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }
}
