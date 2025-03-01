package com.domain.pay.bank.data.helper.usecase

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

/**
 *  This class handler on SingleUseCase
 *  @property lastDisposable as Disposable
 *  @property compositeDisposable as CompositeDisposable
 */
abstract class SingleUseCase
{
    protected var lastDisposable: Disposable? = null
    protected val compositeDisposable = CompositeDisposable()
    
    fun onDisposeLast()
    {
        lastDisposable?.let {
            if (!it.isDisposed)
            {
                it.dispose()
            }
        }
    }
    
    fun onDispose()
    {
        compositeDisposable.clear()
    }
}