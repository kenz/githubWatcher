package org.firespeed.githubwatcher.event

import org.firespeed.githubwatcher.data.model.GithubUser
import org.firespeed.githubwatcher.data.model.GithubUserRepository

sealed class MainActivityEvent {
    data class OnSearchUser(val text: String) : MainActivityEvent()
    data class OnGithubUserItemClick(val githubUser: GithubUser) : MainActivityEvent()
    data class OnGithubUserRepositoryItemClick(val githubUserRepository: GithubUserRepository) : MainActivityEvent()
    data object CloseBrowser:MainActivityEvent()
}