package com.example.motochika.fukuwaraiv2.how

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.motochika.fukuwaraiv2.R
import kotlinx.android.synthetic.main.fragment_how_to_play_1.view.*
import kotlinx.android.synthetic.main.fragment_how_to_play_1.view.back_button_1
import kotlinx.android.synthetic.main.fragment_how_to_play_1.view.next_button_1
import kotlinx.android.synthetic.main.fragment_how_to_play_2.view.*

class HowToPlay2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_how_to_play_2, container, false)

        view.back_button_2.setOnClickListener {

            findNavController().navigate(R.id.action_howToPlay2Fragment_to_howToPlayFragment)
        }

        view.next_button_2.setOnClickListener {
            findNavController().navigate(R.id.action_howToPlay2Fragment_to_howToPlay3Fragment)
        }


        return view
    }
}