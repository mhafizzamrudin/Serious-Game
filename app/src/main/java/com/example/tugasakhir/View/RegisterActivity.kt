package com.example.tugasakhir.View

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import com.example.tugasakhir.Model.User
import com.example.tugasakhir.R
import com.example.tugasakhir.Repository.UserRepository
import com.example.tugasakhir.ViewModel.UserViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registrasi.*

class RegisterActivity : BaseActivity(), UserRepository.UserRegisterListener {

    private lateinit var userViewModel: UserViewModel
    private lateinit var dialog : ProgressDialog

    override fun getResourceLayout(): Int {
        return R.layout.activity_registrasi
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        setTitle("ZamrudinApp")
        showBackButton()
        dialog = ProgressDialog(this)
        userViewModel = UserViewModel(this, mAuth, mDatabase)

        btnCreateAccount.setOnClickListener {
            val user = User()
            user.name = txt_nama.text.toString()
            user.email = txt_email.text.toString()
            userViewModel.register(user, txt_password.text.toString(), this)
            dialog.setTitle("Tunggu Sebentar")
            dialog.show()
        }
    }

    override fun onSuccessCreateUser(user : User) {
        dialog.hide()
        Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onFailedCreateUser() {
        dialog.hide()
        Toast.makeText(this, "Login gagal", Toast.LENGTH_SHORT).show()
    }

}