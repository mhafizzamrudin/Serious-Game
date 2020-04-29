package com.example.tugasakhir.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.tugasakhir.Model.Answer
import com.example.tugasakhir.Model.Avatar
import com.example.tugasakhir.Model.Personality
import com.example.tugasakhir.Model.User
import com.example.tugasakhir.R
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_personality_avatar.*
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource

class CreatePersonalityActivity : BaseActivity(), MultiplePermissionsListener, EasyImage.Callbacks {

    private var selected_image = 1
    private lateinit var easyImage : EasyImage
    private lateinit var user : User
    private var name : Int = 0
    private val names = arrayOf("Pet", "Family", "Vacation", "Hobby")
    private var personalities = arrayOf(Personality(), Personality(), Personality(), Personality())

    override fun getResourceLayout(): Int {
        return R.layout.activity_personality_avatar
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        showBackButton()

        val bundle = intent.extras
        if(bundle != null) {
            user = User().apply {
                name = bundle.getString("user.name", "")
                email = bundle.getString("user.email", "")
                password = bundle.getString("user.password", "")
                phone = bundle.getString("user.phone", "")
                address = bundle.getString("user.address", "")
            }
            user.avatar = Avatar().apply {
                name = bundle.getString("avatar.name", "")
                phone = bundle.getString("avatar.phone", "")
                kewarganegaraan = bundle.getString("avatar.kewarganegaraan", "")
            }
            name = bundle.getInt("name", 0)
//            if(intent.hasExtra("personalities"))
//                personalities = intent.getParcelableArrayExtra("personalities") as Array<Personality>

            setTitle("Daftar ${names[name]}")
        }

        personalities[name].name = names[name]
        personalities[name].answers.add(Answer())
        personalities[name].answers.add(Answer())
        personalities[name].answers.add(Answer())
        personalities[name].answers.add(Answer())

        easyImage = EasyImage.Builder(this)
                .allowMultiple(false)
                .build()

        btn_pilih_gambar_1.setOnClickListener {
            selected_image = 1
            Dexter.withActivity(this)
                    .withPermissions(android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(this)
                    .check()

        }
        btn_pilih_gambar_2.setOnClickListener {
            selected_image = 2
            Dexter.withActivity(this)
                    .withPermissions(android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(this)
                    .check()

        }
        btn_pilih_gambar_3.setOnClickListener {
            selected_image = 3
            Dexter.withActivity(this)
                    .withPermissions(android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(this)
                    .check()

        }
        btn_pilih_gambar_4.setOnClickListener {
            selected_image = 4
            Dexter.withActivity(this)
                    .withPermissions(android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    .withListener(this)
                    .check()

        }

        btn_selanjutnya.setOnClickListener {
            if(name < 3) {
                val intent = Intent(this, CreatePersonalityActivity::class.java)
                intent.putExtra("user.name", user.name)
                intent.putExtra("user.email", user.email)
                intent.putExtra("user.password", user.password)
                intent.putExtra("user.phone", user.phone)
                intent.putExtra("user.address", user.address)

                intent.putExtra("avatar.gender", user.avatar.gender)
                intent.putExtra("avatar.name", user.avatar.name)
                intent.putExtra("avatar.phone", user.avatar.name)
                intent.putExtra("avatar.kewarganegaraan", user.avatar.kewarganegaraan)

                intent.putExtra("name", name+1)

//                intent.putExtra("personalities", personalities)

                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Berhasil mendaftarkan akun", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        easyImage.handleActivityResult(requestCode, resultCode, data, this, this)

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
        if(report != null && report.areAllPermissionsGranted()) {
            easyImage.openChooser(this)
        }

    }

    override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {

    }

    override fun onCanceled(source: MediaSource) {

    }

    override fun onImagePickerError(error: Throwable, source: MediaSource) {

    }

    override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
        val image = imageFiles[0]
        when(selected_image) {
            1 -> {
                Glide.with(this).load(image.file).fitCenter().into(img_1)
            }
            2 -> {
                Glide.with(this).load(image.file).fitCenter().into(img_2)
            }
            3 -> {
                Glide.with(this).load(image.file).fitCenter().into(img_3)
            }
            4 -> {
                Glide.with(this).load(image.file).fitCenter().into(img_4)
            }
        }
        personalities[name].answers[selected_image-1].image = image.file.path
    }
}