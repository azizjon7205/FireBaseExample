package com.example.firebaseexample.managers

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthManager {
    companion object{
        val firebaseauth: FirebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser: FirebaseUser? = firebaseauth.currentUser

        fun isSignedIn(): Boolean{
            return firebaseUser != null
        }

        fun signIn(email: String, password: String, handler: AuthHandler){
            firebaseauth.signInWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    handler.onSuccess()
                } else{
                    handler.onError(task.exception)
                }
            }
        }

        fun signUp(email: String, password: String, handler: AuthHandler){
            firebaseauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    handler.onSuccess()
                } else{
                    handler.onError(task.exception)
                }
            }
        }

        fun signOut(){
            firebaseauth.signOut()
        }
    }
}