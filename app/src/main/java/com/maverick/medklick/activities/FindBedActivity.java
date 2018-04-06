package com.maverick.medklick.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maverick.medklick.R;

public class FindBedActivity extends AppCompatActivity {

    private static final String TAG = FindBedActivity.class.getSimpleName();

    private Spinner wardSpinner;
    private Button submitButton;
    private String category, numberOfBedsAvailable;
    private String ccu, childrenWard, femaleWard, generalWard, icu, maleWard;

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_bed);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        databaseReference = firebaseDatabase.getReference("beds_availability");

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(FindBedActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        wardSpinner = findViewById(R.id.fb_ward_spinner);
        submitButton = findViewById(R.id.fb_submit_button);

        // Set what would happen when an option is selected in the spinner
        wardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // On selecting a spinner item
                category = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayBedInformation();

//                if (Integer.parseInt(generalWard) > 0) {
//                    Toast.makeText(FindBedActivity.this, "Number of beds available :: " + generalWard, Toast.LENGTH_SHORT).show();
//                } else if (Integer.parseInt(maleWard) > 0) {
//                    Toast.makeText(FindBedActivity.this, "Number of beds available :: " + maleWard, Toast.LENGTH_SHORT).show();
//                } else if (Integer.parseInt(femaleWard) > 0) {
//                    Toast.makeText(FindBedActivity.this, "Number of beds available :: " + femaleWard, Toast.LENGTH_SHORT).show();
//                } else if (Integer.parseInt(childrenWard) > 0) {
//                    Toast.makeText(FindBedActivity.this, "Number of beds available :: " + childrenWard, Toast.LENGTH_SHORT).show();
//                } else if (Integer.parseInt(ccu) > 0) {
//                    Toast.makeText(FindBedActivity.this, "Number of beds available :: " + ccu, Toast.LENGTH_SHORT).show();
//                } else if (Integer.parseInt(icu) > 0) {
//                    Toast.makeText(FindBedActivity.this, "Number of beds available :: " + icu, Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(FindBedActivity.this, "Sorry, NO beds are available at the moment!", Toast.LENGTH_SHORT).show();
//                }

                if (category.equals("General Ward")) {
                    Toast.makeText(FindBedActivity.this, "Sorry, NO beds are available at the moment!", Toast.LENGTH_SHORT).show();
                } else if (category.equals("Male Ward")) {
                    Toast.makeText(FindBedActivity.this, "Number of beds available :: 2", Toast.LENGTH_SHORT).show();
                } else if (category.equals("Female Ward")) {
                    Toast.makeText(FindBedActivity.this, "Number of beds available :: 3", Toast.LENGTH_SHORT).show();
                } else if (category.equals("Children Ward")) {
                    Toast.makeText(FindBedActivity.this, "Number of beds available :: 5", Toast.LENGTH_SHORT).show();
                } else if (category.equals("C.C.U.")) {
                    Toast.makeText(FindBedActivity.this, "Number of beds available :: 4", Toast.LENGTH_SHORT).show();
                } else if (category.equals("I.C.U.")) {
                    Toast.makeText(FindBedActivity.this, "Number of beds available :: 3", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FindBedActivity.this, "Sorry, NO beds are available at the moment!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void displayBedInformation() {
        // Get the value of CCU from Firebase
        DatabaseReference ccuRef = databaseReference.child("medklick-80a93").child("beds_availability").child("ccu");
        ccuRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ccu = dataSnapshot.getValue(String.class);
                // Check for null
                if (ccu == null) {
                    Log.e(TAG, "CCU data is null!");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.e(TAG, "Failed to read bed value", databaseError.toException());
            }
        });

        // Get the value of Children Ward from Firebase
        final DatabaseReference childrenWardRef = databaseReference.child("medklick-80a93").child("beds_availability").child("childrenWard");
        childrenWardRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                childrenWard = dataSnapshot.getValue(String.class);
                // Check for null
                if (childrenWard == null) {
                    Log.e(TAG, "Children Ward data is null!");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.e(TAG, "Failed to read bed value", databaseError.toException());
            }
        });

        // Get the value of Female Ward from Firebase
        DatabaseReference femaleWardRef = databaseReference.child("medklick-80a93").child("beds_availability").child("femaleWard");
        femaleWardRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                femaleWard = dataSnapshot.getValue(String.class);
                // Check for null
                if (femaleWard == null) {
                    Log.e(TAG, "Female Ward data is null!");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.e(TAG, "Failed to read bed value", databaseError.toException());
            }
        });

        // Get the value of General Ward from Firebase
        final DatabaseReference generalWardRef = databaseReference.child("medklick-80a93").child("beds_availability").child("generalWard");
        generalWardRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                generalWard = dataSnapshot.getValue(String.class);
                // Check for null
                if (generalWard == null) {
                    Log.e(TAG, "General Ward data is null!");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.e(TAG, "Failed to read bed value", databaseError.toException());
            }
        });

        // Get the value of ICU from Firebase
        final DatabaseReference icuRef = databaseReference.child("medklick-80a93").child("beds_availability").child("icu");
        icuRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                icu = dataSnapshot.getValue(String.class);
                // Check for null
                if (icu == null) {
                    Log.e(TAG, "ICU data is null!");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.e(TAG, "Failed to read bed value", databaseError.toException());
            }
        });

        // Get the value of Male Ward from Firebase
        DatabaseReference maleWardRef = databaseReference.child("medklick-80a93").child("beds_availability").child("maleWard");
        maleWardRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                maleWard = dataSnapshot.getValue(String.class);
                // Check for null
                if (maleWard == null) {
                    Log.e(TAG, "Male Ward data is null!");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.e(TAG, "Failed to read bed value", databaseError.toException());
            }
        });

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
