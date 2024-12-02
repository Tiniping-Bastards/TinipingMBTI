package com.tinipingbastards.tinipingmbti

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.math.MathUtils
import com.tinipingbastards.tinipingmbti.TinipingApplication.Companion.bgmManager
import com.tinipingbastards.tinipingmbti.databinding.ActivityResultSplashBinding
import java.util.Timer
import java.util.TimerTask

class SplashResultActivity : AppCompatActivity() {
    private lateinit var binding : ActivityResultSplashBinding
    private var cursor: Cursor? = null
    private lateinit var dbHelper: DBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbHelper = DBHelper(this)

        val result = intent.getStringExtra("RESULT")

        binding = ActivityResultSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cursor = dbHelper.loadDatabase().query(
            "result",
            arrayOf("type","sound"),  // 가져올 컬럼
            "type = ?",
            arrayOf(result),
            null,
            null,
            null
        )


        if (cursor != null && cursor?.moveToFirst() == true) {
            val tinipingSound = cursor?.getString(1)
            val tinipingSoundId = resources.getIdentifier(tinipingSound, "raw", packageName)

            bgmManager.load(tinipingSoundId)

            // StartLoading
            var loadingTime = 0L
            var loadingPer = 0
            var period = 50
            var minLoadingTime = 2000L

            val timer = Timer(true)
            val timerTask: TimerTask = object : TimerTask() {
                override fun run() {
                    loadingTime += period

                    // 로딩게이지 계산
                    loadingPer = (40f * bgmManager.isLoaded(tinipingSoundId)
                            + 60f * MathUtils.clamp(
                        loadingTime,
                        0L,
                        minLoadingTime
                    ) / minLoadingTime).toInt()

                    binding.loadingText.text = "$loadingPer%"

                    if (loadingPer >= 99) {
                        val intent = Intent(baseContext, ResultActivity::class.java)
                        intent.putExtra("RESULT", result)
                        startActivity(intent)

                        timer.cancel()
                        timer.purge()
                    }
                }
            }
            timer.schedule(timerTask, 0, 50)
        }
    }

//    override fun onPause() {
//        super.onPause()
//
//        // 화면전환 애니메이션 삭제
//        if (Build.VERSION.SDK_INT >= 34) {
//            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_CLOSE, R.anim.none, R.anim.activity_intro_end)
//        } else {
//            overridePendingTransition(R.anim.none, R.anim.activity_intro_end)
//        }
//
//    }
}