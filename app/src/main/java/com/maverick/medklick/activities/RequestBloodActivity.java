package com.maverick.medklick.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.maverick.medklick.R;
import com.maverick.medklick.others.BloodRequests;

import java.util.Calendar;

public class RequestBloodActivity extends AppCompatActivity {

    private Button requestBloodSubmitButton;
    private Spinner bloodGroupSpinner, bloodUnitsSpinner;
    private ProgressBar progressBar;
    private String bloodGroup, bloodUnits;
    private TextView dateOfRequirementTextView;

    private DatePickerDialog datePickerDialog;

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_blood);

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
        databaseReference = firebaseDatabase.getReference("blood_requests");

        dateOfRequirementTextView = findViewById(R.id.request_blood_date_textView);
        bloodGroupSpinner = findViewById(R.id.request_blood_blood_group_spinner);
        bloodUnitsSpinner = findViewById(R.id.blood_units_spinner);
        requestBloodSubmitButton = findViewById(R.id.request_blood_submit_button);
        progressBar = findViewById(R.id.request_blood_progressBar);

        dateOfRequirementTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int rbYear = calendar.get(Calendar.YEAR);
                int rbMonth = calendar.get(Calendar.MONTH);
                int rbDay = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(RequestBloodActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateOfRequirementTextView.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, rbYear, rbMonth, rbDay);
                datePickerDialog.show();
            }
        });

        requestBloodSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                saveRequests();
                progressBar.setVisibility(View.GONE);
                startActivity(new Intent(RequestBloodActivity.this, MainActivity.class));
                finish();
            }
        });

        bloodGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bloodGroup = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bloodUnitsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bloodUnits = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void saveRequests() {
        FirebaseUser user = auth.getCurrentUser();

        String dateOfRequirement = dateOfRequirementTextView.getText().toString().trim();

        BloodRequests bloodRequests = new BloodRequests(bloodGroup, bloodUnits, dateOfRequirement);
        databaseReference.child(user.getUid()).setValue(bloodRequests);
        Toast.makeText(this, "Blood Request submitted successfully!", Toast.LENGTH_SHORT).show();
    }
}
