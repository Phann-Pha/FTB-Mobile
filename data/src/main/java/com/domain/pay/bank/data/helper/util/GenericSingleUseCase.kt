package com.domain.pay.bank.data.helper.util

import com.domain.pay.bank.data.helper.usecase.BuildSingleUseCase
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

/**
 *  Class handler on GenericSingleUseCase
 *  @constructor response as Single<Response<T>>
 */
class GenericSingleUseCase<T>(private val response: Single<Response<T>>) : BuildSingleUseCase<Response<T>>()
{
    override fun buildSingleUseCase(): Single<Response<T>>
    {
        return response
    }
}