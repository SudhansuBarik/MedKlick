package com.maverick.medklick.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maverick.medklick.R;
import com.maverick.medklick.others.UserInformation;

import java.util.Calendar;

public class CreateProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = CreateProfileActivity.class.getSimpleName();

    private EditText nameEditText, mobileEditText, addressEditText, emergencyContactEditText;
    private TextView dobTextView;
    private DatePickerDialog datePickerDialog;
    private Spinner bloodGroupSpinner;
    private Button createProfileButton;
    private ProgressBar progressBar;

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    // Store user's blood group
    private String bloodGroup;
    // Store user's gender
    private String userGender;

    private boolean checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        //To hide AppBar for fullscreen.
        ActionBar ab = getSupportActionBar();
        ab.hide();

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
        databaseReference = firebaseDatabase.getReference("users");

        // store app title to 'app_title' node
        firebaseDatabase.getReference("app_title").setValue("MedKlick");

        // app_title change listener
        firebaseDatabase.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);

                // update toolbar title
                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });

        nameEditText = findViewById(R.id.create_profile_name_editText);
        mobileEditText = findViewById(R.id.create_profile_mobile_editText);
        dobTextView = findViewById(R.id.create_profile_dob_textView);
        addressEditText = findViewById(R.id.create_profile_address_editText);
        emergencyContactEditText = findViewById(R.id.create_profile_emergency_contact_editText);
        bloodGroupSpinner = findViewById(R.id.create_profile_blood_group_spinner);
        createProfileButton = findViewById(R.id.create_profile_button);
        progressBar = findViewById(R.id.create_profile_progressBar);

        dobTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR); // current year
                int mMonth = calendar.get(Calendar.MONTH); // current month
                int mDay = calendar.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(CreateProfileActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                dobTextView.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        createProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameEditText.getText().toString();
                String mobile = mobileEditText.getText().toString();
                String dob = dobTextView.getText().toString();
                String address = addressEditText.getText().toString();
                String emergencyContact = emergencyContactEditText.getText().toString();

                // If any field doesn't have a response then create a toast to prompt the user to enter value
                if (name.equals("")) {
                    Toast.makeText(CreateProfileActivity.this, "Please enter your Name", Toast.LENGTH_SHORT).show();
                } else if (mobile.equals("")) {
                    Toast.makeText(CreateProfileActivity.this, "Please enter your Mobile Number", Toast.LENGTH_SHORT).show();
                } else if (!checked) {
                    Toast.makeText(CreateProfileActivity.this, "Please select a Gender", Toast.LENGTH_SHORT).show();
                } else if (dob.equals("")) {
                    Toast.makeText(CreateProfileActivity.this, "Please enter your Date of Birth", Toast.LENGTH_SHORT).show();
                } else if (address.equals("")) {
                    Toast.makeText(CreateProfileActivity.this, "Please enter your Address", Toast.LENGTH_SHORT).show();
                } else if (emergencyContact.equals("")) {
                    Toast.makeText(CreateProfileActivity.this, "Please enter an Emergency Contact Number", Toast.LENGTH_SHORT).show();
                } else if (bloodGroup.equals("")) {
                    Toast.makeText(CreateProfileActivity.this, "Please enter your Blood Group", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    saveUserInformation();
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(CreateProfileActivity.this, MainActivity.class));
                    finish();
                }
            }
        });

        bloodGroupSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        bloodGroup = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void GenderSelected(View view) {
        // Check if button is selected
        checked = ((RadioButton) view).isChecked();

        // Check which radio button was selected
        switch (view.getId()) {
            case R.id.male_radio_button:
                if (checked)
                    userGender = "Male";
                break;
            case R.id.female_radio_button:
                if (checked)
                    userGender = "Female";
                break;
            case R.id.other_radio_button:
                if (checked)
                    userGender = "Other";
                break;
        }
    }

    private void saveUserInformation() {
        String name = nameEditText.getText().toString().trim();
        String mobile = mobileEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String emergencyContact = emergencyContactEditText.getText().toString().trim();
        String dob = dobTextView.getText().toString().trim();

        FirebaseUser user = auth.getCurrentUser();
        String email = null;
        if (user != null) {
            email = user.getEmail().toString();
        }
        UserInformation userInformation = new UserInformation(name, email, userGender, dob, bloodGroup,
                mobile, address, emergencyContact);
        databaseReference.child(user.getUid()).setValue(userInformation);
        Toast.makeText(this, "Profile created successfully", Toast.LENGTH_SHORT).show();
    }
}
