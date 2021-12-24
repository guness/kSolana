pluginManagement {
    plugins {
        // See https://jmfayard.github.io/refreshVersions
        id("de.fayard.refreshVersions") version "0.30.1"
    }
}

plugins {
    id("de.fayard.refreshVersions")
}

refreshVersions {
    rejectVersionIf {
        candidate.stabilityLevel.isLessStableThan(current.stabilityLevel)
    }
}

include(":app")
include(":ksolana")
rootProject.name = "kSolana"
