plugins {
    id("com.neaniesoft.warami.android-conventions")
}

android {
    defaultConfig {
        namespace = "com.neaniesoft.warami.domain"
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":common"))
}
