package com.example.motochika.fukuwaraiv2

import android.media.FaceDetector
import androidx.core.net.toUri
import com.google.mlkit.vision.common.InputImage

class FaceProcess {

    val visionImage = VisionImage()
    val imputImage = visionImage.imageFromPath(MyfaceActivity(), "res/drawable/myface.jpg".toUri()) ?: null


    fun FaceDetect(){
        val facedt = DetectFace()
        if (imputImage != null) {
            facedt.detectFaces(imputImage)
        }
    }


}