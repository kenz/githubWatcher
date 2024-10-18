package org.firespeed.githubwatcher.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.firespeed.githubwatcher.data.model.GithubUser

/**
 * Github User
 * Reference: https://docs.github.com/en/rest/search/search?apiVersion=2022-11-28#search-users
 */
@Serializable
data class NetworkGithubUsers(
    @SerialName("total_count") val totalCount: Int,
    @SerialName("incomplete_results") val incompleteResults: Boolean,
    val items: List<NetworkGithubUserItem>,
)

@Serializable
data class NetworkGithubUserItem(
    val login: String,
    val id: Int,
    @SerialName("node_id") val nodeId: String,
    @SerialName("avatar_url") val avatarUrl: String,
) {
    fun toGithubUser(): GithubUser = GithubUser(id = id, avatarUrl = avatarUrl, name = login)
}
