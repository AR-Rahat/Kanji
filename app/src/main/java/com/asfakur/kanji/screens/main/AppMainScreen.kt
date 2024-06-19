package com.asfakur.kanji.screens.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.asfakur.kanji.MainNavHost

@Composable
fun AppMainScreen() {
    AppMainScreenSkeleton()
}

@Composable
fun AppMainScreenSkeleton(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    MainNavHost(navController = navController)
}

@Preview
@Composable
private fun MainScreenPreview() {
    AppMainScreenSkeleton()
}
