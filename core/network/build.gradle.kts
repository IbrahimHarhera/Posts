@file:Suppress("UnstableApiUsage", "DSL_SCOPE_VIOLATION")

import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.plugin)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.ibrahim.network"
    compileSdk = 34

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
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    // Hilt
    implementation(libs.hilt)
    kapt(libs.hiltDaggerCompiler)
    // coroutines
    implementation(libs.coroutinesCore)
    implementation(libs.coroutinesAndroid)
    implementation(project(":core:ui"))
    // Networking
    api(libs.bundles.networking)
}