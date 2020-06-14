package com.example.tugasakhir.View

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.core.view.get
import com.example.tugasakhir.Model.Avatar
import com.example.tugasakhir.Model.User
import com.example.tugasakhir.R
import kotlinx.android.synthetic.main.activity_registrasi.*
import kotlinx.android.synthetic.main.activity_registrasi_avatar.*
import kotlinx.android.synthetic.main.activity_registrasi_avatar.txt_nama
import kotlinx.android.synthetic.main.activity_registrasi_avatar.txt_suku

class RegisterAvatarActivity : BaseActivity() {

    private lateinit var user : User

    override fun getResourceLayout(): Int {
        return R.layout.activity_registrasi_avatar
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
//        setTitle("Daftar Avatar")
        setTitle("")
        showBackButton()


        val bundle = intent.extras
        if(bundle != null) {
            user = User().apply {
                name = bundle.getString("name", "")
                email = bundle.getString("email", "")
                password = bundle.getString("password", "")
                phone = bundle.getString("phone", "")
                address = bundle.getString("address", "")
            }

        }

//        img_avatar_female.setOnClickListener{
//            img_avatar_female.setImageResource(R.drawable.avatar_female)
//                img_avatar_male.setImageResource(R.drawable.avatar_male_unselected)
//            user.avatar.gender = "wanita"
//        }
//
//        img_avatar_male.setOnClickListener {
//            img_avatar_female.setImageResource(R.drawable.avatar_female_unselected)
//            img_avatar_male.setImageResource(R.drawable.avatar_male)
//            user.avatar.gender = "pria"
//        }

        btn_selanjutnya.setOnClickListener {
            if(validate()) {
                user.avatar.name = txt_nama.text.toString()
                user.avatar.suku = txt_suku.text.toString()
                user.avatar.gender = sp_gender.selectedItem.toString()
                user.avatar.kewarganegaraan = txt_kewarganegaraan.text.toString()
                val intent = Intent(this, CreatePersonalityActivity::class.java)
                intent.putExtra("user.name", user.name)
                intent.putExtra("user.email", user.email)
                intent.putExtra("user.password", user.password)
                intent.putExtra("user.phone", user.phone)
                intent.putExtra("user.address", user.address)

                intent.putExtra("avatar.gender", user.avatar.gender)
                intent.putExtra("avatar.name", user.avatar.name)
                intent.putExtra("avatar.suku", user.avatar.suku)
                intent.putExtra("avatar.kewarganegaraan", user.avatar.kewarganegaraan)

                startActivity(intent)
                finish()
            }
        }
    }

    fun validate() : Boolean {
        txt_nama.validate("Nama tidak boleh kosong") { it.isNotEmpty() }
        txt_suku.validate("Suku tidak boleh kosong") { it.isNotEmpty() }
        txt_kewarganegaraan.validate("Kewarganegaraan tidak boleh kosong") { it.isNotEmpty() }

        if(txt_nama.error.isNullOrBlank() && txt_suku.error.isNullOrBlank() && txt_kewarganegaraan.error.isNullOrBlank()) {
            return true
        }
        return false

    }

    fun EditText.validate(message: String, validator : (String) -> Boolean) {
        this.error = if(validator(this.text.toString())) null else message
    }
}