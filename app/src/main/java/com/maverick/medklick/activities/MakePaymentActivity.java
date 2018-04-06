package com.maverick.medklick.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.maverick.medklick.R;

import java.util.Calendar;

/**
 * Created by Sudhansu on 23-03-2018.
 */

public class MakePaymentActivity extends AppCompatActivity {

    private Spinner modeOfPaymentSpinner;
    private EditText cardNumberEditText, dateOfExpiryEditText, cvvEditText;
    private Button makePaymentButton;

    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);

        modeOfPaymentSpinner = findViewById(R.id.mode_of_payment_spinner);
        cardNumberEditText = findViewById(R.id.card_number_editText);
        dateOfExpiryEditText = findViewById(R.id.expiry_date_editText);
        cvvEditText = findViewById(R.id.cvv_editText);
        makePaymentButton = findViewById(R.id.make_payment_button);

        dateOfExpiryEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int fdYear = calendar.get(Calendar.YEAR);
                int fdMonth = calendar.get(Calendar.MONTH);
                int fdDay = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(MakePaymentActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateOfExpiryEditText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, fdYear, fdMonth, fdDay);
                datePickerDialog.show();
            }
        });

        // perform setOnClickListener event on simple Toast Button
        makePaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MakePaymentActivity.this, "PAYMENT SUCCESSFULL", Toast.LENGTH_LONG).show();

            }
        });
    }
}
