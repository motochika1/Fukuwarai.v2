package com.example.motochika.fukuwaraiv2

import android.content.Intent
import android.graphics.Point
import android.graphics.PointF
import android.media.Image
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.*

//顔検出用のクラス
//読み込めるイメージの最小サイズは480*360px 顔画像は100*100px 輪郭検出は200*200px
//ランドマーク検出はオプショナル　オイラーY角度で検出される
//ランドマークのポイントはは配列に格納されている
//FaceDetectorOptions　オブジェクト:　faceDetector の初期設定ができる

class DetectFace {

    lateinit var leftEyePos: List<PointF>
    lateinit var rightEyePos: List<PointF>

    fun detectFaces(image: InputImage): List<PointF>{

        //FaceDetectorの初期設定
        val options = FaceDetectorOptions.Builder()
            .setClassificationMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .setMinFaceSize(0.15f)
            .build()

        //FaceDetectorの生成
        val detector = FaceDetection.getClient(options)


        //取得した画像について設定した内容で検出
        val result = detector.process(image)
            .addOnSuccessListener {faces ->

                for (face in faces) {

                    leftEyePos = face.getContour(FaceContour.LEFT_EYE)?.points as List<PointF>

                    rightEyePos = face.getContour(FaceContour.RIGHT_EYE)?.points as List<PointF>

                    val upperLipPos = face.getContour(FaceContour.UPPER_LIP_TOP)?.points

                    val lowerLipPos = face.getContour(FaceContour.LOWER_LIP_BOTTOM)?.points

                }



            }
            .addOnFailureListener{

            }
        return leftEyePos
    }
}