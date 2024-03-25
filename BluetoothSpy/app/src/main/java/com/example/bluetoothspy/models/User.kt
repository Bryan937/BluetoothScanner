package com.example.bluetoothspy.models

import android.util.Log
import com.example.bluetoothspy.utils.FirebaseDatabaseSingleton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener

class User(
    private var email: String,
    private var password: String,
    val favourites: MutableMap<String, Device> = mutableMapOf()
) {
    private val database = FirebaseDatabaseSingleton.instance.usersReference
    companion object {
        val instance: User by lazy { User("random_user@gmail.com", "random_password") }
    }

    private fun sanitizeEmail(): String {
        return email.replace(".", "_46")
    }

    fun updateDatabase() {
        val userReference = database.child(sanitizeEmail())

        val userData = mutableMapOf(
            "password" to password,
            "favourites" to favourites
        )

        userReference.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val favouritesType = object : GenericTypeIndicator<Map<String, Device>>() {}
                    val existingFavourites = snapshot.child("favourites").getValue(favouritesType)
                    val updatedFavourites = (existingFavourites ?: emptyMap()).toMutableMap()
                    updatedFavourites.putAll(favourites)

                    userReference.child("favourites").setValue(updatedFavourites)
                } else {
                    userReference.setValue(userData)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("User", "Error checking user existence within database: $error")
            }

        })
    }

    fun retrieveFromDatabase(onDataFetched: (List<Device>) -> Unit) {
        val userReference = database.child(sanitizeEmail())
        val favouritesList = mutableListOf<Device>()

        userReference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    snapshot.child("favourites").children.forEach { deviceSnapshot ->
                        val device = deviceSnapshot.getValue(Device::class.java)
                        if (device != null) {
                            favouritesList.add(device)
                        }
                    }
                }
                onDataFetched(favouritesList)
            }

            override fun onCancelled(error: DatabaseError) {
                onDataFetched(emptyList())
                Log.e("User", "Error retrieving user favourites within database: $error")
            }
        })
    }
}