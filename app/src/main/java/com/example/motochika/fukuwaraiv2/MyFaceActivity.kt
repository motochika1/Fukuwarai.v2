package com.example.motochika.fukuwaraiv2

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PointF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.motochika.fukuwaraiv2.related_ml.FaceContourGraphic
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myface)



        getButton.setOnClickListener {

            detectFace()
        }
    }

    private fun detectFace() {
        //assetフォルダに保存されている画像をBitmapに変換
        val image = getBitmapFromAsset(this, "myface.jpg")
        //ビットマップ画像をfaceImage型に変換
        val faceImage = image?.let { InputImage.fromBitmap(it, 0) }
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
                        //顔が検出されなかった場合エラーメッセージ表示
                        if(faces.size == 0){
                            Toast.makeText(applicationContext, "Image not found", Toast.LENGTH_SHORT).show()
                        }

                        //一度上に重ねるグラフィックをクリア
                        graphic_overlay.clear()

                    //検出された顔に対して
                    for (face in faces) {
                        //左目の座標(x,y)のリストを保持する
                        val leftEyePos = face.getContour(FaceContour.LEFT_EYE)?.points as List<PointF>
                        //右目の座標(x,y)のリストを保持する
                        val rightEyePos = face.getContour(FaceContour.RIGHT_EYE)?.points as List<PointF>
                        //上唇の座標(x,y)のリストを保持する
                        val upperLipPos = face.getContour(FaceContour.UPPER_LIP_TOP)?.points as List<PointF>
                        //下唇の座標(x,y)のリストを保持する
                        val lowerLipPos = face.getContour(FaceContour.LOWER_LIP_BOTTOM)?.points as List<PointF>

                        //座標に応じてgraphic_overlayにドットを描写するクラスを作成
                        var faceGraphic = FaceContourGraphic(graphic_overlay)
                        //graphic_overlayにドットを追加
                        graphic_overlay.add(faceGraphic)
                        //faceに対してドットを更新
                        faceGraphic.updateFace(face)
                    }


                }
                .addOnFailureListener{
                    getButton.isEnabled = true
                    it.stackTrace

                }
        }
    }

    private fun getBitmapFromAsset(context: Context, filePath: String): Bitmap? {

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