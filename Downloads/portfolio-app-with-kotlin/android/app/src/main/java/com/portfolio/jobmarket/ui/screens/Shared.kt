package com.portfolio.jobmarket.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.portfolio.jobmarket.data.Project
import com.portfolio.jobmarket.ui.AppViewModel

@Composable
fun ProfileHeader(viewModel: AppViewModel, userId: String) {
    val users by viewModel.users.collectAsState()
    val user = users.firstOrNull { it.id == userId } ?: return

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Avatar(user, size = 72)
                Column(modifier = Modifier.weight(1f)) {
                    Text(user.name, style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold)
                    if (user.headline.isNotBlank()) {
                        Text(
                            user.headline,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    if (user.location.isNotBlank()) {
                        Text(
                            user.location,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            if (user.jobCategory.isNotBlank()) {
                AssistChip(onClick = {}, label = { Text(user.jobCategory) })
            }
            if (user.bio.isNotBlank()) {
                Text(user.bio, style = MaterialTheme.typography.bodyLarge)
            }
            if (user.skills.isNotEmpty()) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(user.skills) { skill ->
                        SuggestionChip(onClick = {}, label = { Text(skill) })
                    }
                }
            }
        }
    }
}

@Composable
fun ProjectCard(project: Project, onDelete: (() -> Unit)? = null) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
            if (!project.imageUri.isNullOrBlank()) {
                AsyncImage(
                    model = project.imageUri,
                    contentDescription = project.title,
                    modifier = Modifier.fillMaxWidth().height(160.dp)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    project.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                if (onDelete != null) {
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
            Text(project.description, style = MaterialTheme.typography.bodyMedium)
            if (project.tags.isNotEmpty()) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(project.tags) { tag ->
                        SuggestionChip(onClick = {}, label = { Text(tag) })
                    }
                }
            }
        }
    }
}
