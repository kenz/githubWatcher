package org.firespeed.githubwatcher.data.test

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.firespeed.githubwatcher.data.NetworkDataSource
import org.firespeed.githubwatcher.data.repository.GithubUserDataRepositoryImpl
import org.firespeed.githubwatcher.network.models.NetworkGithubUserDetail
import org.firespeed.githubwatcher.network.models.NetworkGithubUserItem
import org.firespeed.githubwatcher.network.models.NetworkGithubUserRepository
import org.firespeed.githubwatcher.network.models.NetworkGithubUsers
import org.junit.jupiter.api.Assertions.*

import org.junit.Test

class GithubUserDataRepositoryImplTest {
    private val target = GithubUserDataRepositoryImpl(network = TestNetworkSource())

    @Test
    fun searchGithubUsers() = runTest {
        val flow = target.searchGithubUsers("kenz")
        val flowList = flow.toList()
        val initial = flowList.first()
        val result = flowList.last()
        assertEquals(initial.size, 0)
        assertEquals(result.size, 2)
        assertEquals(result[0].name, "kenz0")
        assertEquals(result[0].id, 0)
        assertEquals(result[1].name, "kenz1")
        assertEquals(result[1].id, 1)

    }

    @Test
    fun queryGithubUserDetail() = runTest {
        val flow = target.queryGithubUserDetail("kenz0")
        val result = flow.first()
        assertEquals(result.login, "kenz0")
        assertEquals(result.name, "kenz 0")
        assertEquals(result.avatarUrl, "https://avatars.githubusercontent.com/u/479696?v=1")
        assertEquals(result.followers, 100)
        assertEquals(result.following, 101)

    }

    @Test
    fun queryGithubUserRepository() {
        // todo: Should implement test!
    }
}

class TestNetworkSource : NetworkDataSource {
    override suspend fun searchGithubUsers(keyword: String): NetworkGithubUsers {
        val users = TEST_USERS.filter { it.login.contains(keyword) }
        return NetworkGithubUsers(totalCount = 2, incompleteResults = false, users)
    }

    override suspend fun queryGithubUserDetail(userName: String): NetworkGithubUserDetail
        = TEST_USERS_DETAIL.first { it.login == userName }

    override suspend fun queryGithubUserRepository(userName: String): List<NetworkGithubUserRepository> {
        TODO("Not yet implemented")
    }
}

val TEST_USER_0 = NetworkGithubUserItem(
    login = "kenz0",
    id = 0,
    nodeId = "nodeKenz0",
    avatarUrl = "https://avatars.githubusercontent.com/u/479696?v=1"
)
val TEST_USER_1 = NetworkGithubUserItem(
    login = "kenz1",
    id = 1,
    nodeId = "nodeKenz1",
    avatarUrl = "https://avatars.githubusercontent.com/u/479696?v=2"
)
val TEST_USER_2 = NetworkGithubUserItem(
    login = "tabiq0",
    id = 2,
    nodeId = "nodeTabiq0",
    avatarUrl = "https://avatars.githubusercontent.com/u/479696?v=3"
)
val TEST_USER_3 = NetworkGithubUserItem(
    login = "tabiq1",
    id = 3,
    nodeId = "nodeTabiq1",
    avatarUrl = "https://avatars.githubusercontent.com/u/479696?v=4"
)

val TEST_USER_0_DETAIL = NetworkGithubUserDetail(
    login = TEST_USER_0.login,
    avatarUrl = TEST_USER_0.avatarUrl,
    name = "kenz 0",
    followers = 100,
    following = 101
)
val TEST_USER_1_DETAIL = NetworkGithubUserDetail(
    login = TEST_USER_1.login,
    avatarUrl = TEST_USER_1.avatarUrl,
    name = "kenz 1",
    followers = 110,
    following = 111
)
val TEST_USER_2_DETAIL = NetworkGithubUserDetail(
    login = TEST_USER_2.login,
    avatarUrl = TEST_USER_2.avatarUrl,
    name = "tabiq 0",
    followers = 120,
    following = 121
)
val TEST_USER_3_DETAIL = NetworkGithubUserDetail(
    login = TEST_USER_3.login,
    avatarUrl = TEST_USER_3.avatarUrl,
    name = "tabiq 1",
    followers = 130,
    following = 131
)
val TEST_USERS = listOf(TEST_USER_0, TEST_USER_1, TEST_USER_2, TEST_USER_3)
val TEST_USERS_DETAIL = listOf(TEST_USER_0_DETAIL, TEST_USER_1_DETAIL, TEST_USER_2_DETAIL, TEST_USER_3_DETAIL)