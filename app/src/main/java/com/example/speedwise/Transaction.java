package com.example.speedwise;

public class Transaction {

    private String type;
    private double amount;
    private String description;
    private String category;
    private String date;
    private long timestamp;


    // Constructor
    public Transaction(
            String type,
            double amount,
            String description,
            String category,
            String date,
            long timestamp) {

        this.type = type;
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.date = date;
        this.timestamp = timestamp;
    }


    // Getters

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public long getTimestamp() {
        return timestamp;
    }


    // Setters

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}