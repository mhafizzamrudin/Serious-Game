package com.example.tugasakhir.View

import android.content.Intent
import android.os.Bundle
import com.example.tugasakhir.Model.Avatar
import com.example.tugasakhir.Model.User
import com.example.tugasakhir.R
import kotlinx.android.synthetic.main.activity_registrasi_avatar.*

class RegisterAvatarActivity : BaseActivity() {

    private lateinit var user : User

    override fun getResourceLayout(): Int {
        return R.layout.activity_registrasi_avatar
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        setTitle("Daftar Avatar")
        showBackButton()


        val bundle = intent.extras
        if(bundle != null) {
            user = User()
            user.name = bundle.getString("name", "")
            user.email = bundle.getString("email", "")
            user.password = bundle.getString("password")
        }

        img_avatar_female.setOnClickListener{
            img_avatar_female.setImageResource(R.drawable.avatar_female)
            img_avatar_male.setImageResource(R.drawable.avatar_male_unselected)
            user.avatar.gender = "Female"
        }

        img_avatar_male.setOnClickListener {
            img_avatar_female.setImageResource(R.drawable.avatar_female_unselected)
            img_avatar_male.setImageResource(R.drawable.avatar_male)
            user.avatar.gender = "Male"
        }

        btn_selanjutnya.setOnClickListener {
            user.avatar.name = txt_nama.text.toString()
            user.avatar.kewarganegaraan = sp_kewarganegaraan.selectedItem.toString()
            val intent = Intent(this,CreatePersonalityActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}