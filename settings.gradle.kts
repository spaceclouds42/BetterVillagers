pluginManagement {
    val loomVersion: String by settings
    val detektVersion: String by settings
    val kotlinVersion: String by settings
    val kaptVersion: String by settings
    // val ktMixinVersion: String by settings
    val kotlinxPluginVersion: String by settings

    repositories {
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net")
        }

        maven {
            name = "Detekt"
            url = uri("https://plugins.gradle.org/m2")
        }
    }

    plugins {
        id("fabric-loom") version loomVersion
        id("io.gitlab.arturbosch.detekt") version detektVersion
        // id("org.spaceserve.kotlin-mixins") version ktMixinVersion
        kotlin("jvm") version kotlinVersion
        kotlin("kapt") version kaptVersion
        kotlin("plugin.serialization") version kotlinxPluginVersion
    }
}

val modId: String by settings
rootProject.name = modId
