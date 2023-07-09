plugins {
    id("com.neaniesoft.warami.android-compose-conventions")
    id("org.jmailen.kotlinter")
}

android {
    defaultConfig {
        namespace = "com.neaniesoft.warami.common"
    }
}

dependencies {
    implementation(project(":api"))
}
