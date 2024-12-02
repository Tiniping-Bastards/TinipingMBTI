package com.tinipingbastards.tinipingmbti

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

class BGMManager(val context: Context, val packageName: String) {
    private var bgmHandlers: MutableMap<Int, BGMHandler> = mutableMapOf()

    fun load(address : Int) {
        val bgmHandler = BGMHandler(context, Uri.parse("android.resource://$packageName/$address"))

        bgmHandlers[address] = bgmHandler
    }

    fun play(address: Int) {
        bgmHandlers[address]?.play()
    }

    fun pause(address: Int) {
        bgmHandlers[address]?.pause()
    }

    fun isLoaded (address: Int) : Int {
        if (bgmHandlers[address]!!.isLoaded)
            return 1
        else
            return 0
    }
}