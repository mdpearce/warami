plugins {
    id("com.neaniesoft.warami.android-conventions")
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
