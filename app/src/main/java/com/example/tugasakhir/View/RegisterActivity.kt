package com.example.tugasakhir.View

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import com.afollestad.materialdialogs.MaterialDialog
import com.example.tugasakhir.Interface.StandartInterface
import com.example.tugasakhir.Model.User
import com.example.tugasakhir.R
import com.example.tugasakhir.ViewModel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_registrasi.*

class RegisterActivity : BaseActivity(), StandartInterface.StandartListener {

    private lateinit var registerViewModel : RegisterViewModel
    private lateinit var dialog : ProgressDialog
    private lateinit var user: User

    override fun getResourceLayout(): Int {
        return R.layout.activity_registrasi
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        setTitle("Daftar Akun")
        showBackButton()

        gotoNextPage()

//        registerViewModel = RegisterViewModel(mAuth, mDatabase)
//
//        dialog = ProgressDialog(this)
//
//        btn_berikutnya.setOnClickListener {
//            if(validate()) {
//
//                user = User().apply {
//                    name = txt_nama.text.toString()
//                    email = txt_email.text.toString()
//                    password = txt_password.text.toString()
//                    address = txt_address.text.toString()
//                    phone = txt_phone.text.toString()
//                }
//                dialog.setTitle("Please wait")
//                dialog.setCancelable(false)
//                dialog.show()
//                registerViewModel.isEmailAvailable(user.email, this)
//            }
//
//        }

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

    override fun onSuccess() {
        dialog.dismiss()
        val intent = Intent(this, RegisterAvatarActivity::class.java)
        intent.putExtra("name", user.name)
        intent.putExtra("email", user.email)
        intent.putExtra("password", user.password)
        intent.putExtra("address", user.address)
        intent.putExtra("phone", user.phone)
        startActivity(intent)
        finish()
    }

    override fun onFailed() {
        dialog.dismiss()
        MaterialDialog(this).show {
            title(text="Email sudah digunakan")
            message(text= "Email yang anda daftarkan sudah digunakan")
            positiveButton(text="OK")
        }.show()
    }

    fun gotoNextPage() {
        val intent = Intent(this, RegisterAvatarActivity::class.java)
        intent.putExtra("name", "John Doe")
        intent.putExtra("email", "share424@gmail.com")
        intent.putExtra("password", "mypassword")
        intent.putExtra("address", "Bandung")
        intent.putExtra("phone", "08123456789")
        startActivity(intent)
        finish()
    }

}