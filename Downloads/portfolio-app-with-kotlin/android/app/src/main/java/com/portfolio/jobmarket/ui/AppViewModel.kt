package com.portfolio.jobmarket.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.portfolio.jobmarket.data.AuthRepository
import com.portfolio.jobmarket.data.PortfolioRepository
import com.portfolio.jobmarket.data.Project
import com.portfolio.jobmarket.data.User
import kotlinx.coroutines.flow.StateFlow

class AppViewModel : ViewModel() {

    val portfolioRepo = PortfolioRepository()
    val authRepo = AuthRepository(portfolioRepo)

    val currentUserId: StateFlow<String?> = authRepo.currentUserId
    val users: StateFlow<List<User>> = portfolioRepo.users
    val projects: StateFlow<List<Project>> = portfolioRepo.projects

    var isDarkTheme = mutableStateOf(true)

    fun toggleTheme() {
        isDarkTheme.value = !isDarkTheme.value
    }

    fun currentUser(): User? = currentUserId.value?.let { portfolioRepo.findUser(it) }

    fun signUp(email: String, password: String, name: String) =
        authRepo.signUp(email, password, name)

    fun login(email: String, password: String) = authRepo.login(email, password)

    fun logout() = authRepo.logout()

    fun updateProfile(updated: User) = portfolioRepo.upsertUser(updated)

    fun addProject(project: Project) = portfolioRepo.addProject(project)

    fun deleteProject(id: String) = portfolioRepo.deleteProject(id)

    fun search(query: String, category: String?) = portfolioRepo.search(query, category)
}
