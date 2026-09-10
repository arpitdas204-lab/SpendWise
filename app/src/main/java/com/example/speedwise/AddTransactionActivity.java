package com.example.speedwise;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTransactionActivity extends AppCompatActivity {

    private String transactionDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_transaction);


        EditText amount =
                findViewById(R.id.etAmount);

        EditText description =
                findViewById(R.id.etDescription);

        RadioButton income =
                findViewById(R.id.rbIncome);

        Spinner categorySpinner =
                findViewById(R.id.spCategory);

        Button dateButton =
                findViewById(R.id.btnDate);


        // --------------------------------------------------
        // CATEGORIES
        // --------------------------------------------------

        String[] categories = {
                "Food",
                "Travel",
                "Shopping",
                "Bills",
                "Other"
        };


        ArrayAdapter<String> categoryAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        categories
                );

        categoryAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        categorySpinner.setAdapter(categoryAdapter);


        // --------------------------------------------------
        // CHECK EDIT MODE
        // --------------------------------------------------

        boolean isEditMode =
                getIntent().getBooleanExtra(
                        "editMode",
                        false
                );


        // --------------------------------------------------
        // GET TRANSACTION DATA
        // --------------------------------------------------

        if (isEditMode) {

            transactionDate =
                    getIntent().getStringExtra("date");


            String existingType =
                    getIntent().getStringExtra("type");

            double existingAmount =
                    getIntent().getDoubleExtra(
                            "amount",
                            0
                    );

            String existingDescription =
                    getIntent().getStringExtra(
                            "description"
                    );

            String existingCategory =
                    getIntent().getStringExtra(
                            "category"
                    );


            amount.setText(
                    String.valueOf(existingAmount)
            );


            description.setText(
                    existingDescription
            );


            if ("Income".equals(existingType)) {

                income.setChecked(true);

            } else {

                income.setChecked(false);
            }


            for (int i = 0;
                 i < categories.length;
                 i++) {

                if (categories[i].equals(
                        existingCategory)) {

                    categorySpinner.setSelection(i);

                    break;
                }
            }

        } else {

            // New transaction = today's date

            transactionDate =
                    new SimpleDateFormat(
                            "dd/MM/yyyy",
                            Locale.getDefault()
                    ).format(
                            Calendar.getInstance().getTime()
                    );
        }


        // Show current transaction date

        dateButton.setText(
                transactionDate
        );


        // --------------------------------------------------
        // DATE PICKER
        // --------------------------------------------------

        dateButton.setOnClickListener(v -> {

            Calendar calendar =
                    Calendar.getInstance();


            if (transactionDate != null) {

                try {

                    SimpleDateFormat dateFormat =
                            new SimpleDateFormat(
                                    "dd/MM/yyyy",
                                    Locale.getDefault()
                            );

                    java.util.Date selectedDate =
                            dateFormat.parse(
                                    transactionDate
                            );

                    if (selectedDate != null) {

                        calendar.setTime(
                                selectedDate
                        );
                    }

                } catch (Exception e) {

                    // Keep today's date
                }
            }


            DatePickerDialog datePickerDialog =
                    new DatePickerDialog(

                            this,

                            (view, year, month, dayOfMonth) -> {

                                transactionDate =
                                        String.format(
                                                Locale.getDefault(),
                                                "%02d/%02d/%04d",
                                                dayOfMonth,
                                                month + 1,
                                                year
                                        );


                                dateButton.setText(
                                        transactionDate
                                );
                            },

                            calendar.get(
                                    Calendar.YEAR
                            ),

                            calendar.get(
                                    Calendar.MONTH
                            ),

                            calendar.get(
                                    Calendar.DAY_OF_MONTH
                            )
                    );


            datePickerDialog.show();
        });


        // --------------------------------------------------
        // SAVE BUTTON
        // --------------------------------------------------

        Button saveButton =
                findViewById(
                        R.id.btnSaveTransaction
                );


        saveButton.setOnClickListener(v -> {

            String amountText =
                    amount.getText()
                            .toString()
                            .trim();

            String descriptionText =
                    description.getText()
                            .toString()
                            .trim();


            // --------------------------------------------------
            // VALIDATE EMPTY AMOUNT
            // --------------------------------------------------

            if (amountText.isEmpty()) {

                amount.setError(
                        "Amount is required"
                );

                amount.requestFocus();

                return;
            }


            // --------------------------------------------------
            // VALIDATE AMOUNT FORMAT
            // --------------------------------------------------

            double amountValue;

            try {

                amountValue =
                        Double.parseDouble(amountText);

            } catch (NumberFormatException e) {

                amount.setError(
                        "Please enter a valid number"
                );

                amount.requestFocus();

                return;
            }


            // --------------------------------------------------
            // VALIDATE POSITIVE AMOUNT
            // --------------------------------------------------

            if (amountValue <= 0) {

                amount.setError(
                        "Amount must be greater than 0"
                );

                amount.requestFocus();

                return;
            }


            // --------------------------------------------------
            // VALIDATE DESCRIPTION
            // --------------------------------------------------

            if (descriptionText.isEmpty()) {

                description.setError(
                        "Description is required"
                );

                description.requestFocus();

                return;
            }


            // --------------------------------------------------
            // GET TRANSACTION TYPE
            // --------------------------------------------------

            String type;

            if (income.isChecked()) {

                type = "Income";

            } else {

                type = "Expense";
            }


            // --------------------------------------------------
            // GET CATEGORY
            // --------------------------------------------------

            String category =
                    categorySpinner
                            .getSelectedItem()
                            .toString();


            // --------------------------------------------------
            // CREATE RESULT
            // --------------------------------------------------

            Intent resultIntent =
                    new Intent();


            resultIntent.putExtra(
                    "type",
                    type
            );


            resultIntent.putExtra(
                    "amount",
                    amountValue
            );


            resultIntent.putExtra(
                    "description",
                    descriptionText
            );


            resultIntent.putExtra(
                    "category",
                    category
            );


            resultIntent.putExtra(
                    "date",
                    transactionDate
            );


            resultIntent.putExtra(
                    "editMode",
                    isEditMode
            );


            setResult(
                    RESULT_OK,
                    resultIntent
            );


            finish();
        });
    }
}