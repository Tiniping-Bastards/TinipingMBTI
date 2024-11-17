package com.tinipingbastards.tinipingmbti

import android.database.Cursor
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tinipingbastards.tinipingmbti.databinding.ActivityResultBinding


private lateinit var dbHelper: DBHelper

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private var cursor: Cursor? = null  // Cursor 객체를 클래스 레벨에서 선언

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityResultBinding.inflate(layoutInflater)  // ViewBinding 인플레이트
            setContentView(binding.root)

            // Intent로 전달된 MBTI 결과 가져오기
            val result = intent.getStringExtra("RESULT")

            val db = DBHelper(this).loadDatabase()

            cursor = db.rawQuery("Select * From result", null)

            cursor?.moveToFirst()

            // UI 출력
            binding.description.text = cursor?.getString(2)

    }
}