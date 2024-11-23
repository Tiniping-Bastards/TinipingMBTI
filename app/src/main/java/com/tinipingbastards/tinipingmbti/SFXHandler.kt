package com.tinipingbastards.tinipingmbti

import android.content.Context
import android.media.SoundPool

// 효과음 관리하는 클래스
class SFXHandler (val context : Context) {
    private var soundPool : SoundPool = SoundPool.Builder().build()
    private var soundIDMap : MutableMap<Int, Int> = mutableMapOf()

    fun loadSFX(address : Int)
    {
        val soundID = soundPool.load(context, address, 0)
        soundIDMap[address] = soundID
    }

    fun playSFX(address : Int)
    {
        val soundID : Int? = soundIDMap[address]
        soundPool.play(soundID!!, 1f, 1f , 0, 0, 1f)
    }
}