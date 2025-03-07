package com.domain.pay.bank.kh.common.util

import android.os.SystemClock
import android.view.View

private class SafeClickListener(private var defaultInterval: Int = 1000, private val onSafeCLick: (View) -> Unit) : View.OnClickListener
{
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View)
    {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval)
        {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v)
    }
}

fun View.onSafeOnClickListener(interval: Int = 1000, onSafeClick: (View) -> Unit)
{
    val safeClickListener = SafeClickListener(defaultInterval = interval) {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}