package com.example.motochika.fukuwaraiv2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_myface.*

class MyFaceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myface)



        val faceProcess = FaceProcess()

        getButton.setOnClickListener {

            val leftEyePos = faceProcess.faceDetect()
            leftEyeCo.text = leftEyePos.toString()
        }

    }

}