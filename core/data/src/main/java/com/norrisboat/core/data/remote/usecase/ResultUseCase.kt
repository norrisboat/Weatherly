package com.norrisboat.core.data.remote.usecase

abstract class ResultUseCase<out Type, in Params> where Type : Any {
    abstract suspend fun run(params: Params): Result<Type>
}