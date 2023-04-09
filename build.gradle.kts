plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "de.jjjannik"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.JnCrMx:discord-game-sdk4j:java-impl-SNAPSHOT")

    implementation("com.formdev", "flatlaf", "3.1")

    implementation("org.slf4j", "slf4j-log4j12", "2.0.1")
    implementation("org.apache.logging.log4j", "log4j-api", "2.19.0")
    implementation("org.apache.logging.log4j", "log4j-core", "2.19.0")
    implementation("me.carleslc.Simple-YAML", "Simple-Yaml", "1.8.3")

    compileOnly("org.projectlombok", "lombok", "1.18.24")
    annotationProcessor("org.projectlombok", "lombok", "1.18.24")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    manifest {
        attributes["Main-Class"] = "de.jjjannik.discordrpc.Main"
    }
    archiveFileName.set("DiscordRPC.jar")
}