package com.example.starwarsapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log

class DrawableManager(val context: Context) {
    @SuppressLint("UseCompatLoadingForDrawables")
    fun getDrawable(drawableName: String): Drawable? {
        val uri = "@drawable/${drawableName.lowercase().split(" ").joinToString("_")}"
        try {
            val imageResource = context.resources.getIdentifier(uri, null, context.packageName)
            return context.getDrawable(imageResource)
        } catch (error: Exception) {
            error.localizedMessage?.let { Log.d("Movies", it) }
            val imageResource = context.resources.getIdentifier("@drawable/no_found_image", null, context.packageName)
            return context.getDrawable(imageResource)
        }
    }
}
