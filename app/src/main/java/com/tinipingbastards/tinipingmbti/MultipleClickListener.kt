package com.tinipingbastards.tinipingmbti

import android.view.View

abstract class MultipleClickListener : View.OnClickListener {
    companion object {
        private const val DOUBLE_CLICK_TIME_DELTA: Long = 300 //milliseconds
    }

    private var clickCount: Int = 0
    private var lastClickTime: Long = 0

    override fun onClick(v: View?) {
        val clickTime = System.currentTimeMillis()

        if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
            clickCount++;
        } else {
            clickCount = 1;
        }

        onMultipleClick(v, clickCount)

        lastClickTime = clickTime
    }

    abstract fun onMultipleClick(v: View?, count: Int)
}