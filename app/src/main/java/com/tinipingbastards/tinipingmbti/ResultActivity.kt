package com.tinipingbastards.tinipingmbti

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tinipingbastards.tinipingmbti.databinding.ActivityResultBinding



class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private var cursor: Cursor? = null
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityResultBinding.inflate(layoutInflater)
            setContentView(binding.root)

            dbHelper = DBHelper(this)

            val result = intent.getStringExtra("RESULT")
            val scrollView = findViewById<ScrollView>(R.id.scrollView)
            val shareButton = findViewById<Button>(R.id.shareButton)


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
            val tinipingSoundId = resources.getIdentifier(tinipingSound, "raw", packageName)

            if (tinipingSoundId != 0) {
                TinipingApplication.bgmManager.load(tinipingSoundId)
                TinipingApplication.bgmManager.play(tinipingSoundId)
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
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

            TinipingApplication.sfxHandler.playSFX(R.raw.button_click)
        }

        // 공유하기
        shareButton.setOnClickListener {
            shareHelper.captureAndShare()

            TinipingApplication.sfxHandler.playSFX(R.raw.button_click)
        }
    }
}