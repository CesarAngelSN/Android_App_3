package com.example.farmacan.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.example.farmacan.data.db.AppDatabase
import com.example.farmacan.data.entity.Drug
import com.example.farmacan.ui.nav.NavigationGraph
import com.example.farmacan.ui.theme.FarmacanTheme

class Main : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FarmacanTheme(dynamicColor = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val appDatabase: AppDatabase = Room.databaseBuilder(
                        applicationContext,
                        AppDatabase::class.java,
                        "SQLite_DB").fallbackToDestructiveMigration().
                    allowMainThreadQueries().build()
                    val drugsList = listOf(
                        Drug(0, "Paracetamol", "Acetaminophen", "Panadol", "Analgesic", "Fever and pain relief", "5.99"),
                        Drug(0, "Ibuprofen", "Ibuprofen", "Advil", "Anti-inflammatory", "Pain and inflammation relief. Ibuprofen is a nonsteroidal anti-inflammatory drug (NSAID) used to treat mild to moderate pain, and helps to relieve symptoms of arthritis (osteoarthritis, rheumatoid arthritis, or juvenile arthritis), such as inflammation, swelling, stiffness, and joint pain.", "7.49"),
                        Drug(0, "Omeprazole", "Omeprazole", "Prilosec", "Proton Pump Inhibitor", "Acid reflux treatment. Omeprazole is used to treat certain stomach and esophagus problems (such as acid reflux, ulcers). It works by decreasing the amount of acid your stomach makes. It relieves symptoms such as heartburn, difficulty swallowing, and persistent cough.", "9.99"),
                        Drug(0, "Loratadine", "Loratadine", "Claritin", "Antihistamine", "Allergy relief. Loratadine is an antihistamine used to relieve allergy symptoms such as watery eyes, runny nose, itching eyes/nose, sneezing, hives, and itching.", "3.99"),
                        Drug(0, "Amoxicillin", "Amoxicillin", "Amoxil", "Antibiotic", "Bacterial infection treatment. Amoxicillin is a penicillin antibiotic that fights bacteria. It is used to treat many different types of infections caused by bacteria, such as tonsillitis, bronchitis, pneumonia, and infections of the ear, nose, throat, skin, or urinary tract.", "12.99"),
                        Drug(0, "Azithromycin", "Azithromycin", "Zithromax", "Antibiotic", "Bacterial infection treatment. Azithromycin is used to treat a wide variety of bacterial infections. It is a macrolide-type antibiotic. It works by stopping the growth of bacteria.", "14.99"),
                        Drug(0, "Simvastatin", "Simvastatin", "Zocor", "Statins", "Cholesterol management. Simvastatin is used along with a proper diet to help lower 'bad' cholesterol and fats (such as LDL, triglycerides) and raise 'good' cholesterol (HDL) in the blood.", "8.99"),
                        Drug(0, "Citalopram", "Citalopram", "Celexa", "Antidepressant", "Depression treatment. Citalopram is used to treat depression. It may improve your energy level and feelings of well-being. Citalopram is known as a selective serotonin reuptake inhibitor (SSRI).", "10.49"),
                        Drug(0, "Amlodipine", "Amlodipine", "Norvasc", "Calcium channel blocker", "Hypertension treatment. Amlodipine is used with or without other medications to treat high blood pressure. Lowering high blood pressure helps prevent strokes, heart attacks, and kidney problems.", "7.99"),
                        //Drug(0, "Warfarin", "Warfarin", "Coumadin", "Anticoagulant", "Blood clot prevention. Warfarin is used to prevent blood clots from forming or growing larger in your blood and blood vessels. It is prescribed for people with certain types of irregular heartbeats, people with prosthetic (replacement or mechanical) heart valves, and people who have suffered a heart attack.", "5.49")
                    )
                    if (appDatabase.getDrugDao().getAll().isEmpty()) {
                        drugsList.forEach {
                            appDatabase.getDrugDao().insert(it)
                        }
                    }
                    appDatabase.getDrugDao().getAll().forEach {
                        println(it)
                    }
                    NavigationGraph(appDatabase, applicationContext)
                }
            }
        }
    }
}
