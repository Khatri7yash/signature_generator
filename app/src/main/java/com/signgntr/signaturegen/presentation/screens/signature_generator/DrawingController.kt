package com.signgntr.signaturegen.presentation.screens.signature_generator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

// 1. The State Holder
class DrawingController {
    var strokeWidth by mutableFloatStateOf(5f) // Observable state
    var inkColor by mutableStateOf(Color.Black) // Observable state
    val paths = mutableStateListOf<PathData>()
    val redoPaths = mutableStateListOf<PathData>()
    var isEraser =  mutableStateOf(false)
}

data class PathData(val path: Path, val color: Color, val strokeWidth: Float)