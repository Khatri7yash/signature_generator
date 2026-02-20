package com.signgntr.signaturegen.presentation.screens.signature_generator

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import com.signgntr.signaturegen.domain.common.Result
import com.signgntr.signaturegen.presentation.common.BaseColumn
import com.signgntr.signaturegen.presentation.common.BaseScreen
import com.signgntr.signaturegen.presentation.utils.annotation.ThemePreview

@Composable
fun SignatureGenerator() {
    val state by remember { mutableStateOf(Result.Success<Any>("")) }
    BaseScreen(title = "Generate Signature") {
        BaseColumn(uiState = state) {
            Box {
                CanvasDrawPath()
            }
        }
    }
}

@Composable
fun CanvasView(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        drawCircle(color = Color.Blue, radius = size.minDimension / 4)
    }
}

@Composable
fun CanvasDrawPath() {
    val paths = remember { mutableStateListOf<Path>() }
    val pathOffsets = remember { mutableStateListOf<Offset>() }
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offSet ->
                        paths.add(Path().apply { moveTo(offSet.x, offSet.y) })
                        pathOffsets.add(offSet)
                    },
                    onDragEnd = {
                        pathOffsets.clear()
                    }) { change, _ ->
                    paths.lastOrNull()?.lineTo(change.position.x, change.position.y)
                    pathOffsets.add(change.position)
                }
            }
    ) {

        val drawPath = Path().apply {
            pathOffsets.forEachIndexed { index, offset ->
                if (index == 0)
                    moveTo(offset.x, offset.y)
                else
                    lineTo(offset.x, offset.y)
            }
        }
        drawPath(drawPath, Color.Black, style = Stroke(width = 5f))
        paths.forEach { path ->
            drawPath(path, Color.Black, style = Stroke(width = 5f))
        }
    }
}


@ThemePreview
@Composable
fun Preview() {
    SignatureGenerator()
}