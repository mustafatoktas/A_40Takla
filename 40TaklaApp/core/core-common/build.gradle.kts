plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    //composableler
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.mustafatoktas.core_common"
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

    //modül bağımlılıklar
    implementation(project(":core:core-ui"))

    //composableler
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //konfetti
    implementation(libs.konfetti)

    //Navigation
    implementation(libs.androidx.navigation.compose)

}