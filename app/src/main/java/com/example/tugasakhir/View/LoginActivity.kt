package com.example.tugasakhir.View

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.example.tugasakhir.Interface.StandartInterface
import com.example.tugasakhir.Model.User
import com.example.tugasakhir.R
import com.example.tugasakhir.ViewModel.LoginViewModel
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber
import kotlin.math.log

class LoginActivity : BaseActivity(), StandartInterface.LoginListener {

    private lateinit var loginViewModel : LoginViewModel
    private lateinit var dialog : ProgressDialog

    override fun getResourceLayout(): Int {
        return R.layout.activity_login
    }


    override fun onViewReady(savedInstanceState: Bundle?) {
        hideActionBar()
        dialog = ProgressDialog(this)
        loginViewModel = LoginViewModel(mAuth, mDatabase)

        button_login.setOnClickListener {
            dialog.setTitle("Tunggu Sebentar")
            dialog.show()
            loginViewModel.login(this, Kolom_ID.text.toString(), Kolom_Password.text.toString(), this)
        }

        button_registrasi.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        txt_lupa_katasandi.setOnClickListener {
            val intent = Intent(this, LupaPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSuccess(user: FirebaseUser?) {
        dialog.dismiss()
        MaterialDialog(this).show {
            title(text="Login Berhasil")
            message(text="Berhasil login")
            positiveButton(text="OK") {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onFailed() {
        dialog.dismiss()
        MaterialDialog(this).show {
            title(text="Login Gagal")
            message(text="Username atau password salah")
            positiveButton(text="OK") {
                dismiss()
            }
        }
    }


}