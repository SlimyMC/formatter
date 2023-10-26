plugins {
    java
    `maven-publish`
    alias(libs.plugins.spotless)
}

group = "net.slimymc"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.json.patch)
    implementation(libs.kafka.clients)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

tasks {
    compileJava {
        options.compilerArgs.add("-Xlint:deprecation")
    }
}

spotless {
    lineEndings = com.diffplug.spotless.LineEnding.UNIX

    format("encoding") {
        target("*.*")
        encoding("UTF-8")
        endWithNewline()
        trimTrailingWhitespace()
    }

    yaml {
        target(
            ".github/**/*.yml",
            ".github/**/*.yaml",
        )
        endWithNewline()
        trimTrailingWhitespace()
        jackson()
            .yamlFeature("LITERAL_BLOCK_STYLE", true)
            .yamlFeature("MINIMIZE_QUOTES", true)
            .yamlFeature("SPLIT_LINES", false)
    }

    kotlinGradle {
        target("**/*.gradle.kts")
        endWithNewline()
        trimTrailingWhitespace()
        diktat()
    }

    java {
        target("**/src/**/java/**/*.java")
        importOrder()
        removeUnusedImports()
        indentWithSpaces(2)
        endWithNewline()
        trimTrailingWhitespace()
        eclipse().configFile("eclipse-prefs.xml")
    }
}
