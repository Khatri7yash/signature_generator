package com.signgntr.signaturegen.presentation.screens.signature_generator

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.signgntr.signaturegen.domain.common.Result
import com.signgntr.signaturegen.presentation.common.BaseColumn
import com.signgntr.signaturegen.presentation.common.BaseScreen
import com.signgntr.signaturegen.presentation.utils.annotation.ThemePreview

@Composable
fun SignatureGenerator(navController: NavController) {
    val state by remember { mutableStateOf(Result.Success<Any>("")) }
    BaseScreen(navController = navController, title = "Generate Signature") {
        BaseColumn(uiState = state) {
            Column(verticalArrangement = Arrangement.SpaceEvenly) {
                Box(modifier = Modifier.weight(1f)) {
                    CanvasDrawPath()
                }
                DrawingToolsView()
            }
        }
    }
}

@Composable
private fun DrawingToolsView() {
    val tools = listOf(
        DrawingTools(title = "Brush", icon = Icons.Rounded.Edit),
        DrawingTools(title = "Colors", icon = Icons.Rounded.Menu),
        DrawingTools(title = "Eraser", icon = Icons.Default.Edit),
        DrawingTools(title = "Eraser", icon = Icons.Default.Edit),
        DrawingTools(title = "Eraser", icon = Icons.Default.Edit),
        DrawingTools(title = "Eraser", icon = Icons.Default.Edit),
        DrawingTools(title = "Eraser", icon = Icons.Default.Edit),
        DrawingTools(title = "Eraser", icon = Icons.Default.Edit),
        DrawingTools(title = "Eraser", icon = Icons.Default.Edit),
        DrawingTools(title = "Eraser", icon = Icons.Default.Edit),
        DrawingTools(title = "Eraser", icon = Icons.Default.Edit),
        DrawingTools(title = "Eraser", icon = Icons.Default.Edit),
        DrawingTools(title = "Eraser", icon = Icons.Default.Edit),
    )
    val listState = rememberLazyListState()
    val flingBehavior = rememberSnapFlingBehavior(
        lazyListState = listState
    )
//    val state = rememberScrollAreaState(lazyListState)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        LazyRow(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
//            flingBehavior = flingBehavior,
            state = listState
        ) {
            items(tools.size, key = { it }) {
                ToolView(tools[it])
            }
        }

        AnimatedVisibility(
            visible = listState.isScrollInProgress
        ) {
            HorizontalScrollbar(listState)
        }
//        HorizontalScrollbar(listState)
    }
}


@SuppressLint("FrequentlyChangingValue")
@Composable
fun HorizontalScrollbar(state: LazyListState) {

    val layoutInfo = state.layoutInfo
    val totalItems = layoutInfo.totalItemsCount

    if (totalItems == 0) return

    val visibleItems = layoutInfo.visibleItemsInfo
    if (visibleItems.isEmpty()) return

    val viewportWidth =
        layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset

    val firstVisibleItem = visibleItems.first().index
    val visibleCount = visibleItems.size

    val thumbHeightPercent =
        visibleCount.toFloat() / totalItems

    val thumbOffsetPercent =
        firstVisibleItem.toFloat() / totalItems

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
            .padding(end = 4.dp)
            .background(Color.Blue),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(thumbHeightPercent)
                .fillMaxHeight()
                .align(Alignment.BottomStart)
                .offset(x = (thumbOffsetPercent * 600).dp)
                .background(
                    Color.Gray,
                    RoundedCornerShape(2.dp)
                )
        )
    }
}


@Composable
private fun ToolView(tool: DrawingTools) {
    var selectedState by rememberSaveable { mutableStateOf(tool.isSelected) }
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(10.dp, 5.dp)
    ) {
        IconButton(onClick = {
            selectedState = !selectedState
        }) {
            Icon(imageVector = tool.icon, contentDescription = tool.title)
        }
        Text(
            text = tool.title,
            fontWeight = if (selectedState) FontWeight.Bold else FontWeight.Normal,
            fontSize = 12.sp
        )
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
    SignatureGenerator(navController = NavController(LocalContext.current))
}