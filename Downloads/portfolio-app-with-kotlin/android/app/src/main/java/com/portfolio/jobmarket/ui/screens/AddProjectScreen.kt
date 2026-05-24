package com.portfolio.jobmarket.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.portfolio.jobmarket.data.Project
import com.portfolio.jobmarket.ui.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProjectScreen(
    viewModel: AppViewModel,
    onDone: () -> Unit,
) {
    val user = viewModel.currentUser() ?: return
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var tagsText by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<String?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    val picker = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri -> if (uri != null) imageUri = uri.toString() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New project") },
                actions = {
                    TextButton(onClick = {
                        if (title.isBlank()) {
                            error = "Title required"
                            return@TextButton
                        }
                        viewModel.addProject(
                            Project(
                                ownerId = user.id,
                                title = title.trim(),
                                description = description.trim(),
                                imageUri = imageUri,
                                tags = tagsText.split(",")
                                    .map { it.trim() }.filter { it.isNotEmpty() }
                            )
                        )
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
            if (!imageUri.isNullOrBlank()) {
                AsyncImage(
                    model = imageUri, contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(180.dp)
                )
            }
            OutlinedButton(onClick = {
                picker.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }) {
                Icon(Icons.Default.PhotoCamera, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(if (imageUri == null) "Add cover image" else "Change image")
            }
            OutlinedTextField(value = title, onValueChange = { title = it },
                label = { Text("Title") }, singleLine = true,
                modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = description, onValueChange = { description = it },
                label = { Text("Description") }, minLines = 3,
                modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = tagsText, onValueChange = { tagsText = it },
                label = { Text("Tags (comma-separated)") },
                modifier = Modifier.fillMaxWidth())
            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        }
    }
}
