package com.asfakur.kanji.screens.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asfakur.kanji.ui.theme.Black
import com.asfakur.kanji.ui.theme.Kanji1
import com.asfakur.kanji.ui.theme.Kanji4
import com.asfakur.kanji.ui.theme.Week
import com.asfakur.kanji.ui.theme.Week1
import com.asfakur.kanji.ui.theme.Week2

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    gotoKanjiListIndex: (String) -> Unit
) {
    //println("getWeeks call from screen")
    HomeScreenSkeleton(viewModel = viewModel, nextPage = gotoKanjiListIndex)
}

@Composable
fun HomeScreenSkeleton(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel,
    nextPage: (String) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.fetchWeeks()
    }
    val weeks by viewModel.weeks.observeAsState()
    if (weeks == null) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier.align(Alignment.Center), color = Black)
        }
    } else {
        //println(weeks)
        Scaffold(containerColor = MaterialTheme.colorScheme.primaryContainer) { innerPadding ->
            LazyVerticalGrid(
                modifier = modifier.padding(innerPadding),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(weeks!!) { week ->
                    WeekCard(week, weeks!!.indexOf(week), onClick = nextPage)
                }
            }
        }
    }
}

@Composable
fun WeekCard(text: String, position: Int, onClick: (String) -> Unit) {
    val containerColor: Color = if (position % 2 == 0) {
        Week1
    } else {
        Week2
    }

    OutlinedCard(
        modifier = Modifier
            .size(150.dp)
            .padding(6.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = containerColor
        ),
        onClick = { onClick(text) }
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(modifier = Modifier.size(80.dp), imageVector = Week, contentDescription = null)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    //HomeScreenSkeleton()
}
