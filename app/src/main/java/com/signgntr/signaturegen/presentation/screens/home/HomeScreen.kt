package com.signgntr.signaturegen.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.signgntr.signaturegen.domain.common.Result
import com.signgntr.signaturegen.presentation.common.BaseColumn
import com.signgntr.signaturegen.presentation.common.BaseScreen
import com.signgntr.signaturegen.presentation.utils.annotation.ThemePreview

@Composable
fun HomeScreen() {
    val state by remember { mutableStateOf(Result.Success<Any>("")) }
    BaseScreen(title = "") {
        BaseColumn(uiState = state) {
            Box{
                ButtonScreen()
            }
        }
    }
}

@Composable
fun ButtonScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = {}) {
                Text(text = "Generate Signature")
            }
            Button(onClick = {}) {
                Text(text = "Saved Signature")
            }
        }
    }
}

@ThemePreview
@Composable
fun Preview() {
    HomeScreen()
}