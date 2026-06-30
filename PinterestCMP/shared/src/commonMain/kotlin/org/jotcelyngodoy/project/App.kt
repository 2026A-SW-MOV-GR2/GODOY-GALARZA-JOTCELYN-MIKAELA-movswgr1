package org.jotcelyngodoy.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource

import pinterestcmp.shared.generated.resources.Res
import pinterestcmp.shared.generated.resources.compose_multiplatform
import androidx.compose.foundation.layout.fillMaxSize

@Composable
@Preview
fun App() {
    MaterialTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            FeedScreen(MockPins.list, modifier = Modifier.fillMaxSize())
        }
    }
}