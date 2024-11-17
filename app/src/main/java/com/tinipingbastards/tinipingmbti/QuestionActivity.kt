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
    private lateinit var binding : ActivityQuestionBinding

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        val cursor: Cursor? = dbHelper.readableDatabase.query(
            "questions",  // 테이블 이름
            arrayOf("question_text", "option_1", "option_2"),  // 가져올 컬럼
            null,  // 조건 (없음)
            null,  // 조건 값 (없음)
            null,  // 그룹화 (없음)
            null,  // 정렬 (없음)
            "RANDOM() LIMIT 1"  // 랜덤으로 한 질문만 불러옴
        )

        cursor?.moveToFirst()

        binding.tvQuestion.text = cursor?.getString(0)

//        cursor?.let {
//            if (it.moveToFirst()) {
//                val question = it.getString(it.getColumnIndex("question_text"))
//                val answer1 = it.getString(it.getColumnIndex("option_1"))
//                val answer2 = it.getString(it.getColumnIndex("option_2"))
//
//                tvQuestion.text = question_text
//                btnAnswer1.text = option_1
//                btnAnswer2.text = option_2
//
//
//
//                btnAnswer1.setOnClickListener {
//                    Toast.makeText(this, "답변 1 선택: $answer1", Toast.LENGTH_SHORT).show()
//                }
//
//                btnAnswer2.setOnClickListener {
//                    Toast.makeText(this, "답변 2 선택: $answer2", Toast.LENGTH_SHORT).show()
//                }
//            }
//            it.close()
//        }
    }
}
