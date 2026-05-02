# Back-to-Front

> A dark "hacker" persona Android app — Reverse Engineering Suite  
> Built with **Kotlin + Jetpack Compose + Material 3**

---

## Design

| Token | Value |
|-------|-------|
| Background | Very dark purple `#0D0A1E` |
| Primary text / accent | Baby blue `#89CFF0` |
| Neon accent | Cyan `#00E5FF` |
| Font | `Monospace` (system) |

Features:
- Dark-purple `ColorScheme` applied app-wide via `BackToFrontTheme`
- Baby-blue typography via `BackToFrontTypography`  
- Three styles of **fancy animated buttons** (glow, neon-outline, gradient) — each with a spring-based press-scale animation
- Polished **Home screen** with hero header, feature cards, and a status line
- Edge-to-edge display, dark status bar

---

## Requirements

| Tool | Version |
|------|---------|
| Android Studio | Hedgehog (2023.1.1) or newer |
| JDK | 17 (bundled with Android Studio) |
| Android SDK | compileSdk 34, minSdk **26** |

---

## Build & Run

### Android Studio (recommended)

1. **Clone** the repo  
   ```bash
   git clone https://github.com/kay6888/Back-to-Front.git
   cd Back-to-Front
   ```
2. Open the project root in **Android Studio** (`File → Open`).  
   Android Studio will automatically download the Gradle wrapper and all
   dependencies (requires internet on first open).
3. Connect an Android device (API 26+) or create an AVD.  
4. Click **▶ Run 'app'** (or press `Shift+F10`).

### Command line

```bash
# macOS / Linux
./gradlew assembleDebug

# Windows
gradlew.bat assembleDebug
```

The resulting APK is at `app/build/outputs/apk/debug/app-debug.apk`.

---

## Project Structure

```
Back-to-Front/
├── app/
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── kotlin/com/backtorfront/app/
│       │   ├── MainActivity.kt
│       │   └── ui/
│       │       ├── theme/
│       │       │   ├── Color.kt        ← dark-purple + baby-blue palette
│       │       │   ├── Theme.kt        ← BackToFrontTheme (Material 3 dark)
│       │       │   └── Type.kt         ← Monospace typography
│       │       └── screens/
│       │           └── HomeScreen.kt   ← polished home screen + buttons
│       └── res/values/
│           ├── strings.xml
│           └── themes.xml
├── gradle/
│   ├── libs.versions.toml              ← version catalog
│   └── wrapper/gradle-wrapper.properties
└── settings.gradle.kts
```
