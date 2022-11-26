package com.example.starwarsapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable

class DrawableManager(val context: Context) {
    @SuppressLint("UseCompatLoadingForDrawables")
    fun getDrawable(drawableName: String): Drawable? {
        val uri = "@drawable/${drawableName.lowercase().split(" ").joinToString("_")}"
        val imageResource = context.resources.getIdentifier(uri, null, context.packageName)
        return context.getDrawable(imageResource)
    }
}
