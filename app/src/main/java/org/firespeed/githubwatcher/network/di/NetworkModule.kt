package org.firespeed.githubwatcher.network.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton
import org.firespeed.githubwatcher.BuildConfig
import org.firespeed.githubwatcher.data.NetworkDataSource
import org.firespeed.githubwatcher.network.retrofit.RetrofitGithubNetwork

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply {
                        if (BuildConfig.DEBUG) setLevel(HttpLoggingInterceptor.Level.BODY)
                    },
            ).build()

}

@Module
@InstallIn(SingletonComponent::class)
internal interface NetworkModuleInterface {
    @Singleton
    @Binds
    fun binds(impl: RetrofitGithubNetwork): NetworkDataSource
}