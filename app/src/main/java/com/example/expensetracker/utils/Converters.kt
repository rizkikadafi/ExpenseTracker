package com.example.expensetracker.utils

import androidx.room.TypeConverter
import com.example.expensetracker.data.model.TransactionType

class TransactionTypeConverter {

    @TypeConverter
    fun fromTransactionType(type: TransactionType): String {
        return type.name // Menyimpan nama enum sebagai String
    }

    @TypeConverter
    fun toTransactionType(type: String): TransactionType {
        return TransactionType.valueOf(type) // Mengonversi kembali dari String ke enum
    }
}
