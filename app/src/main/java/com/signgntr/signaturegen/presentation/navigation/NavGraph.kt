package com.signgntr.signaturegen.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.signgntr.signaturegen.presentation.screens.home.HomeScreen
import com.signgntr.signaturegen.presentation.screens.signature_generator.SignatureGenerator

val LocalCurrentRoute = (compositionLocalOf<String?> { null })

@Composable
fun Navigation() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?.substringBeforeLast("/")

    CompositionLocalProvider(LocalCurrentRoute provides currentRoute) {
        NavHost(
            navController = navController,
            startDestination = Screen.HomeScreen.route
        ) {
            composable(Screen.HomeScreen.route) {
                HomeScreen(navController)
            }
            composable(Screen.Signature.route) {
                SignatureGenerator(navController)
            }
        }
    }
}