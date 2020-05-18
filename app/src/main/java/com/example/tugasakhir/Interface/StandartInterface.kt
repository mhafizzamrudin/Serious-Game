package com.example.tugasakhir.Interface

import com.example.tugasakhir.Model.User
import com.google.firebase.auth.FirebaseUser

class StandartInterface {
    interface StandartListener {
        fun onSuccess()
        fun onFailed()
    }

    interface LoginListener {
        fun onSuccess(user : FirebaseUser?)
        fun onFailed()
    }

    interface GetUserListener {
        fun onSuccess(user : User?)
        fun onFailed()
    }
}