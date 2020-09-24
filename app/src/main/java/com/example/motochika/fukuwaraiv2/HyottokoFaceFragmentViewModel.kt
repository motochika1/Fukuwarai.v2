package com.example.motochika.fukuwaraiv2

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.ViewModel

class HyottokoFaceFragmentViewModel : ViewModel() {

    var i = 0

    var listener = View.OnTouchListener(function = { view, motionEvent ->


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