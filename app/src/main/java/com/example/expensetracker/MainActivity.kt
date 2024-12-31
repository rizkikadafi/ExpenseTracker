package com.example.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.expensetracker.data.model.Transaction
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import com.example.expensetracker.ui.screens.HomeScreen
import com.example.expensetracker.ui.screens.AddEditScreen
import com.example.expensetracker.ui.screens.StatisticsScreen
import com.example.expensetracker.ui.screens.TransactionCard
import com.google.gson.Gson
import com.google.gson.GsonBuilder

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                val navController = rememberNavController()

                Scaffold(
                  modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                      NavHost(
                        navController, 
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                      ) {
                          composable("home") { HomeScreen(navController) }
                          composable("add_edit") { AddEditScreen(navController) }
                          composable("add_edit/{transaction}") { navBackStackEntry ->
                              val transactionJson = navBackStackEntry.arguments?.getString("transaction")

                              val gson: Gson = GsonBuilder().create()
                              val transactionObject = gson.fromJson(transactionJson, Transaction::class.java)

                              AddEditScreen(navController, transactionToEdit = transactionObject)
                          }
                          composable("statistics") { StatisticsScreen(navController) }
                      }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExpenseTrackerTheme {
        Greeting("Android")
    }
}
