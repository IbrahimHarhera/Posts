package com.ibrahim.network.network

import com.ibrahim.network.BuildConfig
import android.util.Log
import com.ibrahim.network.R
import com.ibrahim.network.model.NetworkModel
import com.ibrahim.network.network.adapter.stateException
import com.ibrahim.network.utils.ApiException
import com.ibrahim.ui.resProvider.IResourceProvider
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal class NetworkDelegate <T : Any>(
    private val delegate: Call<T>,
    private val bodyConverter: Converter<ResponseBody, NetworkModel>,
    private val resourceProvider: IResourceProvider
) : Call<T> by delegate {

    override fun enqueue(callback: Callback<T>) = delegate.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                callback.onResponse(this@NetworkDelegate, Response.success(response.body()))
            } else {
                callback.onFailure(
                    this@NetworkDelegate,
                    handleApiException(resourceProvider, response.code(), response.errorBody())
                )
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            if (BuildConfig.DEBUG) {
                Log.e("onFailure (API) ---> ", "${t.message}")
            }
            callback.onFailure(this@NetworkDelegate, parseApiException(t))
        }
    })

    override fun clone(): Call<T> =
        NetworkDelegate(delegate.clone(), bodyConverter, resourceProvider)

    private fun parseApiException(exception: Throwable): ApiException = when (exception) {
        is ConnectException, is SocketTimeoutException, is UnknownHostException -> {
            ApiException.NoInternetConnection(
                message = resourceProvider.getText(R.string.no_internet_error_message),
                throwable = exception
            )
        }

        is HttpException -> {
            handleApiException(resourceProvider, exception.code(), exception.response()?.errorBody())
        }

        else -> {
            ApiException.GeneralError(exception.message)
        }
    }

    private fun handleApiException(
        resourceProvider: IResourceProvider,
        code: Int,
        errorBody: ResponseBody?,
        generalNetworkModel: NetworkModel? = null,
    ): ApiException = when (code) {

        200 -> createApiException(resourceProvider, generalNetworkModel)


        HttpURLConnection.HTTP_BAD_GATEWAY

        -> ApiException.GeneralError(
            resourceProvider.getText(R.string.general_network_error)
        )

        HttpURLConnection.HTTP_UNAUTHORIZED -> ApiException.Unauthorized

        HttpURLConnection.HTTP_GATEWAY_TIMEOUT -> {
            errorBody?.let {
                try {
                    val errorResponse =
                        bodyConverter.convert(it) ?: stateException("Can't serialize response")
                    ApiException.TimeOut(resourceProvider.getText(R.string.server_timeout_message))
                } catch (e: Throwable) {
                    if (BuildConfig.DEBUG) {
                        Log.e("API Failed with: ", "$e")
                    }
                    ApiException.ApiError()
                }
            } ?: ApiException.GeneralError(resourceProvider.getText(R.string.general_network_error))
        }


        else -> {
            errorBody?.let {
                try {
                    val errorResponse = bodyConverter.convert(it) ?: stateException("No Error body")
                    createApiException(resourceProvider, errorResponse)
                } catch (e: Throwable) {
                    if (BuildConfig.DEBUG) {
                        Log.e("API Failed with: ", "$e")
                    }
                    ApiException.ApiError()
                }
            } ?: ApiException.GeneralError(resourceProvider.getText(R.string.general_network_error))
        }
    }

}