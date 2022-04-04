package com.example.firebaseexample.managers

import com.example.firebaseexample.model.Post

interface DatabaseHandler {
    fun onSuccess(post: Post? = null, posts: ArrayList<Post> = ArrayList())
    fun onError()
}