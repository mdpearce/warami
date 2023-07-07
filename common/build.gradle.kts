plugins {
    id("com.neaniesoft.warami.android-compose-conventions")
}

android {
    defaultConfig {
        namespace = "com.neaniesoft.warami.common"
    }
}

dependencies {
    implementation(project(":api"))
}
