package com.example.motochika.fukuwaraiv2

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Path
import android.graphics.PointF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceContour
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import kotlinx.android.synthetic.main.activity_myface.*
import java.io.IOException

//顔検出用のクラス
//読み込めるイメージの最小サイズは480*360px 顔画像は100*100px 輪郭検出は200*200px
//ランドマーク検出はオプショナル　オイラーY角度で検出される
//ランドマークのポイントはは配列に格納されている
//FaceDetectorOptions　オブジェクト:　faceDetector の初期設定ができる
class MyFaceActivity : AppCompatActivity() {

    //assetフォルダに保存されている画像をBitmapに変換
    private val faceImage = getBitmapFromAsset(this, "myface.jpg")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myface)

        getButton.setOnClickListener {

            detectFace()
        }
    }

    private fun detectFace() {

        //ビットマップ画像をfaceImage型に変換
        val faceImage = faceImage?.let { InputImage.fromBitmap(it, 0) }
        //FaceDetectorの初期設定
        val options = FaceDetectorOptions.Builder()
            .setClassificationMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .setMinFaceSize(0.15f)
            .build()

        //FaceDetectorの生成
        val detector = FaceDetection.getClient(options)

        //取得中はボタン押せなくする
        getButton.isEnabled = false

        //取得した画像について設定した内容で検出
        if (faceImage != null) {
            detector.process(faceImage)
                .addOnSuccessListener {faces ->

                    getButton.isEnabled = true
                    for (face in faces) {

                        val leftEyePos = face.getContour(FaceContour.LEFT_EYE)?.points as List<PointF>

                        val rightEyePos = face.getContour(FaceContour.RIGHT_EYE)?.points as List<PointF>

                        val upperLipPos = face.getContour(FaceContour.UPPER_LIP_TOP)?.points as List<PointF>

                        val lowerLipPos = face.getContour(FaceContour.LOWER_LIP_BOTTOM)?.points as List<PointF>

                    }

                }
                .addOnFailureListener{
                    getButton.isEnabled = true
                    it.stackTrace

                }
        }
    }

    fun getBitmapFromAsset(context: Context, filePath: String): Bitmap? {

        val assetManager = context.assets
        var bitmap: Bitmap? = null

        try {
            val file = assetManager.open(filePath)
            bitmap = BitmapFactory.decodeStream(file)

        } catch (e:IOException) {
            e.printStackTrace()
        }

        return bitmap
    }

}