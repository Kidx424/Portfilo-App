package com.portfolio.jobmarket.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.content.Intent
import com.portfolio.jobmarket.ui.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioDetailScreen(
    viewModel: AppViewModel,
    userId: String,
    onBack: () -> Unit,
) {
    val users by viewModel.users.collectAsState()
    val projects by viewModel.projects.collectAsState()
    val user = users.firstOrNull { it.id == userId }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(user?.name ?: "Portfolio") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (user != null) {
                        IconButton(onClick = {
                            val text = buildString {
                                appendLine("${user.name} — ${user.headline}")
                                if (user.jobCategory.isNotBlank()) appendLine("Category: ${user.jobCategory}")
                                if (user.skills.isNotEmpty()) appendLine("Skills: ${user.skills.joinToString()}")
                                appendLine("View their portfolio in Portfolio Market.")
                            }
                            val send = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, text)
                            }
                            context.startActivity(Intent.createChooser(send, "Share portfolio"))
                        }) {
                            Icon(Icons.Default.Share, contentDescription = "Share")
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (user == null) {
            Box(Modifier.padding(padding).fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("User not found")
            }
            return@Scaffold
        }
        val userProjects = projects.filter { it.ownerId == user.id }
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { ProfileHeader(viewModel, user.id) }
            item { Text("Projects", style = MaterialTheme.typography.titleLarge) }
            items(userProjects, key = { it.id }) { p -> ProjectCard(p) }
            if (userProjects.isEmpty()) {
                item {
                    Text(
                        "This user hasn't added projects yet.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
