package com.signgntr.signaturegen.presentation.utils.annotation

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Preview(showBackground = true,  uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
annotation class ThemePreview