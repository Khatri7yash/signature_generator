package com.signgntr.signaturegen.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val title: String = "",
    val objectName: String = "",
    val objectPath: String = ""
) {
    data object HomeScreen:
        Screen(route = "home_screen", title = "Home")
    data object Signature:
        Screen("signature_screen", title = "Generate Signature")
    data object SavedSignature:
        Screen("saved_signature", title = "Saved Signature")
}