package com.example.farmacan.ui.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.farmacan.data.db.AppDatabase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewDrugActivity(navController: NavController, appDatabase: AppDatabase) {
    val state = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Input
    )
    Column (Modifier.fillMaxSize(), Arrangement.Top, Alignment.CenterHorizontally){
        var openDialog by remember {
            mutableStateOf(false)
        }
        Button(onClick = {
            openDialog = !openDialog
        }) {
            Text(text = "Mostrar diálogo de Date Picker")
        }
        if (openDialog) {
            DatePickerDialog(
                onDismissRequest = {
                    openDialog = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            openDialog = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openDialog = false
                        }
                    ) {
                        Text("CANCEL")
                    }
                }
            ) {
                DatePicker(
                    state = state
                )
            }
        }
    }
}