package com.example.motochika.fukuwaraiv2

import android.util.Log
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

class FaceProcessTest {

    @Test
    void testFaceDetect() {

        val visionImage = VisionImage()
        val inputImage = visionImage.imageFromPath(MyfaceActivity(), "/res/drawable/myface.jpg".toUri()) ?: null

        val facedt = DetectFace()
        if (inputImage != null) {
            facedt.detectFaces(inputImage)
        }
    }
}
