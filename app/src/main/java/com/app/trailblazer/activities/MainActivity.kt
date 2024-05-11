package com.app.trailblazer.activities

import android.Manifest
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import com.app.trailblazer.navigation.AppBar
import com.app.trailblazer.navigation.DrawerBody
import com.app.trailblazer.navigation.DrawerHeader
import com.app.trailblazer.navigation.DrawerThemeChanger
import com.app.trailblazer.navigation.MenuItem
import android.annotation.SuppressLint
import android.content.Intent
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.app.trailblazer.content.MainPage
import com.app.trailblazer.content.TimerPage
import com.app.trailblazer.content.TrailPage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState


class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()
    private val themeViewModel: ThemeViewModel by viewModels()
    private val timerViewModel: TimerViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "DiscouragedApi")
    @OptIn(ExperimentalFoundationApi::class, ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Display splash screen (works for api >= 31)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isReady.value
            }
        }

        setContent {
            // Is navigation drawer opened
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            // Drawer coroutine scope
            val scope = rememberCoroutineScope()
            // Change theme
            val darkTheme by themeViewModel.darkTheme.observeAsState(initial = false)
            // Set search field focus
            val focusManager = LocalFocusManager.current
            // Trail list after filtration
            val filteredTrails by viewModel.filteredTrails.observeAsState()
            // All trails in the list
            val trails by viewModel.filteredTrails.observeAsState(emptyList())
            // Navigation (which page is currently displayed)
            val viewState by viewModel.viewState.observeAsState(MainActivityViewModel.ViewState.HOME)
            // Which trail is currently displayed
            val trailIndex by viewModel.trailIndex.collectAsState()
            // Horizontal pager state
            val pagerState = rememberPagerState(pageCount = { trails.size })
            // Trail name displayed inside the navigation bar
            val currentTrailName by viewModel.currentTrailName.collectAsState()

            // Change appbar text depending of the mode
            val appBarText = when (viewState) {
                MainActivityViewModel.ViewState.HOME -> "Trailblazer"
                MainActivityViewModel.ViewState.TIMER -> "Timer"
                else -> currentTrailName
            }

            // Set font to lower size for longer names
            val style = when {
                appBarText.length in 23..32 -> MaterialTheme.typography.titleMedium
                appBarText.length > 32 -> MaterialTheme.typography.titleSmall
                else -> MaterialTheme.typography.titleLarge
            }

            // Update current page of horizontal pager whenever a new trail is chosen by the user
            LaunchedEffect(trailIndex) {
                pagerState.animateScrollToPage(trailIndex, animationSpec = tween(durationMillis = 0))
            }

            // Update name while scrolling
            LaunchedEffect(pagerState) {
                snapshotFlow { pagerState.currentPage }.collect { currentPage ->
                    val trail = trails.getOrNull(currentPage)
                    if (trail != null) {
                        viewModel.setTrailName(trail.trailName)
                    }
                }
            }

            TrailblazerAppTheme(darkTheme = darkTheme) {
                // Check camera permissions
                val cameraPermission = Manifest.permission.CAMERA
                val permissionState = rememberPermissionState(cameraPermission)
                val launcher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { isGranted ->
                    if (isGranted) {
                        Toast.makeText(this, "Camera permission was granted", Toast.LENGTH_SHORT).show()
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        this.startActivity(intent)
                    } else {
                        Toast.makeText(this, "Camera permission is required", Toast.LENGTH_SHORT).show()
                    }
                }
                ModalNavigationDrawer(
                    // Define list of navigation drawer elements
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
                                // Define action of clicked elements
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
                        // Set top bar and change its content (based on view state)
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
                        // Add camera FAB, launch camera if permissions are granted
                        floatingActionButton = {
                            if(viewModel.viewState.value != MainActivityViewModel.ViewState.TIMER) {
                                FloatingActionButton(
                                    onClick = {
                                        if (permissionState.status is PermissionStatus.Granted) {
                                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                            this.startActivity(intent)
                                        } else {
                                            launcher.launch(cameraPermission)
                                        }
                                    },
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.background
                                ) {
                                    Icon(Icons.Default.Camera, contentDescription = "Open Camera")
                                }
                            }
                        }
                        // Display correct page (also based on view state)
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
