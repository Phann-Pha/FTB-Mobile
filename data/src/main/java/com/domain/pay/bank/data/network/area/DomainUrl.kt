package com.domain.pay.bank.data.network.area

import com.domain.pay.bank.data.BuildConfig
import com.domain.pay.bank.data.constance.SystemConstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

/** Class used for provide Area URL (uat or production)**/
@Module
@InstallIn(SingletonComponent::class)
class DomainUrl @Inject constructor()
{
    private val baseUrl: String
        get() = if (BuildConfig.IS_PRODUCTION) SystemConstance.BASE_URL_PRODUCTION else SystemConstance.BASE_URL_UAT
    
    @Provides
    fun onPortSTU(): String
    {
        return baseUrl + PortMapping.STU
    }
}