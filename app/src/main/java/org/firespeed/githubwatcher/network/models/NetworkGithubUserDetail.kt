package org.firespeed.githubwatcher.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.firespeed.githubwatcher.data.model.GithubUser
import org.firespeed.githubwatcher.data.model.GithubUserDetail
import kotlin.math.log

/**
 * Github user detail
 * Reference: https://docs.github.com/en/rest/users/users?apiVersion=2022-11-28#get-a-user
 */
@Serializable
data class NetworkGithubUserDetail(
    val login: String,
    @SerialName("avatar_url") val avatarUrl: String,
    val name: String?,
    val followers: Int,
    val following: Int,
) {
    fun toGithubUserDetail(): GithubUserDetail = GithubUserDetail(login = login, avatarUrl = avatarUrl, name = name ?: login, followers = followers, following = following)
}

