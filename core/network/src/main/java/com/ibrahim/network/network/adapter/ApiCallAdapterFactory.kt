package com.ibrahim.network.network.adapter

import com.ibrahim.network.model.NetworkModel
import com.ibrahim.ui.resProvider.IResourceProvider
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiCallAdapterFactory (private val resourceProvider: IResourceProvider) :CallAdapter.Factory(){
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) {
            return null
        }

        check(returnType is ParameterizedType) {
        }

        val responseType = getParameterUpperBound(0, returnType)

        val errorBodyConverter = retrofit.responseBodyConverter<NetworkModel>(
            NetworkModel::class.java,
            annotations
        )

        return ApiResponseAdapter<Any>(responseType, resourceProvider, errorBodyConverter)
    }
}