package com.portfolio.jobmarket.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.portfolio.jobmarket.ui.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: AppViewModel,
    onEdit: () -> Unit,
    onAddProject: () -> Unit,
    onLogout: () -> Unit,
    onBack: () -> Unit,
) {
    val users by viewModel.users.collectAsState()
    val projects by viewModel.projects.collectAsState()
    val currentId by viewModel.currentUserId.collectAsState()
    val user = users.firstOrNull { it.id == currentId } ?: return
    val myProjects = projects.filter { it.ownerId == user.id }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your portfolio") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.Logout, contentDescription = "Logout")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddProject,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Add project") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { ProfileHeader(viewModel, user.id) }
            item {
                Text("Projects", style = MaterialTheme.typography.titleLarge)
            }
            items(myProjects, key = { it.id }) { project ->
                ProjectCard(
                    project = project,
                    onDelete = { viewModel.deleteProject(project.id) }
                )
            }
            if (myProjects.isEmpty()) {
                item {
                    Text(
                        "No projects yet. Tap “Add project” to showcase your work.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
