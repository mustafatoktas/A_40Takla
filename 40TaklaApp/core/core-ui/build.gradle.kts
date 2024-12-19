plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    //composableler
    alias(libs.plugins.kotlin.compose)

    //serialization (navigation için)
    alias(libs.plugins.kotlinx.serialization)

    //navigation için
    id("kotlin-parcelize")
}

android {
    namespace = "com.mustafatoktas.core_ui"
    compileSdk = 35

    defaultConfig {
        minSdk = 27

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)

    //composableler
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //özelleştirilebilir snackbar
    implementation(libs.sonner)

    //serialization (navigation için)
    implementation(libs.kotlinx.serialization.json)
}