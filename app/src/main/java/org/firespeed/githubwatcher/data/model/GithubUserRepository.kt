package org.firespeed.githubwatcher.data.model

/**
 * Github User Repository (expect forked repository)
 * Reference: https://docs.github.com/en/rest/repos/repos?apiVersion=2022-11-28#list-repositories-for-a-user
 */
data class GithubUserRepository(
    val name: String,
    val language: String?,
    val stargazersCount: Int,
    val description: String,
    val url:String?,
)

