package com.example.tugasakhir.ViewModel

import com.example.tugasakhir.Interface.StandartInterface
import com.example.tugasakhir.Repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ResetPassword(val mAuth : FirebaseAuth) {
    fun resetPassword(email : String, listener : StandartInterface.StandartListener) {
        val userRepository = UserRepository(FirebaseDatabase.getInstance())
        userRepository.resetPassword(email, mAuth, listener)
    }
}