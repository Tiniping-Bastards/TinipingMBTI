package com.tinipingbastards.tinipingmbti

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

class ShareHelper(
    private val context: Context,
    private val scrollView: ScrollView,
    private val authority: String
){
    fun captureScrollView(): Bitmap {
        val scrollviewHeight = scrollView.getChildAt(0).height
        val bitmap = Bitmap.createBitmap(scrollView.width, scrollviewHeight, Bitmap.Config.ARGB_8888)
        val canvas = android.graphics.Canvas(bitmap)
        scrollView.draw(canvas)
        return bitmap
    }

    fun shareBitmap(bitmap: Bitmap) {
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()

        val file = File(cachePath, "shared_image.png")
        FileOutputStream(file).use { fos ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        }

        val fileUri: Uri = FileProvider.getUriForFile(context, authority, file)

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, fileUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(Intent.createChooser(shareIntent, "공유하기"))
    }

    fun captureAndShare() {
        val bitmap = captureScrollView()
        shareBitmap(bitmap)
    }
}
