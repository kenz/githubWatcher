package org.firespeed.githubwatcher

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.firespeed.githubwatcher.ui.theme.GithubWatcherTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.flow.StateFlow
import org.firespeed.githubwatcher.event.MainActivityEvent
import org.firespeed.githubwatcher.ui.SearchUserScreen
import org.firespeed.githubwatcher.ui.UserDetailScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.githubUserList.collect()
                viewModel.openedUrl.collect()
            }
        }

        setContent {
            GithubWatcherTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainScreen(viewModel)

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val backStackEntry = navController.currentBackStackEntryFlow.collectAsState(null)

    TopAppBar(
        title = { Text(backStackEntry.value?.destination?.route ?: stringResource(id = R.string.app_name)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (backStackEntry.value?.destination?.route != GithubWatcherScreen.GithubUserSearch.name) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
    )
}

@Composable
fun MainScreen(
    viewModel: MainActivityViewModel,
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current,
) {
    Scaffold(
        topBar = {
            AppBar(
                navController = navController,
            )
        }
    ) { innerPadding ->
        LaunchedEffect(viewModel.openedUrl) {
            viewModel.openedUrl.collect {
                it?.let {
                    CustomTabsIntent.Builder().build().launchUrl(context, Uri.parse(it))
                }
            }
        }

        NavHost(
            navController = navController,
            startDestination = GithubWatcherScreen.GithubUserSearch.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(route = GithubWatcherScreen.GithubUserSearch.name) {
                SearchUserScreen(viewModel.githubUserList) {
                    if (it is MainActivityEvent.OnGithubUserItemClick) {
                        navController.navigate(GithubWatcherScreen.GithubUserDetail.name)
                    }
                    viewModel.onEvent(it)
                }
            }
            composable(route = GithubWatcherScreen.GithubUserDetail.name) {
                UserDetailScreen(viewModel.selectedGithubUser, viewModel.selectedGithubUserDetail, viewModel.selectedGithubUserRepositories) {
                    viewModel.onEvent(it)
                }
            }

        }
    }
}

