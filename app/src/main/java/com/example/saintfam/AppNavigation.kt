package com.example.saintfam

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.saintfam.screens.AuthScreen
import com.example.saintfam.screens.HomeScreen
import com.example.saintfam.screens.LoginScreen
import com.example.saintfam.screens.SignUpScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun AppNavigation(modifier: Modifier = Modifier){
      val isLoggedIn = Firebase.auth.currentUser != null
      val FirstPage = if (isLoggedIn) "home" else "auth"
    val navController = rememberNavController()
    NavHost(navController = navController , startDestination = FirstPage ,modifier = Modifier) {
        composable("auth"){
            AuthScreen(modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),navController)
        }
        composable("login"){
            LoginScreen(modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),navController)
        }
        composable("signup"){
            SignUpScreen(modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),navController)
        }
        composable(route = "home"){
            HomeScreen(modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),navController)
        }
    }
}