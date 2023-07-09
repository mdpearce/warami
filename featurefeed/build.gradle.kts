plugins {
    id("com.neaniesoft.warami.android-compose-conventions")
    id("org.jmailen.kotlinter")
}

android {
    defaultConfig {
        namespace = "com.neaniesoft.warami.featurefeed"
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":common"))
}
