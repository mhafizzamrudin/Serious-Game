package com.example.tugasakhir.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.tugasakhir.Model.Personality
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
    private lateinit var personality: Personality

    override fun getResourceLayout(): Int {
        return R.layout.activity_personality_avatar
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        setTitle("Daftar Avatar")
        showBackButton()

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
            1 -> Glide.with(this).load(image.file).fitCenter().into(img_1)
            2 -> Glide.with(this).load(image.file).fitCenter().into(img_2)
            3 -> Glide.with(this).load(image.file).fitCenter().into(img_3)
            4 -> Glide.with(this).load(image.file).fitCenter().into(img_4)
        }
    }
}