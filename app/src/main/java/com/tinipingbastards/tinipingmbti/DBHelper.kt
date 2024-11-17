package com.tinipingbastards.tinipingmbti.data  // 패키지 경로

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val context = context

    companion object {
        private const val DATABASE_NAME = "mbti_text.db"  // 데이터베이스 파일 이름
        private const val DATABASE_PATH = "/data/data/com.tinipingbastards.tinipingmbti/databases/"  // 내부 저장소 경로
        private const val DATABASE_VERSION = 1  // 데이터베이스 버전
    }

    // 데이터베이스 복사 작업
    @Throws(IOException::class)
    private fun copyDatabase() {
        val dbFile = File(DATABASE_PATH + DATABASE_NAME)

        // 이미 데이터베이스가 존재하지 않으면 assets에서 복사
        if (!dbFile.exists()) {
            val inputStream: InputStream = context.assets.open(DATABASE_NAME)
            val outputStream: OutputStream = FileOutputStream(dbFile)

            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }

            outputStream.flush()
            outputStream.close()
            inputStream.close()

            Log.d("DBHelper", "Database copied to internal storage.")
        }
    }

    // 읽기 전용 데이터베이스 열기
    override fun getReadableDatabase(): SQLiteDatabase {
        try {
            copyDatabase()  // 앱 실행 시 데이터베이스 복사
        } catch (e: IOException) {
            throw RuntimeException("Failed to copy database", e)
        }
        return SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY)
    }

    // 쓰기 가능한 데이터베이스 열기
    override fun getWritableDatabase(): SQLiteDatabase {
        try {
            copyDatabase()  // 앱 실행 시 데이터베이스 복사
        } catch (e: IOException) {
            throw RuntimeException("Failed to copy database", e)
        }
        return SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE)
    }

    override fun onCreate(db: SQLiteDatabase?) {}
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
}
