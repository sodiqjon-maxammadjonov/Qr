package com.sdk.qr.ui.component

import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun AnimatedSection(
    visible: Boolean,
    delay: Long,
    content: @Composable () -> Unit
) {
    val animationSpec = remember {
        tween<Float>(
            durationMillis = 400,
            delayMillis = delay.toInt(),
            easing = EaseOutCubic
        )
    }

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = animationSpec,
        label = "alpha"
    )

    val translationY by animateFloatAsState(
        targetValue = if (visible) 0f else 50f,
        animationSpec = animationSpec,
        label = "translationY"
    )

    Box(
        modifier = Modifier
            .graphicsLayer {
                this.alpha = alpha
                this.translationY = translationY
            }
    ) {
        content()
    }
}