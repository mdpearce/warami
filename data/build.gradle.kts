plugins {
    id("com.neaniesoft.warami.android-conventions")
}

android {
    defaultConfig {
        namespace = "com.neaniesoft.warami.data"
    }
}

dependencies {
    implementation(project(":api"))
    implementation(project(":common"))
}
