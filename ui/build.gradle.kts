@file:Suppress("UnstableApiUsage")

plugins {
    id("com.neaniesoft.warami.android-compose-conventions")
    id("org.jmailen.kotlinter")
}

android {
    defaultConfig {
        namespace = "com.neaniesoft.warami.ui"
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":common"))

    implementation(project(":featurefeed"))
}
