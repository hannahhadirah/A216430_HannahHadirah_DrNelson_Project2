package com.example.a216430_hannahhadirah_drnelson_project2.data.repository

import com.example.a216430_hannahhadirah_drnelson_project2.data.local.FoodDao
import com.example.a216430_hannahhadirah_drnelson_project2.data.local.FoodEntity
import kotlinx.coroutines.flow.Flow

class FoodRepository(
    private val foodDao: FoodDao
) {

    val allFoods: Flow<List<FoodEntity>> =
        foodDao.getAllFoods()

    suspend fun insertFood(food: FoodEntity) {
        foodDao.insert(food)
    }

    suspend fun deleteFood(food: FoodEntity) {
        foodDao.delete(food)
    }

    suspend fun updateFood(food: FoodEntity) {
        foodDao.update(food)
    }
}