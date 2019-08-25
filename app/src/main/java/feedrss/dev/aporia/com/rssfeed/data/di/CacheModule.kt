package feedrss.dev.aporia.com.rssfeed.data.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import dagger.Module
import dagger.Provides
import feedrss.dev.aporia.com.rssfeed.data.db.AppDatabase
import javax.inject.Singleton

/**
 * This module is responsible for providing core caching dependencies:
 *  * Room DB
 *  * SharedPreferences
 */
@Module
abstract class CacheModule {

    @Module
    companion object {
        private const val dbName: String = "feedrss-db"

        @Singleton
        @Provides
        @JvmStatic
        fun provideDatabases(ctx: Context): AppDatabase {
            return Room.databaseBuilder(ctx, AppDatabase::class.java, dbName)
                .fallbackToDestructiveMigration()
                .build()
        }

        @Singleton
        @Provides
        @JvmStatic
        fun provideSharedPreferences(context: Context): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
    }


}