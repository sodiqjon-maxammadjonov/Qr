package com.sdk.qr.ui.theme.size

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.platform.LocalConfiguration
import com.sdk.qr.ui.theme.Dimens


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun responsiveIconSize(): Dp {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    return when {
        screenWidth < 360 -> Dimens.IconSmall
        screenWidth < 600 -> Dimens.IconMedium
        else -> Dimens.IconLarge
    }
}
