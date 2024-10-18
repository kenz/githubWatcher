package org.firespeed.githubwatcher.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.firespeed.githubwatcher.R
import org.firespeed.githubwatcher.data.model.GithubUserRepository
import org.firespeed.githubwatcher.ui.theme.SPACE_WIDTH

@Composable
fun RepositoryRow(githubUserRepository: GithubUserRepository, onItemClick: (GithubUserRepository) -> Unit) {
    ListItem(
        headlineContent = {
            Text(
                text = githubUserRepository.name,
                style = MaterialTheme.typography.titleLarge
            )
        },
        overlineContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    stringResource(R.string.stars),
                    style = MaterialTheme.typography.labelMedium,
                )
                Text(
                    text = githubUserRepository.stargazersCount.toString(),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        },
        supportingContent = {
            Text(
                text = githubUserRepository.description,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        trailingContent = {
            Text(
                text = githubUserRepository.language ?: "",
                style = MaterialTheme.typography.labelMedium
            )
        },
        modifier = Modifier
            .clickable { onItemClick(githubUserRepository) }
    )
}

@Preview("RepositoryRow")
@Composable
fun PreviewRepositoryRowScreen() {
    val githubUserRepository = GithubUserRepository("earthCase", "kotlin", 1, "watch face for wear OS 1 and 2", "https://github.com/kenz/sampleForAndroidWear")
    RepositoryRow(githubUserRepository) {}
}