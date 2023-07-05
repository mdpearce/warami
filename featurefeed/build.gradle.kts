plugins {
    id("com.neaniesoft.warami.android-compose-conventions")
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
