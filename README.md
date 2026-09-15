# SpendWise 💰

SpendWise is an Android-based personal expense tracking application developed using **Java and Android Studio**. The application helps users manage their income and expenses, categorize transactions, monitor their financial balance, and maintain transaction records locally.

## 📱 Project Overview

Managing daily expenses manually can be difficult and time-consuming. SpendWise provides a simple and user-friendly mobile solution for recording and monitoring financial transactions.

The application allows users to add income and expense transactions, assign categories, view financial summaries, edit or delete transactions, and store their data locally on the device.

## ✨ Features

- ➕ Add Income and Expense transactions
- 💰 Enter transaction amount
- 📝 Add transaction descriptions
- 📂 Categorize transactions:
  - Food
  - Travel
  - Shopping
  - Bills
  - Other
- 📊 View Total Income
- 📉 View Total Expenses
- 💵 View Remaining Balance
- 📋 Display all transactions using RecyclerView
- ✏️ Edit existing transactions
- 🗑️ Delete transactions with confirmation
- 📅 Add transaction date and time
- 💾 Local data storage using SharedPreferences
- 🌙 Dark Mode support
- 🔄 Transaction data persists after restarting the application

## 🛠️ Technologies Used

- **Java**
- **Android Studio**
- **XML**
- **Android SDK**
- **RecyclerView**
- **SharedPreferences**
- **Material Components**
- **Git & GitHub**

## 🏗️ Project Structure

The major components of the application include:

- `MainActivity.java` – Displays the dashboard and transaction list.
- `AddTransactionActivity.java` – Handles adding new transactions.
- `Transaction.java` – Model class for transaction data.
- `TransactionAdapter.java` – Handles displaying transactions in RecyclerView.
- `TransactionStorage.java` – Handles local storage of transaction data.
- XML layout files – Define the application's user interface.

## 💾 Data Storage

SpendWise uses **SharedPreferences** for local data storage.

Transaction information such as:

- Transaction type
- Amount
- Description
- Category
- Date and time

is stored locally on the device.

This allows the user's transaction records to remain available even after closing and reopening the application.

## 🧪 Testing

The application was tested for the following functionalities:

- Adding income transactions
- Adding expense transactions
- Calculating total income and expenses
- Calculating remaining balance
- Editing transactions
- Deleting transactions
- Selecting different categories
- Setting transaction date and time
- Persistence of transaction data
- Dark mode
- Handling invalid input
- Displaying transactions correctly in RecyclerView

## 📸 Screenshots

Screenshots demonstrating the application's interface and functionality can be added below.

### Dashboard
_Add screenshot here_

### Add Transaction
_Add screenshot here_

### Transaction List
_Add screenshot here_

### Dark Mode
_Add screenshot here_

## 🎯 Project Objectives

The main objectives of SpendWise are:

1. To develop a simple and user-friendly expense management application.
2. To provide an easy way to record income and expenses.
3. To calculate and display financial summaries.
4. To implement local data persistence.
5. To gain practical experience in Android application development using Java.
6. To apply concepts such as Activities, Intents, RecyclerView, input validation, and SharedPreferences.

## 🚀 Future Scope

Possible future improvements include:

- Cloud synchronization
- User authentication
- Graphs and visual financial reports
- Monthly and yearly expense analysis
- Budget management
- Export transactions to CSV or PDF
- Notifications and spending reminders
- Database integration using Room or SQLite

## 👨‍💻 Developer

**Arpit Das**

Final Year Electronics & Communication Engineering Student  
Guru Nanak Institute of Technology

## 📌 Project Type

**Major Project – Android Application Development**

---

⭐ If you find this project useful, consider giving the repository a star!
