package com.portfolio.jobmarket.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.portfolio.jobmarket.data.JobCategories
import com.portfolio.jobmarket.ui.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    viewModel: AppViewModel,
    onDone: () -> Unit,
) {
    val user = viewModel.currentUser() ?: return

    var name by remember { mutableStateOf(user.name) }
    var headline by remember { mutableStateOf(user.headline) }
    var bio by remember { mutableStateOf(user.bio) }
    var location by remember { mutableStateOf(user.location) }
    var category by remember { mutableStateOf(user.jobCategory) }
    var skillsText by remember { mutableStateOf(user.skills.joinToString(", ")) }
    var photoUri by remember { mutableStateOf(user.photoUri) }
    var categoryMenu by remember { mutableStateOf(false) }

    val photoPicker = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri -> if (uri != null) photoUri = uri.toString() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit profile") },
                actions = {
                    TextButton(onClick = {
                        val updated = user.copy(
                            name = name.trim(),
                            headline = headline.trim(),
                            bio = bio.trim(),
                            location = location.trim(),
                            jobCategory = category,
                            skills = skillsText.split(",").map { it.trim() }.filter { it.isNotEmpty() },
                            photoUri = photoUri,
                        )
                        viewModel.updateProfile(updated)
                        onDone()
                    }) { Text("Save") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(80.dp).clip(CircleShape)) {
                    if (!photoUri.isNullOrBlank()) {
                        AsyncImage(model = photoUri, contentDescription = null,
                            modifier = Modifier.fillMaxSize())
                    } else {
                        Surface(color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.fillMaxSize()) {}
                    }
                }
                OutlinedButton(onClick = {
                    photoPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }) {
                    Icon(Icons.Default.PhotoCamera, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Change photo")
                }
            }
            OutlinedTextField(value = name, onValueChange = { name = it },
                label = { Text("Full name") }, singleLine = true,
                modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = headline, onValueChange = { headline = it },
                label = { Text("Headline") }, singleLine = true,
                modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = location, onValueChange = { location = it },
                label = { Text("Location") }, singleLine = true,
                modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = bio, onValueChange = { bio = it },
                label = { Text("Bio") }, minLines = 3,
                modifier = Modifier.fillMaxWidth())

            ExposedDropdownMenuBox(
                expanded = categoryMenu,
                onExpandedChange = { categoryMenu = !categoryMenu }
            ) {
                OutlinedTextField(
                    value = category.ifBlank { "Select category" },
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Job category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(categoryMenu) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = categoryMenu,
                    onDismissRequest = { categoryMenu = false }
                ) {
                    JobCategories.all.forEach { c ->
                        DropdownMenuItem(
                            text = { Text(c) },
                            onClick = { category = c; categoryMenu = false }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = skillsText, onValueChange = { skillsText = it },
                label = { Text("Skills (comma-separated)") },
                modifier = Modifier.fillMaxWidth()
            )

            if (skillsText.isNotBlank()) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val skills = skillsText.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                    items(skills) { s -> SuggestionChip(onClick = {}, label = { Text(s) }) }
                }
            }
        }
    }
}
