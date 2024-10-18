package org.firespeed.githubwatcher

import androidx.annotation.StringRes

enum class GithubWatcherScreen(@StringRes val title: Int) {
    GithubUserSearch(R.string.app_name), GithubUserDetail(R.string.app_name)

}