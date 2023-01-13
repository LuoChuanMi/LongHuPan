/*
 * This file was generated by the Gradle 'init' task.
 *
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    java
    `maven-publish`
    // shadow
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    maven {
        url = uri("https://nexus.hc.to/content/repositories/pub_releases")
    }

    maven {
        url = uri("https://repo.xbaimiao.com/nexus/content/repositories/releases/")
    }

    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    implementation("net.kyori:adventure-api:4.12.0")
    implementation("net.kyori:adventure-text-minimessage:4.12.0")
    implementation("net.kyori:adventure-platform-bukkit:4.2.0")
    compileOnly("public:points:1.0.0")
    compileOnly("net.milkbowl.vault:VaultAPI:1.7")
    compileOnly("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")
}

group = "pro.sandiao.plugin"
version = "1.0.0"
description = "Longhupan"
java.sourceCompatibility = JavaVersion.VERSION_1_8

tasks {
    shadowJar {
        relocate("net.kyori","pro.sandiao.plugin.longhupan.net.kyori")
        minimize()
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}
