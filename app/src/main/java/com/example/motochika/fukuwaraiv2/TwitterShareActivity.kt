package com.example.motochika.fukuwaraiv2

import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import com.google.android.play.core.internal.e
import kotlinx.android.synthetic.main.activity_twitter_share.*
import kotlinx.android.synthetic.main.fragment_entry.*
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class TwitterShareActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twitter_share)


            share_button.setOnClickListener {
                shareTwitter("Test")
            }
    }


    private fun shareTwitter(message: String) {
        val twitterIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, "This is a test")
            type = "image/plain"
        }

        val packageManager = packageManager
        val resolvedInfoList: List<ResolveInfo> = packageManager.queryIntentActivities(twitterIntent, PackageManager.MATCH_DEFAULT_ONLY)
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
            startActivity(Intent.createChooser(twitterIntent, "test"))
        } else {
           Intent().apply {
               putExtra(Intent.EXTRA_TEXT, message)
               action = Intent.ACTION_VIEW
               data = Uri.parse("https://twitter.com/intent/tweet?text=" + urlEncode(message))
           }

            startActivity(Intent.createChooser(twitterIntent, "test"))
            Toast.makeText(this, "TwitterApp is not found.", Toast.LENGTH_LONG).show()
        }

    }

    private fun urlEncode(url: String): String {

        return try {
            URLEncoder.encode(url, "UTF-8")

        } catch (e: UnsupportedEncodingException) {
            Log.w("TwitterShareActivity", "UTF-8 should always be supported", e)

            ""
        }
    }
}