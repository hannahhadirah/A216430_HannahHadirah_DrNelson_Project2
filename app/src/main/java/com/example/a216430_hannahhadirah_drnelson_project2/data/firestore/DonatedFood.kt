package com.example.a216430_hannahhadirah_drnelson_project2.data.firestore

data class DonatedFood(
    val name: String = "",
    val expiryDate: String = "",
    val donatedAt: Long = System.currentTimeMillis()
)