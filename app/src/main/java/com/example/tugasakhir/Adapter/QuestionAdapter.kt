package com.example.tugasakhir.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.tugasakhir.Model.Answer
import com.example.tugasakhir.Model.Personality
import com.example.tugasakhir.R
import kotlinx.android.synthetic.main.item_question.view.*
import timber.log.Timber

class QuestionAdapter(var answers: MutableList<Answer>) : RecyclerView.Adapter<QuestionAdapter.Holder>() {

//    var selectedFirst = -1
//    var selectedSecond = -1
    var selectedAnswer = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val holder = LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        return Holder(parent.context, holder)
    }

    override fun getItemCount(): Int {
        return answers.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
//        if(position == selectedFirst) {
//            Timber.d("Answer 1: ${answers[selectedFirst].answer}")
//            holder.bind(answers[position], 1)
//        } else if(position == selectedSecond) {
//            Timber.d("Answer 2: ${answers[selectedSecond].answer}")
//            holder.bind(answers[position], 2)
//        } else {
//            holder.bind(answers[position], -1)
//        }
        if(position == selectedAnswer) {
            Timber.d("Answer : ${answers[selectedAnswer].answer}")
            holder.bind(answers[position], true)
        } else {
            holder.bind(answers[position], false)
        }
    }

//    fun getFirstAnswer() = if(selectedFirst != -1) answers[selectedFirst] else null
//    fun getSecondAnswer() = if(selectedSecond != -1) answers[selectedSecond] else null
    fun getSelectedAnswer() = if(selectedAnswer != -1) answers[selectedAnswer] else null


    class Holder(var context : Context, var view : View) : RecyclerView.ViewHolder(view) {
        fun bind(answer : Answer, isSelected : Boolean) {
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            view.img_selected.visibility = View.INVISIBLE
            if(isSelected) {
                view.img_selected.apply {
                    visibility = View.VISIBLE
                }
            }

            Glide.with(context)
                    .load(answer.image)
                    .placeholder(circularProgressDrawable)
                    .into(view.img_view)
            Timber.d("Load Image %s", answer.image)
        }
    }
}