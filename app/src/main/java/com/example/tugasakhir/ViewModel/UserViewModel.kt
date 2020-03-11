package com.example.tugasakhir.ViewModel

import android.app.Activity
import com.example.tugasakhir.Model.User
import com.example.tugasakhir.Repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class UserViewModel(val context: Activity, val mAuth : FirebaseAuth, val database : FirebaseDatabase)  {
    val repo = UserRepository(context)

    public fun register(user : User, password : String, listener : UserRepository.UserRegisterListener) {
        repo.register(user, password, mAuth, object : UserRepository.UserRegisterListener {
            override fun onSuccessCreateUser(user : User) {
                user.id = mAuth.uid
                repo.createUser(user, database)
                listener.onSuccessCreateUser(user)
            }

            override fun onFailedCreateUser() {
                listener.onFailedCreateUser()
            }
        })
    }

    public fun login(email : String, password : String, listener : UserRepository.UserRegisterListener) {
        repo.login(email, password, mAuth, object : UserRepository.UserLoginListener {
            override fun onLoginSuccess() {
                repo.getUser(mAuth.uid!!, database, object : UserRepository.UserListener {
                    override fun onGetUser(user: User) {
                        listener.onSuccessCreateUser(user)
                    }

                })
            }

            override fun onLoginFailed() {
                listener.onFailedCreateUser()
            }

        })
    }


}