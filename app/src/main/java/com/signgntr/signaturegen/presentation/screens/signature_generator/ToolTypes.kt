package com.signgntr.signaturegen.presentation.screens.signature_generator

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.ui.graphics.Color

enum class ToolTypes(val drawingTool:DrawingTools ) {
    SIZE(drawingTool = DrawingTools(title = "Size",icon = Icons.Rounded.Edit)),
//    BRUSH(drawingTool = DrawingTools(title = "Brush",icon = Icons.Rounded.Edit)),
    COLORS(drawingTool = DrawingTools(title = "Colors",icon = Icons.Rounded.Menu)),
    ERASER(drawingTool = DrawingTools(title = "Eraser",icon = Icons.Default.Edit)),
    UNDO(drawingTool = DrawingTools(title = "Undo",icon = Icons.Rounded.Refresh, isSelectable = false)),
    REDO(drawingTool = DrawingTools(title = "Redo",icon = Icons.Rounded.Refresh, isSelectable = false)),

}

enum class InkColor(val color: Color ) {
    BLACK(color = Color.Black),
    BLUE(color = Color.Blue),
    GRAY(color = Color.Gray),
    RED(color = Color.Red),
}