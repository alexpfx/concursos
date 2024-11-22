/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.3/userguide/building_java_projects.html in the Gradle documentation.
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    idea
    val kotlin_version: String = "1.9.10"
    val ksp_version: String = "1.9.10-1.0.13"

    id("com.google.devtools.ksp") version ksp_version
    // Apply  org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version kotlin_version
//    id("com.google.devtools.ksp") version ksp_version

    kotlin("plugin.serialization") version "1.5.10" // Verifique a versão mais recente


    // Apply the application plugin to add support for building a CLI application in Java.
    application

}

idea {
    module {
        // Not using += due to https://github.com/gradle/gradle/issues/8749
        sourceDirs =
            sourceDirs + file("build/generated/ksp/main/kotlin") // or tasks["kspKotlin"].destination
        testSourceDirs =
            testSourceDirs + file("build/generated/ksp/test/kotlin")
        generatedSourceDirs =
            generatedSourceDirs + file("build/generated/ksp/main/kotlin") + file(
                "build/generated/ksp/test/kotlin"
            )
    }
}
repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
    google()

    gradlePluginPortal()

    repositories {
        maven("https://repository.apache.org/content/repositories/snapshots/")
    }

}

ksp {
}


dependencies {
    val pdfBox = "3.0.1-SNAPSHOT"


    implementation("org.jsoup:jsoup:1.16.2")

    implementation("org.apache.commons:commons-text:1.10.0")
    implementation("org.apache.pdfbox:pdfbox:$pdfBox")
    implementation("org.apache.pdfbox:fontbox:$pdfBox")

    // https://mvnrepository.com/artifact/com.google.dagger/dagger
    implementation("com.google.dagger:dagger:2.48.1")

    ksp("com.google.dagger:dagger-compiler:2.48.1")

    implementation("com.github.ajalt.clikt:clikt:4.2.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

    testImplementation("org.mockito:mockito-core:5.5.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.9.1")

    implementation("ch.qos.logback:logback-classic:1.4.11")

    implementation("org.slf4j:slf4j-api:2.0.9") // Verifique a versão mais recente
    implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")

    // https://mvnrepository.com/artifact/org.apache.pdfbox/jbig2-imageio
    implementation("org.apache.pdfbox:jbig2-imageio:3.0.4")

    // https://mvnrepository.com/artifact/com.twelvemonkeys.imageio/imageio-jpeg
    implementation("com.twelvemonkeys.imageio:imageio-jpeg:3.9.4")

    // https://mvnrepository.com/artifact/com.github.jai-imageio/jai-imageio-core
    implementation("com.github.jai-imageio:jai-imageio-core:1.4.0")


    // https://mvnrepository.com/artifact/com.github.jai-imageio/jai-imageio-jpeg2000
    implementation("com.github.jai-imageio:jai-imageio-jpeg2000:1.4.0")

}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use Kotlin Test test framework
            useKotlinTest("1.9.0")
        }
    }
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(20))
    }
}

application {
    // Define the main class for the application.
    mainClass.set("com.github.ax_as.extrator_provas.AppKt")
}

tasks.withType<Test>().configureEach {
    testLogging {
//        events("passed", "skipped", "failed")
        events("failed")
    }
}


tasks.test {
    testLogging {
        outputs.upToDateWhen { false }
        showStandardStreams = true
    }
}


// KSP - To use generated sources
sourceSets.main {
    java.srcDirs("build/generated/ksp/main/kotlin")
}

kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
    sourceSets.test {
        kotlin.srcDir("build/generated/ksp/test/kotlin")
    }
}


tasks.jar {
    duplicatesStrategy =
        DuplicatesStrategy.EXCLUDE  // Estratégia para lidar com entradas duplicadas

    manifest {
        attributes["Main-Class"] =
            "com.github.ax_as.extrator_provas.AppKt"  // Substitua pelo nome da sua classe principal
    }
    from(configurations.runtimeClasspath.get().map {
        if (it.isDirectory) it else zipTree(it)
    })

    exclude("META-INF/*.SF")
    exclude("META-INF/*.DSA")
    exclude("META-INF/*.RSA")
}