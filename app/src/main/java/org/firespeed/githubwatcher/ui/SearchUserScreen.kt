package org.firespeed.githubwatcher.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.firespeed.githubwatcher.R
import org.firespeed.githubwatcher.data.model.GithubUser
import org.firespeed.githubwatcher.event.MainActivityEvent


@Composable
fun SearchUserScreen(itemState: StateFlow<List<GithubUser>>, onEvent: (MainActivityEvent) -> Unit) {
    var searchText by rememberSaveable { mutableStateOf("") }
    val items = itemState.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                singleLine = true,
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .weight(1f)
            )
            Button(
                onClick = { onEvent(MainActivityEvent.OnSearchUser(searchText)) },
                Modifier
                    .padding(4.dp)
                    .align(alignment = Alignment.CenterVertically)
            ) {
                Text(stringResource(R.string.search))
            }
        }
        LazyColumn(
            Modifier
                .padding(4.dp)
                .weight(1f)
        ) {
            items(items.value) {
                UserRow(githubUser = it, onItemClick = { _ ->
                    onEvent(MainActivityEvent.OnGithubUserItemClick(it))
                })
            }
        }

    }

}

@Preview("RepositoryRow")
@Composable
fun PreviewSearchUserScreen() {
    val items = MutableStateFlow(listOf(GithubUser(1, "http://", "kenz")))
    SearchUserScreen(items) {}
}