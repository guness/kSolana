plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlinx-serialization")
    id("kotlin-kapt")
    id("maven-publish")
}

group = "com.guness.ksolana"
version = "0.1.0"

android {
    val versions: Map<String, Int> by rootProject.extra

    compileSdk = versions["compileSdk"]

    defaultConfig {
        minSdk = versions["minSdk"]
        targetSdk = versions["targetSdk"]

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFile("consumer-rules.pro")
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = true
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"))
        }

        named("debug") {
            isMinifyEnabled = false
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

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs = listOf(
            "-Xopt-in=" +
                    "kotlinx.serialization.InternalSerializationApi," +
                    "kotlinx.serialization.ExperimentalSerializationApi"
        )
    }
}

dependencies {
    // Kotlin coroutines
    implementation(KotlinX.coroutines.core)
    testImplementation(KotlinX.coroutines.test)

    // Json lib
    implementation(KotlinX.serialization.json)
    implementation(Square.OkHttp3)

    // Crypto
    implementation("com.github.komputing:KBase58:_")
    implementation("io.github.instantwebp2p:tweetnacl-java:_")

    testImplementation(Testing.junit4)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components["release"])
            }
        }
    }
}
