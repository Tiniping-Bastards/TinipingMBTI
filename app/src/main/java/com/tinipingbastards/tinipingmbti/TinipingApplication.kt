package com.tinipingbastards.tinipingmbti

import android.app.Application

class TinipingApplication : Application() {
    companion object {
        lateinit var bgmHandler: BGMHandler
    }

    override fun onCreate() {
        super.onCreate()

        bgmHandler = BGMHandler(baseContext)

        bgmHandler.load(R.raw.the_first_moment)
    }
}