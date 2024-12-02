package com.tinipingbastards.tinipingmbti

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.tinipingbastards.tinipingmbti.TinipingApplication.Companion.bgmManager
import com.tinipingbastards.tinipingmbti.TinipingApplication.Companion.sfxHandler
import com.tinipingbastards.tinipingmbti.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var bgmHandler: BGMHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 시작하기 버튼 누르면 효과음 + 질문 창으로 이동
        binding.startBtn.setOnClickListener{
            sfxHandler.playSFX(R.raw.button_click)

            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }

        // 로고 연타하면 크레딧 화면으로 넘어가게 하기
        binding.logo.setOnClickListener(object : MultipleClickListener() {
            override fun onClick(v: View?) {
                super.onClick(v)

                sfxHandler.playSFX(R.raw.button_click)

                binding.logo.startAnimation(AnimationUtils.loadAnimation(baseContext, R.anim.main_logo_click))
            }

            override fun onMultipleClick(v: View?, count: Int) {
                if (count == 5)
                {
                    val intent = Intent(baseContext, CreditActivity::class.java)
                    startActivity(intent)
                }
            }
        })

        // 시그니처 사운드 재생
        sfxHandler.playSFX(R.raw.tiniping_signature)

        // UI애니메이션 적용
        binding.logo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.main_logo_show))
        binding.startBtn.startAnimation(AnimationUtils.loadAnimation(this, R.anim.main_start_show))

//        // 배경음악 재생
//         bgmHandler = TinipingApplication.bgmHandler
//        bgmHandler.play(R.raw.the_first_moment, 100, 10000)
    }

    override fun onPause() {
        super.onPause()

        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, R.anim.slide_in_right, R.anim.slide_out_left)
        } else {
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}