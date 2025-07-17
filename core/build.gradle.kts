@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.compose.reload.ComposeHotRun
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.hotReload)
    id("com.vanniktech.maven.publish") version "0.30.0"
}
version = "0.0.1"
group = "io.github.henriquehorbovyi"

java {
    toolchain {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(17)
    }
}
kotlin {
    androidTarget {
        publishLibraryVariants("release", "debug")
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }

    jvm()

    wasmJs {
        browser()
        binaries.executable()
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ParticlesCore"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.material3)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            implementation(compose.uiTooling)
            implementation(libs.androidx.activityCompose)
            implementation("androidx.compose.material:material-ripple:1.7.2")
        }

        jvmMain.dependencies {
            implementation(compose.desktop.macos_arm64)
            // implementation(compose.desktop.currentOs)
        }

    }
}

android {
    namespace = "io.github.henriquehorbovyi.particles"
    compileSdk = 35

    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

//https://developer.android.com/develop/ui/compose/testing#setup
dependencies {
    androidTestImplementation(libs.androidx.uitest.junit4)
    debugImplementation(libs.androidx.uitest.testManifest)
}

//https://github.com/JetBrains/compose-hot-reload
composeCompiler {
    featureFlags.add(ComposeFeatureFlag.OptimizeNonSkippingGroups)
}
tasks.withType<ComposeHotRun>().configureEach {
    mainClass.set("MainKt")
}


mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates(group.toString(), "particles", version.toString())
    pom {
        name = "Particles"
        description = "Common building blocks for crafting Compose UI applications."
        inceptionYear = "2025"
        url = "https://github.com/henriquehorbovyi/particles"
        licenses {
            license {
                name = "MIT License"
                url = "http://www.opensource.org/licenses/mit-license.php"
                distribution = "http://www.opensource.org/licenses/mit-license.php"
            }
        }
        developers {
            developer {
                id = "particles"
                name = "Henrique Horbovyi"
                url = "https://github.com/henriquehorbovyi/"
            }
        }
        scm {
            url = "https://github.com/henriquehorbovyi/particles/"
            connection = "scm:git:git://github.com/henriquehorbovyi/particles.git"
            developerConnection = "scm:git:ssh://git@github.com/henriquehorbovyi/particles.git"
        }
    }
}