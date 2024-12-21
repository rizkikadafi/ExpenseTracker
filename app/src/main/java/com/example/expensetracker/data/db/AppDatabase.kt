package com.example.expensetracker.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.expensetracker.data.model.Transaction
import com.example.expensetracker.data.dao.TransactionDao
import com.example.expensetracker.utils.TransactionTypeConverter

@Database(entities = [Transaction::class], version = 1)
@TypeConverters(TransactionTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}

