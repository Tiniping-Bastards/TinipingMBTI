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

            dbHelper = DBHelper(this)

            cursor = dbHelper.loadDatabase().query(
            "mbti_info",  // 테이블 이름
            arrayOf("type", "description", "path"),  // 가져올 컬럼
            null,  // 조건 (없음)
            null,  // 조건 값 (없음)
            null,  // 그룹화 (없음)
            null,  // 정렬 (없음)
            null  // 정렬 방법 없음
        )

            // Intent로 전달된 MBTI 결과 가져오기
            val result = intent.getStringExtra("result") ?: return

            Toast.makeText(baseContext, result, Toast.LENGTH_LONG).show()

            // DB에서 MBTI 정보 가져오기
//            val mbtiInfo = dbHelper.getMBTIInfo(result)
//
//            val imagePath = dbHelper


            // UI에 데이터 표시
//            if (mbtiInfo != null) {
//                binding.mbtiInfo.text = mbtiInfo.type  // ViewBinding을 사용하여 텍스트 설정
//                binding.description.text = mbtiInfo.description
//                binding
//
//            } else {
//                binding.mbtiInfo.text = "결과를 찾을 수 없습니다."
//            }
    }
}