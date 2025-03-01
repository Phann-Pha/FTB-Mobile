package com.domain.pay.bank.data.helper.util

import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 *  This class handler on GenericResponse
 *  @constructor genericSingleUseCase as GenericSingleUseCase<T>
 */
class GenericResponse<T>(private val genericSingleUseCase: GenericSingleUseCase<T>)
{
    /**
     *  This function used for execution request and return as T
     */
    suspend fun execute(port: String? = null): Resource<T>
    {
        return suspendCoroutine { continuation ->
            try
            {
                genericSingleUseCase.execute(
                    success = { response ->
                        continuation.resume(
                            Resource.Success(
                                data = response.body(),
                                statusCode = response.code().toString(),
                                message = response.message(),
                                rawUrl = response.raw().request.url.toString(),
                                port = port
                            )
                        )
                    },
                    error = { throwable ->
                        continuation.resume(
                            Resource.Error(
                                message = throwable.message,
                                port = port,
                                throwable = throwable
                            )
                        )
                    }
                )
            }
            catch (throwable: Throwable)
            {
                continuation.resume(
                    Resource.Error(
                        message = throwable.message,
                        port = port,
                        throwable = throwable
                    )
                )
            }
        }
    }
    
    /**
     *  This function used for execution request and return as Response<T>
     */
    suspend fun execute2(port: String? = null): Resource<Response<T>>
    {
        return suspendCoroutine { continuation ->
            try
            {
                genericSingleUseCase.execute(
                    success = { response ->
                        continuation.resume(
                            Resource.Success(
                                data = response,
                                statusCode = response.code().toString(),
                                message = response.message(),
                                rawUrl = response.raw().request.url.toString(),
                                port = port
                            )
                        )
                    },
                    error = { throwable ->
                        continuation.resume(
                            Resource.Error(
                                message = throwable.message,
                                port = port,
                                throwable = throwable
                            )
                        )
                    }
                )
            }
            catch (throwable: Throwable)
            {
                continuation.resume(
                    Resource.Error(
                        message = throwable.message,
                        port = port,
                        throwable = throwable
                    )
                )
            }
        }
    }
}