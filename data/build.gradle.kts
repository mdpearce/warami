plugins {
    id("com.neaniesoft.warami.android-conventions")
    alias(libs.plugins.sqldelight)
}

android {
    defaultConfig {
        namespace = "com.neaniesoft.warami.data"
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.neaniesoft.warami.data.db")
        }
    }
}

dependencies {
    implementation(project(":api"))
    implementation(project(":common"))

    implementation(libs.sqldelight.android.driver)
}
