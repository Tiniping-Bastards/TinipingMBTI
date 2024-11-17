package com.tinipingbastards.tinipingmbti

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tinipingbastards.tinipingmbti.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private lateinit var mediaPlayer : MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 시작하기 버튼 누르면 질문 창으로 이동
        binding.startBtn.setOnClickListener{
            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }

        // 배경음악 재생
        mediaPlayer = MediaPlayer.create(this, R.raw.the_first_moment)

        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }
}