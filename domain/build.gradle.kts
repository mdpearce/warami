plugins {
    id("com.neaniesoft.warami.android-conventions")
    id("org.jmailen.kotlinter")
}

android {
    defaultConfig {
        namespace = "com.neaniesoft.warami.domain"
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":common"))

    implementation(libs.androidx.paging.runtime)
    testImplementation(libs.androidx.paging.common)
}
