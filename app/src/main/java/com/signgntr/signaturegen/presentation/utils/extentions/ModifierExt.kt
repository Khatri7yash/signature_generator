package com.signgntr.signaturegen.presentation.utils.extentions

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.showSelected(selected: Boolean) = if(selected) this.background(MaterialTheme.colorScheme.onSecondary) else this