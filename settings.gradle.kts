rootProject.name = "nxcloud-springmvc-partition"

include(":springmvc-partition")

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
