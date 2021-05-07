package com.moovies.data.db


import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.moovies.data.util.Utils
import com.moovies.domain.interfaces.UserRepository
import com.moovies.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


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
        lateinit var user: User
        val result = getChildUser()?.get()?.await()
        val map = result?.value as HashMap<*, *>
        user = User(
            map["email"] as String,
            map["username"] as String,
            map["hashPassword"] as String
        )
        return user
    }

    override suspend fun isFilmFav(id: String): Boolean {
        var isFilmSaved: Boolean
        withContext(Dispatchers.Default) {
            val ref = getChildUser()
                ?.child("films")
                ?.child(id)
            val str: String? = ref?.get()?.await()?.value?.let { it as String }
            isFilmSaved = str != null
        }
        Log.d("Test", "film $id is saved - $isFilmSaved")
        return isFilmSaved
    }

    override suspend fun getFavoriteFilmsId(): List<String> {
        return withContext(Dispatchers.Default) {
            val list = mutableListOf<String>()
            val map = getChildUser()
                ?.child("films")?.get()?.await()?.value?.let { it as HashMap<*, *> }
            map?.let {
                for ((_, value) in it) {
                    list.add(value as String)
                }
            }

            Log.d("Test", list.toString())
            list
        }
    }

    override suspend fun likeFilm(id: String): Boolean {
        var isSuccess = false
        withContext(Dispatchers.Default) {
            getChildUser()?.child("films")?.child(id)?.setValue(id)
                ?.addOnFailureListener {
                    isSuccess = false
                }?.addOnSuccessListener {
                    isSuccess = true
                }
        }
        return isSuccess
    }

    override suspend fun deleteFromFav(id: String): Boolean {
        var isSuccess = false
        withContext(Dispatchers.Default) {
            getChildUser()?.child("films")?.child(id)?.removeValue()
                ?.addOnFailureListener {
                    isSuccess = false
                }?.addOnSuccessListener {
                    isSuccess = true
                }
        }
        Log.d("Test", "film is deleted - $isSuccess")
        return isSuccess
    }

    fun getChildUser(): DatabaseReference? {
        val email = auth.currentUser?.email
        var user: DatabaseReference? = null
        if (email != null) {
            user = firebaseDatabase.reference.child("users")
                .child(Utils.md5(email))
        }
        return user
    }

}