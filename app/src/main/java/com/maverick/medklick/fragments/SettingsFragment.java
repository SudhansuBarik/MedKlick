package com.maverick.medklick.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.maverick.medklick.R;
import com.maverick.medklick.activities.HelpAndSupportActivity;
import com.maverick.medklick.activities.LoginActivity;
import com.maverick.medklick.activities.ProfileActivity;
import com.maverick.medklick.activities.RateActivity;
import com.maverick.medklick.activities.TermsAndPolicyActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private String emailId;

    private Activity activity;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        };

        TextView termsAndPolicyTextView = view.findViewById(R.id.terms_and_policy_textView);
        TextView helpAndSupportTextView = view.findViewById(R.id.help_and_support_textView);
        TextView shareTextView = view.findViewById(R.id.share_textView);
        TextView rateTextView = view.findViewById(R.id.rate_textView);
        TextView profileTextView = view.findViewById(R.id.profile_textView);
        TextView changePasswordTextView = view.findViewById(R.id.change_password_textView);
        TextView logoutTextView = view.findViewById(R.id.logout_textView);
        final ProgressBar progressBar = view.findViewById(R.id.settings_progressBar);

        if (user != null) {
            emailId = user.getEmail();
        }

        // Open Terms and Privacy Policy Activity
        termsAndPolicyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TermsAndPolicyActivity.class);
                startActivity(intent);
            }
        });

        // Open Help and Support Activity
        helpAndSupportTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, HelpAndSupportActivity.class);
                startActivity(intent);
            }
        });

        // Start Share Intent
        shareTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out MedKlick at: https://play.google.com/store/apps/details?id=com.google.android.apps.plus");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        // Open Rating Activity
        rateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, RateActivity.class);
                startActivity(intent);
            }
        });

        // Open My Profile Activity
        profileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ProfileActivity.class);
                startActivity(intent);
            }
        });

        // Open Change Password Activity
        changePasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (!emailId.equals("")) {
                    auth.sendPasswordResetEmail(emailId)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "Reset password email is sent!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(getActivity(), "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                }
            }
        });

        // Logout the user from the App
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Info");
                builder.setMessage("Do you want to logout ??");
                builder.setPositiveButton("Take me away!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        signOut();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        Toast.makeText(getActivity(), "Logged out successfully", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }

    private void signOut() {
        auth.signOut();
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
