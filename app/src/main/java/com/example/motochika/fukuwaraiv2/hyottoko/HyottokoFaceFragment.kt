package com.example.motochika.fukuwaraiv2.hyottoko

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
import com.example.motochika.fukuwaraiv2.R
import com.example.motochika.fukuwaraiv2.ScreenShots
import kotlinx.android.synthetic.main.fragment_hyottoko_face.*
import kotlinx.android.synthetic.main.fragment_hyottoko_face.back_button
import kotlinx.android.synthetic.main.fragment_hyottoko_face.changeFace_button
import kotlinx.android.synthetic.main.fragment_hyottoko_face.defo_button
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
        val vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val vibrationEffect = VibrationEffect.createOneShot(1000, 1)

        val screenShots = ScreenShots()

        var i = 0

        var listener = View.OnTouchListener(function = { view, motionEvent ->


            if (motionEvent.action == MotionEvent.ACTION_MOVE) {


                view.x = motionEvent.rawX - view.width / 2
                view.y = motionEvent.rawY - view.height / 2



                Log.d("MainActivity", "touched")

            }
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {

                vibrator.vibrate(vibrationEffect)

                i += 10

                view.rotation = i.toFloat()

                Log.d("MainActivity", "rotated")


            }

            if (motionEvent.action == MotionEvent.ACTION_UP) {

                vibrator.cancel()

                Log.d("MainActivity", "canceled")


            }



            true

        })



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

                rightEye_image.setOnTouchListener(listener)
                leftEye_image.setOnTouchListener(listener)
                nose_image.setOnTouchListener(listener)
                mouth_image.setOnTouchListener(listener)

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

            defo_button?.setOnClickListener {

                //元の位置に戻す
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


                i = 0

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
                defo_button.visibility = View.INVISIBLE
                share_button.visibility = View.INVISIBLE

                val bitmap: Bitmap = screenShots.takeScreenShotOfRootView(imageView)
                imageView.setImageBitmap(bitmap)
                root.setBackgroundColor(Color.parseColor("#999999"))

                eyebrows.visibility = View.INVISIBLE
                rightEye_image.visibility = View.INVISIBLE
                leftEye_image.visibility = View.INVISIBLE
                mouth_image.visibility = View.INVISIBLE
                nose_image.visibility = View.INVISIBLE


                screenShots.saveScreenShot(requireActivity(), bitmap)
                shareTwitter("Test",bitmap)


                //findNavController().navigate(R.id.action_secondFaceFragment_to_resultFragment)
            }

        }


        return view
    }

    private fun shareTwitter(message: String, bitmapImage: Bitmap) {
        val uri = bitmapImage.bitmapToUri()
        val twitterIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_STREAM,  uri)
            type = "image/png"
        }

        val packageManager = requireActivity().packageManager
        val resolvedInfoList: List<ResolveInfo> = packageManager.queryIntentActivities(twitterIntent, PackageManager.MATCH_DEFAULT_ONLY)
        var isResolved = false

        for (resolveInfo in resolvedInfoList) {
            if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")) {
                twitterIntent.setClassName(
                    resolveInfo.activityInfo.packageName,
                    resolveInfo.activityInfo.name
                )
                isResolved = true
                break
            }
        }

        if (isResolved) {
            startActivity(Intent.createChooser(twitterIntent, "test"))
        } else {
            Intent().apply {
                putExtra(Intent.EXTRA_TEXT, message)
                action = Intent.ACTION_VIEW
                data = Uri.parse("https://twitter.com/intent/tweet?text=" + urlEncode(message))
            }

            startActivity(Intent.createChooser(twitterIntent, "test"))
            Toast.makeText(requireActivity(), "TwitterApp is not found.", Toast.LENGTH_LONG).show()
        }

    }

    private fun urlEncode(url: String): String {

        return try {
            URLEncoder.encode(url, "UTF-8")

        } catch (e: UnsupportedEncodingException) {
            Log.w("TwitterShareActivity", "UTF-8 should always be supported", e)

            ""
        }
    }

    private fun Bitmap.bitmapToUri():Uri {

        val cacheDir: File = requireActivity().cacheDir

        val fileName: String = System.currentTimeMillis().toString() + ".jpg"

        val file = File(cacheDir, fileName)

        val fileOutputStream = FileOutputStream(file)

        this.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
        fileOutputStream?.close()

        return FileProvider.getUriForFile(requireActivity(), "com.example.motochika.fukuwaraiv2.fileprovider", file)
    }


}

