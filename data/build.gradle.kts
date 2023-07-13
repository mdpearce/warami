plugins {
    id("com.neaniesoft.warami.android-conventions")
    alias(libs.plugins.sqldelight)
    id("org.jmailen.kotlinter")
    alias(libs.plugins.protobuf)
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

// Setup protobuf configuration, generating lite Java and Kotlin classes
protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    implementation(project(":api"))
    implementation(project(":common"))

    implementation(libs.sqldelight.android.driver)
    implementation(libs.sqldelight.coroutines.extensions)
    implementation(libs.sqldelight.androidx.paging3.extensions)
    implementation(libs.androidx.datastore)
    implementation(platform(libs.firebase.bom))
    implementation("com.google.firebase:firebase-config-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")

    implementation(libs.androidx.paging.runtime)
    testImplementation(libs.androidx.paging.common)
    implementation(libs.androidx.security.crypto)
}
