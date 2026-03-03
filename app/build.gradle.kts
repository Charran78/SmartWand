import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

// Load secrets from secrets.properties file in the module's root directory
val secretsFile = project.file("secrets.properties")
val secrets = Properties()
if (secretsFile.exists()) {
    secrets.load(FileInputStream(secretsFile))
}
val geminiApiKey = (secrets.getProperty("GEMINI_API_KEY") ?: "").trim('"')
val googleMapsApiKey = (secrets.getProperty("GOOGLE_MAPS_API_KEY") ?: "").trim('"')

android {
    namespace = "com.bdw.smartwand"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.bdw.smartwand"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Expose secrets to the app
        // 1. As a BuildConfig field for code access (Gemini API)
        buildConfigField("String", "GEMINI_API_KEY", "\"$geminiApiKey\"")
        // 2. As a manifest placeholder for manifest access (Maps API)
        manifestPlaceholders["GOOGLE_MAPS_API_KEY"] = googleMapsApiKey
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true // Enable BuildConfig generation
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.google.maps)
    implementation(libs.androidx.maps.compose)
    implementation(libs.google.ai.generativeai)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
