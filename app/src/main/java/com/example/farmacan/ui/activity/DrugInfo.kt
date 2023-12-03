package com.example.farmacan.ui.activity

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farmacan.R
import com.example.farmacan.data.db.AppDatabase

@Composable
fun DrugInfo(appDatabase: AppDatabase, applicationContext: Context, drugId: String?) {
    val drug = drugId?.let { appDatabase.getDrugDao().getById(it.toInt()) }
    var keepReading by remember {
        mutableStateOf(false)
    }
    var buttonEnabled by remember {
        mutableStateOf(true)
    }
    Column (
        Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState(), enabled = keepReading), Arrangement.spacedBy(20.dp), Alignment.CenterHorizontally){
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(color = colorResource(id = R.color.greendrug)),
            contentAlignment = Alignment.Center) {
            if (drug != null) {
                Image(painter = painterResource(id = when (drug.getName()) {
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
                }),
                    contentDescription = "Image",
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(300.dp))
            }
            Button(onClick = {
                keepReading = true
                buttonEnabled = false
            },
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.BottomEnd),
                enabled = buttonEnabled) {
                Text(text = "Keep Reading")
            }
        }
        Text(text = "Characteristics", textAlign = TextAlign.Center, fontSize = 25.sp, fontWeight = FontWeight.Bold)
        Column (Modifier.fillMaxWidth(), Arrangement.spacedBy(20.dp), Alignment.CenterHorizontally){
            if (drug != null) {
                NewBox(boldText = "Name: ", text = drug.getName())
                NewBox(boldText = "Type: ", text = drug.getType())
                NewBox(boldText = "Box: ", text = drug.getPackaging())
                NewBox(boldText = "Price: ", text = drug.getPrice().ifEmpty {
                    "Free"
                })
                NewBox(boldText = "Description: ", text = drug.getDescription())
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}
@Composable
fun NewBox(boldText: String, text: String) {
    Box (
        Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color = colorResource(id = R.color.greendrug))
            .padding(12.dp)
            .fillMaxWidth(0.85f)){
        Row {
            Text(text = boldText, color = Color.Black, fontWeight = FontWeight.Bold)
            Text(text = text, color = Color.Black)
        }

    }
}