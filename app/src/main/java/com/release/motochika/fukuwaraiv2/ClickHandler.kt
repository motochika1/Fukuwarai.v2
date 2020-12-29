package com.release.motochika.fukuwaraiv2

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.fragment_hyottoko_face.*

class ClickHandler {


    private lateinit var root: View
    private lateinit var imageView: ImageView

    @RequiresApi(Build.VERSION_CODES.O)
    fun playClicked(listener: Listener, faceParts: Map<String, View>, buttons: Map<String, Button>) {

        //画像を透明にしている

        faceParts.map {
            it.value.alpha = 0.0.toFloat()
            it.value.setOnTouchListener(listener.getListener())
        }

//        rightEye_image.alpha = 0.0.toFloat()
//        leftEye_image.alpha = 0.0.toFloat()
//        nose_image.alpha = 0.0.toFloat()
//        mouth_image.alpha = 0.0.toFloat()

//        rightEye_image.setOnTouchListener(listener.getListener())
//        leftEye_image.setOnTouchListener(listener.getListener())
//        nose_image.setOnTouchListener(listener.getListener())
//        mouth_image.setOnTouchListener(listener.getListener())

        (buttons["play"] ?: error("")).text = "公開"

    }

    fun showFaceClicked(faceParts: Map<String, View>, buttons: Map<String, Button>) {


        faceParts.map {
            it.value.alpha = 1.0.toFloat()
            it.value.setOnTouchListener(null)
        }

//        //元の透明度に戻している
//        rightEye_image.alpha = 1.0.toFloat()
//        leftEye_image.alpha = 1.0.toFloat()
//        nose_image.alpha = 1.0.toFloat()
//        mouth_image.alpha = 1.0.toFloat()

//        rightEye_image.setOnTouchListener(null)
//        leftEye_image.setOnTouchListener(null)
//        nose_image.setOnTouchListener(null)
//        mouth_image.setOnTouchListener(null)

        (buttons["play"] ?: error("")).text = "シェア"
        (buttons["play-again"] ?: error("")).visibility = View.VISIBLE

    }

    fun shareClicked(screenShots: ScreenShots, twitterShare: TwitterShare,
                             faceParts: Map<String, View>, rootParts: Map<String, View>, activity: Activity, buttons: Map<String, Button>
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

//        eyebrows.visibility = View.INVISIBLE
//        rightEye_image.visibility = View.INVISIBLE
//        leftEye_image.visibility = View.INVISIBLE
//        mouth_image.visibility = View.INVISIBLE
//        nose_image.visibility = View.INVISIBLE


        //screenShots.saveScreenShot(requireActivity(), bitmap)
        twitterShare.shareTwitter("Share",bitmap, activity)
    }



}