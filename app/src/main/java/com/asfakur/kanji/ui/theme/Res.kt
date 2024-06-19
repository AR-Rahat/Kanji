package com.asfakur.kanji.ui.theme


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.asfakur.kanji.R.*


val Splash: Painter
    @Composable
    get() = painterResource(id = drawable.splash)

val Week: ImageVector
    @Composable
    get() = ImageVector.vectorResource(drawable.weeks)