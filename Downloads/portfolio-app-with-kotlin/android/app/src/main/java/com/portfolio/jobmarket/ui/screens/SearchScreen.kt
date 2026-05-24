package com.portfolio.jobmarket.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.portfolio.jobmarket.data.JobCategories
import com.portfolio.jobmarket.ui.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: AppViewModel,
    onBack: () -> Unit,
    onOpenPortfolio: (String) -> Unit,
) {
    var query by remember { mutableStateOf("") }
    var category by remember { mutableStateOf<String?>(null) }
    val users by viewModel.users.collectAsState()

    val results = remember(query, category, users) {
        viewModel.search(query, category)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            OutlinedTextField(
                value = query, onValueChange = { query = it },
                label = { Text("Name, skill, or headline") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = category == null,
                        onClick = { category = null },
                        label = { Text("Any category") }
                    )
                }
                items(JobCategories.all) { c ->
                    FilterChip(
                        selected = category == c,
                        onClick = { category = if (category == c) null else c },
                        label = { Text(c) }
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            HorizontalDivider()
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(results, key = { it.id }) { user ->
                    UserCard(user = user, onClick = { onOpenPortfolio(user.id) })
                }
                if (results.isEmpty()) {
                    item {
                        Text(
                            "No matches. Try a different query or clear the category filter.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
