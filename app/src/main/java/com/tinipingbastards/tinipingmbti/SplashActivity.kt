package com.tinipingbastards.tinipingmbti

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.math.MathUtils
import com.tinipingbastards.tinipingmbti.TinipingApplication.Companion.bgmManager
import com.tinipingbastards.tinipingmbti.databinding.ActivitySplashBinding
import java.util.Timer
import java.util.TimerTask

class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bgmManager.load(R.raw.intro_bgm)
        bgmManager.load(R.raw.tiniping_100)

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
                loadingPer = (20f * bgmManager.isLoaded(R.raw.intro_bgm)
                        + 20f * bgmManager.isLoaded(R.raw.tiniping_100)
                        + 60f * MathUtils.clamp(loadingTime, 0L, minLoadingTime) / minLoadingTime).toInt()

                binding.loadingText.text = "$loadingPer%"

                if (loadingPer >= 99) {
                    val intent = Intent(baseContext, IntroActivity::class.java)
                    startActivity(intent)

                    timer.cancel()
                    timer.purge()
                }
            }
        }

        timer.schedule(timerTask, 0, 50)
    }

    override fun onPause() {
        super.onPause()

        // 화면전환 애니메이션 삭제
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_CLOSE, R.anim.none, R.anim.activity_intro_end)
        } else {
            overridePendingTransition(R.anim.none, R.anim.activity_intro_end)
        }
    }
}