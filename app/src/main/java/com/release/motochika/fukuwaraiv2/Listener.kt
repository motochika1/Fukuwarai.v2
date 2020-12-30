package com.release.motochika.fukuwaraiv2

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi

class Listener(activity: Activity) {

    var i = 0
    private val vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    @RequiresApi(Build.VERSION_CODES.O)
    val vibrationEffect: VibrationEffect = VibrationEffect.createOneShot(1000, 1)

    @RequiresApi(Build.VERSION_CODES.O)
    fun getListener() =
        View.OnTouchListener(function = { view, motionEvent ->


            if (motionEvent.action == MotionEvent.ACTION_MOVE) {


                view.x = motionEvent.rawX - view.width / 2
                view.y = motionEvent.rawY - view.height / 2



                Log.d("MainActivity", "touched")

            }
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {

                vibrator.vibrate(vibrationEffect)

                i += 10

                view.rotation = i.toFloat()

                Log.d("MainActivity", "rotated")


            }

            if (motionEvent.action == MotionEvent.ACTION_UP) {

                vibrator.cancel()

                Log.d("MainActivity", "canceled")

            }
            true

        })
}