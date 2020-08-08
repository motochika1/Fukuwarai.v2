package com.example.motochika.fukuwaraiv2

import android.media.FaceDetector
import androidx.core.net.toUri
import com.google.mlkit.vision.common.InputImage

class FaceProcess {

    private val visionImage = VisionImage()
    private val inputImage = visionImage.imageFromPath(MyfaceActivity(), "/res/drawable/myface.jpg".toUri()) ?: null


    fun faceDetect(){
        val facedt = DetectFace()
        if (inputImage != null) {
            facedt.detectFaces(inputImage)
        }
    }


}