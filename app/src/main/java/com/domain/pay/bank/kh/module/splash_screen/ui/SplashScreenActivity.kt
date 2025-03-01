package com.domain.pay.bank.kh.module.splash_screen.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.domain.pay.bank.kh.R
import com.domain.pay.bank.kh.common.base.BaseComponentActivity
import com.domain.pay.bank.kh.module.main_screen.ui.AppMainActivity
import com.domain.pay.bank.kh.module.splash_screen.viewmodel.SplashScreenViewModel
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen", "UnusedMaterial3ScaffoldPaddingParameter")
class SplashScreenActivity : BaseComponentActivity()
{
    private lateinit var activity: Activity
    private val viewmodel: SplashScreenViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        activity = this
        onChangeIconStatusBarColor(false)
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                ScreenBody(padding)
            }
        }
    }
    
    @Composable
    private fun ScreenBody(padding: PaddingValues)
    {
        val coroutineScope = rememberCoroutineScope()
        val localization = localizationDataStore?.localization?.collectAsStateWithLifecycle("en-US")?.value ?: "en-US"
        
        var onLoadingState by remember { mutableStateOf(false) }
        var onErrorMessageState by remember { mutableStateOf(false) }
        var onSyneDataState by remember { mutableStateOf(false) }
        
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.background_screen),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            MainContent(padding, localization) {
                onSyneDataState = !onSyneDataState
            }
            LaunchedEffect(coroutineScope) {
                viewmodel.loadingState.observe(this@SplashScreenActivity) { value ->
                    onLoadingState = value
                }
                viewmodel.errorState.observe(this@SplashScreenActivity) { value ->
                    onErrorMessageState = value
                }
                viewmodel.onSyneSuccessState.observe(this@SplashScreenActivity) { status ->
                    onSyneDataState = status
                }
            }
            OnLoadingAnimation(onLoadingState)
            AnimatedVisibility(
                visible = onSyneDataState,
                enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween(250)) + fadeIn(animationSpec = tween(250)),
                exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween(250)) + fadeOut(animationSpec = tween(250))
            ) {
                BottomSheetSelectLanguage(modifier = Modifier, localization) { value ->
                    if (localization != value)
                    {
                        coroutineScope.launch {
                            localizationDataStore?.update(value)
                        }
                    }
                    startActivity(AppMainActivity.newInstance(activity))
                    finish()
                }
            }
            if (onErrorMessageState)
            {
                OnShowInformMessage(localization = localization, listener = object : IClickListener
                {
                    override fun onCancel()
                    {
                        onErrorMessageState = false
                    }
                    
                    override fun onOK()
                    {
                        onErrorMessageState = false
                    }
                })
            }
        }
    }
    
    @Composable
    private fun MainContent(padding: PaddingValues, localization: String, callback: () -> Unit = {})
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.padding(start = 24.dp, top = 20.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.bank_logo_none_background),
                    contentDescription = null,
                    modifier = Modifier
                        .width(100.dp)
                        .clickable { callback.invoke() }
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = localizationContext(localization).resources.getString(R.string.welcome_to_ftb_mobile),
                    color = colorResource(R.color.white),
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = localizationContext(localization).resources.getString(R.string.version_name),
                    color = colorResource(R.color.white),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }
        }
    }
    
    @Composable
    private fun BottomSheetSelectLanguage(modifier: Modifier = Modifier, localization: String, callback: (code: String) -> Unit)
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .clip(RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp))
                    .background(colorResource(R.color.white))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(top = 12.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .padding(bottom = 8.dp)
                        .background(colorResource(R.color.color_gray_light))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                callback.invoke("km")
                            }
                        )
                        .padding(vertical = 12.dp, horizontal = 12.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.cambodia_flag),
                        contentDescription = null,
                        modifier = Modifier.size(45.dp)
                    )
                    Text(
                        text = localizationContext(localization).resources.getString(R.string.khmer_language),
                        color = colorResource(R.color.color_background_primary),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(12.dp))
                        .padding(top = 8.dp)
                        .background(colorResource(R.color.color_gray_light))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                callback.invoke("en-US")
                            }
                        )
                        .padding(vertical = 12.dp, horizontal = 12.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.us_flag),
                        contentDescription = null,
                        modifier = Modifier.size(45.dp)
                    )
                    Text(
                        text = localizationContext(localization).resources.getString(R.string.english_language),
                        color = colorResource(R.color.color_background_primary),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}