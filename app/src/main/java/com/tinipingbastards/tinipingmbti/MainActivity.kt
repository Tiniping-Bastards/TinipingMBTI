package com.tinipingbastards.tinipingmbti

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tinipingbastards.tinipingmbti.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var bgmHandler: BGMHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bgmHandler = TinipingApplication.bgmHandler

        // 시작하기 버튼 누르면 질문 창으로 이동
        binding.startBtn.setOnClickListener{
            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)

            bgmHandler.pause(R.raw.the_first_moment, 3000)
        }

        // 배경음악 재생
        bgmHandler.play(R.raw.the_first_moment, 100, 10000)
    }
}