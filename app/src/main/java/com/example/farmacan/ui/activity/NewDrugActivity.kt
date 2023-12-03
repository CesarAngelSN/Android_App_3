package com.example.farmacan.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.farmacan.R
import com.example.farmacan.data.db.AppDatabase
import com.example.farmacan.data.entity.Drug
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ShowToast")
@Composable
fun NewDrugActivity(navController: NavController, appDatabase: AppDatabase, applicationContext: Context) {
    val listOfDrugs = appDatabase.getDrugDao().getAll()
    val listOfBoxes = listOf("Box-10", "Box-20", "Box-30")
    val listOfDrugNames = List(listOfDrugs.size) {
        listOfDrugs[it].getName()
    }
    var selectedDrug by remember {
        mutableStateOf(listOfDrugs[0].getName())
    }
    var selectedBox by remember {
        mutableStateOf(listOfDrugs[0].getPackaging())
    }
    var description by remember {
        mutableStateOf("")
    }
    var selectedDate by remember {
        mutableStateOf("")
    }
    var price by remember {
        mutableStateOf("")
    }
    Column (
        Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        Arrangement.spacedBy(30.dp), Alignment.CenterHorizontally){
        Column (
            Modifier
                .fillMaxWidth()
                .height(150.dp),
            Arrangement.SpaceEvenly, Alignment.CenterHorizontally){
            Text(text = "Select the drug in the list", fontSize = 20.sp)
            NewDropDownMenu(
                list = listOfDrugNames,
                type = "drug",
                onSelectedItem = {
                        drugName -> selectedDrug = drugName
                })
        }
        Divider(Modifier.fillMaxWidth(0.8f), color = colorResource(id = R.color.graydrug), thickness = 1.dp)
        Column (
            Modifier
                .fillMaxWidth()
                .height(180.dp),
            Arrangement.SpaceEvenly, Alignment.CenterHorizontally){
            Text(text = "Select the box size", fontSize = 20.sp)
            NewDropDownMenu(
                list = listOfBoxes,
                type = "box",
                onSelectedItem = {
                        box -> selectedBox = box
                })
            Row (Modifier.fillMaxWidth(0.8f), Arrangement.SpaceEvenly, Alignment.CenterVertically){
                Text(text = "Shown color: ", fontSize = 20.sp)
                Box(modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .height(20.dp)
                    .background(
                        colorResource(
                            id = when (selectedBox) {
                                "Box-10" -> R.color.bluedrug
                                "Box-20" -> R.color.greendrug
                                else -> R.color.graydrug
                            }
                        )
                    ))
            }


        }
        Divider(Modifier.fillMaxWidth(0.8f), color = colorResource(id = R.color.graydrug), thickness = 1.dp)
        Column (
            Modifier
                .fillMaxWidth()
                .height(300.dp),
            Arrangement.SpaceEvenly, Alignment.CenterHorizontally){
            Text(text = "Write a description for the drug", fontSize = 20.sp)
            NewTextField(
                onTextChange = {
                        text -> description = text
                }
            )
        }
        Divider(Modifier.fillMaxWidth(0.8f), color = colorResource(id = R.color.graydrug), thickness = 1.dp)
        Column (
            Modifier
                .fillMaxWidth()
                .height(150.dp),
            Arrangement.SpaceEvenly, Alignment.CenterHorizontally){
            Text(text = "Select the expiration date", fontSize = 20.sp)
            Row (Modifier.fillMaxWidth(0.8f), Arrangement.SpaceBetween, Alignment.CenterVertically){
                NewDatePickerDialog(
                    onDatePicker = {
                            date -> selectedDate = date
                    }
                )
                TextField(value = selectedDate,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.width(125.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedTextColor = Color.White
                    ))
            }

        }
        Divider(Modifier.fillMaxWidth(0.8f), color = colorResource(id = R.color.graydrug), thickness = 1.dp)
        Column (
            Modifier
                .fillMaxWidth()
                .height(150.dp),
            Arrangement.SpaceEvenly, Alignment.CenterHorizontally){
            Text(text = "Set a price for the drug", fontSize = 20.sp)
            Row (Modifier.fillMaxWidth(0.8f),
                Arrangement.SpaceBetween,
                Alignment.CenterVertically){
                NewSlider(
                    onPrice = {
                            newPrice -> price = newPrice
                    }
                )
                TextField(
                    value = price,
                    onValueChange = {},
                    readOnly = true,
                    colors = TextFieldDefaults.colors(
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier.width(90.dp))
            }

        }
        Divider(Modifier.fillMaxWidth(0.8f), color = colorResource(id = R.color.graydrug), thickness = 1.dp)
        Row (Modifier.fillMaxWidth(), Arrangement.SpaceEvenly, Alignment.CenterVertically){
            var active by remember {
                mutableStateOf(false)
            }
            if (active) {
                Dialog(
                    onDismissRequest = {
                        active = false
                    }) {
                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        ),
                        onClick = {},
                        modifier = Modifier
                            .height(220.dp)
                            .padding(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(
                                id = when(selectedBox) {
                                    "Box-10" -> R.color.bluedrug
                                    "Box-20" -> R.color.greendrug
                                    else -> R.color.graydrug
                                })
                        )
                    ) {
                        Box(Modifier.fillMaxSize()) {
                            Column (Modifier.fillMaxSize(), Arrangement.SpaceEvenly, Alignment.CenterHorizontally){
                                Image(painter = painterResource(
                                    id = when (selectedDrug) {
                                        "Amplodipine" -> R.drawable.amlodipine
                                        "Amoxicillin" -> R.drawable.amoxicillin
                                        "Azithromycin" -> R.drawable.azithromycin
                                        "Citalopram" -> R.drawable.citalopram
                                        "Ibuprofen" -> R.drawable.ibuprofen
                                        "Loratadine" -> R.drawable.loratadine
                                        "Omeprazole" -> R.drawable.omeprazole
                                        "Paracetamol" -> R.drawable.paracetamol
                                        "Simvastatin" -> R.drawable.simvastatin
                                        "Warfarin" -> R.drawable.warfarin
                                        else -> R.drawable.amlodipine
                                    }
                                ),
                                    contentDescription = "img",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(90.dp))
                                Text(text = selectedDrug, textAlign = TextAlign.Center, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                                Text(text = "Box size: $selectedBox", textAlign = TextAlign.Center, color = Color.Black)
                                Text(text = if (price.isEmpty()) {
                                    "Price: Free"
                                } else {
                                    "Price: $price$"
                                }, textAlign = TextAlign.Center, color = Color.Black)
                            }
                        }
                    }
                }
            }
            Button(onClick = {
                active = !active
            }) {
                Text(text = "See a preview")
            }
            Button(
                onClick = {
                    val toastWrong = Toast.makeText(applicationContext,
                        "Description mustn't be empty.", Toast.LENGTH_SHORT)
                    val toastRight = Toast.makeText(applicationContext,
                        "Drug added successfully.", Toast.LENGTH_SHORT)
                    if (description.isEmpty()) {
                        toastWrong.show()
                    }
                    else {
                        val drug = Drug(0, selectedDrug,
                            appDatabase.getDrugDao().getByName(selectedDrug).getType(),
                            description, selectedBox, price)
                        appDatabase.getDrugDao().insert(drug)
                        toastRight.show()
                        navController.popBackStack()
                    }
                }) {
                Text(text = "Add New Drug")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewDropDownMenu(list: List<String>, type: String, onSelectedItem: (String) -> Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }
    var selected by remember {
        mutableStateOf(list[0])
    }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = Color.White
            ),
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            list.forEach {
                DropdownMenuItem(
                    text = { Text(text = it, color = Color.White) },
                    onClick = {
                        selected = it
                        onSelectedItem(selected)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun NewTextField(onTextChange: (String) -> Unit) {
    var text by remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onTextChange(text)
        },
        placeholder = {
            Text(text = "Description")
        },
        label = {
            Text(text = "New Description")
        },
        supportingText = {
            Text(text = "${text.length}/500")
        },
        isError = text.length > 500,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(200.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewDatePickerDialog(onDatePicker: (String) -> Unit) {
    val state = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Input
    )
    var openDialog by remember {
        mutableStateOf(false)
    }
    Button(onClick = {
        openDialog = !openDialog
    }) {
        Text(text = "Select Expiration Date")
    }
    if (openDialog) {
        DatePickerDialog(
            onDismissRequest = {
                openDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedDate = state.selectedDateMillis
                        val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate)
                        onDatePicker(formattedDate)
                        println(formattedDate)
                        openDialog = false
                    }
                ) {
                    Text("Ok", color = Color.White, fontSize = 20.sp)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                    }
                ) {
                    Text("Cancel", color = Color.White, fontSize = 20.sp)
                }
            }
        ) {
            DatePicker(
                state = state,
                colors = DatePickerDefaults.colors(
                    containerColor = Color.White

                )
            )
        }
    }
}

@Composable
fun NewSlider(onPrice: (String) -> Unit) {
    var sliderValue by remember {
        mutableFloatStateOf(0f)
    }
    Slider(
        value = sliderValue,
        onValueChange = {
            sliderValue = it
            onPrice(String.format("%.2f", sliderValue))
        },
        valueRange = 0f..100f.toFloat(),
        steps = 100,
        modifier = Modifier.fillMaxWidth(0.75f)
    )
}