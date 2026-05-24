package com.portfolio.jobmarket.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.portfolio.jobmarket.data.JobCategories
import com.portfolio.jobmarket.data.User
import com.portfolio.jobmarket.ui.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowseScreen(
    viewModel: AppViewModel,
    onOpenPortfolio: (String) -> Unit,
    onOpenSearch: () -> Unit,
    onOpenProfile: () -> Unit,
) {
    val users by viewModel.users.collectAsState()
    var category by remember { mutableStateOf<String?>(null) }
    val isDark by viewModel.isDarkTheme

    val filtered = remember(users, category) {
        if (category == null) users else users.filter { it.jobCategory == category }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Discover talent") },
                actions = {
                    IconButton(onClick = { viewModel.toggleTheme() }) {
                        Icon(
                            imageVector = if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Toggle Theme"
                        )
                    }
                    IconButton(onClick = onOpenSearch) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = onOpenProfile) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = category == null,
                        onClick = { category = null },
                        label = { Text("All") }
                    )
                }
                items(JobCategories.all) { c ->
                    FilterChip(
                        selected = category == c,
                        onClick = { category = c },
                        label = { Text(c) }
                    )
                }
            }
            HorizontalDivider()
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filtered, key = { it.id }) { user ->
                    UserCard(user = user, onClick = { onOpenPortfolio(user.id) })
                }
            }
        }
    }
}

@Composable
fun UserCard(user: User, onClick: () -> Unit) {
    ElevatedCard(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Avatar(user)
            Column(modifier = Modifier.weight(1f)) {
                Text(user.name, fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleMedium)
                if (user.headline.isNotBlank()) {
                    Text(
                        user.headline,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                if (user.jobCategory.isNotBlank()) {
                    Spacer(Modifier.height(6.dp))
                    AssistChip(onClick = onClick, label = { Text(user.jobCategory) })
                }
            }
        }
    }
}

@Composable
fun Avatar(user: User, size: Int = 56) {
    val mod = Modifier.size(size.dp).clip(CircleShape)
    if (!user.photoUri.isNullOrBlank()) {
        AsyncImage(
            model = user.photoUri,
            contentDescription = user.name,
            modifier = mod
        )
    } else {
        Box(
            modifier = mod.background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                user.name.firstOrNull()?.uppercase() ?: "?",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
