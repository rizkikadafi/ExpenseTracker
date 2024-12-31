package com.example.expensetracker.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.expensetracker.data.dao.TransactionDao
import com.example.expensetracker.data.model.Transaction
import com.example.expensetracker.data.model.TransactionType

@HiltViewModel
class TransactionViewModel @Inject constructor(
  private val transactionDao: TransactionDao
) : ViewModel() {

    val allTransactions: LiveData<List<Transaction>> = transactionDao.getAllTransactions().asLiveData()
    val totalIncome: LiveData<Double> = getTotalIncome().asLiveData()
    val totalExpense: LiveData<Double> = getTotalExpense().asLiveData()

    fun getTotalIncome(): Flow<Double> {
        return transactionDao.getTotalByType(TransactionType.INCOME.name)
    }

    fun getTotalExpense(): Flow<Double> {
        return transactionDao.getTotalByType(TransactionType.EXPENSE.name)
    }

    // Tambah transaksi
    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            transactionDao.insert(transaction)
        }
    }

    // Update transaksi
    fun updateTransaction(transaction: Transaction) {
        viewModelScope.launch {
            transactionDao.update(transaction)
        }
    }

    // Hapus transaksi
    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            transactionDao.delete(transaction)
        }
    }

    
}
