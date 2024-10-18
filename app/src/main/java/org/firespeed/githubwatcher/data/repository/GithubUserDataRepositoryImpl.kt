package org.firespeed.githubwatcher.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import org.firespeed.githubwatcher.data.model.GithubUser
import org.firespeed.githubwatcher.data.NetworkDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.firespeed.githubwatcher.data.model.GithubUserDetail
import org.firespeed.githubwatcher.data.model.GithubUserRepository
import java.util.ArrayList

class GithubUserDataRepositoryImpl @Inject constructor(
    private val network: NetworkDataSource,
) : GithubUserDataRepository {
    override suspend fun searchGithubUsers(keyword: String): Flow<List<GithubUser>> {
        return flow {
            emit(ArrayList())
            val result = network.searchGithubUsers(keyword)
            emit(result.items.map { it.toGithubUser() })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun queryGithubUserDetail(userName: String): Flow<GithubUserDetail> {
        return flow {
            val result = network.queryGithubUserDetail(userName)
            emit(result.toGithubUserDetail())
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun queryGithubUserRepository(userName: String): Flow<List<GithubUserRepository>> {
        return flow {
            val result = network.queryGithubUserRepository(userName)
            emit(result.map { it.toGithubUserRepository() })
        }.flowOn(Dispatchers.IO)
    }

}