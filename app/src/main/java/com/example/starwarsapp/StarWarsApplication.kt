package com.example.starwarsapp

import android.app.Application
import android.content.res.Resources
import com.example.starwarsapp.data.local.db.StarWarsDB
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StarWarsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        starWarsDB = StarWarsDB.create(this)
        res = resources
    }

    companion object {
        var starWarsDB: StarWarsDB? = null
        var res: Resources? = null

        fun getResources(): Resources? {
            return res
        }
    }
}
