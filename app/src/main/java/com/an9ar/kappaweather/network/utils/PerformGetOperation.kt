package com.an9ar.kappaweather.network.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.an9ar.kappaweather.log
import kotlinx.coroutines.*

fun <T> performFetchOperation(
    networkCall: suspend () -> Resource<T>,
    saveCallResult: suspend (T) -> Unit
): LiveData<Resource.Status> =
    liveData(Dispatchers.IO) {
        emit(Resource.Status.LOADING)
        val responseStatus = networkCall.invoke()

        if (responseStatus.status == Resource.Status.SUCCESS) {
            saveCallResult(responseStatus.data!!)
            emit(Resource.Status.COMPLETED)
        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.Status.ERROR)
        }
    }

fun <T, A> performGetNetworkOperation(
        networkCall: suspend () -> Resource<A>,
        convertResponseTo: suspend (A) -> T
): LiveData<Resource<T>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val responseStatus = networkCall.invoke()
            if (responseStatus.status == Resource.Status.SUCCESS) {
                val convertedResponse = convertResponseTo(responseStatus.data!!)
                emit(Resource.success(convertedResponse))
            } else if (responseStatus.status == Resource.Status.ERROR) {
                log("call error - ${responseStatus.message}")
                emit(Resource.error(responseStatus.message!!))
            }
        }

fun <A> performAsyncGetAndInsertOperation(
    networkCall: suspend () -> Resource<A>,
    saveCallResult: suspend (A) -> Unit
): Deferred<Resource.Status> =
    CoroutineScope(Dispatchers.IO).async {
        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Resource.Status.SUCCESS) {
            saveCallResult(responseStatus.data!!)
            return@async Resource.Status.SUCCESS
        } else if (responseStatus.status == Resource.Status.ERROR) {
            log("error - ${responseStatus.message}")
            return@async Resource.Status.ERROR
        }
        return@async Resource.Status.COMPLETED
    }

fun <T, A> performUpdateOperation(
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> Resource<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = databaseQuery.invoke().map { Resource.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Resource.Status.SUCCESS) {
            saveCallResult(responseStatus.data!!)
        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.error(responseStatus.message!!))
            emitSource(source)
        }
    }

