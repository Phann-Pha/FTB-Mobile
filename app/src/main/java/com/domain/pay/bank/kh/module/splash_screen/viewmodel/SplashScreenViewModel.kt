package com.domain.pay.bank.kh.module.splash_screen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.domain.pay.bank.data.helper.util.Resource
import com.domain.pay.bank.kh.module.splash_screen.domain.repository.SplashScreenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val repo: SplashScreenRepository) : ViewModel()
{
    private val _loadingState: MutableLiveData<Boolean> = MutableLiveData()
    val loadingState: LiveData<Boolean> get() = _loadingState
    
    private val _onSyneSuccessState: MutableLiveData<Boolean> = MutableLiveData()
    val onSyneSuccessState: LiveData<Boolean> get() = _onSyneSuccessState
    
    private val _errorState: MutableLiveData<Boolean> = MutableLiveData()
    val errorState: LiveData<Boolean> get() = _errorState
    
    init
    {
        onSyneDataRequired()
    }
    
    private fun onSyneDataRequired()
    {
        _loadingState.postValue(true)
        repo.onSyneDataRequired().onEach { response ->
            when (response)
            {
                is Resource.Success ->
                {
                    _loadingState.postValue(false)
                    _onSyneSuccessState.postValue(true)
                }
                
                is Resource.Error ->
                {
                    _loadingState.postValue(false)
                    _errorState.postValue(true)
                }
            }
        }.launchIn(viewModelScope)
    }
    
    override fun onCleared()
    {
        super.onCleared()
        viewModelScope.cancel()
    }
}