package com.domain.pay.bank.kh.module.main_screen.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.domain.pay.bank.kh.R
import com.domain.pay.bank.kh.common.base.BaseComponentActivity
import com.domain.pay.bank.kh.module.main_screen.components.AppBarSection
import com.domain.pay.bank.kh.module.main_screen.components.CardItemMainMenu
import com.domain.pay.bank.kh.module.main_screen.components.NewsAndPromotion
import com.domain.pay.bank.kh.module.main_screen.components.ProfileSection
import com.domain.pay.bank.kh.module.main_screen.components.RecentlyTransaction

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class AppMainActivity : BaseComponentActivity()
{
    private lateinit var activity: Activity
    
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        activity = this
        onChangeIconStatusBarColor(false)
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { paddingValue ->
                ScreenBody(paddingValue)
            }
        }
    }
    
    @Composable
    private fun ScreenBody(paddingValues: PaddingValues)
    {
        val localization = localizationDataStore?.localization?.collectAsStateWithLifecycle("en-US")?.value ?: "en-US"
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.color_background_primary))
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                AppBarSection(paddingValues)
                ProfileSection(activity, localization)
                MainMenuContainer(localization)
            }
        }
    }
    
    @Composable
    private fun MainMenuContainer(localization: String)
    {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(color = colorResource(R.color.white))
        ) {
            item {
                MainMenuSection(localization)
            }
            item {
                RecentlyTransactionSection(localization)
            }
            item {
                NewsAndPromotionSection(localization)
            }
            item {
                val height = remember { 210.dp }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height)
                        .padding(start = 12.dp, end = 12.dp, top = 20.dp)
                ){}
            }
        }
    }
    
    @Composable
    private fun MainMenuSection(localization: String)
    {
        val height = remember { 270.dp }
        LazyVerticalGrid(
            userScrollEnabled = false,
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .padding(start = 12.dp, end = 12.dp, top = 20.dp)
        ) {
            item {
                CardItemMainMenu(
                    title = localizationContext(localization).resources.getString(R.string.account),
                    icon = R.drawable.ic_wallet_account
                )
            }
            item {
                CardItemMainMenu(
                    title = localizationContext(localization).resources.getString(R.string.transfer),
                    icon = R.drawable.ic_exchange
                )
            }
            item {
                CardItemMainMenu(
                    title = localizationContext(localization).resources.getString(R.string.cards),
                    icon = R.drawable.ic_card
                )
            }
            item {
                CardItemMainMenu(
                    title = localizationContext(localization).resources.getString(R.string.payments),
                    icon = R.drawable.ic_payments
                )
            }
            item {
                CardItemMainMenu(
                    title = localizationContext(localization).resources.getString(R.string.loan),
                    icon = R.drawable.ic_loan
                )
            }
            item {
                CardItemMainMenu(
                    title = localizationContext(localization).resources.getString(R.string.scan_qr),
                    icon = R.drawable.ic_scan_qr
                )
            }
        }
    }
    
    @Composable
    private fun RecentlyTransactionSection(localization: String)
    {
        val height = remember { 200.dp }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .padding(start = 12.dp, end = 12.dp, top = 20.dp)
        ) {
            Text(
                text = localizationContext(localization).resources.getString(R.string.recently_transaction),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = colorResource(R.color.dark_item),
                modifier = Modifier.padding(start = 6.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            LazyRow(
                userScrollEnabled = true,
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    RecentlyTransaction(
                        title = localizationContext(localization).resources.getString(R.string.account),
                        short = "AC",
                        icon = R.drawable.ic_wallet_account
                    )
                }
                item {
                    RecentlyTransaction(
                        title = localizationContext(localization).resources.getString(R.string.transfer),
                        short = "TR",
                        icon = R.drawable.ic_exchange
                    )
                }
                item {
                    RecentlyTransaction(
                        title = localizationContext(localization).resources.getString(R.string.cards),
                        short = "CA",
                        icon = R.drawable.ic_card
                    )
                }
                item {
                    RecentlyTransaction(
                        title = localizationContext(localization).resources.getString(R.string.payments),
                        short = "PA",
                        icon = R.drawable.ic_payments
                    )
                }
                item {
                    RecentlyTransaction(
                        title = localizationContext(localization).resources.getString(R.string.loan),
                        short = "LO",
                        icon = R.drawable.ic_loan
                    )
                }
                item {
                    RecentlyTransaction(
                        title = localizationContext(localization).resources.getString(R.string.scan_qr),
                        short = "SC",
                        icon = R.drawable.ic_scan_qr
                    )
                }
            }
        }
    }
    
    @Composable
    fun NewsAndPromotionSection(localization: String)
    {
        val height = remember { 210.dp }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .padding(start = 12.dp, end = 12.dp, top = 20.dp)
        ) {
            Text(
                text = localizationContext(localization).resources.getString(R.string.new_and_promotions),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = colorResource(R.color.dark_item),
                modifier = Modifier.padding(start = 6.dp)
            )
            Spacer(modifier = Modifier.height(14.dp))
            NewsAndPromotion()
        }
    }
    
    companion object
    {
        fun newInstance(activity: Activity): Intent
        {
            return Intent(activity, AppMainActivity::class.java)
        }
    }
}