package com.domain.pay.bank.kh.module.splash_screen.domain.service

import com.domain.pay.bank.data.helper.util.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface SplashScreenService
{
    fun onSyneDataRequired(): Flow<Resource<ResponseBody>>
}