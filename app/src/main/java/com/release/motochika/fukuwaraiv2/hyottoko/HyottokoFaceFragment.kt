package com.release.motochika.fukuwaraiv2.hyottoko

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.*
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.release.motochika.fukuwaraiv2.Listener
import com.release.motochika.fukuwaraiv2.R
import com.release.motochika.fukuwaraiv2.ScreenShots
import com.release.motochika.fukuwaraiv2.TwitterShare
import kotlinx.android.synthetic.main.fragment_hyottoko_face.*
import kotlinx.android.synthetic.main.fragment_hyottoko_face.back_button
import kotlinx.android.synthetic.main.fragment_hyottoko_face.changeFace_button
import kotlinx.android.synthetic.main.fragment_hyottoko_face.leftEye_image
import kotlinx.android.synthetic.main.fragment_hyottoko_face.mouth_image
import kotlinx.android.synthetic.main.fragment_hyottoko_face.nose_image
import kotlinx.android.synthetic.main.fragment_hyottoko_face.rightEye_image
import java.io.File
import java.io.FileOutputStream
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

@RequiresApi(Build.VERSION_CODES.O)
class HyottokoFaceFragment : Fragment() {

    private lateinit var root: View
    private lateinit var imageView: ImageView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_hyottoko_face, container, false)
        val screenShots = ScreenShots()
        val twitterShare = TwitterShare()
        var listener = Listener(requireActivity())
        var state: Int
        var count = 0

        requireActivity().window.decorView.viewTreeObserver.addOnGlobalLayoutListener {

            val faceParts = mapOf<String, View>("right-eye" to rightEye_image, "left-eye" to leftEye_image,
                "nose" to nose_image, "mouth" to mouth_image)

            val rootParts = mapOf<String, View>("face" to hyottoko_face, "root" to hyottoko_root, "eye-brows" to eyebrows)

            val rightEyeX = faceParts["right-eye"]?.x
            val rightEyeY = faceParts["right-eye"]?.y
            val leftEyeX = faceParts["left-eye"]?.x
            val leftEyeY = faceParts["left-eye"]?.y
            val noseX = faceParts["nose"]?.x
            val noseY = faceParts["nose"]?.y
            val mouthX = faceParts["mouth"]?.x
            val mouthY = faceParts["mouth"]?.y




            changeFace_button?.setOnClickListener {

                count++
                state = count % 3

                when(state) {

                    1 -> playClicked(listener, faceParts)
                    2 -> showFaceClicked(faceParts)
                    0 -> shareClicked(screenShots, twitterShare, faceParts, rootParts, requireActivity())
                }
            }

            back_button?.setOnClickListener {

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

                (faceParts["right-eye"] ?: error("")).rotation = 0.toFloat()
                (faceParts["left-eye"] ?: error("")).rotation = 0.toFloat()
                (faceParts["nose"] ?: error("")).rotation = 0.toFloat()
                (faceParts["mouth"] ?: error("")).rotation = 0.toFloat()

                changeFace_button.text = "あそぶ"
                back_button.visibility = View.INVISIBLE
                count++
            }

        }
        return view
    }


    private fun playClicked(listener: Listener, faceParts: Map<String, View>) {

        //画像を透明にしている

        faceParts.map {
            it.value.alpha = 0.0.toFloat()
            it.value.setOnTouchListener(listener.getListener())
        }

//        rightEye_image.alpha = 0.0.toFloat()
//        leftEye_image.alpha = 0.0.toFloat()
//        nose_image.alpha = 0.0.toFloat()
//        mouth_image.alpha = 0.0.toFloat()

//        rightEye_image.setOnTouchListener(listener.getListener())
//        leftEye_image.setOnTouchListener(listener.getListener())
//        nose_image.setOnTouchListener(listener.getListener())
//        mouth_image.setOnTouchListener(listener.getListener())

        changeFace_button.text = "公開"

    }

    private fun showFaceClicked(faceParts: Map<String, View>) {


        faceParts.map {
            it.value.alpha = 1.0.toFloat()
            it.value.setOnTouchListener(null)
        }

//        //元の透明度に戻している
//        rightEye_image.alpha = 1.0.toFloat()
//        leftEye_image.alpha = 1.0.toFloat()
//        nose_image.alpha = 1.0.toFloat()
//        mouth_image.alpha = 1.0.toFloat()

//        rightEye_image.setOnTouchListener(null)
//        leftEye_image.setOnTouchListener(null)
//        nose_image.setOnTouchListener(null)
//        mouth_image.setOnTouchListener(null)

        changeFace_button.text = "シェア"
        back_button.visibility = View.VISIBLE

    }


    private fun shareClicked(screenShots: ScreenShots, twitterShare: TwitterShare,
                               faceParts: Map<String, View>, rootParts: Map<String, View>, activity: Activity) {
        root = rootParts["root"] ?: error("")
        imageView = rootParts["face"] as ImageView
        back_button.visibility = View.INVISIBLE
        changeFace_button.visibility = View.INVISIBLE

        val bitmap: Bitmap = screenShots.takeScreenShotOfRootView(imageView)
        imageView.setImageBitmap(bitmap)
        root.setBackgroundColor(Color.parseColor("#999999"))

        (rootParts["eye-brows"] ?: error("")).visibility = View.INVISIBLE
        faceParts.map { it.value.visibility = View.INVISIBLE }

//        eyebrows.visibility = View.INVISIBLE
//        rightEye_image.visibility = View.INVISIBLE
//        leftEye_image.visibility = View.INVISIBLE
//        mouth_image.visibility = View.INVISIBLE
//        nose_image.visibility = View.INVISIBLE


        //screenShots.saveScreenShot(requireActivity(), bitmap)
        twitterShare.shareTwitter("Share",bitmap, activity)
    }

}

