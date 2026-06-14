package com.example.a216430_hannahhadirah_drnelson_project2.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_items")
data class FoodEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val expiryDate: String
)



