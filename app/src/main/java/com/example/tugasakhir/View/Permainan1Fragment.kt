package com.example.tugasakhir.View

import com.afollestad.materialdialogs.MaterialDialog
import com.example.tugasakhir.Model.Question
import com.example.tugasakhir.R
import kotlinx.android.synthetic.main.fragment_permainan1.*
import timber.log.Timber

class Permainan1Fragment : BaseFragment() {
    private lateinit var question : Question
    override fun getResourceLayout(): Int {
        return R.layout.fragment_permainan1
    }

    override fun onViewReady() {
        updateScore()
        randomQuestion()
        btn_next.setOnClickListener {
            if(txt_jawaban.text.toString().toLowerCase() == question.answer.toLowerCase()) {
                (activity as PermainanActivity).score += 10
                // hapus pertanyaanya
                (activity as PermainanActivity).removeQuestionStep1()
                MaterialDialog(activity!!).show {
                    title(text="Jawaban anda benar")
                    message(text="Jawaban anda benar, anda mendapatkan 10 poin, Apakah anda ingin lanjut?")
                    positiveButton {
                        dismiss()

                        val fragment = Permainan2Fragment()
                        replace(R.id.container, fragment, false)
                        updateScore()
                    }
                    negativeButton {
                        dismiss()
                        (activity as PermainanActivity).checkScore()
                    }
                }
            } else {
                MaterialDialog(activity!!).show {
                    title(text="Jawaban anda salah")
                    message(text="Jawaban anda salah, poin anda dikurangi 10")
                    positiveButton {
                        dismiss()
                        (activity as PermainanActivity).score -= 10
                        updateScore()
                    }
                }

            }

        }
    }

    fun randomQuestion() {
        val q = (activity as PermainanActivity).getQuestionStep1()
        if(q != null) {
            question = q
            Timber.d("Question: ${question.question}, answer: ${question.answer}")
            showQuestion(q)
        } else {
            MaterialDialog(activity!!).show {
                title(text="Pertanyaan telah habis")
                message(text="Daftar pertanyaan telah habis, lanjut ke step 2?")
                positiveButton {
                    val fragment = Permainan2Fragment()
                    replace(R.id.container, fragment, true)
                }
                negativeButton {
                    dismiss()
                    activity!!.finish()
                }
            }
        }
    }

    fun showQuestion(question : Question) {
        txt_pertanyaan.text = question.question
    }

    fun updateScore() {
        val score = (activity as PermainanActivity).score
        (activity as PermainanActivity).setTitle("Point : $score")
    }
}