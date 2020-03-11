package com.example.tugasakhir.Repository

import android.app.Activity
import com.example.tugasakhir.Model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import timber.log.Timber
import java.lang.Exception

class UserRepository(val context : Activity) {
    public fun register(user : User, password : String, mAuth : FirebaseAuth, listener : UserRegisterListener) {
        mAuth.createUserWithEmailAndPassword(user.email, password)
                .addOnCompleteListener(context, OnCompleteListener {
                    if(it.isSuccessful) {
                        listener.onSuccessCreateUser(user)
                    } else {
                        listener.onFailedCreateUser()
                    }
                })
                .addOnFailureListener(context, OnFailureListener {
                    Timber.e(it)
                    listener.onFailedCreateUser()
                })
    }

    interface UserRegisterListener {
        fun onSuccessCreateUser(user : User)
        fun onFailedCreateUser()
    }

    interface UserLoginListener {
        fun onLoginSuccess()
        fun onLoginFailed()
    }

    interface UserListener {
        fun onGetUser(user : User)
    }

    public fun createUser(user : User, database : FirebaseDatabase) {
        val ref = database.getReference("user")
        ref.child(user.id!!).setValue(user)
    }

    public fun login(email : String, password : String, mAuth : FirebaseAuth, listener : UserLoginListener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(context, OnCompleteListener {
                    if(it.isSuccessful) {
                        listener.onLoginSuccess()
                    } else {
                        listener.onLoginFailed()
                    }
                })
                .addOnFailureListener(context, OnFailureListener {
                    Timber.e(it)
                    listener.onLoginFailed()
                })
    }

    public fun getUser(id : String, database : FirebaseDatabase, listener : UserListener) {
        val ref = database.getReference("user")
        ref.child(id).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(ds: DataSnapshot) {
                val user = ds.getValue(User::class.java)
                listener.onGetUser(user!!)
            }

        })
    }


}