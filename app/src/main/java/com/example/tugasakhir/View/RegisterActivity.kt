package com.example.tugasakhir.View

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import com.example.tugasakhir.Model.User
import com.example.tugasakhir.R
import com.example.tugasakhir.Repository.UserRepository
import com.example.tugasakhir.ViewModel.UserViewModel
import kotlinx.android.synthetic.main.activity_registrasi.*

class RegisterActivity : BaseActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var dialog : ProgressDialog

    override fun getResourceLayout(): Int {
        return R.layout.activity_registrasi
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        setTitle("Daftar Akun")
        showBackButton()

        btn_berikutnya.setOnClickListener {
            val intent = Intent(this, RegisterAvatarActivity::class.java)
            intent.putExtra("name", txt_nama.text.toString())
            intent.putExtra("email", txt_email.text.toString())
            intent.putExtra("password", txt_password.text.toString())
            startActivity(intent)
            finish()
        }
    }

}