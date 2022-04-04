package com.example.firebaseexample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseexample.R
import com.example.firebaseexample.activity.MainActivity
import com.example.firebaseexample.model.Post

class PostAdapter(var activity: MainActivity, var items: ArrayList<Post>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_post_list, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post: Post = items[position]
        if (holder is PostViewHolder) {
            holder.tv_title.text = post.title!!.toUpperCase()
            holder.tv_body.text = post.body

            holder.ll_delete.setOnClickListener {
                activity.dialogDeletePost(post)
            }
            holder.ll_update.setOnClickListener {
                activity.callUpdateActivity(post)
            }
        }
    }


    inner class PostViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var tv_title: TextView
        var tv_body: TextView
        var ll_update: LinearLayout
        var ll_delete: LinearLayout

        init {
            tv_title = view.findViewById(R.id.tv_title)
            tv_body = view.findViewById(R.id.tv_body)
            ll_update = view.findViewById(R.id.ll_update)
            ll_delete = view.findViewById(R.id.ll_delete)
        }
    }

}