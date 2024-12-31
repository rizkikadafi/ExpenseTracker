package com.example.expensetracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetracker.data.model.Transaction
import com.example.expensetracker.data.model.TransactionType
import com.example.expensetracker.viewmodel.TransactionViewModel
import androidx.compose.material.icons.filled.ArrowBack
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(
    navController: NavController,
    viewModel: TransactionViewModel = hiltViewModel(),
    transactionToEdit: Transaction? = null,

) {

    // Form state
    var title by remember { mutableStateOf(transactionToEdit?.title ?: "") }
    var amount by remember { mutableStateOf(transactionToEdit?.amount?.toString() ?: "") }
    var type by remember { mutableStateOf(transactionToEdit?.type ?: TransactionType.INCOME) }
    var category by remember { mutableStateOf(transactionToEdit?.category ?: "") }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = if (transactionToEdit == null) "Add Transaction" else "Edit Transaction") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title input
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            // Amount input
            TextField(
                value = amount,
                onValueChange = { amount = it.filter { char -> char.isDigit() || char == '.' } },
                label = { Text("Amount") },
                modifier = Modifier.fillMaxWidth()
            )

            // Type selection (Income or Expense)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { type = TransactionType.INCOME },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (type == TransactionType.INCOME) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Text("Income")
                }
                Button(
                    onClick = { type = TransactionType.EXPENSE },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (type == TransactionType.EXPENSE) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Text("Expense")
                }
            }

            // Category input
            TextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth()
            )

            // Save button
            Button(
                onClick = {
                    // Parse amount safely
                    val parsedAmount = amount.toDoubleOrNull() ?: 0.0

                    val newTransaction = Transaction(
                        id = transactionToEdit?.id ?: 0, // Use existing ID if editing
                        title = title,
                        amount = parsedAmount,
                        type = type,
                        category = category,
                        date = transactionToEdit?.date ?: System.currentTimeMillis()
                    )

                    // Save or update transaction
                    if (transactionToEdit == null) {
                        viewModel.addTransaction(newTransaction)
                    } else {
                        viewModel.updateTransaction(newTransaction)
                    }

                    // Navigate back
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}
