package com.example.jumpingmind_assignment.feature.common.repoResult

sealed class RepoResult<out T> {
    data class Success<out T>(val data: T, val source: Source) : RepoResult<T>()
    data class Error(val code: Int, val data: String, val source: Source) : RepoResult<Nothing>()
    data class Exception(val throwable: Throwable, val source: Source) : RepoResult<Nothing>()
    data class Loading(val loadingStatus: Boolean, val source: Source) : RepoResult<Nothing>()
}

enum class Source {
    REMOTE, CACHE
}