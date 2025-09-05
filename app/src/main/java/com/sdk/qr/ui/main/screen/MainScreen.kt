package com.sdk.qr.ui.main.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sdk.qr.R
import com.sdk.qr.ui.main.common.BottomNavItem
import com.sdk.qr.ui.settings.screen.SettingsScreen
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var isLoading by remember { mutableStateOf(true) }
    var selectedItem by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Scan) }

    LaunchedEffect(Unit) {
        delay(100)
        isLoading = false
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                val items = listOf(
                    BottomNavItem.Scan,
                    BottomNavItem.Generate,
                    BottomNavItem.History,
                    BottomNavItem.Settings
                )
                items.forEach { item ->
                    NavigationBarItem(
                        selected = selectedItem == item,
                        onClick = { selectedItem = item },
                        icon = { item.icon() },
                        label = { Text(stringResource(id = item.title)) }
                    )
                }
            }
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            when (selectedItem) {
                is BottomNavItem.Scan -> ScanScreen(Modifier.padding(paddingValues))
                is BottomNavItem.Generate -> GenerateScreen(Modifier.padding(paddingValues))
                is BottomNavItem.History -> HistoryScreen(Modifier.padding(paddingValues))
                is BottomNavItem.Settings -> SettingsScreen()
            }
        }
    }
}

@Composable
private fun ScanScreen(modifier: Modifier = Modifier) {
    CenteredContent(text = stringResource(id = R.string.scan), modifier = modifier)
}

@Composable
private fun GenerateScreen(modifier: Modifier = Modifier) {
    CenteredContent(text = stringResource(id = R.string.generate), modifier = modifier)
}

@Composable
private fun HistoryScreen(modifier: Modifier = Modifier) {
    CenteredContent(text = stringResource(id = R.string.history), modifier = modifier)
}


@Composable
private fun CenteredContent(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
