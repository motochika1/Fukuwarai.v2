package com.example.motochika.fukuwaraiv2

import android.content.Context
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import java.io.IOException
import java.net.URI

//使用するイメージを持ってくる方法を記述したクラス
class VisionImage {

    fun imageFromPath(context: Context, uri: Uri): InputImage?{

        val image: InputImage
        return try {
            image = InputImage.fromFilePath(context, uri)
            image

        } catch (e: IOException){
            e.printStackTrace()
            null
        }
    }
}