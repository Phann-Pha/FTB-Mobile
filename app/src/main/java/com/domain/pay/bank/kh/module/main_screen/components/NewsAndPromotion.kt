package com.domain.pay.bank.kh.module.main_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.domain.pay.bank.kh.R
import kotlin.math.absoluteValue

@Composable
fun NewsAndPromotion()
{
    val list = remember {
        listOf(
            R.drawable.banner_3,
            R.drawable.banner_1,
            R.drawable.banner_2
        )
    }
    val pagerState = rememberPagerState(pageCount = { list.size })
    
    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState
    ) { page ->
        Box(Modifier
            .graphicsLayer {
                val pageOffset = pagerState.calculateCurrentOffsetForPage(page)
                translationX = pageOffset * size.width
                alpha = 1 - pageOffset.absoluteValue
            }
            .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = list[page]),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(18.dp)),
            )
        }
    }
}

fun PagerState.calculateCurrentOffsetForPage(page: Int): Float
{
    return (currentPage - page) + currentPageOffsetFraction
}