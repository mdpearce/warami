plugins {
    id("com.neaniesoft.warami.android-conventions")
    alias(libs.plugins.ksp)
}

android {
    defaultConfig {
        namespace = "com.neaniesoft.warami.api"
    }
}

dependencies {
    implementation(libs.moshi)
    implementation(libs.moshi.adapters)
    api(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.retrofit.converter.scalars)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    ksp(libs.moshi.kotlin.codegen)
}
