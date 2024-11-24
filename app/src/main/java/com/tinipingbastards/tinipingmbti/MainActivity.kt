package com.tinipingbastards.tinipingmbti

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tinipingbastards.tinipingmbti.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var bgmHandler: BGMHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 시그니처 사운드 재생
        TinipingApplication.sfxHandler.playSFX(R.raw.tiniping_signature)

        // 시작하기 버튼 누르면 효과음 + 질문 창으로 이동
        binding.startBtn.setOnClickListener{
            TinipingApplication.sfxHandler.playSFX(R.raw.button_click)

            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }

        // 로고 연타하면 크레딧 화면으로 넘어가게 하기
        binding.logo.setOnClickListener(object : MultipleClickListener() {
            override fun onClick(v: View?) {
                super.onClick(v)

                Log.d("asdf", "Click")

                TinipingApplication.sfxHandler.playSFX(R.raw.button_click)
            }

            override fun onMultipleClick(v: View?, count: Int) {
                if (count == 5)
                {
                    val intent = Intent(baseContext, CreditActivity::class.java)
                    startActivity(intent)
                }
            }
        })

//        // 배경음악 재생
//         bgmHandler = TinipingApplication.bgmHandler
//        bgmHandler.play(R.raw.the_first_moment, 100, 10000)
    }
}