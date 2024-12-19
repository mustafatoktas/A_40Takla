plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    //composableler
    alias(libs.plugins.kotlin.compose)

    //ksp
    alias(libs.plugins.ksp)

    //hilt
    alias(libs.plugins.hilt.plugin)
}

android {
    namespace = "com.mustafatoktas.feat_about"
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

    //modül bağımlılıkları
    implementation(project(":core:core-common"))
    implementation(project(":core:core-ui"))
    implementation(project(":usecase:usecase-app"))

    //composableler
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //hilt
    implementation(libs.hilt)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    //Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    //coil (image cashing)
    implementation(libs.coil.compose)

    //özelleştirilebilir snackbar
    implementation(libs.sonner)
}