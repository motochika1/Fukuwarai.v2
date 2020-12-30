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

            val faceParts = mapOf<String, View>(
                "right-eye" to rightEye_image, "left-eye" to leftEye_image,
                "nose" to nose_image, "mouth" to mouth_image
            )
            val rootParts = mapOf<String, View>(
                "face" to hyottoko_face,
                "root" to hyottoko_root,
                "eye-brows" to eyebrows,
                "share-text" to share_text
            )
            val buttons = mapOf<String, Button>("play" to play_button, "play-again" to back_button)

            val rightEyeX = faceParts["right-eye"]?.x
            val rightEyeY = faceParts["right-eye"]?.y
            val leftEyeX = faceParts["left-eye"]?.x
            val leftEyeY = faceParts["left-eye"]?.y
            val noseX = faceParts["nose"]?.x
            val noseY = faceParts["nose"]?.y
            val mouthX = faceParts["mouth"]?.x
            val mouthY = faceParts["mouth"]?.y


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
            }

            buttons["play-again"]?.setOnClickListener {

                //元の状態に戻す
                if (rightEyeX != null) {
                    faceParts["right-eye"]?.x = rightEyeX
                }
                if (rightEyeY != null) {
                    faceParts["right-eye"]?.y = rightEyeY
                }
                if (leftEyeX != null) {
                    faceParts["left-eye"]?.x = leftEyeX
                }
                if (leftEyeY != null) {
                    faceParts["left-eye"]?.y = leftEyeY
                }
                if (noseX != null) {
                    faceParts["nose"]?.x = noseX
                }
                if (noseY != null) {
                    faceParts["nose"]?.y = noseY
                }
                if (mouthX != null) {
                    faceParts["mouth"]?.x = mouthX
                }
                if (mouthY != null) {
                    faceParts["mouth"]?.y = mouthY
                }

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

