package com.example.starwarsapp.di

import android.content.Context
import com.example.starwarsapp.utils.DrawableManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DrawableModule {
    @Singleton
    @Provides
    fun provideDrawablesManager(@ApplicationContext context: Context): DrawableManager {
        return DrawableManager(context)
    }
}
