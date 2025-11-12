package com.devsphere.interfaceimpl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    // Register (Add user)
    suspend fun registerUser(user: User): Boolean {
        return try {
            db.collection("users").add(user).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // Fetch all users
    suspend fun fetchUsers(): List<User>? {
        return try {
            val snapshot = db.collection("users").orderBy("name").get().await()
            snapshot.toObjects<User>()
        } catch (e: Exception) {
            null
        }
    }
}
