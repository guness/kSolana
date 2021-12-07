plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlinx-serialization")
    id("kotlin-kapt")
    id("maven-publish")
}

group = "com.guness.ksolana"
version = "0.1.1"

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
            isMinifyEnabled = false
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

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets["main"].java.srcDirs)
}

val javadoc = tasks.register("javadoc", Javadoc::class) {
    // Self-explanatory, use these files to generate the Javadoc.
    source = android.sourceSets["main"].java.getSourceFiles()

    // We need to include the Android framework classes, otherwise the Javadoc
    // compiler won't be able to find them.
    classpath += project.files(android.bootClasspath)
}

val javadocJar by tasks.register("javadocJar", Jar::class) {
    // All of this should be self-explanatory from the previous tasks.
    dependsOn(javadoc)
    from(javadoc)
    archiveClassifier.set("javadoc")
}


afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                artifact(sourcesJar)
                artifact(javadocJar)
            }
        }
    }
}
