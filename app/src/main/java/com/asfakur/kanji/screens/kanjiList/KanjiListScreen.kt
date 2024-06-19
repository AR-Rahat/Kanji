package com.asfakur.kanji.screens.kanjiList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.CircularProgressIndicator
import com.asfakur.kanji.models.Kanji
import com.asfakur.kanji.ui.theme.Black
import com.asfakur.kanji.ui.theme.Kanji1
import com.asfakur.kanji.ui.theme.Kanji2
import com.asfakur.kanji.ui.theme.Kanji3
import com.asfakur.kanji.ui.theme.Kanji4

@Composable
fun KanjiList(
    viewModel: KanjiListViewModel,
    Week: String,
    gotoKanjiDetailsIndex: (String, String) -> Unit,
    goBack: () -> Unit
) {
    KanjiListScreenSkeleton(viewModel = viewModel, week = Week, nextPage = gotoKanjiDetailsIndex)
}

@Composable
fun KanjiListScreenSkeleton(
    modifier: Modifier = Modifier,
    viewModel: KanjiListViewModel,
    week: String,
    nextPage: (String, String) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.fetchKanjiList(week)
    }
    val kanjiList by viewModel.kanjiList.observeAsState()
    if (kanjiList == null) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                indicatorColor = Black
            )
        }
    } else {
        Scaffold(containerColor = MaterialTheme.colorScheme.primaryContainer, topBar = {
            Box(
                modifier = Modifier
                    .height(60.dp)
                    .background(Kanji4)
            ) {
                Text(
                    text = week,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }) { innerPadding ->
            LazyVerticalGrid(
                modifier = modifier.padding(innerPadding),
                columns = GridCells.Adaptive(150.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(kanjiList!!) { kanji ->
                    KanjiCard(
                        kanji.kanji,
                        week,
                        kanji,
                        kanjiList!!.indexOf(kanji),
                        onClick = nextPage
                    )
                }
            }
        }
    }
}

@Composable
fun KanjiCard(
    text: String,
    week: String,
    kanji: Kanji,
    position: Int,
    onClick: (String, String) -> Unit
) {
    val colorList = listOf(Kanji1, Kanji2, Kanji3, Kanji4)
    val containerColor: Color = colorList[position % 4]

    OutlinedCard(
        modifier = Modifier
            .size(150.dp)
            .padding(6.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = containerColor
        ),
        onClick = { onClick(week, kanji.id) }
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondary,
            )
        }
    }
}

@Preview
@Composable
private fun KanjiListScreenPreview() {
    //KanjiListScreenSkeleton(nextPage = { })
}
