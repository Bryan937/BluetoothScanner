package com.example.bluetoothspy.utils

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseDatabaseSingleton {
    companion object {
        val instance: FirebaseDatabaseSingleton by lazy { Holder.instance }

        private object Holder {
            val instance = FirebaseDatabaseSingleton()
        }
    }

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val usersReference: DatabaseReference = database.getReference("users")
    val testReference: DatabaseReference = database.getReference("test")
}