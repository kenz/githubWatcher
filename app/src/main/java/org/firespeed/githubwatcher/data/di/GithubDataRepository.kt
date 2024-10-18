package org.firespeed.githubwatcher.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.firespeed.githubwatcher.data.repository.GithubUserDataRepository
import org.firespeed.githubwatcher.data.repository.GithubUserDataRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface GithubDataRepository{
    @Singleton
    @Binds
    fun binds(impl: GithubUserDataRepositoryImpl): GithubUserDataRepository
}