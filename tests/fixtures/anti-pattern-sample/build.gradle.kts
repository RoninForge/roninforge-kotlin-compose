// Anti-pattern sample. DO NOT use as a template.
//
// Violations:
// - kotlinCompilerExtensionVersion (removed in Kotlin 2.0+)
// - kapt instead of ksp
// - hardcoded versions in module (not Version Catalog)
// - Material 2 dependency
// - targetSdk 33 (below Play Store requirement)

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    compileSdk = 33
    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"   // obsolete since Kotlin 2.0
    }
}

dependencies {
    implementation("androidx.compose.material:material:1.5.0")        // Material 2
    implementation("androidx.compose.ui:ui:1.5.0")                    // hardcoded version
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.0")
}
