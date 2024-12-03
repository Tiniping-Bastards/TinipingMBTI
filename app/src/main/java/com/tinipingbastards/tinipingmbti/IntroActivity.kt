package com.tinipingbastards.tinipingmbti

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.tinipingbastards.tinipingmbti.TinipingApplication.Companion.bgmManager
import com.tinipingbastards.tinipingmbti.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    private lateinit var binding : ActivityIntroBinding

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.videoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.intro_video))

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

    override fun onResume() {
        super.onResume()

        // 동영상 처음부터
        binding.videoView.seekTo(0)
        binding.videoView.start()

        // 브금재생
        bgmManager.seekTo(R.raw.intro_bgm,0)
        bgmManager.setVolume(R.raw.intro_bgm, 0.8f)
        bgmManager.play(R.raw.intro_bgm)

        bgmManager.seekTo(R.raw.tiniping_100, 0)
        bgmManager.setVolume(R.raw.tiniping_100, 0.2f)
        bgmManager.play(R.raw.tiniping_100)

        // Skip 텍스트 3초 뒤에 활성화
        binding.skipText.startAnimation(AnimationUtils.loadAnimation(this, R.anim.intro_skip_show))
    }

    override fun onPause() {
        super.onPause()

        // 동영상 멈추기
        binding.videoView.pause()
        bgmManager.pause(R.raw.intro_bgm)

        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_CLOSE, 0, 0)
        } else {
            overridePendingTransition(0, 0)
        }
    }




}