package org.firespeed.githubwatcher.data.model

/**
 * Github User
 * Reference: https://docs.github.com/en/rest/search/search?apiVersion=2022-11-28#search-users
 */
data class GithubUser(
    val id: Int,
    val avatarUrl: String,
    val name: String,
)