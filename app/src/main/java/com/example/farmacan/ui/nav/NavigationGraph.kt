package com.example.farmacan.ui.nav

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.farmacan.data.db.AppDatabase
import com.example.farmacan.ui.activity.AuthenticationActivity
import com.example.farmacan.ui.activity.DrugInfo
import com.example.farmacan.ui.activity.FirstActivity
import com.example.farmacan.ui.activity.LoginActivity
import com.example.farmacan.ui.activity.MainActivity
import com.example.farmacan.ui.activity.NewDrugActivity
import com.example.farmacan.ui.activity.RegisterActivity

@Composable
fun NavigationGraph(appDatabase: AppDatabase, applicationContext: Context) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Paths.FirstActivity.path) {
        composable(Paths.FirstActivity.path) {
            FirstActivity(navController)
        }
        composable(Paths.AuthenticationActivity.path) {
            AuthenticationActivity(navController)
        }
        composable(Paths.LoginActivity.path) {
            LoginActivity(navController, appDatabase, applicationContext)
        }
        composable(Paths.RegisterActivity.path) {
            RegisterActivity(navController, appDatabase, applicationContext)
        }
        composable(Paths.MainActivity.path) {
            MainActivity(navController, appDatabase)
        }
        composable(Paths.NewDrugActivity.path) {
            NewDrugActivity(navController, appDatabase, applicationContext)
        }
        composable(Paths.DrugInfo.path + "/{id}") {
            val drugId = it.arguments?.getString("id")
            DrugInfo(appDatabase, applicationContext, drugId)
        }
    }
}