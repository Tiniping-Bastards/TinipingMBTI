package com.tinipingbastards.tinipingmbti

import android.app.Activity
import android.app.Application
import org.checkerframework.checker.units.qual.h


class TinipingApplication : Application() {
    companion object {
        lateinit var bgmManager: BGMManager
        lateinit var sfxHandler : SFXManager
    }

    override fun onCreate() {
        super.onCreate()

        bgmManager = BGMManager(baseContext, packageName)

        sfxHandler = SFXManager(baseContext)

        sfxHandler.loadSFX(R.raw.button_click)
        sfxHandler.loadSFX(R.raw.tiniping_signature)
    }

}