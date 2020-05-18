package com.example.tugasakhir.Repository

import android.net.Uri
import com.example.tugasakhir.Interface.StandartInterface
import com.example.tugasakhir.Model.Answer
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import timber.log.Timber
import java.io.File

class AnswerRepository {
    fun storeAnswer(uuid : String, personality : String, idx : Int, answer: Answer, mStorage : FirebaseStorage, mDatabase : FirebaseDatabase, listener : StandartInterface.StandartListener) {
        Timber.d("Store Answer image, uuid: %s, personality: %s, index: %d, answer: %s", uuid, personality, idx, answer.answer)
        var ref = mStorage.getReference("answers/${uuid}/${personality}/${idx}.jpg")
        val file = Uri.fromFile(File(answer.image))

        ref.putFile(file)
                .addOnSuccessListener{
                    Timber.d("Upload success")
                    if(it.task.isSuccessful) {
                        it.storage.downloadUrl.addOnCompleteListener {
                            if(it.isSuccessful) {
                                Timber.d("Download url: %s", it.result.toString())
                                val dbRef = mDatabase.getReference("users").child(uuid).child("avatar").child(personality).child("answers").child(idx.toString())
                                dbRef.child("image").setValue(it.result.toString())
                                listener.onSuccess()
                            }
                        }
                    }
                }
                .addOnFailureListener {
                    Timber.e(it)
                    listener.onFailed()
                }
    }
}