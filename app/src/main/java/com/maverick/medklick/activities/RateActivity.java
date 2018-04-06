package com.maverick.medklick.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.maverick.medklick.R;
import com.maverick.medklick.others.Rating;

public class RateActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText feedbackEditText;
    private Button submitButton;
    private ProgressBar progressBar;

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private String ratingValue, feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

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
        databaseReference = firebaseDatabase.getReference("rating");

        ratingBar = findViewById(R.id.ratingBar);
        feedbackEditText = findViewById(R.id.feedback_editText);
        submitButton = findViewById(R.id.rating_submit_button);
        progressBar = findViewById(R.id.rating_progressBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingValue = String.valueOf(rating);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                feedback = feedbackEditText.getText().toString().trim();

                progressBar.setVisibility(View.VISIBLE);
                saveRating();
                progressBar.setVisibility(View.GONE);
                startActivity(new Intent(RateActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void saveRating() {
        FirebaseUser user = auth.getCurrentUser();

        Rating ratingObj = new Rating(ratingValue, feedback);
        databaseReference.child(user.getUid()).setValue(ratingObj);
        Toast.makeText(this, "Thank You for your valuable Rating and Feedback!", Toast.LENGTH_LONG).show();
    }
}
