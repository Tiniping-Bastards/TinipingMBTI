package com.tinipingbastards.tinipingmbti

import android.content.Context
import android.media.MediaPlayer
import com.google.android.material.math.MathUtils
import java.util.Timer
import java.util.TimerTask


class BGMHandler(val context: Context) {
    val volumeMax : Float = 1f
    val volumeMin : Float = 0f

    // 0.1초 간격으로 Fade
    val fadeStepTime : Long = 50

    private var bgmPlayers: MutableMap<Int, MediaPlayer> = mutableMapOf()

    fun load(address: Int) {
        val bgmPlayer = MediaPlayer.create(context, address)
        bgmPlayer.isLooping = true

        bgmPlayers[address] = bgmPlayer
    }

    fun play(address: Int, delay: Long, fadeTime: Long) {
        val mediaPlayer = bgmPlayers[address]

        if (mediaPlayer == null)
            return

        if (mediaPlayer.isPlaying)
            return

        mediaPlayer.start()
        setVolume(mediaPlayer, volumeMin)

        var time = 0f

        val timer = Timer(true)
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                time += fadeStepTime

                setVolume(mediaPlayer, MathUtils.lerp(volumeMin, volumeMax, time / fadeTime))

                if (time >= fadeTime) {
                    setVolume(mediaPlayer, volumeMax)

                    timer.cancel()
                    timer.purge()
                }
            }
        }

        timer.schedule(timerTask, delay, fadeStepTime)
    }

    fun pause(address: Int, fadeTime: Long) {
        val mediaPlayer = bgmPlayers[address]

        if (mediaPlayer == null)
            return

        if (!mediaPlayer.isPlaying)
            return

        setVolume(mediaPlayer, volumeMax)

        var time = 0f

        val timer = Timer(true)
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                time += fadeStepTime

                setVolume(mediaPlayer, MathUtils.lerp(volumeMax, volumeMin, time / fadeTime))

                if (time >= fadeTime) {
                    mediaPlayer.pause()

                    setVolume(mediaPlayer, volumeMin)

                    timer.cancel()
                    timer.purge()
                }
            }
        }

        timer.schedule(timerTask, 0, fadeStepTime)
    }

    fun setVolume(mediaPlayer : MediaPlayer, value: Float) {
        var volume = value

        if (volume < volumeMin)
            volume = volumeMin
        else if (volume > volumeMax)
            volume = volumeMax;

        mediaPlayer.setVolume(volume, volume)
    }
}