package org.firespeed.githubwatcher.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.firespeed.githubwatcher.R
import org.firespeed.githubwatcher.data.model.GithubUser
import org.firespeed.githubwatcher.data.model.GithubUserDetail
import org.firespeed.githubwatcher.data.model.GithubUserRepository
import org.firespeed.githubwatcher.event.MainActivityEvent
import org.firespeed.githubwatcher.ui.theme.SPACE_WIDTH


@Composable
fun UserDetailScreen(userState: StateFlow<GithubUser?>, userDetailState: StateFlow<GithubUserDetail?>, userRepositoriesState: StateFlow<List<GithubUserRepository>>, onEvent: (MainActivityEvent) -> Unit) {
    val user = userState.collectAsState()
    val userDetail = userDetailState.collectAsState()
    val userRepositories = userRepositoriesState.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(user.value?.avatarUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(96.dp).padding(8.dp)
            )
            Column {
                Text(
                    text = user.value?.name ?: "",
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier
                )
                Text(
                    text = userDetail.value?.name ?: "",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                )
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                stringResource(R.string.followers),
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = userDetail.value?.followers?.toString() ?: "",
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(modifier = Modifier.width(SPACE_WIDTH))
            Text(
                stringResource(R.string.following),
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = userDetail.value?.following?.toString() ?: "",
                style = MaterialTheme.typography.bodyLarge,
            )

        }

        LazyColumn(
            Modifier
                .padding(4.dp)
                .weight(1f)
        ) {
            items(userRepositories.value) {
                RepositoryRow(githubUserRepository = it, onItemClick = { _ ->
                    onEvent(MainActivityEvent.OnGithubUserRepositoryItemClick(it))
                })
            }
        }
    }

}

@Preview("UserDetailScreen")
@Composable
fun PreviewUserDetailScreen() {
    val githubUser = MutableStateFlow(GithubUser(1, "hoge.jpg", "kenz"))
    val githubUserDetail = MutableStateFlow(GithubUserDetail("kenz", "hoge.jpg", "kenz tabiq", 2, 3))
    val githubUserRepositories = MutableStateFlow(listOf(GithubUserRepository("earthCase", "kotlin", 1, "watch face for wear OS 1 and 2", "https://github.com/kenz/sampleForAndroidWear")))
    UserDetailScreen(githubUser, githubUserDetail, githubUserRepositories) {}
}