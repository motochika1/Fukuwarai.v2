package com.release.motochika.fukuwaraiv2.instructions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.release.motochika.fukuwaraiv2.R
import kotlinx.android.synthetic.main.fragment_how_to_play_1.view.*

class HowToPlay1Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_how_to_play_1, container, false)

        view.next_button_1.setOnClickListener {
            findNavController().navigate(R.id.action_howToPlayFragment_to_howToPlay2Fragment)
        }

        return view
    }
}