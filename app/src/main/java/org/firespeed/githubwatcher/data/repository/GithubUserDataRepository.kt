package org.firespeed.githubwatcher.data.repository

import kotlinx.coroutines.flow.Flow
import org.firespeed.githubwatcher.data.model.GithubUser
import org.firespeed.githubwatcher.data.model.GithubUserDetail
import org.firespeed.githubwatcher.data.model.GithubUserRepository

interface GithubUserDataRepository {
    suspend fun searchGithubUsers(keyword: String): Flow<List<GithubUser>>
    suspend fun queryGithubUserDetail(userName: String): Flow<GithubUserDetail>
    suspend fun queryGithubUserRepository(userName: String): Flow<List<GithubUserRepository>>
}