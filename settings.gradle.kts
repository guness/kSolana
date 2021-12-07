pluginManagement {
    plugins {
        id("de.fayard.refreshVersions").version("0.23.0")
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
