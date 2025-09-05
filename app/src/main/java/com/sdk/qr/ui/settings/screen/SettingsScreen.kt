package com.sdk.qr.ui.settings.screen

import SettingsItem
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.sdk.qr.R
import com.sdk.qr.ui.settings.common.SettingsGroup
import com.sdk.qr.ui.settings.common.SettingsSwitchItem
import com.sdk.qr.ui.settings.component.ThemeSelection
import com.sdk.qr.ui.theme.size.responsiveIconSize
import com.sdk.qr.ui.theme.size.responsiveSpacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {

    var isDarkModeEnabled by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.settings)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.height(responsiveSpacing()))

            SettingsGroup(title = stringResource(R.string.appearance)) {

                ThemeSelection()

                SettingsItem(
                    title = stringResource(R.string.language),
                    subtitle = stringResource(R.string.language_desc),
                    leading = {
                        Icon(
                            painter = painterResource(R.drawable.ic_globus),
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
                    onClick = { /* Language selection */ }
                )
            }

            Spacer(modifier = Modifier.height(responsiveSpacing() * 4))
        }
    }
}
