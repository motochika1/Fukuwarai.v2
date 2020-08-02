package com.example.motochika.fukuwaraiv2

import com.google.mlkit.vision.face.FaceDetectorOptions

class FaceDetector {
    val realTimeOpts = FaceDetectorOptions.Builder()
        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
        .build()
}