package com.example.saintfam

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.saintfam.com.example.saintfam.screens.ProductDetailsScreen
import com.example.saintfam.pages.*
import com.example.saintfam.screens.*
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun AppNavigation(modifier: Modifier = Modifier){
      val isLoggedIn = Firebase.auth.currentUser != null
      val FirstPage = if (isLoggedIn) "home" else "auth"
    val navController = rememberNavController()
    GlobalNavigation.navController = navController
    NavHost(navController = navController , startDestination = FirstPage ,modifier = modifier) {
        composable("auth"){
            AuthScreen(navController = navController)
        }
        composable("login"){
            LoginScreen(navController = navController)
        }
        composable("signup"){
            SignUpScreen(navController = navController)
        }
        composable(route = "home"){
            HomeScreen(navController = navController)
        }
        composable(route = "category-product/{categoryId}"){
            val catId = it.arguments?.getString("categoryId")
            CategoryProductPage(categoryId = catId?:"")
        }

        composable(route = "checkout"){
            CheckOut()
        }
        composable(route = "productdetailsscreen/{productId}"){
            var productId = it.arguments?.getString("productId")
            ProductDetailsScreen(modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),productId?:"")
        }
    }
}

object  GlobalNavigation  {
    lateinit var navController: NavHostController
}