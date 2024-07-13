package com.ibrahim.network.network

import com.ibrahim.network.R
import com.ibrahim.network.model.NetworkModel
import com.ibrahim.network.utils.ApiException
import com.ibrahim.ui.resProvider.IResourceProvider

fun createApiException(
    resourceProvider: IResourceProvider,
    errorResponse: NetworkModel?
): ApiException.ApiError {
    return ApiException.ApiError(
        message = errorResponse?.error?.info
            ?: (resourceProvider.getText(R.string.general_network_error)),
        statusCode = errorResponse?.error?.code.toString()
    )
}