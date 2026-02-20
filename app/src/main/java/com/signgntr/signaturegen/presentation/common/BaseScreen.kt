package com.signgntr.signaturegen.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavController
import com.signgntr.signaturegen.presentation.navigation.LocalCurrentRoute
import com.signgntr.signaturegen.presentation.navigation.Screen
import com.signgntr.signaturegen.presentation.theme.SignatureGeneratorTheme
import com.signgntr.signaturegen.presentation.utils.annotation.ThemePreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(
    navController: NavController,
    title: String,
    isEmpty: Boolean = false,
    content: @Composable (() -> Unit)
) {
    val route = LocalCurrentRoute.current
    val scope = rememberCoroutineScope()

    SignatureGeneratorTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            topBar = {
                CenterAlignedTopAppBar(
                    modifier = Modifier.testTag("topBar"),
                    title = {
                        Text(
                            modifier = Modifier.testTag("screenTitle"),
                            text = title
                        )
                    },
                    navigationIcon = {
                        when {
                            route == Screen.HomeScreen.route -> {}

                            else -> {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Localized description"
                                    )
                                }
                            }
                        }

                    },
                    actions = {
                        if(route == Screen.Signature.route)
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = "Localized description"
                            )
                        }
                    })

            },
            bottomBar = {
            },
            snackbarHost = {}) { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                color = MaterialTheme.colorScheme.background
            ) {
                content()
            }
        }
    }
}

@ThemePreview
@Composable
fun Preview() {
    BaseScreen(navController = NavController(LocalContext.current),
        "Title"
    ) {

    }
}