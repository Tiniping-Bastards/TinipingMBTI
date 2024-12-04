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
    private lateinit var imageHandler: ImageHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbHelper = DBHelper(this)

        val result = intent.getStringExtra("RESULT")

        binding = ActivityResultSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageList = listOf(
            R.drawable.enfj, R.drawable.enfp, R.drawable.entj, R.drawable.enfp, R.drawable.estj,
            R.drawable.entp, R.drawable.esfj, R.drawable.esfp, R.drawable.infj, R.drawable.infp,
            R.drawable.intj, R.drawable.intp, R.drawable.isfj, R.drawable.isfp, R.drawable.istj, R.drawable.istp
        )

        imageHandler = ImageHandler(binding.changImage, imageList, 100L)
        imageHandler.start()

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

    override fun onPause() {
        super.onPause()

        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_CLOSE, 0, 0)
        } else {
            overridePendingTransition(0, 0)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        imageHandler.stop()
    }
}