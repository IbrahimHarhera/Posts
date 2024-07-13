package com.ibrahim.network.network.adapter

import com.ibrahim.network.model.NetworkModel
import com.ibrahim.network.network.NetworkDelegate
import com.ibrahim.ui.resProvider.IResourceProvider
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

class ApiResponseAdapter<T : Any>(
    private val successType: Type,
    private val resourceProvider: IResourceProvider,
    private val errorConverter: Converter<ResponseBody, NetworkModel>
) : CallAdapter<T, Call<T>> {
    override fun responseType(): Type = successType

    override fun adapt(call: Call<T>): Call<T> = NetworkDelegate(call, errorConverter, resourceProvider)
}