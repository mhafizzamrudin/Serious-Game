package com.example.tugasakhir.View

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.tugasakhir.Model.User
import com.example.tugasakhir.R
import com.example.tugasakhir.Repository.UserRepository
import com.example.tugasakhir.ViewModel.UserViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), UserRepository.UserRegisterListener {

    private lateinit var userViewModel: UserViewModel
    private lateinit var dialog : ProgressDialog

    override fun getResourceLayout(): Int {
        return R.layout.activity_login
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        hideActionBar()
        dialog = ProgressDialog(this)
        userViewModel = UserViewModel(this, mAuth, mDatabase)
        button_login.setOnClickListener {
            userViewModel.login(Kolom_ID.text.toString(), Kolom_Password.text.toString(), this)
            dialog.setTitle("Tunggu Sebentar")
            dialog.show()
        }

        button_registrasi.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSuccessCreateUser(user: User) {
        dialog.dismiss()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onFailedCreateUser() {
        Toast.makeText(this, "Login gagal", Toast.LENGTH_SHORT).show()
    }
}