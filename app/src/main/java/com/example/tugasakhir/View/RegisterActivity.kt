package com.example.tugasakhir.View

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import com.example.tugasakhir.Model.Avatar
import com.example.tugasakhir.Model.User
import com.example.tugasakhir.R
import com.example.tugasakhir.Repository.UserRepository
import com.example.tugasakhir.ViewModel.UserViewModel
import kotlinx.android.synthetic.main.activity_registrasi.*

class RegisterActivity : BaseActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var dialog : ProgressDialog
    private lateinit var user: User

    override fun getResourceLayout(): Int {
        return R.layout.activity_registrasi
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        setTitle("Daftar Akun")
        showBackButton()

        btn_berikutnya.setOnClickListener {
            if(validate()) {

                user = User().apply {
                    name = txt_nama.text.toString()
                    email = txt_email.text.toString()
                    password = txt_password.text.toString()
                    address = txt_address.text.toString()
                    phone = txt_phone.text.toString()
                }
                val intent = Intent(this, RegisterAvatarActivity::class.java)
                intent.putExtra("name", user.name)
                intent.putExtra("email", user.email)
                intent.putExtra("password", user.password)
                intent.putExtra("address", user.address)
                intent.putExtra("phone", user.phone)
                startActivity(intent)
                finish()
            }

        }

    }

    fun validate() : Boolean {

        txt_nama.validate("Nama tidak boleh kosong") { it.isNotEmpty() }
        txt_password.validate("Password tidak boleh kosong") { it.isNotEmpty() }
        txt_email.validate("Email tidak valid") { it.isValidEmail() }
        txt_phone.validate("No HP tidak boleh kosong") { it.isNotEmpty() }
        txt_address.validate("Alamat tidak boleh kosong") { it.isNotEmpty() }

        if(txt_nama.error.isNullOrBlank() && txt_password.error.isNullOrBlank() && txt_email.error.isNullOrBlank() && txt_phone.error.isNullOrBlank() && txt_address.error.isNullOrBlank()) {
            return true
        }

        return false
    }

    fun EditText.validate(message: String, validator : (String) -> Boolean) {
        this.error = if(validator(this.text.toString())) null else message
    }

    fun String.isValidEmail() : Boolean = this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

}