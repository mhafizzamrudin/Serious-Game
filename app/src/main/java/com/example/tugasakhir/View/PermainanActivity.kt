package com.example.tugasakhir.View

import android.app.ProgressDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.afollestad.materialdialogs.MaterialDialog
import com.example.tugasakhir.Interface.StandartInterface
import com.example.tugasakhir.Model.Personality
import com.example.tugasakhir.Model.Question
import com.example.tugasakhir.Model.User
import com.example.tugasakhir.R
import com.example.tugasakhir.ViewModel.ResetPassword
import com.google.gson.Gson
import timber.log.Timber

class PermainanActivity : BaseActivity(), StandartInterface.StandartListener {

    private lateinit var frag_permainan1 : Permainan1Fragment
    private lateinit var frag_permainan2 : Permainan2Fragment
    private lateinit var user : User
    private lateinit var resetPasswordViewModel : ResetPassword
    private val question_step1 = mutableListOf<Question>()
    private val question_step2 = mutableListOf<Personality>()
    var score = 0
    private lateinit var dialog : ProgressDialog

    override fun getResourceLayout(): Int {
        return R.layout.activity_permainan
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_reset_password, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.btn_check) {
            checkScore()
        }
        return true
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        resetPasswordViewModel = ResetPassword(mAuth)
        dialog = ProgressDialog(this)
        if(intent.hasExtra("user")) {
            val user_json = intent.getStringExtra("user")
            user = Gson().fromJson(user_json, User::class.java)
            question_step1.add(Question("Siapakah nama Avatar anda?", user.avatar.name))
            question_step1.add(Question("Apakah jenis kelamin Avatar anda?", user.avatar.gender))
            question_step1.add(Question("Apakah Suku Avatar anda?", user.avatar.suku))
            question_step1.add(Question("Apakah kewarganegaraan Avatar anda?", user.avatar.kewarganegaraan))

            question_step2.add(user.avatar.pet)
            question_step2.add(user.avatar.vacation)
            question_step2.add(user.avatar.family)
            question_step2.add(user.avatar.hobby)
        } else {
            MaterialDialog(this).show {
                title(text="Terjadi kesalahan")
                message(text="Gagal mendapatkan info user")
                positiveButton {
                    finish()
                }
            }
        }

        frag_permainan1 = Permainan1Fragment()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, frag_permainan1)
        transaction.commit()
    }

    fun getQuestionStep1() : Question? {
        if(question_step1.size > 0) {
            question_step1.shuffle()
            return question_step1[0]
        } else {
            return null
        }
    }

    fun getQuestionStep2() : Personality? {
        if(question_step2.size > 0) {
            question_step2.shuffle()
            return question_step2[0]
        } else {
            return null
        }
    }

    fun removeQuestionStep1() {
        question_step1.removeAt(0)
    }

    fun removeQuestionStep2() {
        question_step2.removeAt(0)
    }

    fun checkScore() {
        if(score < 40) {
            MaterialDialog(this).show {
                title(text="Score tidak mencukupi")
                message(text="Anda harus memiliki score minimum sebanyak 50 point, Main Lagi?")
                positiveButton {
                    frag_permainan1 = Permainan1Fragment()
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.container, frag_permainan1)
                    transaction.commit()
                }
                negativeButton {
                    dismiss()
                }
            }
        } else {
            MaterialDialog(this).show {
                title(text="Score anda telah mencukupi")
                message(text="Score anda telah mencukupi, sekarang anda bisa mereset password anda")
                positiveButton {
                    dialog.setTitle("Please Wait")
                    dialog.setCancelable(false)
                    dialog.show()
                    resetPasswordViewModel.resetPassword(user.email, this@PermainanActivity)
                }
            }
        }
    }

    override fun onSuccess() {
        dialog.dismiss()
        MaterialDialog(this@PermainanActivity).show {
            title(text="Link reset password terkirim")
            message(text="Cek email anda untuk mereset password anda")
            positiveButton {
                dismiss()
                finish()
            }
        }
    }

    override fun onFailed() {
        dialog.dismiss()
        MaterialDialog(this@PermainanActivity).show {
            title(text="Terjadi kesalahan")
            message(text="Gagal mengirim link reset password ke email anda, apakah email anda valid?")
            positiveButton {
                dismiss()
                finish()
            }
        }
    }

}