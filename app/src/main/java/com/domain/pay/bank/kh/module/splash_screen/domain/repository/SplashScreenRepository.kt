package com.domain.pay.bank.kh.module.splash_screen.domain.repository

import com.domain.pay.bank.data.helper.util.Resource
import com.domain.pay.bank.kh.module.splash_screen.domain.service.SplashScreenService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import javax.inject.Inject

class SplashScreenRepository @Inject constructor() : SplashScreenService
{
    override fun onSyneDataRequired(): Flow<Resource<ResponseBody>> = flow {
        try
        {
            delay(300)
            emit(Resource.Success(null))
        }
        catch (e: Exception)
        {
            emit(Resource.Error(null))
        }
    }
}