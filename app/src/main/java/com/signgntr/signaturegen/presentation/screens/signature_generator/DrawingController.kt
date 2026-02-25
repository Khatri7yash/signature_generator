package com.signgntr.signaturegen.presentation.screens.signature_generator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

// 1. The State Holder
class DrawingController {
    var strokeWidth by mutableFloatStateOf(5f) // Observable state
    var inkColor by mutableStateOf(Color.Black) // Observable state
}