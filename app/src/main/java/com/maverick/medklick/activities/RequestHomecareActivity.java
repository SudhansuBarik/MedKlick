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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.maverick.medklick.R;
import com.maverick.medklick.others.HomecareRequests;

import java.util.Calendar;

public class RequestHomecareActivity extends AppCompatActivity {

    private Spinner hospitalSpinner;
    private TextView fromDateTextView, toDateTextView;
    private String hospital, category;
    private boolean checked;
    private Button submitButton;

    private DatePickerDialog datePickerDialog;
    private ProgressBar progressBar;

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_homecare);

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
        databaseReference = firebaseDatabase.getReference("homecare_requests");

        hospitalSpinner = findViewById(R.id.rh_hospital_spinner);
        fromDateTextView = findViewById(R.id.rh_from_date_textView);
        toDateTextView = findViewById(R.id.rh_to_date_textView);
        submitButton = findViewById(R.id.rh_submit_button);
        progressBar = findViewById(R.id.rh_progressBar);

        hospitalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hospital = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fromDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR); // current year
                int mMonth = calendar.get(Calendar.MONTH); // current month
                int mDay = calendar.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(RequestHomecareActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                fromDateTextView.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        toDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR); // current year
                int mMonth = calendar.get(Calendar.MONTH); // current month
                int mDay = calendar.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(RequestHomecareActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                toDateTextView.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {

            String fromDate = fromDateTextView.getText().toString().trim();
            String toDate = toDateTextView.getText().toString().trim();

            @Override
            public void onClick(View v) {
//                if (fromDate.equals("")) {
//                    Toast.makeText(RequestHomecareActivity.this,
//                            "Please select the date from which you require the service", Toast.LENGTH_SHORT).show();
//                } else if (toDate.equals("")) {
//                    Toast.makeText(RequestHomecareActivity.this,
//                            "Please select the date till which you require the service", Toast.LENGTH_SHORT).show();
//                } else if (!checked) {
//                    Toast.makeText(RequestHomecareActivity.this,
//                            "Please select the category of service you require", Toast.LENGTH_SHORT).show();
//                } else {
                progressBar.setVisibility(View.VISIBLE);
                saveHomecareRequests();
                progressBar.setVisibility(View.GONE);
                startActivity(new Intent(RequestHomecareActivity.this, MainActivity.class));
                finish();
//                }
            }
        });
    }

    public void CategorySelected(View view) {
        // Check if button is selected
        checked = ((RadioButton) view).isChecked();

        // Check which radio button was selected
        switch (view.getId()) {
            case R.id.male_radio_button:
                if (checked)
                    category = "6 Hours Service";
                break;
            case R.id.female_radio_button:
                if (checked)
                    category = "12 Hours Service";
                break;
            case R.id.other_radio_button:
                if (checked)
                    category = "24 Hours Service";
                break;
        }
    }

    private void saveHomecareRequests() {

        FirebaseUser user = auth.getCurrentUser();

        String fromDate = fromDateTextView.getText().toString().trim();
        String toDate = toDateTextView.getText().toString().trim();

        HomecareRequests homecareRequests = new HomecareRequests(hospital, fromDate, toDate, category);
        databaseReference.child(user.getUid()).setValue(homecareRequests);
        Toast.makeText(this, "Homecare request submitted successfully", Toast.LENGTH_SHORT).show();
    }
}
