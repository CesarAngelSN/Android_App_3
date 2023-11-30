package com.example.farmacan.ui.nav

sealed class Paths(val path: String) {
    object FirstActivity : Paths("firstactivity")
    object AuthenticationActivity : Paths("authenticationactivity")
    object LoginActivity : Paths("loginactivity")
    object RegisterActivity : Paths("registeractivity")
    object MainActivity : Paths("mainactivity")
    object NewDrugActivity : Paths("newdrugactivity")
}
