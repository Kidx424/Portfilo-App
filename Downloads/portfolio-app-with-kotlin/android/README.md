# Portfolio Job Marketplace - Android (Kotlin)

A Kotlin Android app where users build portfolios and apply to jobs by category/facility. Built with Jetpack Compose + Material 3.

## Features

- Email/password sign up & login (local, in-memory)
- Editable profile with photo upload (gallery picker)
- Projects / work-samples gallery with image
- Skills and job-category filtering
- Public shareable portfolio page (per user)
- Search & browse other users by name, skill, or category

## How to run

1. Open the `android/` folder in **Android Studio** (Hedgehog or newer).
2. Let Gradle sync. Min SDK 26, Target SDK 34, Kotlin 2.1.
3. Run on an emulator or physical device (API 26+).

## Architecture

- `data/` — Models + in-memory repositories (`AuthRepository`, `PortfolioRepository`) backed by `StateFlow`. Swap these for Room / a backend later without touching UI.
- `ui/screens/` — Compose screens (Login, Signup, Browse, Search, Profile, EditProfile, PortfolioDetail, AddProject).
- `ui/navigation/AppNav.kt` — `NavHost` wiring with type-safe routes.
- `ui/AppViewModel.kt` — Single shared `ViewModel` exposing repository state.
- `ui/theme/` — Material 3 theme.

## Notes

- Auth and storage are in-memory for the scaffold; data resets on app restart. Hook up Room or a backend (Supabase/Firebase/Ktor) where the repositories are.
- Images are referenced by `Uri` from the system photo picker.
