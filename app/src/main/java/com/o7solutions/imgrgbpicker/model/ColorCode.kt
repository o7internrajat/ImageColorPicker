package com.o7solutions.imgrgbpicker.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "color_code")
class ColorCode (
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    var code1: String?= null,
)
