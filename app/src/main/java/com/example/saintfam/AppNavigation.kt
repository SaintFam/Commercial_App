package com.example.saintfam

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.saintfam.screens.AuthScreen
import com.example.saintfam.screens.LoginScreen
import com.example.saintfam.screens.SignUpScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier){

    val navController = rememberNavController()
    NavHost(navController = navController , startDestination = "auth",modifier = Modifier) {
        composable("auth"){
            AuthScreen(modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),navController)
        }
        composable("login"){
            LoginScreen(modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding())
        }
        composable("signup"){
            SignUpScreen(modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding())
        }
    }
}