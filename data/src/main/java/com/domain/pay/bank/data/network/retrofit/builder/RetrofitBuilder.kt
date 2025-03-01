package com.domain.pay.bank.data.network.retrofit.builder

import com.domain.pay.bank.data.network.area.DomainUrl
import com.domain.pay.bank.data.network.area.PortMapping
import com.domain.pay.bank.data.network.area.PortUtil
import com.domain.pay.bank.data.network.retrofit.provider.HttpClientProvider
import com.domain.pay.bank.data.network.service.APIInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Named

/**
 *  Class used for provide retrofit builder This class we create for provide all retrofit builder that have different baseUrl
 *
 *  @property client used for provide OkHttpClient (note don't used private access modifier, when used @Inject)
 *  @property client used for provide specific area url (note don't used private access modifier, when used @Inject)
 * */

@Module
@InstallIn(SingletonComponent::class)
class RetrofitBuilder @Inject constructor()
{
    internal val client: HttpClientProvider
        @Inject get() = HttpClientProvider()
    
    internal val domain: DomainUrl
        @Inject get() = DomainUrl()
    
    @Provides
    @Named(PortMapping.STU)
    fun onRetrofitSTUBuilder(): Retrofit = Retrofit.Builder().baseUrl(domain.onPortSTU())
        .addConverterFactory(MoshiConverterFactory.create().asLenient())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(client.onOkHttpClientProvider())
        .build()
    
    @Provides
    @Named(PortUtil.portSTU)
    fun onRetrofit(@Named(PortMapping.STU) retrofit: Retrofit): APIInterface = retrofit.create(APIInterface::class.java)
}