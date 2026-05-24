package com.portfolio.jobmarket.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * In-memory auth. Replace with a real backend (Supabase, Firebase, Ktor + JWT, etc.).
 */
class AuthRepository(private val portfolioRepository: PortfolioRepository) {

    private val _currentUserId = MutableStateFlow<String?>(null)
    val currentUserId: StateFlow<String?> = _currentUserId.asStateFlow()

    fun signUp(email: String, password: String, name: String): Result<User> {
        if (portfolioRepository.findUserByEmail(email) != null) {
            return Result.failure(IllegalStateException("Email already registered"))
        }
        if (email.isBlank() || password.length < 6 || name.isBlank()) {
            return Result.failure(IllegalArgumentException("Invalid credentials"))
        }
        val user = User(email = email.trim(), password = password, name = name.trim())
        portfolioRepository.upsertUser(user)
        _currentUserId.value = user.id
        return Result.success(user)
    }

    fun login(email: String, password: String): Result<User> {
        val user = portfolioRepository.findUserByEmail(email.trim())
            ?: return Result.failure(IllegalArgumentException("No account for that email"))
        if (user.password != password) {
            return Result.failure(IllegalArgumentException("Incorrect password"))
        }
        _currentUserId.value = user.id
        return Result.success(user)
    }

    fun logout() {
        _currentUserId.value = null
    }
}
