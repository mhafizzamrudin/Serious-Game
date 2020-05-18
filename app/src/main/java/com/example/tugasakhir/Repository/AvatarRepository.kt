package com.example.tugasakhir.Repository

import com.example.tugasakhir.Interface.StandartInterface
import com.google.firebase.database.FirebaseDatabase

class AvatarRepository(val mDatabase : FirebaseDatabase) {

    fun insert(uuid : String, avatar : AvatarRepository, listener : StandartInterface.StandartListener) {
        var ref = mDatabase.getReference("users").child(uuid)
        ref.child("avatar").setValue(avatar::class.java)


    }
}