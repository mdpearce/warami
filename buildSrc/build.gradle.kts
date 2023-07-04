plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    google()
}

dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(libs.android.gradle.application.plugin)
    implementation(libs.android.gradle.library.plugin)
    implementation(libs.kotlin.android.gradle.plugin)
    implementation(libs.kotlin.android.extensions.gradle.plugin)
    implementation(libs.dagger.hilt.plugin)
}
