plugins {
    id("com.neaniesoft.warami.android-conventions")
    kotlin("kapt")
}

android {
    defaultConfig {
        namespace = "com.neaniesoft.warami.api"
    }
}

dependencies {
    implementation(libs.moshi)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    kapt(libs.moshi.kotlin.codegen)
}
