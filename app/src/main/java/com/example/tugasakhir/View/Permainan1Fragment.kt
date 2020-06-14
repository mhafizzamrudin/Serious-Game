package com.example.tugasakhir.View

import android.widget.Button
import cn.iwgang.familiarrecyclerview.baservadapter.FamiliarEasyAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.example.tugasakhir.Model.Question
import com.example.tugasakhir.R
import kotlinx.android.synthetic.main.fragment_permainan1.*
import kotlinx.android.synthetic.main.fragment_permainan1.btn_backspace
import timber.log.Timber
import kotlin.random.Random

class Permainan1Fragment : BaseFragment() {
    private lateinit var question : Question
    private lateinit var keyboardAdapter : FamiliarEasyAdapter<Char>
    private lateinit var keys : MutableList<Char>
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

        keys = mutableListOf()
        val new_keys = question.answer.toUpperCase().toMutableList()
        for(i in 0..26) {
            new_keys.add(Random.nextInt(65, 90).toChar())
        }
        val new = new_keys.distinct().toMutableList()
        new.shuffle()
        keys.addAll(new)
        keyboardAdapter = object : FamiliarEasyAdapter<Char>(context!!, R.layout.item_key, keys) {
            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val button = holder.findView<Button>(R.id.btn_key)
                button.text = keys[position].toString()
                button.setOnClickListener {
                    val ans = txt_jawaban.text.toString()
                    txt_jawaban.setText(ans + keys[position])
                }
            }

        }

        virtual_keyboard_view.adapter = keyboardAdapter

        btn_backspace.setOnClickListener {
            val ans = txt_jawaban.text.toString()
            if(ans.isEmpty()) return@setOnClickListener
            txt_jawaban.setText(ans.substring(0, ans.length - 1))
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