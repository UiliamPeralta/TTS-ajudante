package com.example.uiliam.ttsvozajudante.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dev.peralta.voztransform.dataBase.PalavraDao
import com.example.uiliam.ttsvozajudante.model.Palavra

@Database(entities = [(Palavra::class)], version = 1)
abstract class AppRoomDataBase : RoomDatabase() {
    abstract fun palavraDao(): PalavraDao
    companion object {
        private var INSTANCE: AppRoomDataBase? = null

        fun getDataBase(context: Context): AppRoomDataBase? {
            synchronized(AppRoomDataBase::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppRoomDataBase::class.java,
                            "app_database").build()
                }
            }
            return INSTANCE
        }
    }

}