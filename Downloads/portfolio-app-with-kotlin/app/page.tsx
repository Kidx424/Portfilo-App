export default function Page() {
  return (
    <main className="min-h-screen bg-background px-6 py-16 text-foreground">
      <div className="mx-auto flex w-full max-w-2xl flex-col gap-8">
        <header className="flex flex-col gap-3">
          <span className="font-mono text-xs uppercase tracking-widest text-muted-foreground">
            Kotlin Android Source
          </span>
          <h1 className="text-balance text-3xl font-semibold tracking-tight">
            Portfolio Market — Job Marketplace App
          </h1>
          <p className="text-pretty leading-relaxed text-muted-foreground">
            A native Android app written in Kotlin with Jetpack Compose + Material 3. Users sign up,
            build a portfolio (photo, bio, skills, projects), browse and search talent by job
            category, and share public portfolio pages. Source lives in the{" "}
            <code className="rounded bg-muted px-1.5 py-0.5 font-mono text-sm">android/</code> folder
            of this project — v0&apos;s preview can&apos;t run Kotlin, so open it in Android Studio.
          </p>
        </header>

        <section className="rounded-lg border border-border bg-card p-6">
          <h2 className="mb-3 text-lg font-semibold">How to run</h2>
          <ol className="list-decimal space-y-2 pl-5 text-sm leading-relaxed text-muted-foreground">
            <li>
              Download this project (top-right menu) and open the{" "}
              <code className="rounded bg-muted px-1 font-mono">android/</code> folder in Android
              Studio Hedgehog or newer.
            </li>
            <li>Wait for Gradle sync. Min SDK 26, Kotlin 2.1, Compose BOM 2024.02.</li>
            <li>
              Run on an emulator or device. Try the seeded login{" "}
              <code className="rounded bg-muted px-1 font-mono">ada@example.com / password</code>.
            </li>
          </ol>
        </section>

        <section className="rounded-lg border border-border bg-card p-6">
          <h2 className="mb-3 text-lg font-semibold">What&apos;s included</h2>
          <ul className="grid grid-cols-1 gap-2 text-sm text-muted-foreground sm:grid-cols-2">
            <li>• Email/password auth (in-memory)</li>
            <li>• Profile editing + photo picker</li>
            <li>• Projects gallery with images & tags</li>
            <li>• Skills + job-category filtering</li>
            <li>• Public shareable portfolio page</li>
            <li>• Search & browse by name/skill/category</li>
          </ul>
        </section>

        <section className="rounded-lg border border-border bg-card p-6">
          <h2 className="mb-3 text-lg font-semibold">Project structure</h2>
          <pre className="overflow-x-auto rounded bg-muted p-4 font-mono text-xs leading-relaxed">
{`android/
  settings.gradle.kts
  build.gradle.kts
  app/
    build.gradle.kts
    src/main/
      AndroidManifest.xml
      java/com/portfolio/jobmarket/
        MainActivity.kt
        data/        (Models, AuthRepository, PortfolioRepository)
        ui/
          AppViewModel.kt
          theme/     (Color, Type, Theme)
          navigation/AppNav.kt
          screens/   (Login, Signup, Browse, Search,
                     Profile, EditProfile, AddProject,
                     PortfolioDetail, Shared)`}
          </pre>
        </section>

        <p className="text-sm text-muted-foreground">
          Repositories are in-memory so data resets on app restart. Swap{" "}
          <code className="rounded bg-muted px-1 font-mono">PortfolioRepository</code> /{" "}
          <code className="rounded bg-muted px-1 font-mono">AuthRepository</code> for Room or a
          backend (Supabase, Firebase, Ktor) without touching the UI layer.
        </p>
      </div>
    </main>
  );
}
