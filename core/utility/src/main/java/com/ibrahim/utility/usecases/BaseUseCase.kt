package com.ibrahim.utility.usecases

import com.ibrahim.utility.connectivity.Resource
import com.ibrahim.utility.obtainOutcome
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.flowOn


abstract class BaseUseCase<in params, Result>(private val dispatcher: CoroutineDispatcher) {

    protected abstract fun execute(params: params): Flow<Result>

    @ExperimentalCoroutinesApi
    operator fun invoke(params: params):Flow<Resource<Result>> =
        execute(params)
            .obtainOutcome()
            .flowOn(dispatcher)
}