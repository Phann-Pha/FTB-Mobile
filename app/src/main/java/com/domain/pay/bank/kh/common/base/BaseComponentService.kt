package com.domain.pay.bank.kh.common.base

import android.content.Context
import androidx.compose.runtime.Composable

interface BaseComponentService
{
    /** This property used for alternative language
     * */
    @Composable
    fun localizationContext(tag: String): Context
    
    /** This function used for change icon status bar color.
     * @param isLightStatusBars if true icon status bar will be white else to gray
     * */
    fun onChangeIconStatusBarColor(isLightStatusBars: Boolean)
}