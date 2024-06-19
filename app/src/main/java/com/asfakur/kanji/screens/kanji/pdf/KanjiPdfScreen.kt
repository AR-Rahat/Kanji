package com.asfakur.kanji.screens.kanji.pdf


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.CircularProgressIndicator
import com.asfakur.kanji.models.Kanji
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.util.FitPolicy


@Composable
fun KanjiPdfContent(kanji: Kanji, pdfViewModel: PdfViewModel = viewModel()) {
    val pdfStream by pdfViewModel.pdfStream.observeAsState()

    LaunchedEffect(Unit) {
        pdfViewModel.loadPdf(kanji.pdf)
    }
    if (pdfStream == null) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.Center)
            )
        }
    } else {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            AndroidView(factory = { context ->
                PDFView(context, null).apply {
                    layoutParams = android.widget.LinearLayout.LayoutParams(
                        android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                        android.widget.LinearLayout.LayoutParams.MATCH_PARENT
                    )
                }
            },
                update = { pdfView ->
                    pdfView.fromStream(pdfStream)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .defaultPage(0)
                        .enableAnnotationRendering(false)
                        .password(null)
                        .scrollHandle(null)
                        .enableAntialiasing(true)
                        .spacing(0)
                        .autoSpacing(false)
                        .pageFitPolicy(FitPolicy.WIDTH)
                        .fitEachPage(false)
                        .pageSnap(false)
                        .pageFling(false)
                        .nightMode(false)
                        .load()
                }
            )
        }
    }
}