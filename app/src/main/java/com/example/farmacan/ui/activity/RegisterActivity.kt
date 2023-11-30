package com.example.farmacan.ui.activity

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.farmacan.R
import com.example.farmacan.data.db.AppDatabase
import com.example.farmacan.data.entity.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterActivity(navController: NavController, appDatabase: AppDatabase, applicationContext: Context){
    Column (
        Modifier
            .fillMaxSize(), Arrangement.SpaceEvenly, Alignment.CenterHorizontally){
        var user by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }
        var repeatedPassword by remember {
            mutableStateOf("")
        }
        var isError1 by remember {
            mutableStateOf(true)
        }
        var isError2 by remember {
            mutableStateOf(true)
        }
        var isError3 by remember {
            mutableStateOf(true)
        }
        Image(painter = painterResource(id = R.drawable.farmacanlogo), contentDescription = "brain", Modifier.scale(1.3f, 1.3f))
        Text(text = "Introduce your Information", fontSize = 20.sp)
        Column (Modifier.fillMaxWidth(), Arrangement.SpaceEvenly, Alignment.CenterHorizontally) {
            OutlinedTextField(
                value = user,
                onValueChange = { text -> user = text
                    isError1 = user.length > 15 || !user.matches(Regex("^[0-9a-zA-Z-_]*$")) || user.isEmpty() },
                label = { Text("User") },
                supportingText = {
                    Text(text = "${user.length}/15")
                },
                isError = isError1
            )
            OutlinedTextField(
                value = password,
                onValueChange = { text -> password = text
                    isError2 = password.length > 15 || !password.matches(Regex("^[0-9a-zA-Z-_]*$")) || password.isEmpty()},
                label = { Text("Password") },
                supportingText = {
                    Text(text = "${password.length}/15")
                },
                isError = isError2,
                visualTransformation = PasswordVisualTransformation()
            )
            OutlinedTextField(
                value = repeatedPassword,
                onValueChange = { text -> repeatedPassword = text
                    isError3 = repeatedPassword != password},
                label = { Text("Repeat the Password") },
                isError = isError3,
                visualTransformation = PasswordVisualTransformation()
            )
        }
        Row (Modifier.fillMaxWidth(), Arrangement.SpaceEvenly){
            Button(onClick = {
                val rightUser = Toast.makeText(applicationContext,
                    "Register for $user successfully done", Toast.LENGTH_SHORT)
                val wrongUser = Toast.makeText(applicationContext,
                    "User $user already exists", Toast.LENGTH_SHORT)
                if (appDatabase.getUserDao().getByName(user) == null) {
                    var newUser = User(0, user, password)
                    appDatabase.getUserDao().insert(newUser)
                    println(appDatabase.getUserDao().getAll())
                    rightUser.show()
                    navController.navigate("mainactivity/$user") {
                        popUpTo("registeractivity") {
                            inclusive = true
                        }
                    }
                }
                else {
                    wrongUser.show()
                    user = ""
                    password = ""
                    repeatedPassword = ""
                }
            },
                Modifier.width(140.dp),
                enabled = !isError1 && !isError2 && !isError3) {
                Text(text = "Register", fontSize = 18.sp)
            }
            Button(onClick = {
                navController.popBackStack()
            }, Modifier.width(140.dp)) {
                Text(text = "Back", fontSize = 18.sp)
            }
        }
    }
}