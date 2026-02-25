package com.signgntr.signaturegen.presentation.screens.signature_generator

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.signgntr.signaturegen.presentation.utils.annotation.ThemePreview
import com.signgntr.signaturegen.presentation.utils.extentions.showSelected

@Composable
fun ColorPickerView() {
    // Grab the controller from the "wormhole"
    val controller = LocalDrawingController.current

    Column {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            InkColor.entries.forEachIndexed { index, color ->
                item(key = index) {
                    ColorItem(color.color == (controller.inkColor), color.color) { color ->
                        controller.inkColor = color
                    }
                }
            }
        }
    }
}

@Composable
private fun ColorItem(
    isSelected: Boolean,
    color: Color,
    selectedSize: (Color) -> Unit
) {
    Canvas(
        modifier = Modifier
            .size(50.dp)
            .clip(shape = CircleShape)
            .showSelected(isSelected)
            .clickable(onClick = { selectedSize(color) })
    ) {
        drawCircle(
            color = color,
            radius = 35f
        )
    }
}

@ThemePreview
@Composable
private fun Preview() {
    ColorPickerView()
}