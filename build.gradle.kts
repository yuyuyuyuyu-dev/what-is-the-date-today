plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.spotless)
    alias(libs.plugins.detekt)
}

allprojects {
    apply(plugin = "com.diffplug.spotless")
    apply(plugin = "io.gitlab.arturbosch.detekt")

    spotless {
        kotlin {
            target("**/*.kt")
            targetExclude(
                "**/build/**/*.kt",
                "**/.idea/**/*.kt",
                "**/bin/**/*.kt",
                "**/generated/**/*.kt",
            )
            // Style base is driven by .editorconfig (ktlint_code_style = android_studio).
            // Spotless only reads per-rule ktlint toggles from here (not from .editorconfig),
            // so the project-level overrides live in this map:
            //  - function-naming: owned by detekt (which exempts @Composable); don't duplicate.
            //  - trailing commas: the Android style guide is silent, so honor Kotlin's official
            //    convention (which encourages them) over the android_studio default of removing.
            ktlint().editorConfigOverride(
                mapOf(
                    "ktlint_standard_function-naming" to "disabled",
                    // IntelliJ-native keys: ktlint honors them and they avoid the
                    // ktlint_* toggle-parsing bug on the trailing-comma properties.
                    "ij_kotlin_allow_trailing_comma" to "true",
                    "ij_kotlin_allow_trailing_comma_on_call_site" to "true",
                ),
            )
        }
        kotlinGradle {
            target("**/*.gradle.kts")
            targetExclude("**/build/**/*.gradle.kts")
            ktlint()
        }
        format("web") {
            target("**/*.html", "**/*.css", "**/*.js")
            targetExclude(
                "**/build/**",
                "**/.gradle/**",
                "**/.kotlin/**",
                "**/kotlin-js-store/**",
                "**/node_modules/**",
            )
            prettier()
        }
    }

    detekt {
        buildUponDefaultConfig = true
        source.setFrom(files("src"))
        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    }
}
