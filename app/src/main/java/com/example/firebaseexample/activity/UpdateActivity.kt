package com.example.firebaseexample.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.firebaseexample.R
import com.example.firebaseexample.managers.DatabaseHandler
import com.example.firebaseexample.managers.DatabaseManager
import com.example.firebaseexample.model.Post
import com.example.firebaseexample.utils.Extensions.toast

class UpdateActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        initViews()
    }

    private fun initViews(){
        val tv_title_toolbar: TextView = findViewById(R.id.tv_title_toolbar)
        tv_title_toolbar.text = "Update Post"

        val iv_close: ImageView = findViewById(R.id.iv_close)
        iv_close.setOnClickListener {
            finish()
        }

        val b_update: Button = findViewById(R.id.b_create)
        b_update.text = "Update"

        val et_title: EditText = findViewById(R.id.et_title)
        val et_body: EditText = findViewById(R.id.et_body)

        val post = intent.getSerializableExtra("post") as Post
        et_body.text.insert(0, post.body)
        et_title.text.insert(0, post.title)

        b_update.setOnClickListener {
            if (et_body.text.toString() != post.body || et_title.text.toString() != post.title){
                val updatedPost = post.id?.let { Post(it!!, et_title.text.toString(), et_body.text.toString()) }
                apiUpdatePost(updatedPost!!)
            }
        }

    }

    fun apiUpdatePost(post: Post){
        DatabaseManager.apiUpdatePost(post, object: DatabaseHandler {
            override fun onSuccess(post: Post?, posts: ArrayList<Post>) {
                finishIntent()
                toast("Updated successfully")
            }

            override fun onError() {
                toast("Update failure")
            }

        })
    }

    fun finishIntent(){
        val resultIntent = intent
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}