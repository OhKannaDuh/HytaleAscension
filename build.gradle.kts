import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.jvm.tasks.Jar

plugins {
    java
    id("com.gradleup.shadow") version "9.2.2"
}

group = "dev.faye"
version = "1.0.0"

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(25)) }
}

repositories { mavenCentral() }

val hytaleRoot = file("C:/Code/hytale-mods")
val filesDir = hytaleRoot.resolve("files")
val serverJar = filesDir.resolve("HytaleServer.jar")
val assetsZip = filesDir.resolve("Assets.zip")
val modsDir = filesDir.resolve("dev-mods")


dependencies {
    compileOnly(files(serverJar))

    implementation("com.google.inject:guice:7.0.0")
    implementation("jakarta.inject:jakarta.inject-api:2.0.1")
}

tasks.named("processResources") {
    doFirst {
        check(file("src/main/resources/manifest.json").exists()) {
            "Missing src/main/resources/manifest.json"
        }
    }
}

tasks.named<Jar>("jar") {
    enabled = false
}

tasks.named<ShadowJar>("shadowJar") {
    archiveBaseName.set("mod-learning")
    archiveClassifier.set("")

    mergeServiceFiles()

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

val deployMod = tasks.register("deployMod") {
    dependsOn(tasks.named("shadowJar"))

    doLast {
        val shaded = tasks.named<ShadowJar>("shadowJar").get().archiveFile.get().asFile
        check(shaded.exists() && shaded.length() > 0) {
            "Shaded jar missing/empty: ${shaded.absolutePath}"
        }

        modsDir.mkdirs()

        val out = modsDir.resolve("mod-learning.jar")
        shaded.copyTo(out, overwrite = true)
    }
}

tasks.register<JavaExec>("runServer") {
    dependsOn(deployMod)

    doFirst {
        check(serverJar.exists()) { "Missing server jar: ${serverJar.absolutePath}" }
        check(assetsZip.exists()) { "Missing assets zip: ${assetsZip.absolutePath}" }
        check(modsDir.exists()) { "Missing mods dir: ${modsDir.absolutePath}" }
    }

    javaLauncher.set(javaToolchains.launcherFor {
        languageVersion.set(JavaLanguageVersion.of(25))
    })

    mainClass.set("-jar")
    args(
        serverJar.absolutePath,
        "--assets", assetsZip.absolutePath,
        "--mods", modsDir.absolutePath
    )

    workingDir = filesDir
    isIgnoreExitValue = true
    standardInput = System.`in`
}

// Optional convenience: `gradlew run` works too
tasks.register("run") {
    dependsOn("runServer")
}
