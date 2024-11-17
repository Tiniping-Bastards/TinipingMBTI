package com.tinipingbastards.tinipingmbti

import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tinipingbastards.tinipingmbti.DBHelper  // DBHelper 클래스 경로
import com.tinipingbastards.tinipingmbti.databinding.ActivityMainBinding
import com.tinipingbastards.tinipingmbti.databinding.ActivityQuestionBinding

class QuestionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionBinding
    private lateinit var dbHelper: DBHelper
    private var cursor: Cursor? = null  // Cursor 객체를 클래스 레벨에서 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        // DB에서 질문 데이터를 가져옵니다.
        cursor = dbHelper.readableDatabase.query(
            "questions",  // 테이블 이름
            arrayOf("question_text", "option_1", "option_2"),  // 가져올 컬럼
            null,  // 조건 (없음)
            null,  // 조건 값 (없음)
            null,  // 그룹화 (없음)
            null,  // 정렬 (없음)
            null  // 정렬 방법 없음
        )

        cursor?.moveToFirst()

        updateUI()

        binding.btnAnswer1.setOnClickListener {
            processAnswer(1)
        }

        binding.btnAnswer2.setOnClickListener {
            processAnswer(2)
        }
    }

    private fun updateUI() {
        if (cursor != null && !cursor!!.isAfterLast) {
            binding.tvQuestion.text = cursor?.getString(0)  // 질문 텍스트
            binding.btnAnswer1.text = cursor?.getString(1) // 옵션 1
            binding.btnAnswer2.text = cursor?.getString(2) // 옵션 2
        } else {
            Toast.makeText(this, "모든 질문이 끝났습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun processAnswer(selectedOption: Int) {
        Toast.makeText(this, "선택한 옵션: $selectedOption", Toast.LENGTH_SHORT).show()

        if (cursor?.moveToNext() == true) {
            updateUI()
        } else {
            Toast.makeText(this, "모든 질문이 끝났습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cursor?.close()
    }
}
