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
        //diktat()
    }

    java {
        target("**/src/**/java/**/*.java")
        importOrder()
        removeUnusedImports()
        indentWithSpaces(2)
        endWithNewline()
        trimTrailingWhitespace()

        val url = "https://raw.githubusercontent.com/SlimyMC/formatter/fix/url/eclipse-prefs.xml"
        val localFilePath = project.layout.projectDirectory
            .dir(".gradle/caches/formatter")
            .file("eclipse-prefs.xml")
            .asFile
        val urlConnection = URL(url).openConnection()
        urlConnection.connect()
        urlConnection.getInputStream().use { inputStream ->
            localFilePath.parentFile?.mkdirs()
            localFilePath.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }

        eclipse().configFile(localFilePath)
    }
}
