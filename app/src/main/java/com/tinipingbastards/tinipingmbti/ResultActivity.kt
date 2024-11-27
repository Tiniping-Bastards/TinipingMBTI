package com.tinipingbastards.tinipingmbti

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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


            cursor = dbHelper.loadDatabase().query(
                "result",
                arrayOf("type","description", "path", "name", "fits", "unfits", "fitsname", "unfitsname"),  // 가져올 컬럼
                "type = ?",
                arrayOf(result),
                null,
                null,
                null
        )

        if (cursor != null && cursor?.moveToFirst() == true) {

            binding.description.text = cursor?.getString(1)
            binding.name.text = cursor?.getString(3)
            binding.fitsName.text = cursor?.getString(6)
            binding.unFitsName.text = cursor?.getString(7)

            val imageName = cursor?.getString(2)
            val imageFits = cursor?.getString(4)
            val imageUnFits = cursor?.getString(5)

            val imageResourceId = resources.getIdentifier(imageName, "drawable", packageName)
            val imageResourceId2 = resources.getIdentifier(imageFits, "drawable", packageName)
            val imageResourceId3 = resources.getIdentifier(imageUnFits, "drawable", packageName)

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

        val retryButton = findViewById<Button>(R.id.returnButton)
        retryButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

            TinipingApplication.sfxHandler.playSFX(R.raw.button_click)
        }

    }
}