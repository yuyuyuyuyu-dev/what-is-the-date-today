import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.aboutLibraries)
    alias(libs.plugins.ksp)
    alias(libs.plugins.composePwa)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
}

ktlint {
    version = libs.versions.ktlintCli.get()
    filter {
        val buildPath =
            layout.buildDirectory
                .get()
                .asFile
                .toPath()
        exclude { it.file.toPath().startsWith(buildPath) }
    }
}

detekt {
    source.setFrom("src")
    buildUponDefaultConfig = true
    config.setFrom(rootProject.files("config/detekt/detekt.yml"))
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.kotlinx.datetime)
            implementation(libs.aboutlibraries.compose.core)
            implementation(libs.myComposables)
            implementation(libs.jetbrains.material3.adaptiveNavigation3)
            implementation(libs.jetbrains.lifecycle.viewmodelNavigation3)
            implementation(libs.jetbrains.navigation3.ui)
            implementation(libs.jetbrains.window.core)
            implementation(libs.kotlin.inject.runtime)
        }
        wasmJsTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.compose.uiTest)
        }
    }
}

dependencies {
    add("kspWasmJs", libs.kotlin.inject.compiler)
}

ksp { arg("me.tatarka.inject.dumpSetup", "true") }

aboutLibraries {
    export {
        outputFile = file("src/commonMain/composeResources/files/aboutlibraries.json")
    }
}

compose.resources {
    customDirectory(
        sourceSetName = "commonMain",
        directoryProvider =
            tasks
                .named("exportLibraryDefinitions")
                .map { layout.projectDirectory.dir("src/commonMain/composeResources") },
    )
}
