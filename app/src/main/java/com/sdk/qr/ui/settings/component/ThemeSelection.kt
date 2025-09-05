package com.sdk.qr.ui.settings.component

import SettingsItem
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sdk.qr.R
import com.sdk.qr.domain.model.ThemeMode
import com.sdk.qr.ui.settings.viewmodel.ThemeViewModel
import com.sdk.qr.ui.theme.size.responsiveIconSize
import com.sdk.qr.ui.theme.size.responsivePadding

@Composable
fun ThemeSelection(
    viewModel: ThemeViewModel = viewModel()
) {
    val themeMode by viewModel.themeMode.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    // Settings item
    SettingsItem(
        title = stringResource(R.string.theme),
        subtitle = "${stringResource(R.string.selected)}: ${themeMode.name}",
        leading = {
            Icon(
                painter = painterResource(R.drawable.ic_brush),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(responsiveIconSize())
            )
        },
        trailing = {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_right),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(responsiveIconSize())
            )
        },
        onClick = { showDialog = true }
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(text = stringResource(R.string.theme))
            },
            text = {
                Column {
                    ThemeMode.entries.forEach { mode ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(responsivePadding()),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = mode.name)
                            RadioButton(
                                selected = themeMode == mode,
                                onClick = {
                                    viewModel.changeThemeMode(mode)
                                    showDialog = false
                                }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}
