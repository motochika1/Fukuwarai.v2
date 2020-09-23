package com.example.motochika.fukuwaraiv2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_entry.*
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

class EntryFragment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_entry)

        val actionBar = supportActionBar

        actionBar!!.hide()

        entry_okame_button.setOnClickListener {


            startActivity(Intent(this,OkameFaceFragment::class.java))
        }

        entry_hyottoko_button.setOnClickListener {


            startActivity(Intent(this,HyottokoFaceFragment::class.java))
        }

        license.setOnClickListener {

            startActivity(Intent(this, OssLicensesMenuActivity::class.java))
        }

        test_button.setOnClickListener {

            startActivity(Intent(this, MyFaceActivity::class.java))
        }

    }
}
