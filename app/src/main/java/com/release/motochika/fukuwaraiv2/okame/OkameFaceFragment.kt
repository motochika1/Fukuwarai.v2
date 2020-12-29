package com.release.motochika.fukuwaraiv2.okame


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.release.motochika.fukuwaraiv2.*
import kotlinx.android.synthetic.main.fragment_okame_face.*

class OkameFaceFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_okame_face, container, false)
        val clickHandler = ClickHandler()
        val screenShots = ScreenShots()
        val twitterShare = TwitterShare()
        var listener = Listener(requireActivity())
        var state: Int
        var count = 0

        requireActivity().window.decorView.viewTreeObserver.addOnGlobalLayoutListener {

            val faceParts = mapOf<String, View>(
                "right-eye" to rightEye_image, "left-eye" to leftEye_image,
                "nose" to nose_image, "mouth" to mouth_image
            )
            val rootParts = mapOf<String, View>(
                "face" to okame_face,
                "root" to okame_root,
                "eye-brows" to eyeBrows
            )
            val buttons = mapOf<String, Button>("play" to play_button, "play-again" to back_button)


            buttons["play"]?.setOnClickListener {

                count++
                state = count % 3

                when (state) {
                    1 -> clickHandler.playClicked(listener, faceParts, buttons)
                    2 -> clickHandler.showFaceClicked(faceParts, buttons)
                    0 -> clickHandler.shareClicked(
                        screenShots,
                        twitterShare,
                        faceParts,
                        rootParts,
                        requireActivity(),
                        buttons
                    )
                }

                faceParts.forEach {
                    Log.d(it.key + "-x", it.value.x.toString())
                    Log.d(it.key + "-y", it.value.y.toString())
                }
            }

            buttons["play-again"]?.setOnClickListener {

                //元の状態に戻す
                faceParts["right-eye"]?.x = 284.0F
                faceParts["right-eye"]?.y = 816.0F
                faceParts["left-eye"]?.x = 536.0F
                faceParts["left-eye"]?.y = 816.0F
                faceParts["nose"]?.x = 376.0F
                faceParts["nose"]?.y = 921.0F
                faceParts["mouth"]?.x = 250.0F
                faceParts["mouth"]?.y = 1188.0F

                listener.i = 0

                faceParts.map { it.value.rotation = 0.toFloat() }

                (buttons["play"] ?: error("")).text = "あそぶ"
                (buttons["play-again"] ?: error("")).visibility = View.INVISIBLE
                count++
            }
        }
        return view
    }
}


