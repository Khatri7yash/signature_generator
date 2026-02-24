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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.signgntr.signaturegen.presentation.utils.annotation.ThemePreview
import com.signgntr.signaturegen.presentation.utils.extentions.showSelected

@Composable
fun SizeView(selected: Int, selectedSize: (Int) -> Unit) {
    Column {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(8, key = { it }) {
                SizeItem(it == selected, it) { size ->
                    selectedSize(size)
                }
            }
        }
    }
}

@Composable
private fun SizeItem(
    isSelected: Boolean,
    size: Int,
    selectedSize: (Int) -> Unit
) {
    Canvas(
        modifier = Modifier
            .size(50.dp)
            .clip(shape = CircleShape)
            .showSelected(isSelected)
            .clickable(onClick = { selectedSize(size) })
    ) {
        drawLine(
            color = Color.Black,
            start = Offset(50f, 50f),
            end = Offset(150f, 100f),
            strokeWidth = size + 5f
        )
    }
}

@ThemePreview
@Composable
private fun Preview() {
    SizeView(0) {}
}