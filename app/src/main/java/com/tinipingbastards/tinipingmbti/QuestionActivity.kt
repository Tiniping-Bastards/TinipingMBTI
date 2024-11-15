package com.tinipingbastards.tinipingmbti

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuestionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_question)

        val tvQuestion: TextView = findViewById(R.id.tvQuestion)
        val btnAnswer1: Button = findViewById(R.id.btnAnswer1)
        val btnAnswer2: Button = findViewById(R.id.btnAnswer2)

        val question = "질문 1"
        val answer1 = "답변 1"
        val answer2 = "답변 2"

        tvQuestion.text = question
        btnAnswer1.text = answer1
        btnAnswer2.text = answer2

        btnAnswer1.setOnClickListener {
            Toast.makeText(this, "답변 1 선택: $answer1", Toast.LENGTH_SHORT).show()
        }

        btnAnswer2.setOnClickListener {
            Toast.makeText(this, "답변 2 선택: $answer2", Toast.LENGTH_SHORT).show()
        }
    }
}
