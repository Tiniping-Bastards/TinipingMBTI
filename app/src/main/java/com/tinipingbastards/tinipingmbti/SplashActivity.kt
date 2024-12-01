package com.tinipingbastards.tinipingmbti

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.math.MathUtils
import com.tinipingbastards.tinipingmbti.databinding.ActivitySplashBinding
import java.util.Timer
import java.util.TimerTask

class SplashActivity : AppCompatActivity(), MediaPlayer.OnPreparedListener {

    private var isMediaLoaded = 0

    private lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        TinipingApplication.mediaPlayer = MediaPlayer()
        TinipingApplication.mediaPlayer.setDataSource(baseContext, Uri.parse("android.resource://" + packageName + "/" + R.raw.tiniping_100))
        TinipingApplication.mediaPlayer.setOnPreparedListener(this)
        TinipingApplication.mediaPlayer.prepareAsync()

        // StartLoading
        var loadingTime = 0L
        var loadingPer = 0
        var period = 50
        var minLoadingTime = 2000L

        val timer = Timer(true)
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                loadingTime += period

                // 로딩게이지 계산
                loadingPer = (30f * isMediaLoaded  + 70f * MathUtils.clamp(loadingTime, 0L, minLoadingTime) / minLoadingTime).toInt()

                binding.loadingText.text = "$loadingPer%"

                if (loadingPer >= 100) {
                    val intent = Intent(baseContext, IntroActivity::class.java)
                    startActivity(intent)

                    timer.cancel()
                    timer.purge()
                }
            }
        }

        timer.schedule(timerTask, 0, 50)
    }

    override fun onPrepared(mediaPlayer: MediaPlayer) {
        isMediaLoaded = 1
    }

    override fun onPause() {
        super.onPause()

        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_CLOSE, R.anim.none, R.anim.activity_intro_end)
        } else {
            overridePendingTransition(R.anim.none, R.anim.activity_intro_end)
        }
    }
}