package com.tinipingbastards.tinipingmbti

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tinipingbastards.tinipingmbti.TinipingApplication.Companion.bgmManager
import com.tinipingbastards.tinipingmbti.TinipingApplication.Companion.sfxHandler
import com.tinipingbastards.tinipingmbti.databinding.ActivityResultBinding



class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private var cursor: Cursor? = null
    private lateinit var dbHelper: DBHelper

    private var tinipingSoundId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        val result = intent.getStringExtra("RESULT")
        val scrollView = findViewById<ScrollView>(R.id.scrollView)
        val shareButton = findViewById<Button>(R.id.shareButton)


        sfxHandler.playSFX(R.raw.activity_changed)

        cursor = dbHelper.loadDatabase().query(
            "result",
            arrayOf("type","description", "path", "name", "fits", "unfits",
                    "fitsname", "unfitsname","oneline", "desc", "sound"),  // 가져올 컬럼
            "type = ?",
            arrayOf(result),
            null,
            null,
            null
        )

        val shareHelper = ShareHelper(
            context = this,
            scrollView = scrollView,
            authority = "${packageName}.fileprovider"
        )

        // DB에서 결과값 가져오고 보여주기
        if (cursor != null && cursor?.moveToFirst() == true) {

            binding.mbti.text = cursor?.getString(0)
            binding.description.text = cursor?.getString(1)
            binding.name.text = cursor?.getString(3)
            binding.fitsName.text = cursor?.getString(6)
            binding.unFitsName.text = cursor?.getString(7)
            binding.oneLine.text = cursor?.getString(8)
            binding.desc.text = cursor?.getString(9)

            val imageName = cursor?.getString(2)
            val imageFits = cursor?.getString(4)
            val imageUnFits = cursor?.getString(5)
            val tinipingSound = cursor?.getString(10)

            val imageResourceId = resources.getIdentifier(imageName, "drawable", packageName)
            val imageResourceId2 = resources.getIdentifier(imageFits, "drawable", packageName)
            val imageResourceId3 = resources.getIdentifier(imageUnFits, "drawable", packageName)

            tinipingSoundId = resources.getIdentifier(tinipingSound, "raw", packageName)

            if (tinipingSoundId != 0) {
                bgmManager.setVolume(R.raw.tiniping_100, 0.2f)

                bgmManager.seekTo(tinipingSoundId, 0)
                bgmManager.play(tinipingSoundId)
            } else {
                Toast.makeText(this, "데이터를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
            }

            if (imageResourceId != 0 && imageResourceId2 != 0 && imageResourceId3 != 0) {
                binding.mbtiImage.setImageResource(imageResourceId)
                binding.fair.setImageResource(imageResourceId2)
                binding.unfair.setImageResource(imageResourceId3)
            } else {
                binding.mbtiImage.setImageResource(R.drawable.enfp)
                binding.fair.setImageResource(R.drawable.enfp)
                binding.unfair.setImageResource(R.drawable.enfp)
            }
        } else {
            Toast.makeText(this, "데이터를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
        }

        cursor?.close()

        // 다시하기
        val retryButton = findViewById<Button>(R.id.returnButton)
        retryButton.setOnClickListener {
            finish()

           sfxHandler.playSFX(R.raw.button_click)
        }

        // 공유하기
        shareButton.setOnClickListener {
            shareHelper.captureAndShare()

            sfxHandler.playSFX(R.raw.button_click)
        }

    }

    override fun onResume() {
        super.onResume()

        binding.title.startAnimation(AnimationUtils.loadAnimation(this,R.anim.result_text_show1))
        binding.mbti.startAnimation(AnimationUtils.loadAnimation(this,R.anim.result_text_show1))
        binding.mbtiImage.startAnimation(AnimationUtils.loadAnimation(this,R.anim.result_text_show2))
        binding.name.startAnimation(AnimationUtils.loadAnimation(this,R.anim.result_text_show2))
        binding.oneLine.startAnimation((AnimationUtils.loadAnimation(this,R.anim.result_text_show3)))
        binding.desc.startAnimation((AnimationUtils.loadAnimation(this,R.anim.result_text_show3)))
    }

    override fun onPause() {
        super.onPause()

        bgmManager.pause(tinipingSoundId)
    }
}