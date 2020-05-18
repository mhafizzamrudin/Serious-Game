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
import com.example.tugasakhir.ViewModel.LupaPasswordViewModel
import com.example.tugasakhir.ViewModel.RegisterViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_lupa_password.btn_berikutnya
import kotlinx.android.synthetic.main.activity_lupa_password.txt_email

class LupaPasswordActivity : BaseActivity() {
    private lateinit var lupaPasswordViewModel: LupaPasswordViewModel
    private lateinit var dialog : ProgressDialog
    override fun getResourceLayout(): Int {
        return R.layout.activity_lupa_password
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        setTitle("Lupa Password")
        lupaPasswordViewModel = LupaPasswordViewModel()
        dialog = ProgressDialog(this)
        btn_berikutnya.setOnClickListener {
            txt_email.validate("Email tidak valid") { it.isValidEmail() }
            if(txt_email.text.isEmpty()) {
                return@setOnClickListener
            }
            dialog.setTitle("Please wait")
            dialog.setCancelable(false)
            dialog.show()
            lupaPasswordViewModel.getUser(txt_email.text.toString(), mDatabase, object : StandartInterface.GetUserListener {
                override fun onSuccess(user : User?) {
                    dialog.dismiss()
                    val intent = Intent(this@LupaPasswordActivity, PermainanActivity::class.java)
                    val gson = Gson()
                    intent.putExtra("user", gson.toJson(user))
                    startActivity(intent)
                    finish()
                }

                override fun onFailed() {
                    dialog.dismiss()
                    MaterialDialog(this@LupaPasswordActivity).show {
                        title(text="Email tidak ditemukan")
                        message(text="Email yang anda masukkan tidak terdaftar")
                        positiveButton(text="OK")
                    }
                }

            })

        }
    }

    fun EditText.validate(message: String, validator : (String) -> Boolean) {
        this.error = if(validator(this.text.toString())) null else message
    }

    fun String.isValidEmail() : Boolean = this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

}