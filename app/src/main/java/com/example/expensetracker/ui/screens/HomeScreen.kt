package com.example.expensetracker.ui.screens

import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetracker.data.model.Transaction
import com.example.expensetracker.viewmodel.TransactionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: TransactionViewModel = hiltViewModel()
) {

    val transactionsState = viewModel.allTransactions.observeAsState(initial = emptyList())
    val transactions = transactionsState.value

    Scaffold(
      topBar = {
          TopAppBar(
              title = { Text("Home") },
              actions = {
                  IconButton(onClick = { navController.navigate("statistics") }) {
                      Icon(
                          imageVector = Icons.Default.BarChart,
                          contentDescription = "Statistics"
                      )
                  }
              }
          )
      },
      floatingActionButton = {
          FloatingActionButton(
              onClick = { navController.navigate("add_edit") },
              containerColor = MaterialTheme.colorScheme.primary
          ) {
              Text("+", style = MaterialTheme.typography.bodyLarge)
          }
      },
      modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
      Column(
          modifier = Modifier
              .fillMaxSize()
              .padding(16.dp)
      ) {
          SummarySection(
            income = viewModel.totalIncome.observeAsState(initial = 0.0).value,
            expense = viewModel.totalExpense.observeAsState(initial = 0.0).value
          )

          Spacer(modifier = Modifier.height(16.dp))

          Text(
              text = "Transactions",
              style = MaterialTheme.typography.titleMedium
          )

          Spacer(modifier = Modifier.height(8.dp))

          TransactionList(
              transactions = transactions,
          )
      }
    }

}

@Composable
fun SummarySection(income: Double, expense: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Income",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Rp ${income.toInt()}",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Column {
                Text(
                    text = "Expense",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.error
                )
                Text(
                    text = "Rp ${expense.toInt()}",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Composable
fun TransactionList(
    transactions: List<Transaction>
) {
    if (transactions.isEmpty()) {
        Text(
            text = "No transactions available",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(transactions) { transaction ->
                TransactionCard(transaction = transaction)
            }
        }
    }
}

@Composable
fun TransactionCard(transaction: Transaction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = transaction.title,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = transaction.category,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Text(
                text = "Rp ${transaction.amount.toInt()}",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}
