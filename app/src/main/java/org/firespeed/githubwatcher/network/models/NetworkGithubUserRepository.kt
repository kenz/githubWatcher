package org.firespeed.githubwatcher.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.firespeed.githubwatcher.data.model.GithubUserRepository

/**
 * Github Repository
 * Reference: https://docs.github.com/en/rest/repos/repos?apiVersion=2022-11-28#list-repositories-for-a-user
 */
@Serializable
data class NetworkGithubUserRepository(
    val name: String,
    val fork: Boolean,
    val language: String?,
    @SerialName("stargazers_count") val stargazersCount: Int,
    val description: String?,
    @SerialName("html_url") val url: String?,
) {
    fun toGithubUserRepository() = GithubUserRepository(name = name, language = language, stargazersCount = stargazersCount, description = description ?: "", url = url)

}

