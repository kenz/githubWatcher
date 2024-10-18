package org.firespeed.githubwatcher.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import org.firespeed.githubwatcher.R
import org.firespeed.githubwatcher.data.model.GithubUser

@Composable
fun UserRow(githubUser: GithubUser, onItemClick: (GithubUser) -> Unit) {
    ListItem(
        headlineContent = {
            Text(
                text = githubUser.name,
                style = MaterialTheme.typography.titleLarge,
            )
        },
        leadingContent = {
            val painter = rememberAsyncImagePainter(model = githubUser.avatarUrl, error = painterResource(R.drawable.ic_launcher_foreground))
            Image(
                painter = painter,
                contentDescription = githubUser.name,
                modifier = Modifier.size(48.dp)

            )
        },
        modifier = Modifier
            .clickable { onItemClick(githubUser) }
    )
}

@Preview("UserRow")
@Composable
fun PreviewUserRow() {
    val githubUser = GithubUser(1, "hoge.jpg", "kenz")
    UserRow(githubUser) {}
}