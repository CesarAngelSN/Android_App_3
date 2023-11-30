package com.example.farmacan.ui.activity

import androidx.compose.animation.core.EaseOutCirc
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.farmacan.R

@Composable
fun AuthenticationActivity(navController: NavController) {
    Column(
        Modifier
            .fillMaxSize(), Arrangement.SpaceEvenly, Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.farmacanlogo), contentDescription = "logo",
            Modifier
                .fillMaxWidth(0.6f)
                .scale(1.2f, 1.2f))
        Column (Modifier.fillMaxWidth(), Arrangement.SpaceEvenly, Alignment.CenterHorizontally) {
            Button(onClick = {
                navController.navigate("loginactivity")
            },
                Modifier
                    .fillMaxWidth(0.7f)
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)) {
                Text(text = "Log In", fontSize = 20.sp)
            }
            Button(onClick = {
                navController.navigate("registeractivity")
            },
                Modifier
                    .fillMaxWidth(0.7f)
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)) {
                Text(text = "Register", fontSize = 20.sp)
            }
        }
    }
}