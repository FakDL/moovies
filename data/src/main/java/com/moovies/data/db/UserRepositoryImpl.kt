package com.moovies.data.db


import com.moovies.data.util.Utils
import com.moovies.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.moovies.domain.interfaces.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.tasks.await


class UserRepositoryImpl @Inject constructor(
    val auth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : UserRepository {



    override suspend fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun createUser(email: String, username: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).await()
        val database = firebaseDatabase.reference
        val user = auth.currentUser?.email?.let { User(it, username, password) }
        database.child("users").child(Utils.md5(email)).setValue(user)
    }

    override suspend fun getUser(): User {
        val email = auth.currentUser?.email
        lateinit var user: User
        if (email != null) {
            val result = firebaseDatabase.reference.child("users")
                .child(Utils.md5(email)).get().await()
            val map = result.value as HashMap<*, *>
            user = User(map["email"] as String, map["username"] as String, map["hashPassword"] as String)
        }else {user = User("","","")}
        return user
    }


}