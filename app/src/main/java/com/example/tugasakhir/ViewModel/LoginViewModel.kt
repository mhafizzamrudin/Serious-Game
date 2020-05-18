package com.example.tugasakhir.ViewModel

import android.app.Activity
import com.example.tugasakhir.Interface.StandartInterface
import com.example.tugasakhir.Repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import timber.log.Timber

class LoginViewModel(val mAuth : FirebaseAuth, val mDatabase : FirebaseDatabase) {

    fun login(activity : Activity, email : String, password : String, listener : StandartInterface.LoginListener) {
        Timber.d("Login")
        val userRepository = UserRepository(mDatabase)
        userRepository.login(activity, email, password, mAuth, listener)
    }
}