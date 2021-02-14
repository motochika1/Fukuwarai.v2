package com.release.motochika.fukuwaraiv2.hyottoko

import android.os.*
import android.view.*
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.release.motochika.fukuwaraiv2.*
import kotlinx.android.synthetic.main.fragment_hyottoko_face.*

@RequiresApi(Build.VERSION_CODES.O)
class HyottokoFaceFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_hyottoko_face, container, false)
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
                "face" to hyottoko_face,
                "root" to hyottoko_root,
                "eye-brows" to eyebrows,
                "share-text" to share_text
            )
            val buttons = mapOf<String, Button>("play" to play_button, "play-again" to back_button)

            val rightEyeX = draggableParts["right-eye"]?.x
            val rightEyeY = draggableParts["right-eye"]?.y
            val leftEyeX = draggableParts["left-eye"]?.x
            val leftEyeY = draggableParts["left-eye"]?.y
            val noseX = draggableParts["nose"]?.x
            val noseY = draggableParts["nose"]?.y
            val mouthX = draggableParts["mouth"]?.x
            val mouthY = draggableParts["mouth"]?.y


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
                        "ひょっとこの福笑い"
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

