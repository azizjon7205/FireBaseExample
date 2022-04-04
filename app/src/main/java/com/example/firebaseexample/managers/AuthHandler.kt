package com.example.firebaseexample.managers

import java.lang.Exception

interface AuthHandler {
    fun onSuccess()
    fun onError(exception: Exception?)
}