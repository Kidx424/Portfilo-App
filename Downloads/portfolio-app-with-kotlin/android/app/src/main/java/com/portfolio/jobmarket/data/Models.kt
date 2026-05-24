package com.portfolio.jobmarket.data

import java.util.UUID

data class User(
    val id: String = UUID.randomUUID().toString(),
    val email: String,
    val password: String, // plaintext for scaffold; replace with hashed auth
    val name: String,
    val headline: String = "",
    val bio: String = "",
    val photoUri: String? = null,
    val jobCategory: String = "",
    val skills: List<String> = emptyList(),
    val location: String = "",
)

data class Project(
    val id: String = UUID.randomUUID().toString(),
    val ownerId: String,
    val title: String,
    val description: String,
    val imageUri: String? = null,
    val tags: List<String> = emptyList(),
)

object JobCategories {
    val all = listOf(
        "Software Engineering",
        "Design",
        "Product",
        "Marketing",
        "Healthcare",
        "Education",
        "Construction",
        "Hospitality",
        "Finance",
        "Logistics",
    )
}
