package org.firespeed.githubwatcher.network.retrofit

import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.firespeed.githubwatcher.config.API_TOKEN
import org.firespeed.githubwatcher.data.NetworkDataSource
import org.firespeed.githubwatcher.network.models.NetworkGithubUsers
import org.firespeed.githubwatcher.network.models.NetworkGithubUserDetail
import org.firespeed.githubwatcher.network.models.NetworkGithubUserRepository
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.Path

/**
 * User github v3
 * Ref https://docs.github.com/en/rest/
 */
private interface NetworkDataSourceApi {
    @GET(value = "search/users?q=Q")
    suspend fun searchUser(
        @Query("q") keyword: String,
    ): NetworkGithubUsers

    @GET(value = "users/{user_name}")
    suspend fun queryUser(
        @Path("user_name") userName: String,
    ): NetworkGithubUserDetail

    @GET(value = "users/{user_name}/repos")
    suspend fun queryRepository(
        @Path("user_name") userName: String,
    ): List<NetworkGithubUserRepository>
}


@Singleton
internal class RetrofitGithubNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : NetworkDataSource {

    private val networkApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .client(OkHttpClient.Builder()
                .addInterceptor {
                    it.proceed(
                        it.request().newBuilder().header("Authorization", API_TOKEN).build()
                    )
                }
                .build())
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(NetworkDataSourceApi::class.java)


    override suspend fun searchGithubUsers(keyword: String): NetworkGithubUsers = networkApi.searchUser(keyword)
    override suspend fun queryGithubUserDetail(userName: String): NetworkGithubUserDetail = networkApi.queryUser(userName)
    override suspend fun queryGithubUserRepository(userName: String): List<NetworkGithubUserRepository> = networkApi.queryRepository(userName).filter { !it.fork }
}

const val BASE_URL = "https://api.github.com/"