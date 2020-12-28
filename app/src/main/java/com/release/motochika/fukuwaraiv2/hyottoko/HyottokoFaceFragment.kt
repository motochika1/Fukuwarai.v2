package com.release.motochika.fukuwaraiv2.hyottoko

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
import kotlinx.android.synthetic.main.fragment_hyottoko_face.open_button
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

        requireActivity().window.decorView.viewTreeObserver.addOnGlobalLayoutListener {

            val rightEyeX = rightEye_image?.x
            val rightEyeY = rightEye_image?.y
            val leftEyeX = leftEye_image?.x
            val leftEyeY = leftEye_image?.y
            val noseX = nose_image?.x
            val noseY = nose_image?.y
            val mouthX = mouth_image?.x
            val mouthY = mouth_image?.y

            changeFace_button?.setOnClickListener {

                //画像を透明にしている
                rightEye_image.alpha = 0.0.toFloat()
                leftEye_image.alpha = 0.0.toFloat()
                nose_image.alpha = 0.0.toFloat()
                mouth_image.alpha = 0.0.toFloat()

                rightEye_image.setOnTouchListener(listener.getListener())
                leftEye_image.setOnTouchListener(listener.getListener())
                nose_image.setOnTouchListener(listener.getListener())
                mouth_image.setOnTouchListener(listener.getListener())
            }

            open_button?.setOnClickListener {

                //元の透明度に戻している
                rightEye_image.alpha = 1.0.toFloat()
                leftEye_image.alpha = 1.0.toFloat()
                nose_image.alpha = 1.0.toFloat()
                mouth_image.alpha = 1.0.toFloat()

                rightEye_image.setOnTouchListener(null)
                leftEye_image.setOnTouchListener(null)
                nose_image.setOnTouchListener(null)
                mouth_image.setOnTouchListener(null)
            }

            back_button?.setOnClickListener {

                //元の状態に戻す
                if (rightEyeX != null) {
                    rightEye_image.x = rightEyeX
                }
                if (rightEyeY != null) {
                    rightEye_image.y = rightEyeY
                }
                if (leftEyeX != null) {
                    leftEye_image.x = leftEyeX
                }
                if (leftEyeY != null) {
                    leftEye_image.y = leftEyeY
                }
                if (noseX != null) {
                    nose_image.x = noseX
                }
                if (noseY != null) {
                    nose_image.y = noseY
                }
                if (mouthX != null) {
                    mouth_image.x = mouthX
                }
                if (mouthY != null) {
                    mouth_image.y = mouthY
                }

                listener.i = 0

                rightEye_image.rotation = 0.toFloat()
                leftEye_image.rotation = 0.toFloat()
                nose_image.rotation = 0.toFloat()
                mouth_image.rotation = 0.toFloat()
            }

            share_button?.setOnClickListener {
                root = hyottoko_root
                imageView = hyottoko_face
                back_button.visibility = View.INVISIBLE
                changeFace_button.visibility = View.INVISIBLE
                open_button.visibility = View.INVISIBLE
                share_button.visibility = View.INVISIBLE

                val bitmap: Bitmap = screenShots.takeScreenShotOfRootView(imageView)
                imageView.setImageBitmap(bitmap)
                root.setBackgroundColor(Color.parseColor("#999999"))

                eyebrows.visibility = View.INVISIBLE
                rightEye_image.visibility = View.INVISIBLE
                leftEye_image.visibility = View.INVISIBLE
                mouth_image.visibility = View.INVISIBLE
                nose_image.visibility = View.INVISIBLE


                //screenShots.saveScreenShot(requireActivity(), bitmap)
                twitterShare.shareTwitter("Share",bitmap, requireActivity())
            }
        }
        return view
    }
}

