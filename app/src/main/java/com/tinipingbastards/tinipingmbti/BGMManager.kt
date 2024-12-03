package com.tinipingbastards.tinipingmbti

import android.content.Context
import android.health.connect.datatypes.units.Volume
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner

class BGMManager(val context: Context, val packageName: String) : DefaultLifecycleObserver {
    private var bgmHandlers: MutableMap<Int, BGMHandler> = mutableMapOf()
    private var forcedPausedHandlers: MutableList<BGMHandler> = mutableListOf()

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    // 앱이 백그라운드로 전환되면 모든 재생 멈추기
    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)

        bgmHandlers.forEach{(address, bgmHandler) ->
            if (bgmHandler.isPlaying)
            {
                bgmHandler.pause()

                forcedPausedHandlers.add(bgmHandler)
            }
        }
    }

    // 앱이 포그라운드로 전환되면 멈췄던 재생들만 다시 시작하기
    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)

        forcedPausedHandlers.forEach { pausedHandler ->
            pausedHandler.play()
        }

        forcedPausedHandlers.clear()
    }

    // 죽을 때 자원 다 반환하기
    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)

        bgmHandlers.forEach{(address, bgmHandler) ->
            bgmHandler.release()
        }
    }

    fun load(address : Int) {
        if (bgmHandlers.containsKey(address))
            return

        val bgmHandler = BGMHandler(context, Uri.parse("android.resource://$packageName/$address"))
        bgmHandlers[address] = bgmHandler
    }

    fun play(address: Int) {
        bgmHandlers[address]?.play()
    }

    fun pause(address: Int) {
        bgmHandlers[address]?.pause()
    }

    fun isLoaded(address: Int) : Int {
        if (bgmHandlers[address]!!.isLoaded)
            return 1
        else
            return 0
    }

    fun setVolume(address: Int, volume: Float)
    {
        bgmHandlers[address]?.setVolume(volume)
    }

    fun seekTo(address: Int, pos:Int) {
        bgmHandlers[address]?.seekTo(pos)
    }
}