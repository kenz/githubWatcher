package org.firespeed.githubwatcher

import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.firespeed.githubwatcher.data.model.GithubUser
import org.firespeed.githubwatcher.data.model.GithubUserDetail
import org.firespeed.githubwatcher.data.model.GithubUserRepository
import org.firespeed.githubwatcher.data.repository.GithubUserDataRepository
import org.firespeed.githubwatcher.event.MainActivityEvent
import org.firespeed.githubwatcher.event.MainActivityEvent.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val githubUserDataRepository: GithubUserDataRepository,
) : ViewModel(), CoroutineScope {

    private val _githubUserList: MutableStateFlow<List<GithubUser>> = MutableStateFlow(ArrayList())
    val githubUserList = _githubUserList.asStateFlow()

    private val _selectedGithubUser: MutableStateFlow<GithubUser?> = MutableStateFlow(null)
    val selectedGithubUser = _selectedGithubUser.asStateFlow()

    private val _selectedGithubUserDetail: MutableStateFlow<GithubUserDetail?> = MutableStateFlow(null)
    val selectedGithubUserDetail = _selectedGithubUserDetail.asStateFlow()

    private val _selectedGithubUserRepositories: MutableStateFlow<List<GithubUserRepository>> = MutableStateFlow(ArrayList())
    val selectedGithubUserRepositories = _selectedGithubUserRepositories.asStateFlow()

    private val _openedUrl: MutableStateFlow<String?> = MutableStateFlow(null)
    val openedUrl = _openedUrl.asStateFlow()
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun onEvent(event: MainActivityEvent) {
        when (event) {
            is OnSearchUser -> {
                launch(context = Dispatchers.IO) {
                    githubUserDataRepository.searchGithubUsers(event.text).collect {
                        _githubUserList.value = it
                    }
                }
            }

            is OnGithubUserItemClick -> {
                _selectedGithubUser.value = event.githubUser
                val userName = event.githubUser.name
                launch(context = Dispatchers.IO) {
                    githubUserDataRepository.queryGithubUserDetail(userName).collect {
                        _selectedGithubUserDetail.value = it
                    }
                    githubUserDataRepository.queryGithubUserRepository(userName).collect {
                        _selectedGithubUserRepositories.value = it
                    }
                }
            }

            is OnGithubUserRepositoryItemClick -> {
                _openedUrl.value = event.githubUserRepository.url
            }

            CloseBrowser -> {}
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
