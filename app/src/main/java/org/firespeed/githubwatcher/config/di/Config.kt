package org.firespeed.githubwatcher.config.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.firespeed.githubwatcher.config.Config
import org.firespeed.githubwatcher.config.YourConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface GithubDataRepository{
    @Singleton
    @Binds
    fun binds(impl: YourConfig): Config
}