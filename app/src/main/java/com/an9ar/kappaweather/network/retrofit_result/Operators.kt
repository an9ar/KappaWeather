package com.an9ar.kappaweather.network.retrofit_result

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

fun <T> Result<T>.isSuccess(): Boolean {
    return this is Result.Success
}

fun <T> Result<T>.asSuccess(): Result.Success<T> {
    return this as Result.Success<T>
}

@OptIn(ExperimentalContracts::class)
fun <T> Result<T>.isFailure(): Boolean {
    contract {
        returns(true) implies(this@isFailure is Result.Failure<*>)
    }
    return this is Result.Failure<*>
}

fun <T> Result<T>.asFailure(): Result.Failure<*> {
    return this as Result.Failure<*>
}

fun <T> Result<T>.onResult(
    onSuccess: (Result<T>) -> Unit,
    onFailure: (Result<T>) -> Unit
) {
    if (this.isSuccess()) {
        onSuccess(this)
    }
    if (this.isFailure()) {
        onFailure(this)
    }
}