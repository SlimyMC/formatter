import java.net.URL

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("com.diffplug.spotless:spotless-plugin-gradle:6.22.0")
    }
}

apply<com.diffplug.gradle.spotless.SpotlessPlugin>()

repositories {
    mavenCentral()
}

val eclipsePrefsUrl = "https://raw.githubusercontent.com/SlimyMC/formatter/main/eclipse-prefs.xml"
val eclipsePrefsPath = project.layout.projectDirectory
    .dir(".gradle/caches/formatter")
    .file("eclipse-prefs.xml")
    .asFile
val eclipsePrefsConnection = URL(eclipsePrefsUrl).openConnection()
eclipsePrefsConnection.connect()
eclipsePrefsConnection.getInputStream().use { inputStream ->
    eclipsePrefsPath.parentFile?.mkdirs()
    eclipsePrefsPath.outputStream().use { outputStream ->
        inputStream.copyTo(outputStream)
    }
}

extensions.configure<com.diffplug.gradle.spotless.SpotlessExtension> {
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

        eclipse().configFile(eclipsePrefsPath)
    }
}
