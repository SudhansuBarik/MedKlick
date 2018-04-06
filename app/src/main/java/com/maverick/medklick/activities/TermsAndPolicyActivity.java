package com.maverick.medklick.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.maverick.medklick.R;

import butterknife.BindFont;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TermsAndPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_policy);
    }
}
