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

    var selectedFirst = -1
    var selectedSecond = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val holder = LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        return Holder(parent.context, holder)
    }

    override fun getItemCount(): Int {
        return answers.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if(position == selectedFirst) {
            Timber.d("Answer 1: ${answers[selectedFirst].answer}")
            holder.bind(answers[position], 1)
        } else if(position == selectedSecond) {
            Timber.d("Answer 2: ${answers[selectedSecond].answer}")
            holder.bind(answers[position], 2)
        } else {
            holder.bind(answers[position], -1)
        }
    }

    fun getFirstAnswer() = if(selectedFirst != -1) answers[selectedFirst] else null
    fun getSecondAnswer() = if(selectedSecond != -1) answers[selectedSecond] else null

    class Holder(var context : Context, var view : View) : RecyclerView.ViewHolder(view) {
        fun bind(answer : Answer, isSelected : Int) {
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            view.txt_selected.visibility = View.INVISIBLE
            if(isSelected == 1) {
                view.txt_selected.apply {
                    visibility = View.VISIBLE
                    text = "1"
                }
            } else if(isSelected == 2) {
                view.txt_selected.apply {
                    visibility = View.VISIBLE
                    text = "2"
                }
            }

            Glide.with(context)
                    .load(answer.image)
                    .placeholder(circularProgressDrawable)
                    .into(view.img_view)
        }
    }
}