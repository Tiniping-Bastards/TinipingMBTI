package com.tinipingbastards.tinipingmbti

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.math.MathUtils
import com.tinipingbastards.tinipingmbti.TinipingApplication.Companion.bgmManager
import com.tinipingbastards.tinipingmbti.databinding.ActivitySplashBinding
import java.util.Timer
import java.util.TimerTask

class SplashActivity : AppCompatActivity() {
    companion object {
        var LOADING_INTERVAL = 50L              // 로딩간격
        var LOADING_TIME_MIN = 2000L            // 최소 로딩시간 2초
    }

    private lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        // 로딩하기
        bgmManager.load(R.raw.intro_bgm)
        bgmManager.load(R.raw.tiniping_100)

        bgmManager.setVolume(R.raw.intro_bgm, 0f)
        bgmManager.setVolume(R.raw.tiniping_100, 0f)

        // UI업데이트
        var loadingTime = 0L
        var loadingPer = 0

        val timer = Timer(true)
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                loadingTime += LOADING_INTERVAL

                // 로딩게이지 계산
                loadingPer = (20f * bgmManager.isLoaded(R.raw.intro_bgm)
                        + 20f * bgmManager.isLoaded(R.raw.tiniping_100)
                        + 60f * MathUtils.clamp(loadingTime, 0L, LOADING_TIME_MIN) / LOADING_TIME_MIN).toInt()

                binding.loadingText.text = "$loadingPer%"

                if (loadingPer >= 99) {
                    val intent = Intent(baseContext, IntroActivity::class.java)
                    startActivity(intent)

                    timer.cancel()
                    timer.purge()
                }
            }
        }

        timer.schedule(timerTask, 0, LOADING_INTERVAL)
    }

    override fun onPause() {
        super.onPause()

        // 화면전환 애니메이션 삭제
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_CLOSE, 0, 0)
        } else {
            overridePendingTransition(0, 0)
        }
    }
}