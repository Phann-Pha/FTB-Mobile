package com.domain.pay.bank.data.network.service

import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface APIInterface
{
    @GET("fact")
    fun onGetUserInfo(): Single<Response<ResponseBody>>
}