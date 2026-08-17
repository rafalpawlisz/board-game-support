# Board Game Support

[![CI](https://github.com/rafalpawlisz/board-game-support/actions/workflows/ci.yml/badge.svg)](https://github.com/rafalpawlisz/board-game-support/actions/workflows/ci.yml)

A small Android companion app for the specific board games I play. Rather than a generic
dice-and-timer toolbox, every screen serves one game — so the tools stay simple, and a new one
is added only when a game actually calls for it.

## Games

- **Catan** — rolls two ordinary dice. The total is shown large with the two dice beneath it,
  because the table plays off the total.
- **Imago** — rolls an eight-sided die (1–8).
- **Wielki Zakład** — the first tap of a game picks a random colour (green, yellow or orange);
  every tap after that gives a number from 1 to 3. A 60-second round can be timed alongside it,
  started by hand because only the table knows which draws need it. **New game** brings the
  colour back.
- **5 sekund** — a five-second round: name your three answers before it runs out.

Roll results are drawn in all four corners of the screen, each rotated toward its edge, so
everyone sitting around the table can read them. Countdowns are shown at both ends of the screen,
the far one upside down, so two players facing each other can each read it and tap to start or
stop.

## Feel

- **Roll animation** — values tumble briefly and slow to a stop, like a real die. The final
  value is drawn up front, so the animation is purely cosmetic and never skews the odds.
- **Sound & haptics** — every roll and timer action gives audible and tactile feedback, so it
  works even with the phone muted. The last five seconds tick, and the round ends with a
  hard-to-miss triple beep and triple vibration, holding "0" on screen so a glance still tells you
  the time is up.
- **Stays awake** — the screen won't dim while a tool is open, so the phone can sit on the table
  mid-game.
- **Modern look** — edge-to-edge, Material 3 with dynamic colour (Material You) on Android 12+.

## Tech stack

- **Kotlin 2.4** with **Jetpack Compose** (Material 3) — Compose-only UI, no XML layouts
- **Navigation Compose** for screen navigation
- **Koin** for dependency injection
- **Coroutines** drive the roll animation and the countdown
- **minSdk 24** · **targetSdk 36** · **compileSdk 37** · **AGP 9.3** · **Gradle 9.7**
- Dependency versions live in a [Gradle version catalog](gradle/libs.versions.toml)

## Install

Signed APKs live on the [Releases page](https://github.com/rafalpawlisz/board-game-support/releases) —
download the newest one onto the phone and open it. Pushing a `vX.Y.Z` tag is what builds, signs and
publishes a release; the version name and code come from the tag.

## Building

Open the project in Android Studio and run the `app` configuration, or use the command line:

```bash
./gradlew assembleDebug     # build the debug APK
./gradlew installDebug      # build and install on a connected device/emulator
```

## Testing

Unit tests cover every ViewModel and the shared roll engine. They use virtual time
(`kotlinx-coroutines-test`), so the whole suite — including the 60-second countdown — runs in a
fraction of a second:

```bash
./gradlew testDebugUnitTest
```

## Continuous integration

Every push to `master` and every pull request runs the unit tests, Android Lint and the debug +
release builds on GitHub Actions. The debug APK and the test/lint reports are uploaded as build
artifacts.

## Project layout

```
app/src/main/java/io/github/rafalpawlisz/boardgamesupport/
├── MainActivity.kt      # single activity, hosts the Compose NavHost
├── MainApplication.kt   # Koin setup
├── Routes.kt            # navigation route constants
├── ui/                  # Composable screens + shared UI (FourValues, PlayButton, KeepScreenOn, …)
└── viewmodel/           # ViewModels + DiceRoller, ToneGenerator, Haptics
```
