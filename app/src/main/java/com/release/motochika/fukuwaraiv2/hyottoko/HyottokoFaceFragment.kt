package com.release.motochika.fukuwaraiv2.hyottoko

import android.os.*
import android.util.Log
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
                "eye-brows" to eyebrows
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
                faceParts["right-eye"]?.x = 189.0F
                faceParts["right-eye"]?.y = 783.0F
                faceParts["left-eye"]?.x = 510.0F
                faceParts["left-eye"]?.y = 783.0F
                faceParts["nose"]?.x = 341.0F
                faceParts["nose"]?.y = 870.0F
                faceParts["mouth"]?.x = 297.0F
                faceParts["mouth"]?.y = 1137.0F

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

