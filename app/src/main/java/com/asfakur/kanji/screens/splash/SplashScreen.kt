package com.asfakur.kanji.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.asfakur.kanji.R
import com.asfakur.kanji.ui.theme.Splash
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    gotoHomeIndex: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2000)
        gotoHomeIndex()
    }
    SplashScreenSkeleton()
}

@Composable
fun SplashScreenSkeleton(modifier: Modifier = Modifier) {
    Scaffold(containerColor = MaterialTheme.colorScheme.primaryContainer) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(modifier = Modifier.fillMaxSize(),painter = Splash, contentDescription = null)
        }
    }
}

@Preview
@Composable
private fun SplashPreview() {
    SplashScreenSkeleton()
}