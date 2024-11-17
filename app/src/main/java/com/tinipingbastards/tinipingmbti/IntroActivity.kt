package com.tinipingbastards.tinipingmbti

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tinipingbastards.tinipingmbti.databinding.ActivityIntroBinding
import com.tinipingbastards.tinipingmbti.databinding.ActivityMainBinding

class IntroActivity : AppCompatActivity() {
    private lateinit var binding : ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 인트로 비디오 재생
        binding.videoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.intro))
        binding.videoView.start()

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
}