plugins {
    id("com.neaniesoft.warami.android-conventions")
}

android {
    defaultConfig {
        namespace = "com.neaniesoft.warami.common"
    }
}

dependencies {
    implementation(project(":api"))
    api(libs.ulid.creator)
}
