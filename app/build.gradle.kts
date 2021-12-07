plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    val versions: Map<String, Int> by rootProject.extra

    compileSdk = versions["compileSdk"]

    defaultConfig {
        applicationId = "com.guness.ksolana.app"
        minSdk = versions["minSdk"]
        targetSdk = versions["targetSdk"]
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        named("release") {
            isDebuggable = false
            isMinifyEnabled = false
            isShrinkResources = false
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"))
        }

        named("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    implementation(project(":ksolana"))
    implementation(AndroidX.core.ktx)
    implementation(AndroidX.lifecycle.runtimeKtx)
    implementation(Google.android.material)
    implementation(AndroidX.appCompat)
    testImplementation(Testing.junit4)
    androidTestImplementation(AndroidX.test.ext.junit)
    androidTestImplementation(AndroidX.test.espresso.core)
}
