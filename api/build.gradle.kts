import java.util.Properties

plugins {
    id("com.neaniesoft.warami.android-conventions")
}

val localProperties = Properties()
localProperties.load(rootProject.file("local.properties").inputStream())

android {
    defaultConfig {
        namespace = "com.neaniesoft.warami.api"
        resValue(
            "string",
            "warami_base_url",
            localProperties["com.neaniesoft.warami.api.baseUrl"] as String,
        )
    }
}

dependencies {
    implementation(libs.moshi)
    implementation(libs.moshi.adapters)
    api(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.retrofit.converter.scalars)
    api(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    ksp(libs.moshi.kotlin.codegen)
}
