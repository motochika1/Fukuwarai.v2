package com.example.motochika.fukuwaraiv2

import android.graphics.Bitmap
import android.graphics.PointF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceContour
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import kotlinx.android.synthetic.main.activity_myface.*

//顔検出用のクラス
//読み込めるイメージの最小サイズは480*360px 顔画像は100*100px 輪郭検出は200*200px
//ランドマーク検出はオプショナル　オイラーY角度で検出される
//ランドマークのポイントはは配列に格納されている
//FaceDetectorOptions　オブジェクト:　faceDetector の初期設定ができる
class MyFaceActivity : AppCompatActivity() {

    lateinit var mImage: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myface)


    }

    fun detectFaces() {

        val faceImage = InputImage.fromBitmap(mImage, 0)
        //FaceDetectorの初期設定
        val options = FaceDetectorOptions.Builder()
            .setClassificationMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .setMinFaceSize(0.15f)
            .build()

        //FaceDetectorの生成
        val detector = FaceDetection.getClient(options)


        //取得した画像について設定した内容で検出
        detector.process(faceImage)
            .addOnSuccessListener {faces ->

                for (face in faces) {

                    val leftEyePos = face.getContour(FaceContour.LEFT_EYE)?.points as List<PointF>

                    val ightEyePos = face.getContour(FaceContour.RIGHT_EYE)?.points as List<PointF>

                    val upperLipPos = face.getContour(FaceContour.UPPER_LIP_TOP)?.points

                    val lowerLipPos = face.getContour(FaceContour.LOWER_LIP_BOTTOM)?.points

                }

            }
            .addOnFailureListener{

            }
    }

}