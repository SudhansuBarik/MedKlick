package com.maverick.medklick.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.maverick.medklick.R;
import com.maverick.medklick.others.Queries;

public class PostQueryActivity extends AppCompatActivity {

    private Spinner problemAreaSpinner;
    private EditText problemEditText;
    private Button submitButton;
    private ProgressBar progressBar;

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private String problemArea, problem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_query);

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
        databaseReference = firebaseDatabase.getReference("queries");

        problemAreaSpinner = findViewById(R.id.pq_problem_area_spinner);
        problemEditText = findViewById(R.id.pq_problem_editText);
        submitButton = findViewById(R.id.pq_submit_button);
        progressBar = findViewById(R.id.pq_progressBar);

        problemAreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                problemArea = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                problem = problemEditText.getText().toString().trim();

                progressBar.setVisibility(View.VISIBLE);
                saveQueries();
                progressBar.setVisibility(View.GONE);
                startActivity(new Intent(PostQueryActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void saveQueries() {
        FirebaseUser user = auth.getCurrentUser();

        Queries queries = new Queries(problemArea, problem);
        databaseReference.child(user.getUid()).setValue(queries);
        Toast.makeText(this, "Problem submitted successfully! You will get your response via e-mail.", Toast.LENGTH_LONG).show();
    }
}
