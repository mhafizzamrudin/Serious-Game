package com.example.tugasakhir.View

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView
import cn.iwgang.familiarrecyclerview.baservadapter.FamiliarEasyAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.example.tugasakhir.Adapter.QuestionAdapter
import com.example.tugasakhir.Model.Personality
import com.example.tugasakhir.R
import kotlinx.android.synthetic.main.fragment_permainan2.*

class Permainan2Fragment : BaseFragment() {

    private lateinit var questionAdapter : QuestionAdapter
    private lateinit var keyboardAdapter : FamiliarEasyAdapter<String>
    private lateinit var personality: Personality
    private lateinit var keys : MutableList<String>
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
//            if(questionAdapter.selectedFirst == -1) {
//                questionAdapter.selectedFirst = position
//            } else if(questionAdapter.selectedSecond == -1) {
//                questionAdapter.selectedSecond = position
//            } else if(questionAdapter.selectedFirst == position){
//                questionAdapter.selectedFirst = -1
//                if(questionAdapter.selectedSecond != -1) {
//                    questionAdapter.selectedFirst = questionAdapter.selectedSecond
//                    questionAdapter.selectedSecond = -1
//                }
//            } else if(questionAdapter.selectedSecond == position) {
//                questionAdapter.selectedSecond = -1
//            } else {
//                questionAdapter.selectedSecond = position
//            }
            questionAdapter.selectedAnswer = position
            questionAdapter.notifyDataSetChanged()
            keys.clear()
            val answer = questionAdapter.getSelectedAnswer()!!
            answer.answer.forEach {
                keys.add(it.toString())
            }
            keys.shuffle()
            keyboardAdapter.notifyDataSetChanged()
        }

        btn_check.setOnClickListener {
            if(questionAdapter.getSelectedAnswer() == null) {
                MaterialDialog(context!!).show {
                    title(text="Pilih Gambar")
                    message(text="Pilih gambar terlebih dahulu")
                    positiveButton(text="OK")
                }
            } else if(txt_answer.text.toString().toLowerCase() == questionAdapter.getSelectedAnswer()!!.answer.toLowerCase()) {
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

        // virtual keyboard
        keys = mutableListOf()
        keyboardAdapter = object : FamiliarEasyAdapter<String>(context!!, R.layout.item_key, keys) {
            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val key = holder.findView<Button>(R.id.btn_key)
                key.text = keyboardAdapter.getData(position)
                key.setOnClickListener {
                    val ans = txt_answer.text.toString()
                    txt_answer.setText(ans + keys[position])
                }
            }
        }

        virtual_keyboard_view.adapter = keyboardAdapter
        btn_backspace.setOnClickListener {
            val ans = txt_answer.text.toString()
            txt_answer.setText(ans.substring(0, ans.length - 1))
        }



    }
}