package com.maverick.medklick.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maverick.medklick.R;
import com.maverick.medklick.others.UserInformation;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = ProfileActivity.class.getSimpleName();

    private Button btnChangeEmail, btnChangePassword, btnSendResetEmail, btnRemoveUser,
            changeEmail, changePassword, sendEmail, remove;
    private TextView nameTextView, emailTextView, genderTextView, dobTextView, bloodGroupTextView,
            mobileNumberTextView, addressTextView, emergencyContactTextView;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        databaseReference = firebaseDatabase.getReference("users");

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        nameTextView = findViewById(R.id.profile_name_textView);
        emailTextView = findViewById(R.id.profile_email_textView);
        genderTextView = findViewById(R.id.profile_gender_textView);
        dobTextView = findViewById(R.id.profile_dob_textView);
        bloodGroupTextView = findViewById(R.id.profile_blood_group_textView);
        mobileNumberTextView = findViewById(R.id.profle_mobile_number_textView);
        addressTextView = findViewById(R.id.profile_address_textView);
        emergencyContactTextView = findViewById(R.id.profile_emergency_contact_textView);

        displayUserInformation();
    }

    private void displayUserInformation() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            databaseReference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);

                    // Check for null
                    if (userInformation == null) {
                        Log.e(TAG, "User data is null!");
                    } else {
                        // Display user information
                        nameTextView.setText(userInformation.name);
                        emailTextView.setText(userInformation.email);
                        genderTextView.setText(userInformation.gender);
                        dobTextView.setText(userInformation.dob);
                        bloodGroupTextView.setText(userInformation.bloodGroup);
                        mobileNumberTextView.setText(userInformation.mobile);
                        addressTextView.setText(userInformation.address);
                        emergencyContactTextView.setText(userInformation.emergencyContact);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Failed to read value
                    Log.e(TAG, "Failed to read user", databaseError.toException());
                }
            });
        } else {
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
