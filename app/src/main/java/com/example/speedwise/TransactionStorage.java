package com.example.speedwise;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionStorage {

    private SharedPreferences preferences;

    public TransactionStorage(Context context) {

        preferences = context.getSharedPreferences(
                "TransactionPrefs",
                Context.MODE_PRIVATE
        );
    }


    // --------------------------------------------------
    // SAVE TRANSACTIONS
    // --------------------------------------------------

    public void saveTransactions(List<Transaction> transactionList) {

        StringBuilder data = new StringBuilder();

        for (Transaction transaction : transactionList) {

            data.append(transaction.getType()).append("|");
            data.append(transaction.getAmount()).append("|");
            data.append(transaction.getDescription()).append("|");
            data.append(transaction.getCategory()).append("|");
            data.append(transaction.getDate()).append("|");
            data.append(transaction.getTimestamp()).append("\n");
        }

        preferences.edit()
                .putString("transactions", data.toString())
                .apply();
    }


    // --------------------------------------------------
    // LOAD TRANSACTIONS
    // --------------------------------------------------

    public List<Transaction> loadTransactions() {

        List<Transaction> transactionList =
                new java.util.ArrayList<>();

        String data =
                preferences.getString("transactions", "");

        if (data.isEmpty()) {
            return transactionList;
        }


        String[] transactions =
                data.split("\n");


        for (String transactionData : transactions) {

            String[] parts =
                    transactionData.split("\\|");


            // ------------------------------------------
            // NEW FORMAT: 6 fields
            // ------------------------------------------

            if (parts.length == 6) {

                String type = parts[0];

                double amount =
                        Double.parseDouble(parts[1]);

                String description = parts[2];

                String category = parts[3];

                String date = parts[4];

                long timestamp =
                        Long.parseLong(parts[5]);


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


            // ------------------------------------------
            // OLD FORMAT: 5 fields
            // ------------------------------------------

            else if (parts.length == 5) {

                String type = parts[0];

                double amount =
                        Double.parseDouble(parts[1]);

                String description = parts[2];

                String category = parts[3];

                String date = parts[4];


                // Old transactions don't have
                // a timestamp.
                // Give them today's timestamp.

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


            // ------------------------------------------
            // VERY OLD FORMAT: 4 fields
            // ------------------------------------------

            else if (parts.length == 4) {

                String type = parts[0];

                double amount =
                        Double.parseDouble(parts[1]);

                String description = parts[2];

                String category = parts[3];


                // Give old transactions today's date

                String date =
                        new SimpleDateFormat(
                                "dd/MM/yyyy",
                                Locale.getDefault()
                        ).format(new Date());


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
        }


        return transactionList;
    }
}