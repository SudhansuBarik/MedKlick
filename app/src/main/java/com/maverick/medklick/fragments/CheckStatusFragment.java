package com.maverick.medklick.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maverick.medklick.R;
import com.maverick.medklick.activities.LoginActivity;
import com.maverick.medklick.others.BloodRequests;
import com.maverick.medklick.others.Queries;


/**
 * A simple {@link Fragment} subclass.
 */
public class CheckStatusFragment extends Fragment {

    private static final String TAG = CheckStatusFragment.class.getSimpleName();

    private LinearLayout bloodRequestLinearLayout, queriesLinearLayout;
    private TextView bloodGroupTextView, unitsTextView, dateOfRequirementTextView, queryTextView,
            problemAreaTextView;

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference bloodDatabaseReference, queryDatabaseReference;

    public CheckStatusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_check_status, container, false);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();

        // get reference
        bloodDatabaseReference = firebaseDatabase.getReference("blood_requests");
        queryDatabaseReference = firebaseDatabase.getReference("queries");

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                }
            }
        };

        bloodRequestLinearLayout = view.findViewById(R.id.blood_request_linearLayout);
        queriesLinearLayout = view.findViewById(R.id.query_linearLayout);
        bloodGroupTextView = view.findViewById(R.id.cs_blood_group_textView);
        unitsTextView = view.findViewById(R.id.cs_units_of_blood_textView);
        dateOfRequirementTextView = view.findViewById(R.id.cs_date_of_requirement_textView);
        queryTextView = view.findViewById(R.id.cs_query_textView);
        problemAreaTextView = view.findViewById(R.id.cs_problem_area_textView);

        displayStatusInformation();

        return view;
    }

    private void displayStatusInformation() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            bloodDatabaseReference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    BloodRequests bloodRequests = dataSnapshot.getValue(BloodRequests.class);

                    // Check for null
                    if (bloodRequests == null) {
                        Log.e(TAG, "Blood Request data is null!");
                        bloodRequestLinearLayout.setVisibility(View.GONE);
                    } else {
                        // Display information
                        bloodGroupTextView.setText(bloodRequests.bloodGroup);
                        unitsTextView.setText(bloodRequests.unitsOfBlood);
                        dateOfRequirementTextView.setText(bloodRequests.dateOfRequirement);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Failed to read value
                    Log.e(TAG, "Failed to read blood request", databaseError.toException());
                }
            });

            queryDatabaseReference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Queries queries = dataSnapshot.getValue(Queries.class);

                    // Check for null
                    if (queries == null) {
                        Log.e(TAG, "Blood Request data is null!");
                        queriesLinearLayout.setVisibility(View.GONE);
                    } else {
                        // Display information
                        problemAreaTextView.setText(queries.problemArea);
                        queryTextView.setText(queries.problem);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Failed to read value
                    Log.e(TAG, "Failed to read query", databaseError.toException());
                }
            });
        } else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    @Override
    public void onResume() {
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
