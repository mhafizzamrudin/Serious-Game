package com.example.tugasakhir.View

import android.os.Bundle
import com.example.tugasakhir.R
import kotlinx.android.synthetic.main.activity_login2.*

class MainActivity : BaseActivity() {
    override fun getResourceLayout(): Int {
        return R.layout.activity_login2
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        hideActionBar()

        button_login.setOnClickListener {

        }

        button_registrasi.setOnClickListener {

        }
    }


}