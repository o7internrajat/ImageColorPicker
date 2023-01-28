package com.o7solutions.imgrgbpicker.dao

import androidx.room.*
import com.o7solutions.imgrgbpicker.model.ColorCode

@Dao
interface ColorCodeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(colorCode: ColorCode)

    @Delete
    fun deleteUser(colorCode: ColorCode)

    @Query("SELECT * FROM color_code ORDER BY id ASC")
    fun getAllUser(): List<ColorCode>

}