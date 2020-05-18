package com.example.tugasakhir.View

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.example.tugasakhir.Interface.StandartInterface
import com.example.tugasakhir.Model.Answer
import com.example.tugasakhir.Model.Avatar
import com.example.tugasakhir.Model.Personality
import com.example.tugasakhir.Model.User
import com.example.tugasakhir.R
import com.example.tugasakhir.ViewModel.RegisterViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_personality_avatar.*
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource
import timber.log.Timber

class CreatePersonalityActivity : BaseActivity(), MultiplePermissionsListener, EasyImage.Callbacks {

    private var selected_image = 1
    private lateinit var easyImage : EasyImage
    private lateinit var user : User
    private var name : Int = 0
    private val names = arrayOf("Pet", "Family", "Vacation", "Hobby")
    private var personalities = arrayOf(Personality(), Personality(), Personality(), Personality())
    private lateinit var mStorage : FirebaseStorage
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var dialog : ProgressDialog

    override fun getResourceLayout(): Int {
        return R.layout.activity_personality_avatar
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        showBackButton()

        mStorage = FirebaseStorage.getInstance()
        registerViewModel = RegisterViewModel(mAuth, mDatabase)
        dialog = ProgressDialog(this)

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
                gender = bundle.getString("avatar.gender", "")
                name = bundle.getString("avatar.name", "")
                phone = bundle.getString("avatar.phone", "")
                kewarganegaraan = bundle.getString("avatar.kewarganegaraan", "")
            }
            name = bundle.getInt("name", 0)
            if(intent.hasExtra("personalities")) {
                val gson_str = intent.getStringExtra("personalities")
                Timber.i(gson_str)
                val gson = Gson()
                personalities = gson.fromJson(gson_str, personalities::class.java)
            }

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
            if(!validate()) {
               return@setOnClickListener
            }
            personalities[name].answers[0].answer = txt_answer_1.text.toString()
            personalities[name].answers[1].answer = txt_answer_2.text.toString()
            personalities[name].answers[2].answer = txt_answer_3.text.toString()
            personalities[name].answers[3].answer = txt_answer_4.text.toString()
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

                val gson = Gson()
                intent.putExtra("personalities", gson.toJson(personalities))

                startActivity(intent)
                finish()
            } else {
                user.avatar.pet = personalities[0]
                user.avatar.family = personalities[1]
                user.avatar.vacation = personalities[2]
                user.avatar.hobby = personalities[3]
                dialog.setTitle("Please wait")
                dialog.setCancelable(false)
                dialog.show()
                registerViewModel.register(this, user, mStorage, object : StandartInterface.StandartListener {
                    override fun onSuccess() {
                        dialog.dismiss()
                        MaterialDialog(this@CreatePersonalityActivity).show {
                            title(text="Berhasil mendaftarkan akun")
                            message(text="Akun anda berhasil didaftarkan")
                            positiveButton(text="OK") {
                                finish()
                            }
                        }
                    }

                    override fun onFailed() {
                        dialog.dismiss()
                        MaterialDialog(this@CreatePersonalityActivity).show {
                            title(text="Gagal mendaftarkan akun")
                            message(text="Terjadi kesalahan ketika mendaftarkan akun anda, coba lagi nanti")
                            positiveButton(text="OK")
                        }
                    }

                })

            }
        }
    }

    private fun validate(): Boolean {
        txt_answer_1.validate("Jawaban tidak boleh kosong") { it.isNotEmpty() }
        txt_answer_2.validate("Jawaban tidak boleh kosong") { it.isNotEmpty() }
        txt_answer_3.validate("Jawaban tidak boleh kosong") { it.isNotEmpty() }
        txt_answer_4.validate("Jawaban tidak boleh kosong") { it.isNotEmpty() }
        if(txt_answer_1.text.isEmpty()) return false
        if(txt_answer_2.text.isEmpty()) return false
        if(txt_answer_3.text.isEmpty()) return false
        if(txt_answer_4.text.isEmpty()) return false

        personalities[name].answers.forEach {
            if(it.image.isEmpty()) {
                MaterialDialog(this).show {
                    title(text="Gambar belum dipilih")
                    message(text="Pilih gambar terlebih dahulu")
                    positiveButton(text="OK")
                }
                return false
            }
        }
        return true
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

    fun EditText.validate(message: String, validator : (String) -> Boolean) {
        this.error = if(validator(this.text.toString())) null else message
    }
}