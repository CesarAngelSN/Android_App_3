package com.example.farmacan.ui.activity

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.farmacan.R
import com.example.farmacan.data.db.AppDatabase

@SuppressLint("ShowToast")
@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
fun LoginActivity(navController: NavController, appDatabase: AppDatabase, applicationContext: Context){
    var user by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Column (
        Modifier
            .fillMaxSize(), Arrangement.SpaceEvenly, Alignment.CenterHorizontally){
        Image(painter = painterResource(id = R.drawable.farmacanlogo),
            contentDescription = "brain",
            Modifier.scale(1.3f, 1.3f))
        Text(text = "Introduce your Credentials", textAlign = TextAlign.Center, fontSize = 20.sp)
        Column (Modifier.fillMaxWidth(), Arrangement.SpaceEvenly, Alignment.CenterHorizontally) {
            OutlinedTextField(
                value = user,
                onValueChange = { text -> user = text},
                label = { Text("User") },
                supportingText = {
                    Text(text = "${user.length}/15")
                },
                isError = (user.length > 15 || !user.matches(Regex("^[0-9a-zA-Z-_]*$")))
            )
            OutlinedTextField(
                value = password,
                onValueChange = { text -> password = text },
                label = { Text("Password") },
                supportingText = {
                    Text(text = "${password.length}/15")
                },
                isError = (password.length > 15 || !password.matches(Regex("^[0-9a-zA-Z-_]*$"))),
                visualTransformation = PasswordVisualTransformation()
            )
        }
        Row (Modifier.fillMaxWidth(), Arrangement.SpaceEvenly){
            Button(onClick = {
                val currentUser = appDatabase.getUserDao().getByName(user)
                if (currentUser != null && currentUser.getPassword() == password){
                    val userFound = Toast.makeText(applicationContext,
                        "Login for $user successfully done", Toast.LENGTH_SHORT)
                    userFound.show()
                    navController.navigate("mainactivity") {
                        popUpTo("loginactivity") {
                            inclusive = true
                        }
                    }
                }
                else if (currentUser != null){
                    val incorrectPassword = Toast.makeText(applicationContext,
                        "Password for user $user is not correct", Toast.LENGTH_SHORT)
                    incorrectPassword.show()
                    password = ""
                }
                else {
                    val userNotExists = Toast.makeText(applicationContext,
                        "User $user does not exist", Toast.LENGTH_SHORT)
                    userNotExists.show()
                    user = ""
                    password = ""
                }
            },
                Modifier.width(120.dp)) {
                Text(text = "Go", fontSize = 18.sp)
            }
            Button(onClick = {
                navController.popBackStack()
            },
                Modifier.width(120.dp)) {
                Text(text = "Back", fontSize = 18.sp)
            }
        }
    }
}