package com.example.expensetracker.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetracker.viewmodel.TransactionViewModel
import com.example.expensetracker.data.model.TransactionType
import kotlin.math.max

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    navController: NavController,
    viewModel: TransactionViewModel = hiltViewModel()
) {
    val transactions = viewModel.allTransactions.observeAsState(initial = emptyList()).value

    val totalIncome = transactions.filter { it.type == TransactionType.INCOME }
        .sumOf { it.amount }
    val totalExpense = transactions.filter { it.type == TransactionType.EXPENSE }
        .sumOf { it.amount }

    val maxValue = max(totalIncome, totalExpense).takeIf { it > 0 } ?: 1.0

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Statistics") },
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
            Text(
                text = "Total Income and Expense",
                style = MaterialTheme.typography.titleMedium
            )

            // Bar chart
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val barWidth = size.width / 4
                    val spacing = size.width / 4
                    val incomeHeight = (totalIncome / maxValue) * size.height.toDouble()
                    val expenseHeight = (totalExpense / maxValue) * size.height.toDouble()

                    // Draw income bar
                    drawRect(
                        color = Color.Green,
                        topLeft = androidx.compose.ui.geometry.Offset(spacing / 2, size.height - incomeHeight.toFloat()),
                        size = androidx.compose.ui.geometry.Size(barWidth, incomeHeight.toFloat()),
                    )

                    // Draw expense bar
                    drawRect(
                        color = Color.Red,
                        topLeft = androidx.compose.ui.geometry.Offset(spacing * 1.5f, size.height - expenseHeight.toFloat()),
                        size = androidx.compose.ui.geometry.Size(barWidth, expenseHeight.toFloat()),
                    )
                }
            }

            // Display values
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                    Text(text = "Income", color = Color.Green, style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Rp. ${totalIncome.toInt()}", style = MaterialTheme.typography.bodyLarge)
                }
                Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                    Text(text = "Expense", color = Color.Red, style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Rp. ${totalExpense.toInt()}", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
