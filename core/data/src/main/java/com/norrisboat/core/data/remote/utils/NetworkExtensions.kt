@file:Suppress("UNCHECKED_CAST")

package com.norrisboat.core.data.remote.utils

fun <T> Any?.getResults(errorMessage: String? = null): Result<T> {
    return try {
        if (this as? T != null) {
            Result.success(this as T)
        } else {
            Result.failure(Exception(errorMessage ?: "Something went wrong"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}