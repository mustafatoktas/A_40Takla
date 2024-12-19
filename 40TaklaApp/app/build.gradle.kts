plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    //ksp
    alias(libs.plugins.ksp)

    //hilt
    alias(libs.plugins.hilt.plugin)

    //serialization (navigation için)
    alias(libs.plugins.kotlinx.serialization)

    //navigation için
    id("kotlin-parcelize")

    //google services (firebase için)
    alias(libs.plugins.google.services)

    //detekt
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.mustafatoktas.kirktakla"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mustafatoktas.kirktakla"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        compose = true
        viewBinding = false
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //modül bağımlılıkları
    implementation(project(":core:core-ui"))
    implementation(project(":core:core-common"))
    implementation(project(":core:core-data"))
    implementation(project(":core:core-domain"))
    implementation(project(":usecase:usecase-app"))
    implementation(project(":usecase:usecase-firebase"))
    implementation(project(":feat:feat-congratulation"))
    implementation(project(":feat:feat-list"))
    implementation(project(":feat:feat-about"))
    implementation(project(":feat:feat-main"))

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

    //splash screen
    implementation(libs.androidx.core.splashscreen)

    //Navigation
    implementation(libs.androidx.navigation.compose)

    //serialization (navigation için)
    implementation(libs.kotlinx.serialization.json)

    //detekt
    detektPlugins(libs.detekt)

    //firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation (libs.firebase.firestore)

    //coil
    implementation(libs.coil.compose)

}

//detekt yapılandırması
detekt {
    config.setFrom(file("$rootDir/detekt/detektConfig.yml"))
    source.from(files("src/main/kotlin", "src/test/kotlin"))
    parallel = true
    autoCorrect = true
    buildUponDefaultConfig = true
}