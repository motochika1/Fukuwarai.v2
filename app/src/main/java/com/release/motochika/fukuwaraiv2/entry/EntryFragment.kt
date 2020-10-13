package com.release.motochika.fukuwaraiv2.entry

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.release.motochika.fukuwaraiv2.R
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import kotlinx.android.synthetic.main.fragment_entry.*
import kotlinx.android.synthetic.main.fragment_entry.view.*

class EntryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_entry, container, false)

        view.entry_okame_button.setOnClickListener {

            findNavController().navigate(R.id.action_entryFragment_to_firstFaceAFragment)

        }

        view.entry_hyottoko_button.setOnClickListener {

            findNavController().navigate(R.id.action_entryFragment_to_secondFaceFragment)
        }

        view.howTo_play_button.setOnClickListener {

            findNavController().navigate(R.id.action_entryFragment_to_howToPlayFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        license.setOnClickListener {

            startActivity(Intent(activity, OssLicensesMenuActivity::class.java))
        }

    }
}
