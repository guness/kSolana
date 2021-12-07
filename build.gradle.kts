buildscript {
    extra["versions"] = mapOf(
        "minSdk" to 26,
        "targetSdk" to 31,
        "compileSdk" to 31
    )

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:_")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:_")
        classpath("org.jetbrains.kotlin:kotlin-serialization:_")
    }
}

allprojects {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
    }
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
}
