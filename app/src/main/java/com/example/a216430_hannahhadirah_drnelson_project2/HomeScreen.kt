package com.example.a216430_hannahhadirah_drnelson_project2

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.animation.animateContentSize
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@Composable
fun HomeScreen(navController: NavController, viewModel: FoodViewModel) {

    var expandedRecent by remember { mutableStateOf(false) }
    var expandedExpiry by remember { mutableStateOf(false) }

    val foodList by viewModel.foodList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f)
            )
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
//header
        AppHeader(
            title = "FreshKeeper",
            subtitle = "Reduce waste, save more"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // summary card
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = R.drawable.foodie),
                    contentDescription = "Food Icon",
                    modifier = Modifier.size(60.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                // info text
                Column {
                    Text(
                        "${foodList.size} Items Added",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    Text(
                        "Track your food and reduce waste",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))


        Spacer(modifier = Modifier.height(18.dp))

        // recently added food
        ExpandCard(
            title = "Recently Added",
            expanded = expandedRecent,
            onClick = { expandedRecent = !expandedRecent },
        ) {
            if (foodList.isEmpty()) {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Text(
                        "📦",
                        fontSize = 36.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        "No food items yet",
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        "Start adding food to track expiry dates",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                foodList.takeLast(3).forEach {
                    Text("• ${it.name}")
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // expiring soon items
        ExpandCard(
            title = "Expiring Soon",
            expanded = expandedExpiry,
            onClick = { expandedExpiry = !expandedExpiry }
        ) {

            val expiringList = foodList.filter {
                viewModel.getDaysLeft(it.expiryDate) <= 3
            }

            if (expiringList.isEmpty()) {
                Text("No urgent items")
            } else {
                expiringList.forEach { food ->

                    val daysLeft = viewModel.getDaysLeft(food.expiryDate)

                    val color = when {
                        daysLeft <= 1 -> MaterialTheme.colorScheme.error
                        daysLeft <= 2 -> MaterialTheme.colorScheme.tertiary
                        else -> MaterialTheme.colorScheme.primary
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Box(
                            modifier = Modifier
                                .background(color, RoundedCornerShape(12.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("$daysLeft d", color = Color.White, fontSize = 12.sp)
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(food.name)
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }

        // info cards
        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // food tip
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(140.dp),
                shape = RoundedCornerShape(18.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {

                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        "💡 Food Tip",
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "Store vegetables in airtight containers to keep them fresh longer.",
                        fontSize = 12.sp
                    )
                }
            }

            // sdg card
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(140.dp),
                shape = RoundedCornerShape(18.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {

                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        "🌍 SDG 12",
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "FreshKeeper helps reduce food waste through smart tracking.",
                        fontSize = 12.sp
                    )
                }
            }
        }




        // recipe preview
        Spacer(modifier = Modifier.height(14.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {

            Column(modifier = Modifier.padding(14.dp)) {

                Text(
                    "🍽 Suggested Recipe",
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                if (foodList.isEmpty()) {
                    Text("Add food items to get recipe suggestions.")
                } else {
                    Text("Go to Recipes tab to see meal suggestions")
                }
            }
        }
    }
}



//for expanded card
@Composable
fun ExpandCard(
    title: String,
    expanded: Boolean,
    onClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(14.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(title, fontWeight = FontWeight.Bold)
                Text(if (expanded) "▲" else "▼")
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(10.dp))
                content()
            }
        }
    }
}


//it creates a reusable ui header across all screens to maintain consistent design.
@Composable
fun AppHeader(
    title: String,
    subtitle: String,
    showProfile: Boolean = true,
    showBack: Boolean = false,
    onBackClick: () -> Unit = {}
) {


    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.tertiary
                        )
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                // profile + welcome
                Row(verticalAlignment = Alignment.CenterVertically) {

                    if (showBack) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(6.dp))
                    }

                    if (showProfile) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .background(
                                    MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f),
                                    RoundedCornerShape(50)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("👤")
                        }

                        Spacer(modifier = Modifier.width(10.dp))
                    }

                    Column {

                        Text(
                            text = "Welcome, Hannah 👋",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.95f)
                        )
                    }
                }

                // right side title
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    Text(
                        subtitle,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                    )
                }
            }
        }
    }
}
