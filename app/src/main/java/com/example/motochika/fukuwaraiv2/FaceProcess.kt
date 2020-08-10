package com.example.motochika.fukuwaraiv2

import android.content.ContentResolver
import android.net.Uri
import androidx.core.net.toUri
import java.io.File

class FaceProcess {

    private val visionImage = VisionImage()
    private val inputImage2 = R.drawable.myface
    private val inputImage = visionImage.imageFromPath(MyFaceActivity(), Uri.fromFile())

    val facedt = DetectFace()

    fun faceDetect(){

        if (inputImage != null) {
            facedt.detectFaces(inputImage)
        }
    }

    fun getPos(facedt : DetectFace){


    }


}