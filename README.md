# Chonchkhi 🦴

**ჩონჩხი** (*chonchkhi* — Georgian for "skeleton") — a clean, opinionated **multi-module
Android starter** to skip the boilerplate on your next app.

Clone it (or use it as a GitHub *template*) and you already have a version-catalog-driven,
multi-module Compose project with CI, unit tests, and sane defaults wired up.

## What's inside

- 🧱 **Multi-module** layout — an `:app` (Compose application) that depends on a reusable
  `:core` library module, so business logic stays out of the UI.
- 📚 **Version catalog** (`gradle/libs.versions.toml`) — one place for every dependency and plugin.
- 🎨 **Jetpack Compose + Material 3** — dynamic color, light/dark, edge-to-edge, adaptive icon.
- ✅ **CI** (GitHub Actions) — assemble + Android lint + unit tests on every push/PR.
- 🧪 **Unit tests** wired in the `:core` module (JVM, fast).
- 🤖 **Dependabot**, PR + issue templates, `.editorconfig` — repo hygiene out of the box.

## Structure

```
app/     Compose application (applicationId io.github.meko123456.chonchkhi)
core/    Android library — pure/business logic, unit-tested (depends-on target)
gradle/  libs.versions.toml — the single source of dependency versions
config/  detekt config (optional static analysis)
.github/ CI, dependabot, PR & issue templates
```

## Toolchain

- Gradle 9.3.1 · AGP 9.1.1 · Kotlin 2.3.21 · Compose BOM 2026.06.01
- compileSdk 36 · minSdk 26 · Java 17

## Use it

```bash
# As a template: click "Use this template" on GitHub, or:
git clone https://github.com/Meko123456/Chonchkhi.git my-app
cd my-app
./gradlew :app:assembleDebug     # build
./gradlew test                   # unit tests
./gradlew :app:lintDebug         # lint
```

Then rename the `io.github.meko123456.chonchkhi` package + `applicationId` to your own and start
adding feature modules alongside `:core`.

## License

[MIT](LICENSE)
