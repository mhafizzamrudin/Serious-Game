package com.example.tugasakhir.View

import android.content.Intent
import android.os.Bundle
import com.example.tugasakhir.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class MainActivity : BaseActivity() {
    override fun getResourceLayout(): Int {
        return R.layout.activity_home
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        hideActionBar()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser

        //if(currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        //}
    }
}