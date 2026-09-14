package com.example.speedwise;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerTransactions;
    TransactionAdapter adapter;
    ArrayList<Transaction> transactionList;
    TransactionStorage storage;

    TextView tvBalance;
    TextView tvIncome;
    TextView tvExpenses;

    SwitchMaterial switchTheme;

    double totalIncome = 0;
    double totalExpenses = 0;

    Transaction transactionBeingEdited;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        tvBalance = findViewById(R.id.tvBalance);
        tvIncome = findViewById(R.id.tvIncome);
        tvExpenses = findViewById(R.id.tvExpenses);

        switchTheme = findViewById(R.id.switchTheme);

        recyclerTransactions =
                findViewById(R.id.recyclerTransactions);

        storage = new TransactionStorage(this);


        // ---------------------------------------------
        // DARK MODE SWITCH
        // ---------------------------------------------

        if (AppCompatDelegate.getDefaultNightMode()
                == AppCompatDelegate.MODE_NIGHT_YES) {

            switchTheme.setChecked(true);

        } else {

            switchTheme.setChecked(false);
        }


        switchTheme.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {

                    if (isChecked) {

                        AppCompatDelegate.setDefaultNightMode(
                                AppCompatDelegate.MODE_NIGHT_YES
                        );

                    } else {

                        AppCompatDelegate.setDefaultNightMode(
                                AppCompatDelegate.MODE_NIGHT_NO
                        );
                    }
                }
        );


        // Load transactions
        transactionList = new ArrayList<>(
                storage.loadTransactions()
        );


        // Sort newest first
        sortTransactions();


        calculateTotals();
        updateDashboard();


        adapter = new TransactionAdapter(

                transactionList,

                // DELETE
                transaction -> {

                    showDeleteConfirmation(transaction);

                },

                // EDIT
                transaction -> {

                    transactionBeingEdited = transaction;

                    Intent intent = new Intent(
                            MainActivity.this,
                            AddTransactionActivity.class
                    );

                    intent.putExtra(
                            "editMode",
                            true
                    );

                    intent.putExtra(
                            "type",
                            transaction.getType()
                    );

                    intent.putExtra(
                            "amount",
                            transaction.getAmount()
                    );

                    intent.putExtra(
                            "description",
                            transaction.getDescription()
                    );

                    intent.putExtra(
                            "category",
                            transaction.getCategory()
                    );

                    intent.putExtra(
                            "date",
                            transaction.getDate()
                    );

                    intent.putExtra(
                            "timestamp",
                            transaction.getTimestamp()
                    );

                    startActivityForResult(
                            intent,
                            101
                    );
                }
        );


        recyclerTransactions.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerTransactions.setAdapter(adapter);


        // Add Transaction button
        Button addTransactionButton =
                findViewById(R.id.btnAddTransaction);


        addTransactionButton.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    AddTransactionActivity.class
            );

            startActivityForResult(
                    intent,
                    100
            );
        });
    }


    // --------------------------------------------------
    // SORT TRANSACTIONS
    // --------------------------------------------------

    private void sortTransactions() {

        SimpleDateFormat dateFormat =
                new SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale.getDefault()
                );


        Collections.sort(
                transactionList,
                (t1, t2) -> {

                    try {

                        Date date1 =
                                dateFormat.parse(
                                        t1.getDate()
                                );

                        Date date2 =
                                dateFormat.parse(
                                        t2.getDate()
                                );

                        int dateComparison =
                                date2.compareTo(date1);


                        if (dateComparison != 0) {

                            return dateComparison;
                        }


                        return Long.compare(
                                t2.getTimestamp(),
                                t1.getTimestamp()
                        );

                    } catch (Exception e) {

                        return Long.compare(
                                t2.getTimestamp(),
                                t1.getTimestamp()
                        );
                    }
                }
        );
    }


    // --------------------------------------------------
    // ADD / EDIT RESULT
    // --------------------------------------------------

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data) {

        super.onActivityResult(
                requestCode,
                resultCode,
                data
        );


        if (resultCode != RESULT_OK ||
                data == null) {

            return;
        }


        String type =
                data.getStringExtra("type");

        double amount =
                data.getDoubleExtra(
                        "amount",
                        0
                );

        String description =
                data.getStringExtra("description");

        String category =
                data.getStringExtra("category");

        String date =
                data.getStringExtra("date");

        boolean isEditMode =
                data.getBooleanExtra(
                        "editMode",
                        false
                );


        // ADD
        if (requestCode == 100 &&
                !isEditMode) {

            long timestamp =
                    System.currentTimeMillis();


            Transaction transaction =
                    new Transaction(
                            type,
                            amount,
                            description,
                            category,
                            date,
                            timestamp
                    );

            transactionList.add(transaction);
        }


        // EDIT
        else if (
                requestCode == 101 &&
                        isEditMode &&
                        transactionBeingEdited != null
        ) {

            transactionBeingEdited.setType(type);

            transactionBeingEdited.setAmount(amount);

            transactionBeingEdited.setDescription(
                    description
            );

            transactionBeingEdited.setCategory(
                    category
            );

            transactionBeingEdited.setDate(
                    date
            );


            // Keep the original timestamp
            transactionBeingEdited.setTimestamp(
                    transactionBeingEdited.getTimestamp()
            );


            transactionBeingEdited = null;
        }


        // Sort
        sortTransactions();


        // SAVE UPDATED LIST
        storage.saveTransactions(
                transactionList
        );


        calculateTotals();

        adapter.notifyDataSetChanged();

        updateDashboard();
    }


    // --------------------------------------------------
    // DELETE CONFIRMATION
    // --------------------------------------------------

    private void showDeleteConfirmation(
            Transaction transaction) {

        new AlertDialog.Builder(this)

                .setTitle("Delete Transaction")

                .setMessage(
                        "Are you sure you want to delete this transaction?"
                )

                .setNegativeButton(
                        "Cancel",
                        null
                )

                .setPositiveButton(
                        "Delete",
                        (dialog, which) -> {

                            transactionList.remove(
                                    transaction
                            );

                            storage.saveTransactions(
                                    transactionList
                            );

                            calculateTotals();

                            adapter.notifyDataSetChanged();

                            updateDashboard();
                        }
                )

                .show();
    }


    // --------------------------------------------------
    // CALCULATE TOTALS
    // --------------------------------------------------

    private void calculateTotals() {

        totalIncome = 0;
        totalExpenses = 0;


        for (Transaction transaction :
                transactionList) {

            if (transaction.getType()
                    .equals("Income")) {

                totalIncome +=
                        transaction.getAmount();

            } else {

                totalExpenses +=
                        transaction.getAmount();
            }
        }
    }


    // --------------------------------------------------
    // UPDATE DASHBOARD
    // --------------------------------------------------

    private void updateDashboard() {

        double balance =
                totalIncome - totalExpenses;


        tvBalance.setText(
                "₹" +
                        String.format(
                                "%.2f",
                                balance
                        )
        );


        tvIncome.setText(
                "₹" +
                        String.format(
                                "%.2f",
                                totalIncome
                        )
        );


        tvExpenses.setText(
                "₹" +
                        String.format(
                                "%.2f",
                                totalExpenses
                        )
        );
    }
}