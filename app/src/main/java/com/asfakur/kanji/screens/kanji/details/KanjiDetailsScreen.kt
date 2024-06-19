package com.asfakur.kanji.screens.kanji.details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PauseCircleFilled
import androidx.compose.material.icons.outlined.PlayCircleFilled
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.CircularProgressIndicator
import com.asfakur.kanji.KanjiScreen
import com.asfakur.kanji.models.Kanji
import com.asfakur.kanji.screens.kanji.KanjiViewModel
import com.asfakur.kanji.screens.kanji.media.KanjiMediaContent
import com.asfakur.kanji.screens.kanji.pdf.KanjiPdfContent
import com.asfakur.kanji.screens.kanji.tutorial.KanjiTutorialContent
import com.asfakur.kanji.ui.theme.Black
import com.asfakur.kanji.ui.theme.Kanji4
import com.asfakur.kanji.ui.theme.Week1
import com.asfakur.kanji.ui.theme.White

@Composable
fun KanjiDetails(
    viewModel: KanjiViewModel,
    week: String,
    id: String,
    navController: NavHostController,
    goBack: () -> Unit
) {
    BackHandler {
        goBack()
    }
    KanjiDetailsSkeleton(viewModel, week, id, navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KanjiDetailsSkeleton(
    viewModel: KanjiViewModel,
    week: String,
    id: String,
    navController: NavHostController

) {
    LaunchedEffect(Unit) {
        viewModel.fetchKanjiDetails(week, id)
    }
    val kanji by viewModel.kanjiDetails.observeAsState()
    if (kanji == null) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                indicatorColor = Black
            )
        }
    } else {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        val text = when (navController.currentDestination?.route) {
                            KanjiScreen.KanjiDetails.route -> {
                                "Kanji Details"
                            }

                            KanjiScreen.KanjiMedia.route -> {
                                "Kanji Stroke Order"
                            }

                            KanjiScreen.KanjiPdf.route -> {
                                "PDF Examples & Exercise"
                            }

                            KanjiScreen.KanjiTutorial.route -> {
                                "Youtube Video Tutorial"
                            }

                            else -> "Nothing"
                        }
                        Text(modifier = Modifier.fillMaxWidth().padding(16.dp),text = text, textAlign = TextAlign.Center, style = MaterialTheme.typography.headlineLarge)
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Kanji4,
                        titleContentColor = Black
                    )
                )
            },
            bottomBar = {
                BottomBar(navController, week = week, id = id)
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                //println(navController.currentDestination)
                when (navController.currentDestination?.route) {
                    KanjiScreen.KanjiDetails.route -> {
                        DetailsPageContent(kanji = kanji!!)
                    }

                    KanjiScreen.KanjiMedia.route -> {
                        KanjiMediaContent(kanji = kanji!!)
                    }

                    KanjiScreen.KanjiPdf.route -> {
                        KanjiPdfContent(kanji = kanji!!)
                    }

                    KanjiScreen.KanjiTutorial.route -> {
                        KanjiTutorialContent(kanji = kanji!!)
                    }
                }
            }
        }
    }
}

@Composable
fun DetailsPageContent(
    kanji: Kanji,
    audioPlayer: AudioPlayerViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val isPlaying by audioPlayer.isPlaying.collectAsState()
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        OutlinedCard(
            modifier = Modifier
                .size(200.dp)
                .align(
                    Alignment.CenterHorizontally
                )
        ) {
            Text(
                text = kanji.kanji,
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center,
                fontSize = 180.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = kanji.word,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 64.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = kanji.meaning,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 64.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        IconButton(
            onClick = {
                if (isPlaying) {
                    audioPlayer.pausePlayback()
                } else {
                    val audio = kanji.audio
                    audioPlayer.startPlayback(audio)
                }
            },
            modifier = Modifier
                .size(48.dp)
                .align(
                    Alignment.CenterHorizontally
                ),
        ) {
            if (isPlaying) {
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
                    contentDescription = "Pause Video",
                    tint = Color.Green
                )
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController, week: String, id: String) {
    val screens = listOf(
        KanjiScreen.KanjiDetails,
        KanjiScreen.KanjiMedia,
        KanjiScreen.KanjiPdf,
        KanjiScreen.KanjiTutorial
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route?.substringBeforeLast("/")
    println(currentDestination)
    val bottomBarDestination = screens.any { screen ->
        //println(screen.route + "<-->" + currentDestination?.route)
        screen.route == currentDestination?.route
    }
    //println(currentDestination?.route)
    if (bottomBarDestination) {
        BottomNavigation(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Week1,
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController,
                    week = week,
                    id = id
                )
            }
        }
    }

}


@Composable
fun RowScope.AddItem(
    screen: KanjiScreen,
    currentDestination: NavDestination?,
    navController: NavHostController,
    week: String,
    id: String
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.route?.split("/")?.take(2)
            ?.joinToString("/") == screen.route.split("/").take(2).joinToString("/"),
        selectedContentColor = White,
        unselectedContentColor = Black,
        onClick = {
            val route = when (screen.route) {
                KanjiScreen.KanjiDetails.route -> {
                    KanjiScreen.KanjiDetails.route
                        .replaceFirst("{${KanjiScreen.KanjiDetails.WEEK}}", newValue = week)
                        .replaceFirst("{${KanjiScreen.KanjiDetails.ID}}", newValue = id)
                }

                KanjiScreen.KanjiMedia.route -> {
                    KanjiScreen.KanjiMedia.route
                        .replaceFirst("{${KanjiScreen.KanjiMedia.WEEK}}", newValue = week)
                        .replaceFirst("{${KanjiScreen.KanjiMedia.ID}}", newValue = id)
                }

                KanjiScreen.KanjiPdf.route -> {
                    KanjiScreen.KanjiPdf.route
                        .replaceFirst("{${KanjiScreen.KanjiPdf.WEEK}}", newValue = week)
                        .replaceFirst("{${KanjiScreen.KanjiPdf.ID}}", newValue = id)
                }

                KanjiScreen.KanjiTutorial.route -> {
                    KanjiScreen.KanjiTutorial.route
                        .replaceFirst("{${KanjiScreen.KanjiTutorial.WEEK}}", newValue = week)
                        .replaceFirst("{${KanjiScreen.KanjiTutorial.ID}}", newValue = id)
                }

                else -> screen.route
            }
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}

@Preview
@Composable
private fun KanjiDetailsPreview() {
    MaterialTheme {
        //KanjiDetailsSkeleton()
    }
}