package org.firespeed.githubwatcher.data

import org.firespeed.githubwatcher.network.models.NetworkGithubUsers
import org.firespeed.githubwatcher.network.models.NetworkGithubUserDetail
import org.firespeed.githubwatcher.network.models.NetworkGithubUserRepository

interface NetworkDataSource {
    suspend fun searchGithubUsers(keyword: String): NetworkGithubUsers
    suspend fun queryGithubUserDetail(userName: String): NetworkGithubUserDetail
    suspend fun queryGithubUserRepository(userName: String): List<NetworkGithubUserRepository>
}