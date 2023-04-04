rootProject.name = "nxcloud-springmvc-partition"

include(":springmvc-partition")
include(":ext-spring-boot-starter-springmvc-partition")
include(":sample")

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
