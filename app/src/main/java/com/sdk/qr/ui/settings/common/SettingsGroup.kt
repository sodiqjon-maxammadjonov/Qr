package com.sdk.qr.ui.settings.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sdk.qr.ui.theme.size.responsivePadding
import com.sdk.qr.ui.theme.size.responsiveSpacing

@Composable
fun SettingsGroup(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(
                vertical = 8.dp
            )
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            content()
        }

        Spacer(modifier = Modifier.height(responsiveSpacing() * 1.5f))
    }
}