package com.signgntr.signaturegen.presentation.screens.signature_generator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.signgntr.signaturegen.domain.common.Result
import com.signgntr.signaturegen.presentation.common.BaseColumn
import com.signgntr.signaturegen.presentation.common.BaseScreen
import com.signgntr.signaturegen.presentation.utils.annotation.ThemePreview

@Composable
fun SignatureGenerator(){
    val state by remember { mutableStateOf(Result.Success<Any>("")) }
    BaseScreen(title = "Generate Signature") {
        BaseColumn(uiState = state) {
            Box{
                CanvasView(Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun CanvasView(modifier: Modifier = Modifier) {
    androidx.compose.foundation.Canvas(modifier = modifier) {
        drawCircle(color = Color.Blue, radius = size.minDimension / 2)
    }
}


@ThemePreview
@Composable
fun Preview() {
    SignatureGenerator()
}