plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.kotlin.compose) // <-- добавлен плагин компилятора Compose
}

android {
    namespace = "com.example.customscanapp"
    compileSdk = 36   // обновлено

    defaultConfig {
        applicationId = "com.example.customscanapp"
        minSdk = 24
        targetSdk = 36   // обновлено
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        // добавлены opt-in флаги для экспериментальных Material API
        freeCompilerArgs += listOf(
            "-Xopt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-Xopt-in=androidx.compose.material.ExperimentalMaterialApi"
        )
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

dependencies {
    // AndroidX
    implementation("androidx.core:core-ktx:1.15.0") // обновлено
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.0") // обновлено
    implementation("androidx.activity:activity-compose:1.9.1") // обновлено
    implementation("com.google.android.material:material:1.9.0")

    // Jetpack Compose (актуальный BOM)
    implementation(platform("androidx.compose:compose-bom:2025.09.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // Retrofit + Gson
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1") // обновлено
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1") // обновлено
    androidTestImplementation(platform("androidx.compose:compose-bom:2025.09.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
