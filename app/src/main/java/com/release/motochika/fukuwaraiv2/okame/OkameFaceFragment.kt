package com.release.motochika.fukuwaraiv2.okame


import android.os.Build
import android.os.Bundle
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

            val draggableParts = mapOf<String, View>(
                "right-eye" to rightEye_image, "left-eye" to leftEye_image,
                "nose" to nose_image, "mouth" to mouth_image
            )
            val undraggableParts = mapOf<String, View>(
                "face" to okame_face,
                "root" to okame_root,
                "eye-brows" to eyeBrows,
                "share-text" to share_text
            )
            val buttons = mapOf<String, Button>("play" to play_button, "play-again" to back_button)

            val rightEyeX = rightEye_image?.x
            val rightEyeY = rightEye_image?.y
            val leftEyeX = leftEye_image?.x
            val leftEyeY = leftEye_image?.y
            val noseX = nose_image?.x
            val noseY = nose_image?.y
            val mouthX = mouth_image?.x
            val mouthY = mouth_image?.y

            buttons["play"]?.setOnClickListener {

                count++
                state = count % 3

                when (state) {
                    1 -> clickHandler.playClicked(listener, draggableParts, buttons)
                    2 -> clickHandler.showFaceClicked(draggableParts, buttons)
                    0 -> clickHandler.shareClicked(
                        screenShots,
                        twitterShare,
                        draggableParts,
                        undraggableParts,
                        requireActivity(),
                        buttons,
                        "おかめの福笑い"
                    )
                }
            }

            buttons["play-again"]?.setOnClickListener {

                //元の状態に戻す
                if (rightEyeX != null) {
                    draggableParts["right-eye"]?.x = rightEyeX
                }
                if (rightEyeY != null) {
                    draggableParts["right-eye"]?.y = rightEyeY
                }
                if (leftEyeX != null) {
                    draggableParts["left-eye"]?.x = leftEyeX
                }
                if (leftEyeY != null) {
                    draggableParts["left-eye"]?.y = leftEyeY
                }
                if (noseX != null) {
                    draggableParts["nose"]?.x = noseX
                }
                if (noseY != null) {
                    draggableParts["nose"]?.y = noseY
                }
                if (mouthX != null) {
                    draggableParts["mouth"]?.x = mouthX
                }
                if (mouthY != null) {
                    draggableParts["mouth"]?.y = mouthY
                }

                listener.i = 0

                draggableParts.map { it.value.rotation = 0.toFloat() }

                (buttons["play"] ?: error("")).text = "あそぶ"
                (buttons["play-again"] ?: error("")).visibility = View.INVISIBLE
                count++
            }
        }
        return view
    }
}


