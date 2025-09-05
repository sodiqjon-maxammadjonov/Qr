package com.sdk.qr.ui.main.common

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.sdk.qr.R
import com.sdk.qr.ui.theme.size.responsiveIconSize

sealed class BottomNavItem(val title: Int, val icon: @Composable () -> Unit) {
    object Scan : BottomNavItem(
        R.string.scan,
        {
            Icon(
                painter = painterResource(R.drawable.ic_scan),
                contentDescription = null,
                modifier = Modifier.size(responsiveIconSize())
            )
        }
    )
    object Generate : BottomNavItem(
        R.string.generate,
        {
            Icon(
                painter = painterResource(R.drawable.ic_qr),
                contentDescription = null,
                modifier = Modifier.size(responsiveIconSize())
            )
        }
    )
    object History : BottomNavItem(
        R.string.history,
        {
            Icon(
                painter = painterResource(R.drawable.ic_history),
                contentDescription = null,
                modifier = Modifier.size(responsiveIconSize())
            )
        }
    )
    object Settings : BottomNavItem(
        R.string.settings,
        {
            Icon(
                painter = painterResource(R.drawable.ic_settings),
                contentDescription = null,
                modifier = Modifier.size(responsiveIconSize())
            )
        }
    )
}
