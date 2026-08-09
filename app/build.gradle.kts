import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

// Release signing is optional. Credentials come from an untracked keystore.properties
// (local builds) or from environment variables (CI secrets); with neither, the release
// build simply stays unsigned, so clean clones and CI without secrets still build.
val keystoreProperties = Properties().apply {
    val propertiesFile = rootProject.file("keystore.properties")
    if (propertiesFile.exists()) {
        propertiesFile.inputStream().use { load(it) }
    }
}

fun signingValue(property: String, environmentVariable: String): String? =
    keystoreProperties.getProperty(property) ?: System.getenv(environmentVariable)

// Releases are versioned from the git tag: CI builds with -PappVersion=1.2.3. Local
// builds fall back to a placeholder, so day-to-day work needs no version bookkeeping.
val appVersion = findProperty("appVersion")?.toString() ?: "1.0-dev"

// 1.2.3 -> 10203, so the code always grows with the version. Anything that is not a
// three-part version (the local placeholder) stays at 1.
fun versionCodeOf(version: String): Int {
    val parts = version.split(".").mapNotNull { it.toIntOrNull() }
    if (parts.size < 3) return 1
    return parts[0] * 10_000 + parts[1] * 100 + parts[2]
}

android {
    namespace = "io.github.rafalpawlisz.boardgamesupport"
    compileSdk = 37

    defaultConfig {
        applicationId = "io.github.rafalpawlisz.boardgamesupport"
        minSdk = 24
        targetSdk = 36
        versionCode = versionCodeOf(appVersion)
        versionName = appVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        val storeFilePath = signingValue("storeFile", "KEYSTORE_FILE")
        if (storeFilePath != null && file(storeFilePath).exists()) {
            create("release") {
                storeFile = file(storeFilePath)
                storePassword = signingValue("storePassword", "KEYSTORE_PASSWORD")
                keyAlias = signingValue("keyAlias", "KEY_ALIAS")
                keyPassword = signingValue("keyPassword", "KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        release {
            signingConfigs.findByName("release")?.let { signingConfig = it }
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.koin.androidx.compose)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
