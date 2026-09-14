package com.example.speedwise;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter
        extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<Transaction> transactionList;

    private OnTransactionDeleteListener deleteListener;
    private OnTransactionEditListener editListener;


    // Delete listener
    public interface OnTransactionDeleteListener {
        void onDelete(Transaction transaction);
    }


    // Edit listener
    public interface OnTransactionEditListener {
        void onEdit(Transaction transaction);
    }


    // Constructor
    public TransactionAdapter(
            List<Transaction> transactionList,
            OnTransactionDeleteListener deleteListener,
            OnTransactionEditListener editListener) {

        this.transactionList = transactionList;
        this.deleteListener = deleteListener;
        this.editListener = editListener;
    }


    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.item_transaction,
                        parent,
                        false
                );

        return new TransactionViewHolder(view);
    }


    @Override
    public void onBindViewHolder(
            @NonNull TransactionViewHolder holder,
            int position) {

        Transaction transaction =
                transactionList.get(position);


        // Description
        holder.description.setText(
                transaction.getDescription()
        );


        // Category
        holder.category.setText(
                transaction.getCategory()
        );


        // Date
        holder.date.setText(
                transaction.getDate()
        );


        // Time
        SimpleDateFormat timeFormat =
                new SimpleDateFormat(
                        "hh:mm:ss a",
                        Locale.getDefault()
                );

        String time =
                timeFormat.format(
                        new Date(transaction.getTimestamp())
                );

        holder.time.setText(time);


        // Income / Expense
        if (transaction.getType().equals("Income")) {

            holder.amount.setText(
                    "+ ₹" + transaction.getAmount()
            );

            holder.emoji.setText("💰");

        } else {

            holder.amount.setText(
                    "- ₹" + transaction.getAmount()
            );

            holder.emoji.setText("💸");
        }


        // Edit button
        holder.editButton.setOnClickListener(v -> {

            editListener.onEdit(transaction);

        });


        // Delete button
        holder.deleteButton.setOnClickListener(v -> {

            deleteListener.onDelete(transaction);

        });


        // Tapping the transaction also edits it
        holder.itemView.setOnClickListener(v -> {

            editListener.onEdit(transaction);

        });
    }


    @Override
    public int getItemCount() {

        return transactionList.size();

    }


    // ViewHolder
    public static class TransactionViewHolder
            extends RecyclerView.ViewHolder {

        TextView emoji;
        TextView description;
        TextView category;
        TextView date;
        TextView time;
        TextView amount;

        Button editButton;
        Button deleteButton;


        public TransactionViewHolder(
                @NonNull View itemView) {

            super(itemView);


            emoji = itemView.findViewById(
                    R.id.tvEmoji
            );

            description = itemView.findViewById(
                    R.id.tvDescription
            );

            category = itemView.findViewById(
                    R.id.tvCategory
            );

            date = itemView.findViewById(
                    R.id.tvDate
            );

            time = itemView.findViewById(
                    R.id.tvTime
            );

            amount = itemView.findViewById(
                    R.id.tvAmount
            );

            editButton = itemView.findViewById(
                    R.id.btnEdit
            );

            deleteButton = itemView.findViewById(
                    R.id.btnDelete
            );
        }
    }
}