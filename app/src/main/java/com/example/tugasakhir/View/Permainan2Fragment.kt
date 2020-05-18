package com.example.tugasakhir.View

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.example.tugasakhir.Adapter.QuestionAdapter
import com.example.tugasakhir.Model.Personality
import com.example.tugasakhir.R
import kotlinx.android.synthetic.main.fragment_permainan2.*

class Permainan2Fragment : BaseFragment() {

    private lateinit var questionAdapter : QuestionAdapter
    private lateinit var personality: Personality
    override fun getResourceLayout(): Int {
        return R.layout.fragment_permainan2
    }

    override fun onViewReady() {
        val p = (activity as PermainanActivity).getQuestionStep2()
        if(p != null) {
            personality = p
            questionAdapter = QuestionAdapter(p.answers)
            rv_question.adapter = questionAdapter
        } else {
            MaterialDialog(activity!!).show {
                title(text="Pertanyaan habis")
                message(text="Daftar pertanyaan telah habis, anda gagal")
                positiveButton {
                    activity!!.finish()
                }
            }
        }

        rv_question.setOnItemClickListener { familiarRecyclerView, view, position ->
            if(questionAdapter.selectedFirst == -1) {
                questionAdapter.selectedFirst = position
            } else if(questionAdapter.selectedSecond == -1) {
                questionAdapter.selectedSecond = position
            } else if(questionAdapter.selectedFirst == position){
                questionAdapter.selectedFirst = -1
                if(questionAdapter.selectedSecond != -1) {
                    questionAdapter.selectedFirst = questionAdapter.selectedSecond
                    questionAdapter.selectedSecond = -1
                }
            } else if(questionAdapter.selectedSecond == position) {
                questionAdapter.selectedSecond = -1
            } else {
                questionAdapter.selectedSecond = position
            }
            questionAdapter.notifyDataSetChanged()
        }

        btn_check.setOnClickListener {
            if(questionAdapter.getFirstAnswer() == null || questionAdapter.getSecondAnswer() == null) {
                MaterialDialog(context!!).show {
                    title(text="Pilih Gambar")
                    message(text="Pilih 2 gambar terlebih dahulu")
                    positiveButton(text="OK")
                }
            } else if(txt_answer_1.text.toString().toLowerCase() == questionAdapter.getFirstAnswer()!!.answer.toLowerCase() &&
                    txt_answer_2.text.toString().toLowerCase() == questionAdapter.getSecondAnswer()!!.answer.toLowerCase()) {
                (activity as PermainanActivity).score += 20
                (activity as PermainanActivity).removeQuestionStep2()
                MaterialDialog(context!!).show {
                    title(text="Jawaban benar")
                    message(text="Jawaban anda benar, anda mendapatkan 20 point, Apakah anda ingin lanjut?")
                    positiveButton(text="IYA") {
                        val fragment = Permainan1Fragment()
                        replace(R.id.container, fragment, false)
                    }
                    negativeButton(text="TIDAK") {
                        (activity as PermainanActivity).checkScore()
                        dismiss()
                    }
                }
            } else {
                (activity as PermainanActivity).score -= 20
                MaterialDialog(context!!).show {
                    title(text="Jawaban salah")
                    message(text="Jawaban anda salah, point anda dikurangi 20, Apakah anda ingin lanjut?")
                    positiveButton(text="IYA") {
                        val fragment = Permainan1Fragment()
                        replace(R.id.container, fragment, false)
                    }
                    negativeButton(text="TIDAK") {
                        (activity as PermainanActivity).checkScore()
                        dismiss()
                    }
                }
            }
        }

    }
}