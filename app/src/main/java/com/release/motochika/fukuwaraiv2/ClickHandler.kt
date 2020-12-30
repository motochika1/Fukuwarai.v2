package com.release.motochika.fukuwaraiv2

import android.animation.ObjectAnimator
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.fragment_hyottoko_face.*

class ClickHandler {


    private lateinit var root: View
    private lateinit var imageView: ImageView

    @RequiresApi(Build.VERSION_CODES.O)
    fun playClicked(
        listener: Listener,
        faceParts: Map<String, View>,
        buttons: Map<String, Button>
    ) {

        //画像を透明にしている

        faceParts.map {
            ObjectAnimator.ofFloat(it.value, "alpha", 1f, 0f).apply {
                duration = 200
                start()
            }
            it.value.setOnTouchListener(listener.getListener())
        }
        (buttons["play"] ?: error("")).text = "オープン！"
        ObjectAnimator.ofFloat(buttons["play-again"], View.TRANSLATION_X, 100f).apply {
            duration = 500
            start()
        }
    }

    fun showFaceClicked(faceParts: Map<String, View>, buttons: Map<String, Button>) {


        faceParts.map {
            ObjectAnimator.ofFloat(it.value, "alpha", 1f).apply {
                duration = 500
                start()
            }
            it.value.setOnTouchListener(null)
        }

        (buttons["play"] ?: error("")).text = "Twitterにシェア"

        (buttons["play-again"] ?: error("")).visibility = View.VISIBLE

        ObjectAnimator.ofFloat(buttons["play-again"], View.TRANSLATION_X, -10f).apply {
            duration = 500
            start()
        }

    }

    fun shareClicked(
        screenShots: ScreenShots,
        twitterShare: TwitterShare,
        faceParts: Map<String, View>,
        rootParts: Map<String, View>,
        activity: Activity,
        buttons: Map<String, Button>
    ) {
        root = rootParts["root"] ?: error("")
        imageView = rootParts["face"] as ImageView
        (buttons["play"] ?: error("")).visibility = View.INVISIBLE
        (buttons["play-again"] ?: error("")).visibility = View.INVISIBLE

        val bitmap: Bitmap = screenShots.takeScreenShotOfRootView(imageView)
        imageView.setImageBitmap(bitmap)
        root.setBackgroundColor(Color.parseColor("#999999"))

        (rootParts["eye-brows"] ?: error("")).visibility = View.INVISIBLE
        faceParts.map { it.value.visibility = View.INVISIBLE }

        twitterShare.shareTwitter("Share", bitmap, activity)
    }
}