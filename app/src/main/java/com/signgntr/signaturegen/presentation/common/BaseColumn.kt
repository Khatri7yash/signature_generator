package com.signgntr.signaturegen.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.signgntr.signaturegen.domain.common.Result


@Composable
fun <R> BaseColumn(
    uiState: Result<R>,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    // Update errorState whenever errorMessage changes
    Column(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = if (uiState == Result.Loading) Arrangement.Center else Arrangement.Top
    ) {
        when (uiState) {
            is Result.Loading -> {
                CircularProgressIndicator(modifier = Modifier.testTag("ProgressIndicator"))
            }

            is Result.Success -> {
                content()
            }

            is Result.Error -> {
                ErrorAlert(errorMessage = uiState.exceptionMessage) {
                    // Clear the error state when the "OK" button is clicked
                }
            }
        }
    }
}