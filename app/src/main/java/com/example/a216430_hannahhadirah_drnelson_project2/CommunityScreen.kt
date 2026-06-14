package com.example.a216430_hannahhadirah_drnelson_project2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.a216430_hannahhadirah_drnelson_project2.data.firestore.DonatedFood
import com.example.a216430_hannahhadirah_drnelson_project2.data.repository.DonationRepository
import com.example.a216430_hannahhadirah_drnelson_project2.LocationHelper

@Composable
fun CommunityScreen(viewModel: FoodViewModel) {

    var donatedList by remember { mutableStateOf(listOf<DonatedFood>()) }
    val repo = remember { DonationRepository() }
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->

        if (isGranted) {
            LocationHelper(context).getCurrentLocation { lat, lon ->

                val uri = Uri.parse("geo:$lat,$lon?q=food+bank+near+me")

                val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                    setPackage("com.google.android.apps.maps")
                }

                try {
                    context.startActivity(intent)
                } catch (e: Exception) {
                    context.startActivity(
                        Intent(Intent.ACTION_VIEW, uri)
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        repo.getAllDonations {
            donatedList = it
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f))
            .padding(16.dp)
    ) {

        // header
        AppHeader(
            title = "Community",
            subtitle = "Food sharing network",
            showProfile = false
        )

        Spacer(modifier = Modifier.height(16.dp))

        // GPS button
        Button(
            onClick = {
                permissionLauncher.launch(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("📍 Find Nearby Donation Centres")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // empty state
        if (donatedList.isEmpty()) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("🌍", fontSize = 40.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "No Donations Yet",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        "Be the first to share food with the community!",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

        } else {

            // the donated food list
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(donatedList) { food ->

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {

                        Column(modifier = Modifier.padding(16.dp)) {

                            Text(
                                "🍱 ${food.name}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                "📅 Expiry: ${food.expiryDate}",
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                "🌍 Shared with community",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}