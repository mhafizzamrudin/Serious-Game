package com.example.tugasakhir.ViewModel

import com.example.tugasakhir.Interface.StandartInterface
import com.example.tugasakhir.Repository.UserRepository
import com.google.firebase.database.FirebaseDatabase

class LupaPasswordViewModel {
    fun getUser(email : String, mDatabase : FirebaseDatabase, listener : StandartInterface.GetUserListener) {
        val userRepository = UserRepository(mDatabase)
        userRepository.getUser(email, listener)
    }
}