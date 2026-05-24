package com.portfolio.jobmarket.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * In-memory portfolio store. Swap with Room / remote source later.
 */
class PortfolioRepository {

    private val _users = MutableStateFlow<List<User>>(seedUsers())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    private val _projects = MutableStateFlow<List<Project>>(seedProjects(_users.value))
    val projects: StateFlow<List<Project>> = _projects.asStateFlow()

    fun findUserByEmail(email: String): User? =
        _users.value.firstOrNull { it.email.equals(email, ignoreCase = true) }

    fun findUser(id: String): User? = _users.value.firstOrNull { it.id == id }

    fun projectsForUser(userId: String): List<Project> =
        _projects.value.filter { it.ownerId == userId }

    fun upsertUser(user: User) {
        _users.value = _users.value.toMutableList().also { list ->
            val idx = list.indexOfFirst { it.id == user.id }
            if (idx >= 0) list[idx] = user else list.add(user)
        }
    }

    fun addProject(project: Project) {
        _projects.value = _projects.value + project
    }

    fun deleteProject(projectId: String) {
        _projects.value = _projects.value.filterNot { it.id == projectId }
    }

    fun search(query: String, category: String?): List<User> {
        val q = query.trim().lowercase()
        return _users.value.filter { user ->
            val matchesQuery = q.isBlank() ||
                user.name.lowercase().contains(q) ||
                user.headline.lowercase().contains(q) ||
                user.skills.any { it.lowercase().contains(q) }
            val matchesCategory = category.isNullOrBlank() || user.jobCategory == category
            matchesQuery && matchesCategory
        }
    }

    private fun seedUsers(): List<User> = listOf(
        User(
            email = "ada@example.com", password = "password",
            name = "Ada Lovelace", headline = "Backend engineer | Distributed systems",
            bio = "10+ years building reliable services.",
            jobCategory = "Software Engineering",
            skills = listOf("Kotlin", "Go", "Postgres", "Kubernetes"),
            location = "Remote"
        ),
        User(
            email = "kai@example.com", password = "password",
            name = "Kai Mensah", headline = "Product designer with a systems mindset",
            bio = "Design systems, mobile, accessibility.",
            jobCategory = "Design",
            skills = listOf("Figma", "Design Systems", "Prototyping"),
            location = "Accra"
        ),
        User(
            email = "lin@example.com", password = "password",
            name = "Lin Park", headline = "Registered nurse",
            bio = "ICU specialist, 6 yrs.",
            jobCategory = "Healthcare",
            skills = listOf("Critical Care", "Triage", "Patient Education"),
            location = "Seoul"
        ),
    )

    private fun seedProjects(users: List<User>): List<Project> {
        val ada = users.first { it.email == "ada@example.com" }
        val kai = users.first { it.email == "kai@example.com" }
        return listOf(
            Project(
                ownerId = ada.id,
                title = "Realtime metrics pipeline",
                description = "Kafka + Kotlin Flow ingest at 200k events/s.",
                tags = listOf("Kotlin", "Kafka")
            ),
            Project(
                ownerId = ada.id,
                title = "Auth service rewrite",
                description = "Migrated legacy Java auth to Ktor; latency -60%.",
                tags = listOf("Ktor", "JWT")
            ),
            Project(
                ownerId = kai.id,
                title = "Banking app redesign",
                description = "Led a 4-month redesign; CSAT +18 pts.",
                tags = listOf("Mobile", "Figma")
            ),
        )
    }
}
