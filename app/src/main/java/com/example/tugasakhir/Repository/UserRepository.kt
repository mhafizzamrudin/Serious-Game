package com.example.tugasakhir.Repository

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.tugasakhir.Interface.StandartInterface
import com.example.tugasakhir.Model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import timber.log.Timber

class UserRepository(val mDatabase : FirebaseDatabase) {
    fun isEmailAvailable(email : String, listener : StandartInterface.StandartListener) {
        val ref = mDatabase.getReference("users")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                listener.onFailed()
            }

            override fun onDataChange(ds: DataSnapshot) {
                var isFound = false
                ds.children.forEach {
                    if(it.child("email").value == email) {
                        listener.onFailed()
                        isFound = true
                        return@forEach
                    }
                }
                if(!isFound) listener.onSuccess()
            }

        })
    }

    fun register(context : Activity, user : User, mAuth : FirebaseAuth, mStorage : FirebaseStorage, listener : StandartInterface.StandartListener) {
        Timber.d("Register user. email: %s, password: %s", user.email, user.password)
        mAuth.createUserWithEmailAndPassword(user.email, user.password!!)
                .addOnCompleteListener(context, object : OnCompleteListener<AuthResult> {
                    override fun onComplete(task: Task<AuthResult>) {
                        if(task.isSuccessful) {
                            Timber.d("Success create user")
                            val cUser = mAuth.currentUser
                            val ref = mDatabase.getReference("users")
                            var count = 0
                            val callback = object : StandartInterface.StandartListener {
                                override fun onSuccess() {
                                    count++
                                    if(count == 16) {
                                        listener.onSuccess()
                                    }
                                }

                                override fun onFailed() {
                                    count++
                                    if(count == 16) {
                                        listener.onSuccess()
                                    }
                                }

                            }
                            ref.child(cUser!!.uid).setValue(user)
                            var idx = 0
                            user.avatar.pet.answers.forEach {
                                val answerRepository = AnswerRepository()
                                answerRepository.storeAnswer(cUser.uid!!, "pet", idx++, it, mStorage, mDatabase, callback)
                            }
                            idx = 0
                            user.avatar.family.answers.forEach {
                                val answerRepository = AnswerRepository()
                                answerRepository.storeAnswer(cUser.uid!!, "family", idx++, it, mStorage, mDatabase, callback)
                            }
                            idx = 0
                            user.avatar.vacation.answers.forEach {
                                val answerRepository = AnswerRepository()
                                answerRepository.storeAnswer(cUser.uid!!, "vacation", idx++, it, mStorage, mDatabase, callback)
                            }
                            idx = 0
                            user.avatar.hobby.answers.forEach {
                                val answerRepository = AnswerRepository()
                                answerRepository.storeAnswer(cUser.uid!!, "hobby", idx++, it, mStorage, mDatabase, callback)
                            }
//                            listener.onSuccess()
                        } else {
                            Timber.e("Failed to create user")
                            listener.onFailed()
                        }
                    }
                })
                .addOnFailureListener(context) {
                    Timber.e(it)
                    listener.onFailed()
                }
    }

    fun login(activity : Activity, email : String, password : String, mAuth : FirebaseAuth, listener : StandartInterface.LoginListener) {
        Timber.d("Login, email: %s, password: %s", email, password)
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity) {
                    if(it.isSuccessful) {
                        Timber.d("Login successfully")
                        listener.onSuccess(mAuth.currentUser)
                    } else {
                        Timber.e("Login failed")
                        listener.onFailed()
                    }
                }
                .addOnFailureListener {
                    Timber.e(it)
                    listener.onFailed()
                }
    }

    fun getUser(email : String, listener : StandartInterface.GetUserListener) {
        val ref = mDatabase.getReference("users")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                listener.onFailed()
            }

            override fun onDataChange(ds: DataSnapshot) {
                ds.children.forEach {
                    if(it.child("email").value == email) {
                        val user = it.getValue(User::class.java)
                        listener.onSuccess(user)
                        return
                    }
                }
                listener.onFailed()
            }

        })
    }

    fun resetPassword(email : String, mAuth : FirebaseAuth, listener : StandartInterface.StandartListener) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        listener.onSuccess()
                    } else {
                        listener.onFailed()
                    }
                }
                .addOnFailureListener {
                    Timber.e(it)
                    listener.onFailed()
                }
    }


}