package com.signgntr.signaturegen.presentation.screens.signature_generator

import androidx.compose.ui.graphics.vector.ImageVector

data class DrawingTools(
    val title: String,
    val icon: ImageVector,
    val isSelected: Boolean = false,
    val isSelectable: Boolean= true)