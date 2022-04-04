package com.example.firebaseexample.utils

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import android.widget.Toast

object Extensions {
    fun Activity.toast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun Uri.getFileExtension(context: Context): String? {
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(context.contentResolver.getType(this))
    }
}