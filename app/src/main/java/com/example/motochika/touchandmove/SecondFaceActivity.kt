package com.example.motochika.fukuwaraiv2

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_second_face.*

class SecondFaceActivity : AppCompatActivity() {

    var i = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_face)

        val actionBar = supportActionBar

        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val vibrationEffect = VibrationEffect.createOneShot(100, 1)

        actionBar!!.hide()

        var listener = View.OnTouchListener(function = { view, motionEvent ->



            if (motionEvent.action == MotionEvent.ACTION_MOVE) {


                vibrator.vibrate(vibrationEffect)
                view.x = motionEvent.rawX - view.width/2
                view.y = motionEvent.rawY - view.height/2



                Log.d("MainActivity","touched")

            }
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {

                i+=10

                view.rotation =i.toFloat()

                Log.d("MainActivity","rotated")


            }


            true

        })

        window.decorView.viewTreeObserver.addOnGlobalLayoutListener {

            val rightEyeX = rightEye_image.x
            val rightEyeY = rightEye_image.y
            val leftEyeX = leftEye_image.x
            val leftEyeY = leftEye_image.y
            val noseX = nose_image.x
            val noseY = nose_image.y
            val mouthX = mouth_image.x
            val mouthY = mouth_image.y



        changeFace_button.setOnClickListener {

            //画像を透明にしている
            rightEye_image.alpha = 0.0.toFloat()
            leftEye_image.alpha =  0.0.toFloat()
            nose_image.alpha = 0.0.toFloat()
            mouth_image.alpha =  0.0.toFloat()


            rightEye_image.setOnTouchListener(listener)
            leftEye_image.setOnTouchListener(listener)
            nose_image.setOnTouchListener(listener)
            mouth_image.setOnTouchListener(listener)

        }

        open_button.setOnClickListener {

            //元の透明度に戻している
            rightEye_image.alpha = 1.0.toFloat()
            leftEye_image.alpha =  1.0.toFloat()
            nose_image.alpha = 1.0.toFloat()
            mouth_image.alpha =  1.0.toFloat()

            rightEye_image.setOnTouchListener(null)
            leftEye_image.setOnTouchListener(null)
            nose_image.setOnTouchListener(null)
            mouth_image.setOnTouchListener(null)

        }

            defo_button.setOnClickListener {

                //元の位置に戻す
                rightEye_image.x = rightEyeX
                rightEye_image.y = rightEyeY
                leftEye_image.x = leftEyeX
                leftEye_image.y = leftEyeY
                nose_image.x = noseX
                nose_image.y = noseY
                mouth_image.x =  mouthX
                mouth_image.y = mouthY

            }

            back_button.setOnClickListener {

                //元の状態に戻す
                rightEye_image.x = rightEyeX
                rightEye_image.y = rightEyeY
                leftEye_image.x = leftEyeX
                leftEye_image.y = leftEyeY
                nose_image.x = noseX
                nose_image.y = noseY
                mouth_image.x =  mouthX
                mouth_image.y = mouthY


                i = 0

                rightEye_image.rotation = 0.toFloat()
                leftEye_image.rotation = 0.toFloat()
                nose_image.rotation = 0.toFloat()
                mouth_image.rotation = 0.toFloat()


            }

        }


    }
}
