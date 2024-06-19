package com.asfakur.kanji.screens.kanji.media

import android.net.Uri
import android.view.MotionEvent
import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PauseCircleFilled
import androidx.compose.material.icons.outlined.PlayCircleFilled
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.asfakur.kanji.models.Kanji


@Composable
fun KanjiMediaContent(kanji: Kanji, videoViewModel: VideoViewModel = viewModel()) {
    val context = LocalContext.current
    val videoView = remember { VideoView(context) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = "Stroke Order",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        AndroidView(
            factory = { videoView },
            update = { videoView ->
                videoView.setVideoURI(Uri.parse(kanji.video))
                videoView.setMediaController(null)
                videoView.setOnCompletionListener {
                    videoViewModel.onCompletion()
                }
                if (videoViewModel.isPlaying.value) {
                    videoView.start()
                } else {
                    videoView.pause()
                }
            },
            modifier = Modifier
                .size(360.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = {
                    if (videoViewModel.isPlaying.value) {
                        videoViewModel.pause()
                        videoView.pause()
                    } else {
                        videoViewModel.play()
                        videoView.start()
                    }
                },
                modifier = Modifier.size(64.dp)
            ) {
                if (videoViewModel.isPlaying.value) {
                    Icon(
                        modifier = Modifier.size(48.dp),
                        imageVector = Icons.Outlined.PauseCircleFilled,
                        contentDescription = "Pause Video",
                        tint = Color.Red
                    )
                } else {
                    Icon(
                        modifier = Modifier.size(48.dp),
                        imageVector = Icons.Outlined.PlayCircleFilled,
                        contentDescription = "Play Video",
                        tint = Color.Green
                    )
                }
            }
        }
    }
    LaunchedEffect(videoViewModel.isPlaying.value) {
        if (videoViewModel.isPlaying.value) {
            videoView.start()
        } else {
            videoView.pause()
        }
    }
}