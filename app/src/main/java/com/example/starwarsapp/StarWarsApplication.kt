package com.example.starwarsapp

import android.app.Application
import com.example.starwarsapp.data.local.db.StarWarsDB

class StarWarsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        starWarsDB = StarWarsDB.create(this)
    }

    companion object {
        var starWarsDB: StarWarsDB? = null
    }
}
