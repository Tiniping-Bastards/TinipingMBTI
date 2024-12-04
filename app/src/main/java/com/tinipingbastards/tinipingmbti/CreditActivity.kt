package com.tinipingbastards.tinipingmbti

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tinipingbastards.tinipingmbti.databinding.ActivityCreditBinding
import com.tinipingbastards.tinipingmbti.databinding.ActivityMainBinding

class CreditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 박현수
        binding.phs.imageView.setImageResource(R.drawable.intp)
        binding.phs.nameText.text = "박현수"
        binding.phs.messageText.text = "잠자고 싶다"
        binding.phs.roleText.text = "인트로 | 디테일"

        // 서지훈
        binding.sjh.imageView.setImageResource(R.drawable.enfp)
        binding.sjh.nameText.text = "서지훈"
        binding.sjh.messageText.text = "즐거웠고 다시 보지 말자 티니핑~"
        binding.sjh.roleText.text = "질문창 | DB"

        // 민주원
        binding.mjw.imageView.setImageResource(R.drawable.istp)
        binding.mjw.nameText.text = "민주원"
        binding.mjw.messageText.text = "아휴~"
        binding.mjw.roleText.text = "결과창 | 공유기능"
    }
}