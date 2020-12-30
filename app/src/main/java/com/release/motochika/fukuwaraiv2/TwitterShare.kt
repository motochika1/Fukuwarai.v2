package com.release.motochika.fukuwaraiv2

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.UnsupportedEncodingException
import java.net.URLEncoder


class TwitterShare {

    fun shareTwitter(message: String, bitmapImage: Bitmap, activity: Activity, faceType: String) {
        val uri = bitmapImage.toUri(activity)
        val storeUrl = "https://play.google.com/store/apps/details?id=com.release.motochika.fukuwaraiv2"
        val twitterIntent = Intent(Intent.ACTION_SEND).apply {

            putExtra(Intent.EXTRA_TEXT, storeUrl)
            putExtra(Intent.EXTRA_TEXT, "$faceType $storeUrl \n\n #2021年 #お正月 #福笑い")
            type = "text/plain"
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "image/png"
        }

        val packageManager = activity.packageManager
        val resolvedInfoList: List<ResolveInfo> =
            packageManager.queryIntentActivities(twitterIntent, PackageManager.MATCH_DEFAULT_ONLY)
        var isResolved = false

        for (resolveInfo in resolvedInfoList) {
            if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")) {
                twitterIntent.setClassName(
                    resolveInfo.activityInfo.packageName,
                    resolveInfo.activityInfo.name
                )
                isResolved = true
                break
            }
        }

        if (isResolved) {
            activity.startActivity(Intent.createChooser(twitterIntent, "share"))
        } else {
            Intent().apply {
                putExtra(Intent.EXTRA_TEXT, message)
                action = Intent.ACTION_VIEW
                data = Uri.parse("https://twitter.com/intent/tweet?text=" + message.toUrl())
            }

            activity.startActivity(Intent.createChooser(twitterIntent, "share"))
            Toast.makeText(activity, "TwitterApp is not found.", Toast.LENGTH_LONG).show()
        }

    }

    private fun String.toUrl(): String {

        return try {
            URLEncoder.encode(this, "UTF-8")

        } catch (e: UnsupportedEncodingException) {
            Log.w("TwitterShareActivity", "UTF-8 should always be supported", e)

            ""
        }
    }

    private fun Bitmap.toUri(activity: Activity): Uri {

        val cacheDir: File = activity.cacheDir

        val fileName: String = System.currentTimeMillis().toString() + ".jpg"

        val file = File(cacheDir, fileName)

        val fileOutputStream = FileOutputStream(file)

        this.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
        fileOutputStream?.close()

        return FileProvider.getUriForFile(
            activity,
            "com.release.motochika.fukuwaraiv2.fileprovider",
            file
        )
    }
}