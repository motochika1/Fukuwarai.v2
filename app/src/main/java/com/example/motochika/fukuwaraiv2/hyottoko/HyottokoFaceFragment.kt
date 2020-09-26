package com.example.motochika.fukuwaraiv2.hyottoko

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.motochika.fukuwaraiv2.R
import kotlinx.android.synthetic.main.fragment_hyottoko_face.*

@RequiresApi(Build.VERSION_CODES.O)
class HyottokoFaceFragment : Fragment() {



    private lateinit var viewModel: HyottokoFaceFragmentViewModel


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_hyottoko_face, container, false)
        val vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val vibrationEffect = VibrationEffect.createOneShot(1000, 1)

        viewModel = ViewModelProvider(this).get(HyottokoFaceFragmentViewModel::class.java)

        requireActivity().window.decorView.viewTreeObserver.addOnGlobalLayoutListener {

            val rightEyeX = rightEye_image.x
            val rightEyeY = rightEye_image.y
            val leftEyeX = leftEye_image.x
            val leftEyeY = leftEye_image.y
            val noseX = nose_image.x
            val noseY = nose_image.y
            val mouthX = mouth_image.x
            val mouthY = mouth_image.y



            changeFace_button.setOnClickListener {

                //画像を透明にしている
                rightEye_image.alpha = 0.0.toFloat()
                leftEye_image.alpha = 0.0.toFloat()
                nose_image.alpha = 0.0.toFloat()
                mouth_image.alpha = 0.0.toFloat()


                rightEye_image.setOnTouchListener(viewModel.listener)
                leftEye_image.setOnTouchListener(viewModel.listener)
                nose_image.setOnTouchListener(viewModel.listener)
                mouth_image.setOnTouchListener(viewModel.listener)

            }

            open_button.setOnClickListener {

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

            defo_button.setOnClickListener {

                //元の位置に戻す
                rightEye_image.x = rightEyeX
                rightEye_image.y = rightEyeY
                leftEye_image.x = leftEyeX
                leftEye_image.y = leftEyeY
                nose_image.x = noseX
                nose_image.y = noseY
                mouth_image.x = mouthX
                mouth_image.y = mouthY

            }

            back_button.setOnClickListener {

                //元の状態に戻す
                rightEye_image.x = rightEyeX
                rightEye_image.y = rightEyeY
                leftEye_image.x = leftEyeX
                leftEye_image.y = leftEyeY
                nose_image.x = noseX
                nose_image.y = noseY
                mouth_image.x = mouthX
                mouth_image.y = mouthY


                viewModel.i = 0

                rightEye_image.rotation = 0.toFloat()
                leftEye_image.rotation = 0.toFloat()
                nose_image.rotation = 0.toFloat()
                mouth_image.rotation = 0.toFloat()


            }
        }
        return view
    }

}

