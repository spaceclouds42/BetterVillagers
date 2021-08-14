import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val mcVersion: String by project
val yarnVersion: String by project
val loaderVersion: String by project
val fapiVersion: String by project
val languageAdapterVersion: String by project
val modVersion: String by project
val mavenGroup: String by project
val archivesBaseName: String by project
val ekhoVersion: String by project
val configVersion: String by project
val aegisVersion: String by project

project.version = modVersion
project.group = mavenGroup

plugins {
    id("fabric-loom")
    // id("org.spaceserve.kotlin-mixins")
    id("io.gitlab.arturbosch.detekt")
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.serialization")
}

repositories {
    maven {
        name = "Modrinth"
        url = uri("https://api.modrinth.com/maven")
    }
}

dependencies {
    // Fabric
    minecraft("com.mojang:minecraft:$mcVersion")
    mappings("net.fabricmc:yarn:$yarnVersion:v2")
    modImplementation("net.fabricmc:fabric-loader:$loaderVersion")
    modImplementation(fabricApi.module("fabric-command-api-v1", fapiVersion))

    // Kotlin
    modImplementation("net.fabricmc:fabric-language-kotlin:$languageAdapterVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.15.0") // Static analysis

    // Config
    modImplementation("maven.modrinth:config:$configVersion")
    include("maven.modrinth:config:$configVersion")
    
    // Ekho
    modImplementation("maven.modrinth:ekho:$ekhoVersion")
    include("maven.modrinth:ekho:$ekhoVersion")
    
    // Aegis
    modImplementation("maven.modrinth:aegis:$aegisVersion")
    include("maven.modrinth:aegis:$aegisVersion")

    // Automatone
    modImplementation(files("libs/automatone/automatone-0.4.1.jar"))
}

tasks {
    // Replaces "version" value in fabric.mod.json with version defined in gradle.properties
    processResources {
        inputs.property("version", project.version)

        from(sourceSets.main.get().resources.srcDirs) {
            include("fabric.mod.json")
            expand("version" to project.version)
            duplicatesStrategy = DuplicatesStrategy.INCLUDE
        }
    }

    // Ensures encoding is set to UTF-8, regardless of system default
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "16"
    }

    java {
        withSourcesJar()
    }

    jar {
        from("LICENSE.md")
    }
}

detekt {
    buildUponDefaultConfig = true
    config = rootProject.files("detekt.yml")
}
