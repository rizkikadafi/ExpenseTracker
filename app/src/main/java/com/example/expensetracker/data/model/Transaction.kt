package com.example.expensetracker.data.model

import androidx.lifecycle.ViewModel
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

enum class TransactionType {
  INCOME,
  EXPENSE,
}

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val amount: Double,
    val type: TransactionType,
    val category: String,
    val date: Long
)
