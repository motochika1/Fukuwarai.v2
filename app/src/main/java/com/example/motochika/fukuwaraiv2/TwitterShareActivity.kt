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

            }
    }



}