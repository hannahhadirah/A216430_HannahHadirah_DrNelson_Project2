package com.example.a216430_hannahhadirah_drnelson_project2.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(food: FoodEntity)

    @Query("SELECT * FROM food_items")
    fun getAllFoods(): Flow<List<FoodEntity>>

    @Delete
    suspend fun delete(food: FoodEntity)

    @Update
    suspend fun update(food: FoodEntity)
}


