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
import androidx.compose.ui.graphics.StrokeCap
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
//                CanvasView(Modifier.fillMaxSize())
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
    val signatures = remember { mutableStateListOf<List<Offset>>() }
    val points = remember { mutableStateListOf<Offset>() }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        points.clear()
                    },
                    onDragEnd = {
                        signatures.add(points)
//                        points.clear()
                    }) { change, _ ->
                    // 3. Adding to state list triggers recomposition
                    points.add(change.position)
                }
            }
    ) {


        // 4. Build the path dynamically from state
//        val singlePath = Path().apply {
//            points.forEachIndexed { i, point ->
//                if (i == 0) moveTo(point.x, point.y) else lineTo(point.x, point.y)
//            }
//        }
//        drawPath(singlePath, Color.Black, style = Stroke(width = 5f))


        // 4. Build the path dynamically from state
        signatures.forEach { singleSignature ->
            val path = Path().apply {
                singleSignature.forEachIndexed { i, point ->
                    if (i == 0) moveTo(point.x, point.y) else lineTo(point.x, point.y)
                }
            }
            drawPath(path, Color.Black, style = Stroke(width = 5f))
        }
    }
}


@ThemePreview
@Composable
fun Preview() {
    SignatureGenerator()
}