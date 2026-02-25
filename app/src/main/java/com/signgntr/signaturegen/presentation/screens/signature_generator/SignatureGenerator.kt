package com.signgntr.signaturegen.presentation.screens.signature_generator

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.signgntr.signaturegen.domain.common.Result
import com.signgntr.signaturegen.presentation.common.BaseColumn
import com.signgntr.signaturegen.presentation.common.BaseScreen
import com.signgntr.signaturegen.presentation.utils.annotation.ThemePreview
import com.signgntr.signaturegen.presentation.utils.extentions.showSelected
import kotlinx.coroutines.launch


// 2. The "Wormhole" (CompositionLocal)
val LocalDrawingController = staticCompositionLocalOf<DrawingController> {
    error("No Controller Provided")
}

@Composable
fun SignatureGenerator(navController: NavController) {
    val state by remember { mutableStateOf(Result.Success<Any>("")) }
    val controller = remember { DrawingController() }

    CompositionLocalProvider(LocalDrawingController provides controller) {
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
}

@Composable
private fun DrawingToolsView() {
//    val listState = rememberLazyListState()
//    val flingBehavior = rememberSnapFlingBehavior(
//        lazyListState = listState
//    )
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
//            state = listState
        ) {
            ToolTypes.entries.forEachIndexed { index, types ->
                item(key = index) {
                    ToolView(types.drawingTool)
                }
            }
        }

//        AnimatedVisibility(
//            visible = listState.isScrollInProgress
//        ) {
//            HorizontalScrollbar(listState)
//        }
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ToolView(tool: DrawingTools) {
    var selectedState by rememberSaveable { mutableStateOf(tool.isSelected) }
    val tooltipState = rememberTooltipState(isPersistent = true)
    val coroutineScope = rememberCoroutineScope()
    val controller = LocalDrawingController.current

    LaunchedEffect(tooltipState.isVisible) {
        if (!tooltipState.isVisible) {
            selectedState = false
        }
    }

    TooltipBox(
        positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
        tooltip = {
            RichTooltip(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = tool.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                },
            ) {
                GetDrawingToolContentView(
                    type = tool.title,
                )
            }
        },
        state = tooltipState
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(10.dp, 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .showSelected(selectedState),
                onClick = {
                    if (tool.isSelectable && tool.title != ToolTypes.ERASER.drawingTool.title) {
                        coroutineScope.launch {
                            selectedState = !selectedState
                            tooltipState.show()
                        }
                    } else if (tool.title == ToolTypes.UNDO.drawingTool.title) {
                        controller.paths.removeLastOrNull()?.let {
                            controller.redoPaths.add(it)
                        }
                    } else if (tool.title == ToolTypes.REDO.drawingTool.title) {
                        controller.redoPaths.removeLastOrNull()?.let {
                            controller.paths.add(it)
                        }
                    } else if (tool.title == ToolTypes.RESET.drawingTool.title) {
                        controller.paths.clear()
                        controller.redoPaths.clear()
                    } else if (tool.title == ToolTypes.ERASER.drawingTool.title) {
                        controller.isEraser.value = !controller.isEraser.value
                        selectedState = !selectedState
                    }
                }) {
                Icon(
                    modifier = Modifier.rotate(if (tool.title.lowercase() == "redo") 180f else 0f),
                    imageVector = tool.icon,
                    contentDescription = tool.title
                )
            }
            Text(
                text = tool.title,
                fontWeight = if (tool.isSelectable && selectedState) FontWeight.Bold else FontWeight.Normal,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun GetDrawingToolContentView(type: String) {
    when (type.lowercase()) {
        ToolTypes.SIZE.drawingTool.title.lowercase() -> {
            SizeView()
        }

//        ToolTypes.BRUSH.drawingTool.title.lowercase() -> {
//            BrushTypesView(0) {
//
//            }
//        }

        ToolTypes.COLORS.drawingTool.title.lowercase() -> {
            ColorPickerView()
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
    val pathOffsets = remember { mutableStateListOf<Offset>() }
    val controller = LocalDrawingController.current

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offSet ->
                        controller.paths.add(Path().apply { moveTo(offSet.x, offSet.y) })
                        pathOffsets.add(offSet)
                    },
                    onDragEnd = {
                        pathOffsets.clear()
                    }) { change, _ ->
                    controller.paths.lastOrNull()?.lineTo(change.position.x, change.position.y)
                    pathOffsets.add(change.position)
                }
            }
//            .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    ) {
//        with(drawContext.canvas.nativeCanvas) {
//            val checkPoint = saveLayer(null, null) // Creates the offscreen buffer

            val drawPath = Path().apply {
                pathOffsets.forEachIndexed { index, offset ->
                    if (index == 0)
                        moveTo(offset.x, offset.y)
                    else
                        lineTo(offset.x, offset.y)
                }
            }
            drawPath(
                drawPath,
                controller.inkColor,
                style = Stroke(width = controller.strokeWidth, cap = StrokeCap.Round),
//                blendMode = if (controller.isEraser.value) BlendMode.Clear else BlendMode.Src
            )
            controller.paths.forEach { path ->
                drawPath(
                    path,
                    controller.inkColor,
                    style = Stroke(width = controller.strokeWidth, cap = StrokeCap.Round),
//                blendMode = if (controller.isEraser.value) BlendMode.Clear else BlendMode.Src
                )
            }
//            restoreToCount(checkPoint) // Merges the layer back to the screen
        }
//    }
}


@ThemePreview
@Composable
private fun Preview() {
    SignatureGenerator(navController = NavController(LocalContext.current))
}