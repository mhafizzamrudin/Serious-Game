package com.example.tugasakhir.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Personality(var name : String = "", var answers : MutableList<Answer> = mutableListOf()) : Parcelable {
//    var id : String? = null
//    var name : String = ""
//    var answers = mutableListOf<Answer>()
}