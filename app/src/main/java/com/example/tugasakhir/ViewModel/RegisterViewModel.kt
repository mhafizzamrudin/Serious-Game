package com.example.tugasakhir.ViewModel

import android.app.Activity
import com.example.tugasakhir.Interface.StandartInterface
import com.example.tugasakhir.Model.User
import com.example.tugasakhir.Repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class RegisterViewModel(val mUser : FirebaseAuth, val mDatabase : FirebaseDatabase) {

    fun isEmailAvailable(email : String, listener : StandartInterface.StandartListener) {
        val userRepository = UserRepository(mDatabase)
        userRepository.isEmailAvailable(email, listener)
    }

    fun register(activity : Activity, user : User, mStorage : FirebaseStorage, listener : StandartInterface.StandartListener) {
        val userRepository = UserRepository(mDatabase)
        userRepository.register(activity, user, mUser, mStorage, listener)
    }
}