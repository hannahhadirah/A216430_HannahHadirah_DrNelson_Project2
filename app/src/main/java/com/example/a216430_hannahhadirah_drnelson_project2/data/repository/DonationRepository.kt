package com.example.a216430_hannahhadirah_drnelson_project2.data.repository

import com.example.a216430_hannahhadirah_drnelson_project2.data.firestore.DonatedFood
import com.google.firebase.firestore.FirebaseFirestore

class DonationRepository {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("donated_food")

    fun donateFood(food: DonatedFood) {
        collection.add(food)
    }

    fun getAllDonations(onResult: (List<DonatedFood>) -> Unit) {
        collection
            .orderBy("donatedAt")
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val list = snapshot.documents.mapNotNull {
                        it.toObject(DonatedFood::class.java)
                    }
                    onResult(list)
                }
            }
    }
}