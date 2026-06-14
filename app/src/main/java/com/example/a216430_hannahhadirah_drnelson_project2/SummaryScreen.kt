package com.example.a216430_hannahhadirah_drnelson_project2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.StrokeCap


@Composable
fun SummaryScreen(navController: NavController, viewModel: FoodViewModel) {

    val foodList by viewModel.foodList.collectAsState()
    val totalItems = foodList.size
    val expired = foodList.count {
        viewModel.getDaysLeft(it.expiryDate) < 0
    }
    val expiringSoon = foodList.count { food ->
        val daysLeft = viewModel.getDaysLeft(food.expiryDate)
        daysLeft in 0..3
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f)
            )
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        // header
        AppHeader(
            title = "Summary",
            subtitle = "Food tracking analytics",
            showProfile = false
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            "Monitor your food usage and reduce unnecessary waste.",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // top analytics row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            AnalyticsCard(
                title = "Total",
                value = totalItems.toString(),
                emoji = "📦",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f)
            )

            AnalyticsCard(
                title = "Urgent",
                value = expiringSoon.toString(),
                emoji = "⚠",
                color = Color(0xFFFF9800),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // second row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            AnalyticsCard(
                title = "Expired",
                value = expired.toString(),
                emoji = "❌",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.weight(1f),
            )

            AnalyticsCard(
                title = "Fresh",
                value = (totalItems - expiringSoon - expired).coerceAtLeast(0).toString(),
                emoji = "🌱",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // food status section
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {

            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    "📊 Food Status Overview",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                StatusProgress(
                    label = "Fresh Food",
                    value = totalItems - expiringSoon - expired,
                    total = totalItems,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(10.dp))

                StatusProgress(
                    label = "Expiring Soon",
                    value = expiringSoon,
                    total = totalItems,
                    color = MaterialTheme.colorScheme.tertiary
                )

                Spacer(modifier = Modifier.height(10.dp))

                StatusProgress(
                    label = "Expired",
                    value = expired,
                    total = totalItems,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // sdg impact
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {

            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    "🌍 SDG Goal 12",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "FreshKeeper encourages responsible consumption by helping users reduce household food waste through better tracking and planning."
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // motivation card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {

            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    "💡 Smart Reminder",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Check your expiring items daily to avoid unnecessary food waste and save money."
                )
            }
        }
    }
}

@Composable
fun AnalyticsCard(
    title: String,
    value: String,
    emoji: String,
    color: Color,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)

    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            Text(
                emoji,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                value,
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = color
            )

            Text(
                title,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun StatusProgress(
    label: String,
    value: Int,
    total: Int,
    color: Color
) {

    val progress =
        if (total == 0) 0f
        else value.toFloat() / total.toFloat()

    Column {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label)
            Text("$value")
        }

        Spacer(modifier = Modifier.height(4.dp))

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            color = color,
            trackColor = color.copy(alpha = 0.2f),
            strokeCap = StrokeCap.Round
        )
    }
}