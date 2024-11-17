package com.tinipingbastards.tinipingmbti

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tinipingbastards.tinipingmbti.databinding.ActivityResultBinding



class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private var cursor: Cursor? = null  // Cursor 객체를 클래스 레벨에서 선언
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityResultBinding.inflate(layoutInflater)  // ViewBinding 인플레이트
            setContentView(binding.root)

            dbHelper = DBHelper(this)

            // Intent로 전달된 MBTI 결과 가져오기
            val result = intent.getStringExtra("RESULT")

            val db = DBHelper(this).loadDatabase()

            cursor = dbHelper.loadDatabase().query(
                "result",  // 테이블 이름
                arrayOf("type","description", "path"),  // 가져올 컬럼
                "type = ?",
                arrayOf(result),
                null,  // 그룹화 (없음)
                null,  // 정렬 (없음)
                null  // 정렬 방법 없음
        )

        if (cursor != null && cursor?.moveToFirst() == true) {
            // UI 업데이트
            binding.mbtiInfo.text = cursor?.getString(0)  // 첫 번째 컬럼: type
            binding.description.text = cursor?.getString(1)  // 두 번째 컬럼: description

            // 이미지 파일명 가져오기
            val imageName = cursor?.getString(2)  // 세 번째 컬럼: path

            // 이미지 파일명에 해당하는 리소스를 drawable에서 가져오기
            val imageResourceId = resources.getIdentifier(imageName, "drawable", packageName)
            if (imageResourceId != 0) {
                // 이미지 리소스를 ImageView에 설정
                binding.mbtiImage.setImageResource(imageResourceId)
            } else {
                // 이미지가 없는 경우 대체 이미지 설정
                binding.mbtiImage.setImageResource(R.drawable.enfp)
            }
        } else {
            // 데이터가 없을 경우 처리
            Toast.makeText(this, "데이터를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
        }

        // 커서 닫기
        cursor?.close()

    }
}