package com.app.trailblazer.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.SignalCellularAlt1Bar
import androidx.compose.material.icons.filled.SignalCellularAlt2Bar
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import com.app.trailblazer.ui.theme.TrailblazerAppTheme
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import com.app.trailblazer.navigation.AppBar
import com.app.trailblazer.navigation.DrawerBody
import com.app.trailblazer.navigation.DrawerHeader
import com.app.trailblazer.navigation.DrawerThemeChanger
import com.app.trailblazer.navigation.MenuItem
import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.setValue
import com.app.trailblazer.content.MainPage
import com.app.trailblazer.content.TimerPage
import com.app.trailblazer.content.TrailPage


class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()
    private val themeViewModel: ThemeViewModel by viewModels()
    private val timerViewModel: TimerViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "DiscouragedApi")
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val darkTheme by themeViewModel.darkTheme.observeAsState(initial = false)
            val focusManager = LocalFocusManager.current
            val filteredTrails by viewModel.filteredTrails.observeAsState()
            val trails by viewModel.filteredTrails.observeAsState(emptyList())
            val viewState by viewModel.viewState.observeAsState(MainActivityViewModel.ViewState.HOME)
            val trailIndex by viewModel.trailIndex.observeAsState(0)
            val pagerState = rememberPagerState(pageCount = { trails.size })
            var trailName by remember { mutableStateOf("") }
            val appBarText = when (viewState) {
                MainActivityViewModel.ViewState.HOME -> "Trailblazer"
                MainActivityViewModel.ViewState.TIMER -> "Timer"
                else -> trailName
            }
            val style = when {
                appBarText.length in 23..32 -> MaterialTheme.typography.titleMedium
                appBarText.length > 32 -> MaterialTheme.typography.titleSmall
                else -> MaterialTheme.typography.titleLarge
            }

            LaunchedEffect(trailIndex) {
                pagerState.scrollToPage(trailIndex)
                trailName = trails.getOrNull(pagerState.currentPage)?.trailName ?: ""
            }

            LaunchedEffect(pagerState.currentPage) {
                trailName = trails.getOrNull(pagerState.currentPage)?.trailName ?: ""
            }

            TrailblazerAppTheme(darkTheme = darkTheme) {
                ModalNavigationDrawer(
                    modifier = Modifier.fillMaxSize(),
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet {
                            DrawerHeader()
                            DrawerBody(
                                items = listOf(
                                    MenuItem(
                                        id = "home",
                                        title = "Home",
                                        contentDescription = "Go to home screen",
                                        icon = Icons.Default.Home
                                    ),
                                    MenuItem(
                                        id = "easy",
                                        title = "Easy trails",
                                        contentDescription = "Go to easy trails screen",
                                        icon = Icons.Default.SignalCellularAlt1Bar
                                    ),
                                    MenuItem(
                                        id = "medium",
                                        title = "Medium trails",
                                        contentDescription = "Go to medium trails screen",
                                        icon = Icons.Default.SignalCellularAlt2Bar
                                    ),
                                    MenuItem(
                                        id = "hard",
                                        title = "Hard trails",
                                        contentDescription = "Go to hard trails screen",
                                        icon = Icons.Default.SignalCellularAlt
                                    ),
                                    MenuItem(
                                        id = "timer",
                                        title = "Timer",
                                        contentDescription = "Go to hard timer screen",
                                        icon = Icons.Default.Schedule
                                    ),
                                ),
                                onItemClick = { item ->
                                    scope.launch {
                                        drawerState.close()
                                    }
                                    when (item.id) {
                                        "home" -> {
                                            viewModel.difficulty = ""
                                        }
                                        "easy", "medium", "hard" -> {
                                            viewModel.difficulty = item.id
                                        }
                                        else -> viewModel.viewState.value = MainActivityViewModel.ViewState.TIMER
                                    }
                                }
                            )
                            DrawerThemeChanger(themeViewModel)
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.background)
                            )
                        }
                    }
                ) {
                    Scaffold(
                        topBar = {
                            AppBar(
                                appBarText = appBarText,
                                textStyle = style,
                                navigationIcon = if (viewState == MainActivityViewModel.ViewState.HOME) Icons.Default.Menu else Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = if (viewState == MainActivityViewModel.ViewState.HOME) "Show navigation drawer" else "Go back",
                                onClick = {
                                    if (viewState == MainActivityViewModel.ViewState.HOME) {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    } else {
                                        viewModel.viewState.value = MainActivityViewModel.ViewState.HOME
                                    }
                                }
                            )
                        },
                        modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { focusManager.clearFocus() },
                    ) { contentPadding ->
                        when (viewModel.viewState.value) {
                            MainActivityViewModel.ViewState.HOME -> {
                                MainPage(
                                    viewModel = viewModel,
                                    context = this,
                                    contentPadding = contentPadding,
                                    filteredTrails = filteredTrails
                                )
                            }
                            MainActivityViewModel.ViewState.TRAIL -> {
                                TrailPage(
                                    pagerState = pagerState,
                                    trails = trails,
                                    context = this
                                )
                            }
                            else -> {
                                TimerPage(
                                    viewModel = timerViewModel,
                                    context = this
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
