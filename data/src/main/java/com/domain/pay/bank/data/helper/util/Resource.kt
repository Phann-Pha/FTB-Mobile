package com.domain.pay.bank.data.helper.util

sealed class Resource<T>(
    val data: T? = null,
    val statusCode: String? = null,
    val message: String? = null,
    val rawUrl: String? = null,
    val port: String? = null,
    val throwable: Throwable? = null
)
{
    class Success<T>(
        data: T? = null,
        statusCode: String? = null,
        message: String? = null,
        rawUrl: String? = null,
        port: String? = null
    ) : Resource<T>(
        data = data,
        statusCode = statusCode,
        message = message,
        rawUrl = rawUrl,
        port = port
    )
    
    class Error<T>(
        data: T? = null,
        statusCode: String? = null,
        message: String? = null,
        rawUrl: String? = null,
        port: String? = null,
        throwable: Throwable? = null
    ) : Resource<T>(
        data = data,
        statusCode = statusCode,
        message = message,
        rawUrl = rawUrl,
        port = port,
        throwable = throwable
    )
}