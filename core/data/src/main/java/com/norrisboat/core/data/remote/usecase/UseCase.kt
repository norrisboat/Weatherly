package com.norrisboat.core.data.remote.usecase

abstract class UseCase<out Type, in Params> where Type : Any {
    abstract suspend fun run(params: Params): Type
}