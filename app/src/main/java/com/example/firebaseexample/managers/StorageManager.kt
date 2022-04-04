package com.example.firebaseexample.managers

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import android.webkit.MimeTypeMap

import android.content.ContentResolver
import android.content.Context
import com.example.firebaseexample.utils.Extensions.getFileExtension


class StorageManager {
    companion object{
        val storage = FirebaseStorage.getInstance()
        var storageRef = storage.reference
        var photosRef = storageRef.child("photos")

        fun uploadPhoto(context: Context, uri: Uri, handler: StorageHandler){
            val fileName = System.currentTimeMillis().toString()+".${uri.getFileExtension(context)}"
            val uploadTask: UploadTask = photosRef.child(fileName).putFile(uri)
            uploadTask.addOnSuccessListener {
                val result = it.metadata!!.reference!!.downloadUrl
                result.addOnSuccessListener {uri ->
                    var imgUrl = uri.toString()
                    handler.onSuccess(imgUrl)
                }.addOnFailureListener { e ->
                    handler.onError(e)
                }
            }.addOnFailureListener { e ->
                handler.onError(e)
            }
        }

    }
}

/**
 * return type (extension of file) from url
 *
    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }
 */