import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.library")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

val libs = the<LibrariesForLibs>()

android {

    namespace = "com.neaniesoft.warami"
    compileSdk = libs.versions.sdk.compile.get().toInt()

    defaultConfig {
        minSdk = libs.versions.sdk.min.get().toInt()
        targetSdk = libs.versions.sdk.targer.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        jvmTarget = libs.versions.kotlin.jvm.target.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.dagger.hilt.android)
    implementation(libs.androidx.appcompat.resources)
    kapt(libs.dagger.hilt.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.dagger.hilt.android.testing)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.dagger.hilt.android.testing)
}
