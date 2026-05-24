package com.portfolio.jobmarket.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.portfolio.jobmarket.ui.AppViewModel
import com.portfolio.jobmarket.ui.screens.AddProjectScreen
import com.portfolio.jobmarket.ui.screens.BrowseScreen
import com.portfolio.jobmarket.ui.screens.EditProfileScreen
import com.portfolio.jobmarket.ui.screens.LoginScreen
import com.portfolio.jobmarket.ui.screens.PortfolioDetailScreen
import com.portfolio.jobmarket.ui.screens.ProfileScreen
import com.portfolio.jobmarket.ui.screens.SearchScreen
import com.portfolio.jobmarket.ui.screens.SignupScreen

object Routes {
    const val LOGIN = "login"
    const val SIGNUP = "signup"
    const val BROWSE = "browse"
    const val SEARCH = "search"
    const val PROFILE = "profile"
    const val EDIT_PROFILE = "edit_profile"
    const val ADD_PROJECT = "add_project"
    const val PORTFOLIO = "portfolio/{userId}"
    fun portfolio(userId: String) = "portfolio/$userId"
}

@Composable
fun AppNav(viewModel: AppViewModel) {
    val nav = rememberNavController()
    val currentUserId by viewModel.currentUserId.collectAsState()
    val start = if (currentUserId == null) Routes.LOGIN else Routes.BROWSE

    NavHost(navController = nav, startDestination = start) {
        composable(Routes.LOGIN) {
            LoginScreen(
                viewModel = viewModel,
                onLoggedIn = {
                    nav.navigate(Routes.BROWSE) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onGoToSignup = { nav.navigate(Routes.SIGNUP) }
            )
        }
        composable(Routes.SIGNUP) {
            SignupScreen(
                viewModel = viewModel,
                onSignedUp = {
                    nav.navigate(Routes.EDIT_PROFILE) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onBack = { nav.popBackStack() }
            )
        }
        composable(Routes.BROWSE) {
            BrowseScreen(
                viewModel = viewModel,
                onOpenPortfolio = { id -> nav.navigate(Routes.portfolio(id)) },
                onOpenSearch = { nav.navigate(Routes.SEARCH) },
                onOpenProfile = { nav.navigate(Routes.PROFILE) },
            )
        }
        composable(Routes.SEARCH) {
            SearchScreen(
                viewModel = viewModel,
                onBack = { nav.popBackStack() },
                onOpenPortfolio = { id -> nav.navigate(Routes.portfolio(id)) },
            )
        }
        composable(Routes.PROFILE) {
            ProfileScreen(
                viewModel = viewModel,
                onEdit = { nav.navigate(Routes.EDIT_PROFILE) },
                onAddProject = { nav.navigate(Routes.ADD_PROJECT) },
                onLogout = {
                    viewModel.logout()
                    nav.navigate(Routes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onBack = { nav.popBackStack() }
            )
        }
        composable(Routes.EDIT_PROFILE) {
            EditProfileScreen(
                viewModel = viewModel,
                onDone = { nav.popBackStack() }
            )
        }
        composable(Routes.ADD_PROJECT) {
            AddProjectScreen(
                viewModel = viewModel,
                onDone = { nav.popBackStack() }
            )
        }
        composable(
            route = Routes.PORTFOLIO,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStack ->
            val userId = backStack.arguments?.getString("userId").orEmpty()
            PortfolioDetailScreen(
                viewModel = viewModel,
                userId = userId,
                onBack = { nav.popBackStack() }
            )
        }
    }
}
