package com.domain.pay.bank.kh.common.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.core.view.WindowCompat
import com.domain.pay.bank.data.local.localization.LocalizationDataStore
import com.domain.pay.bank.kh.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Locale

@AndroidEntryPoint
open class BaseComponentActivity : ComponentActivity(), BaseComponentService
{
    private lateinit var activity: Activity
    protected var localizationDataStore: LocalizationDataStore? = null
    
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        activity = this
        onInitObject()
    }
    
    private fun onInitObject()
    {
        localizationDataStore = LocalizationDataStore(activity)
    }
    
    @Composable
    override fun localizationContext(tag: String): Context
    {
        val local = Locale(tag)
        Locale.setDefault(local)
        val config = activity.resources.configuration
        config.setLocale(local)
        return activity.createConfigurationContext(config)
    }
    
    override fun onChangeIconStatusBarColor(isLightStatusBars: Boolean)
    {
        val window = activity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = isLightStatusBars
        }
    }
    
    interface IClickListener
    {
        fun onCancel()
        fun onOK()
    }
    
    @Composable
    fun OnShowInformMessage(localization: String = "en-US", listener: IClickListener? = null)
    {
        AlertDialog(
            shape = RoundedCornerShape(16.dp),
            containerColor = colorResource(R.color.white),
            onDismissRequest = { },
            text = {
                Text(
                    text = localizationContext(localization).resources.getString(R.string.default_error_message_body),
                    fontSize = 16.sp,
                    lineHeight = 25.sp,
                    textAlign = TextAlign.Start
                )
            },
            confirmButton = {
                TextButton(
                    colors = ButtonColors(
                        containerColor = colorResource(R.color.white),
                        contentColor = colorResource(R.color.color_background_primary),
                        disabledContentColor = colorResource(R.color.color_white_gray),
                        disabledContainerColor = colorResource(R.color.color_white_gray)
                    ),
                    onClick = { listener?.onOK() }
                ) {
                    Text(localizationContext(localization).resources.getString(R.string.OK).uppercase())
                }
            },
            dismissButton = {
                TextButton(
                    colors = ButtonColors(
                        containerColor = colorResource(R.color.white),
                        contentColor = colorResource(R.color.color_background_primary),
                        disabledContentColor = colorResource(R.color.color_white_gray),
                        disabledContainerColor = colorResource(R.color.color_white_gray)
                    ),
                    onClick = { listener?.onCancel() }
                ) {
                    Text(localizationContext(localization).resources.getString(R.string.Cancel).uppercase())
                }
            },
        )
    }
    
    @Composable
    fun OnLoadingAnimation(isLoading: Boolean = false)
    {
        val loading = MutableStateFlow(isLoading)
        if (loading.collectAsState().value)
        {
            AlertDialog(
                properties = DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false
                ),
                containerColor = colorResource(R.color.transparent),
                onDismissRequest = { },
                text = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(40.dp),
                            color = colorResource(R.color.white),
                            trackColor = colorResource(R.color.color_gray_light),
                        )
                    }
                },
                confirmButton = {},
                dismissButton = {},
            )
        }
    }
}