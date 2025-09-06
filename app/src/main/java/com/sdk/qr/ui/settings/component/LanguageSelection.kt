package com.sdk.qr.ui.settings.component


import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sdk.qr.R
import com.sdk.qr.data.util.getLocalizedLanguageString
import com.sdk.qr.domain.model.settings.LanguageMode
import com.sdk.qr.ui.settings.common.SettingsItem
import com.sdk.qr.ui.settings.viewmodel.LanguageViewModel
import com.sdk.qr.ui.theme.size.responsiveBorderRadius
import com.sdk.qr.ui.theme.size.responsiveIconSize
import com.sdk.qr.ui.theme.size.responsivePadding


@Composable
fun LanguageSelection(
    viewModel: LanguageViewModel = viewModel()
) {
    val languageMode by viewModel.languageMode.collectAsStateWithLifecycle()
    var showDialog by rememberSaveable { mutableStateOf(false) }

    SettingsItem(
        title = stringResource(R.string.language),
        subtitle = "${stringResource(R.string.selected_label)}: ${stringResource(languageMode.labelRes)}",
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
        onClick = { showDialog = true }
    )

    if (showDialog) {
        LanguageSelectionDialog(
            currentLanguageMode = languageMode,
            onLanguageSelected = { mode ->
                viewModel.changeLanguageMode(mode)
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
private fun LanguageSelectionDialog(
    currentLanguageMode: LanguageMode,
    onLanguageSelected: (LanguageMode) -> Unit,
    onDismiss: () -> Unit
) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut() + scaleOut()
    ) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = getLocalizedLanguageString(R.string.language, currentLanguageMode),
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LanguageMode.entries.forEach { mode ->
                        LanguageOptionItem(
                            isSelected = currentLanguageMode == mode,
                            label = getLocalizedLanguageString(mode.labelRes, mode),
                            onSelect = { onLanguageSelected(mode) }
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text(
                        text = getLocalizedLanguageString(R.string.cancel, currentLanguageMode),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            },
            shape = RoundedCornerShape(responsiveBorderRadius())
        )
    }
}

@Composable
private fun LanguageOptionItem(
    isSelected: Boolean,
    label: String,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(responsiveBorderRadius()))
            .selectable(
                selected = isSelected,
                onClick = onSelect,
                role = Role.RadioButton
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 4.dp else 1.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(responsivePadding()),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.onPrimaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
            RadioButton(
                selected = isSelected,
                onClick = null // onClick Card orqali ishlaydi
            )
        }
    }
}
