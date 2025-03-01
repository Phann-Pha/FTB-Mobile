package com.domain.pay.bank.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Invocation

/**
 * Class can be used to intercept and modify HTTP requests or responses,
 * so we can use it to modify our network request
 * before it gets executed.
 * */

class DefaultInterceptor(private val authKey: String = "", private val authValue: String = "") : Interceptor
{
    @Retention(AnnotationRetention.RUNTIME)
    @Target(AnnotationTarget.FUNCTION)
    annotation class Authorized
    
    override fun intercept(chain: Interceptor.Chain): Response
    {
        var request = chain.request()
        val invocation = chain.request().tag(Invocation::class.java) ?: return chain.proceed(chain.request())
        containedOnInvocation(invocation).forEach { annotation ->
            request = onHandleAnnotation(annotation, request, authKey, authValue)
        }
        return chain.proceed(request)
    }
    
    private fun containedOnInvocation(invocation: Invocation): Set<Annotation>
    {
        return invocation.method().annotations.toSet()
    }
    
    private fun onHandleAnnotation(annotation: Annotation, request: Request, key: String, value: String): Request
    {
        return when (annotation)
        {
            is Authorized -> addAuthHeader(request, key, value)
            else -> request
        }
    }
    
    private fun addAuthHeader(request: Request, key: String, value: String): Request
    {
        return request.newBuilder()
            .addHeader(key, value)
            .build()
    }
}