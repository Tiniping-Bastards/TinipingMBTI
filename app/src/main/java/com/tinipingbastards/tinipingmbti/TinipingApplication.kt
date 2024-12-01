package com.tinipingbastards.tinipingmbti

import android.app.Application
import android.media.MediaPlayer
import android.media.SoundPool

class TinipingApplication : Application() {
    companion object {
        lateinit var bgmHandler: BGMHandler
        lateinit var sfxHandler : SFXHandler

        lateinit var mediaPlayer : MediaPlayer
    }

    override fun onCreate() {
        super.onCreate()

        mediaPlayer = MediaPlayer.create(baseContext, R.raw.tiniping_100)

        bgmHandler = BGMHandler(baseContext)
        bgmHandler.load(R.raw.tiniping_100)

        sfxHandler = SFXHandler(baseContext)

        sfxHandler.loadSFX(R.raw.button_click)
        sfxHandler.loadSFX(R.raw.tiniping_signature)
    }
}