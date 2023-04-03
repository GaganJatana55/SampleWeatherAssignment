package com.example.jumpingmind_assignment.feature.base

import com.example.jumpingmind_assignment.feature.common.repoResult.RepoResult
import com.example.jumpingmind_assignment.feature.common.repoResult.Source
import com.example.jumpingmind_assignment.utils.InternetConnectivity
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import org.json.JSONObject
import retrofit2.Response

abstract class BaseRepository constructor(private var internetConnectivity: InternetConnectivity) {


    fun <T, P> safeDBCall(
        dbCall: suspend () -> T,
        entityProcessor: (T) -> P,
    ): Flow<RepoResult<P>> = flow {
        emit(RepoResult.Loading(true, Source.CACHE))
        val flow = flowOf(safeDBCallInternal(dbCall, entityProcessor))
        emit(RepoResult.Loading(false, Source.CACHE))
        emitAll(flow)
    }.flowOn(Dispatchers.IO)


    private suspend fun <T, P> safeDBCallInternal(
        dbCall: suspend () -> T,
        entityProcessor: (T) -> P,
    ): RepoResult<P> = try {
        RepoResult.Success(entityProcessor.invoke(dbCall.invoke()), Source.CACHE)
    } catch (exception: Exception) {
        RepoResult.Exception(exception, Source.CACHE)
    }




    fun <T, P> safeApiCall(
        apiCall: suspend () -> Response<T>,
        saveNetworkResult: suspend (T) -> P,
    ): Flow<RepoResult<T>> = flow {
        emit(RepoResult.Loading(true, Source.REMOTE))
        val flow = flowOf(safeApiCallInternal(apiCall, saveNetworkResult))
        emit(RepoResult.Loading(false, Source.REMOTE))
        emitAll(flow)
    }.flowOn(Dispatchers.IO)

    private suspend fun <T, P> safeApiCallInternal(
        apiCall: suspend () -> Response<T>,
        saveNetworkResult: suspend (T) -> P
    ) = try {
        val apiResult = apiCall.invoke()
        if (apiResult.isSuccessful) {
            if (apiResult.body() != null) {
                apiResult.body()?.let {
                    saveNetworkResult.invoke(it)
                }
                RepoResult.Success(apiResult.body()!!, Source.REMOTE)
            } else {
                RepoResult.Error(apiResult.code(), apiResult.message() ?: "", Source.REMOTE)
            }
        } else {
            RepoResult.Error(apiResult.code(), apiResult.message() ?: "", Source.REMOTE)
        }
    } catch (ex: Exception) {
        if (ex.localizedMessage != null && ex.localizedMessage != "Canceled") {
            RepoResult.Exception(ex, Source.REMOTE)
        } else {
            RepoResult.Exception(
                ex, Source.REMOTE
            )
        }
    }

}