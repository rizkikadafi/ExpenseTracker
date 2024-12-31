package com.example.expensetracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.expensetracker.data.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    // Tambah transaksi
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: Transaction)

    // Update transaksi
    @Update
    suspend fun update(transaction: Transaction)

    // Hapus transaksi
    @Delete
    suspend fun delete(transaction: Transaction)

    @Query("SELECT SUM(amount) FROM transactions WHERE type = :type")
    fun getTotalByType(type: String): Flow<Double>

    // Ambil semua transaksi
    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<Transaction>>

    // Ambil transaksi berdasarkan kategori
    @Query("SELECT * FROM transactions WHERE category = :category ORDER BY date DESC")
    fun getTransactionsByCategory(category: String): LiveData<List<Transaction>>

}
