package com.tinipingbastards.tinipingmbti

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.tinipingbastards.tinipingmbti.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    private lateinit var binding : ActivityIntroBinding

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // BGM 재생 시작
        //TinipingApplication.bgmHandler.play(R.raw.tiniping_100, 0, 0)
        TinipingApplication.mediaPlayer.start()
        TinipingApplication.mediaPlayer.setVolume(0.5f, 0.5f)

        // 인트로 비디오 재생
        binding.videoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.intro_video))
        binding.videoView.start()

        // Skip 텍스트 3초 뒤에 활성화
        binding.skipText.startAnimation(AnimationUtils.loadAnimation(this, R.anim.intro_skip_show))

        // 인트로 비디오 누르면 메인화면으로 이동
        binding.videoView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // 인트로 비디오 끝나면 메인화면으로 이동
        binding.videoView.setOnCompletionListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()

        binding.videoView.pause()

        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_CLOSE, R.anim.none, R.anim.activity_intro_end)
        } else {
            overridePendingTransition(R.anim.none, R.anim.activity_intro_end)
        }
    }
}