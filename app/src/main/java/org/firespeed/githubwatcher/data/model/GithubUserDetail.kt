package org.firespeed.githubwatcher.data.model

/**
 * Github user detail
 * Reference: https://docs.github.com/en/rest/users/users?apiVersion=2022-11-28#get-a-user
 */
data class GithubUserDetail(
    val login:String,
    val avatarUrl:String,
    val name:String,
    val followers:Int,
    val following:Int
)

