@file:Suppress("UnstableApiUsage", "DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.plugin)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.parcelize)
    alias(libs.plugins.navigation.safeargs)
}

android {
    namespace = "com.ibrahim.task"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ibrahim.task"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildFeatures {
            buildConfig = true
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(project(":core:db"))
    implementation(project(":core:network"))
    implementation(project(":core:ui"))
    implementation(project(":core:utility"))
    implementation(libs.coreKtx)
    implementation(libs.appcompat)
    implementation(libs.materialDesign)
    //Navigation
    implementation(libs.bundles.navigationComponent)
    //Hilt
    implementation(libs.hilt)
    implementation(libs.multidex)
    implementation(libs.constraintlayout)
    kapt(libs.hiltDaggerCompiler)
    // Arch Components
    implementation(libs.bundles.archComponents)
    // Kotlin Coroutines
    implementation(libs.bundles.kotlinCoroutines)
    implementation(libs.hiltWorker)
    implementation(libs.androidWorker)
}

kapt {
    correctErrorTypes = true
}