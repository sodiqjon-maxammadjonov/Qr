package com.sdk.qr.ui.theme.size

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.TextUnit
import com.sdk.qr.ui.theme.Dimens

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun responsiveTextSize(): TextUnit {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    return when {
        screenWidth < 360 -> Dimens.TextSmall
        screenWidth < 600 -> Dimens.TextMedium
        else -> Dimens.TextLarge
    }
}