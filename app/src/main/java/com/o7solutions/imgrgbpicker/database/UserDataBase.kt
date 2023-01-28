package com.o7solutions.imgrgbpicker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.o7solutions.imgrgbpicker.dao.ColorCodeDao
import com.o7solutions.imgrgbpicker.R
import com.o7solutions.imgrgbpicker.model.ColorCode

@Database(entities = [ColorCode::class], version = 1, exportSchema = false)
abstract class UserDataBase :RoomDatabase(){

    abstract fun userDao(): ColorCodeDao

    companion object {
        private var userDataBase: UserDataBase? = null

        @Synchronized
        fun getData(context: Context): UserDataBase {
            if (userDataBase == null) {
                userDataBase = Room.databaseBuilder(
                    context.applicationContext,
                    UserDataBase::class.java,
                    context.resources.getString(R.string.app_name)
                ).build()
            }

            return userDataBase!!
        }
    }
}