package com.tinipingbastards.tinipingmbti

import android.os.Handler
import android.os.Looper
import android.widget.ImageView

class ImageHandler(
    private val imageView: ImageView,
    private val imageList: List<Int>,
    private val interval: Long = 60L
) {
    private val handler = Handler(Looper.getMainLooper())
    private var currentImageIndex = 0
    private var isRunning = false

    private val imageChangeRunnable = object : Runnable {
        override fun run() {
            if (isRunning) {
                imageView.setImageResource(imageList[currentImageIndex])
                currentImageIndex = (currentImageIndex + 1) % imageList.size
                handler.postDelayed(this, interval)
            }
        }
    }

    fun start() {
        if (!isRunning) {
            isRunning = true
            handler.post(imageChangeRunnable)
        }
    }

    fun stop() {
        isRunning = false
        handler.removeCallbacksAndMessages(null)
    }
}