package com.example.firebaseexample.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseexample.R
import com.example.firebaseexample.adapter.PostAdapter
import com.example.firebaseexample.managers.AuthManager
import com.example.firebaseexample.managers.DatabaseHandler
import com.example.firebaseexample.managers.DatabaseManager
import com.example.firebaseexample.model.Post
import com.example.firebaseexample.utils.Extensions.toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : BaseActivity() {
    lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setLayoutManager(GridLayoutManager(this, 1))

        var iv_logout = findViewById<ImageView>(R.id.iv_logout)
        iv_logout.setOnClickListener {
            AuthManager.signOut()
            callSignInActivity(this)
        }

        var fab_create = findViewById<FloatingActionButton>(R.id.fab_create)
        fab_create.setOnClickListener {
            callCreateActivity()
        }

        apiLoadPosts()
    }

    fun apiLoadPosts() {
        showLoading(this)
        DatabaseManager.apiLoadPosts(object : DatabaseHandler {
            override fun onSuccess(post: Post?, posts: ArrayList<Post>) {
                refreshAdapter(posts)
                dismissLoading()
            }

            override fun onError() {
                dismissLoading()
            }
        })
    }

    fun apiDeletePost(post: Post) {
        adapter.items.clear()
        showLoading(this)
        DatabaseManager.apiDeletePost(post, object: DatabaseHandler {
            override fun onSuccess(post: Post?, posts: ArrayList<Post>) {
                apiLoadPosts()
            }

            override fun onError() {
                dismissLoading()
                Log.d("@@@", "Deleting failure")
            }
        })
    }

    fun dialogDeletePost(post: Post){
        AlertDialog.Builder(this).apply {
            setTitle("Delete Post")
            setMessage("Are you sure you want to delete this post?")
            setPositiveButton(android.R.string.yes){dialog, which ->
                apiDeletePost(post)
            }
            setNegativeButton(android.R.string.no){dialog, which ->
                dialog.dismiss()
            }
            setIcon(android.R.drawable.ic_dialog_alert)
            create()
            show()
        }
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            // Load all posts...
            apiLoadPosts()
        }
    }

    fun callCreateActivity() {
        val intent = Intent(this, CreateActivity::class.java)
        resultLauncher.launch(intent)
    }

    fun callUpdateActivity(post: Post){
        val intent = Intent(this, UpdateActivity::class.java)
        intent.putExtra("post", post)
        resultLauncher.launch(intent)
    }

    fun refreshAdapter(posts: ArrayList<Post>) {
        adapter = PostAdapter(this, posts)
        recyclerView.adapter = adapter
    }

}