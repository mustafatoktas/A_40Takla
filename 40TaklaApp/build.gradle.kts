buildscript {
    dependencies {
        classpath(libs.hilt.android.gradle.plugin)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    //ksp
    alias(libs.plugins.ksp) apply false

    //hilt
    alias(libs.plugins.hilt.plugin) apply false

    //serialization (navigation için)
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.android.library) apply false

    //google services (firebase için)
    alias(libs.plugins.google.services) apply false

    //detekt
    alias(libs.plugins.detekt) apply false
}