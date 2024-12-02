package com.tinipingbastards.tinipingmbti

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor
import com.google.android.material.math.MathUtils
import java.util.Timer
import java.util.TimerTask


class BGMHandler(context: Context, uri: Uri) : MediaPlayer.OnPreparedListener{
    var isLoaded = false

    private var mediaPlayer: MediaPlayer

    init {
        isLoaded = false

        mediaPlayer = MediaPlayer()
        mediaPlayer.isLooping = true
        mediaPlayer.setDataSource(context, uri)
        mediaPlayer.setOnPreparedListener(this)
        mediaPlayer.prepareAsync()
    }

    override fun onPrepared(p0: MediaPlayer?) {
        isLoaded = true
    }

    fun play() {
        mediaPlayer.start()
    }

    fun pause() {
        mediaPlayer.pause()
    }

    fun setVolume(volume: Float) {
        mediaPlayer.setVolume(volume, volume)
    }
}