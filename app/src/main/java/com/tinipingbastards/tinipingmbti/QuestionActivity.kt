package com.tinipingbastards.tinipingmbti

import android.content.Intent
import android.database.Cursor
import android.graphics.Typeface
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.tinipingbastards.tinipingmbti.databinding.ActivityQuestionBinding

class QuestionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionBinding
    private lateinit var dbHelper: DBHelper
    private var cursor: Cursor? = null  // Cursor 객체를 클래스 레벨에서 선언
    private var result: String = ""     // 최종 MBTI 결과를 저장할 변수
    private var currentQuestionIndex: Int = 0
    private var eCount: Int = 0         // "E" 선택 횟수
    private var iCount: Int = 0         // "I" 선택 횟수
    private var sCount: Int = 0         // "S" 선택 횟수
    private var nCount: Int = 0         // "N" 선택 횟수
    private var tCount: Int = 0         // "T" 선택 횟수
    private var fCount: Int = 0         // "F" 선택 횟수
    private var jCount: Int = 0         // "J" 선택 횟수
    private var pCount: Int = 0         // "P" 선택 횟수

    private lateinit var progressBar: ProgressBar

    private lateinit var questionNumberTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        progressBar = binding.progressBar

        questionNumberTextView = binding.tvQuestionNumber


        val customFont: Typeface? = ResourcesCompat.getFont(this, R.font.navi)
        binding.tvQuestion.typeface = customFont
        binding.tvQuestionNumber.typeface = customFont
        binding.btnAnswer1.typeface = customFont
        binding.btnAnswer2.typeface = customFont

        cursor = dbHelper.loadDatabase().query(
            "questions",  // 테이블 이름
            arrayOf("question_text","question_type", "option_1", "option_2"),  // 가져올 컬럼
            null,  // 조건 (없음)
            null,  // 조건 값 (없음)
            null,  // 그룹화 (없음)
            null,  // 정렬 (없음)
            null  // 정렬 방법 없음
        )

        cursor?.moveToFirst()

        updateUI()

        binding.btnAnswer1.setOnClickListener {
            TinipingApplication.sfxHandler.playSFX(R.raw.button_click)
            applyAnimations()
            processAnswer(1)
        }

        binding.btnAnswer2.setOnClickListener {
            TinipingApplication.sfxHandler.playSFX(R.raw.button_click)
            applyAnimations()
            processAnswer(2)
        }
    }


    private fun updateUI() {
        if (cursor != null && !cursor!!.isAfterLast) {
            binding.tvQuestion.text = cursor?.getString(0)  // 질문 텍스트
            binding.btnAnswer1.text = cursor?.getString(2) // 옵션 1
            binding.btnAnswer2.text = cursor?.getString(3) // 옵션 2

            updateProgressBar()
            updateQuestionNumberText()
        } else {
            finish()
        }
    }

    private fun updateProgressBar() {
        val totalQuestions = 12
        val progress = ((currentQuestionIndex ) * 100) / totalQuestions
        progressBar.progress = progress
    }

    private fun updateQuestionNumberText() {
        val totalQuestions = 12
        val questionText = "${currentQuestionIndex + 1}/$totalQuestions"
        questionNumberTextView.text = questionText
    }

    private fun processAnswer(selectedOption: Int) {
        val questionNumber = cursor?.position ?: return

        calculateAnswerResult(questionNumber + 1, selectedOption)

        currentQuestionIndex++

        if (cursor?.moveToNext() == true) {
            updateUI()
        } else {
            result = calculateResult()


            val intent = Intent(this, SplashResultActivity::class.java)
            intent.putExtra("RESULT", result)
            startActivity(intent)

        }
    }

    private fun applyAnimations() {
        val click: Animation = AnimationUtils.loadAnimation(this, R.anim.question_text)
        val click1: Animation = AnimationUtils.loadAnimation(this, R.anim.question_btn1_show)
        val click2: Animation = AnimationUtils.loadAnimation(this, R.anim.question_btn2_show)

        binding.tvQuestion.startAnimation(click)
        binding.btnAnswer1.startAnimation(click1)
        binding.btnAnswer2.startAnimation(click2)
    }


    private fun calculateAnswerResult(questionNumber: Int, selectedOption: Int) {
        // 각 질문에 해당하는 타입을 설정
        when (questionNumber) {
            in setOf(1,2,9) -> if (selectedOption == 1) eCount++ else iCount++
            in setOf(3,4,10) -> if (selectedOption == 1) sCount++ else nCount++
            in setOf(5,6,11) -> if (selectedOption == 1) tCount++ else fCount++
            in setOf(7,8,12) -> if (selectedOption == 1) jCount++ else pCount++
        }
    }

    private fun calculateResult(): String {
        // 각 지표에 대해 더 많이 선택된 항목을 결과로 출력
        val EOrI = if (eCount > iCount) "E" else "I"
        val SOrN = if (sCount > nCount) "S" else "N"
        val TOrF = if (tCount > fCount) "T" else "F"
        val JOrP = if (jCount > pCount) "J" else "P"

        return EOrI + SOrN + TOrF + JOrP
    }

    override fun onDestroy() {
        super.onDestroy()
        cursor?.close()
    }
}
