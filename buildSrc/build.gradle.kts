import email.haemmerle.gradle.base.BasePlugin

buildscript {
    repositories {
        maven { url = uri("https://dl.bintray.com/mhmmerle/snapshots") }
    }
    dependencies {
        classpath("email.haemmerle.gradle:base-plugin:cb0ab8a")
    }
}

group = "email.haemmerle.gradle"
description = "Gradle base plugin for kotlin projects and the CI chain github -> travis -> bintray"

plugins {
    `java-gradle-plugin`
    id("com.gradle.build-scan").version("2.4.2")
}
apply<BasePlugin>()

gradlePlugin {
    plugins {
        create("basePlugin") {
            id = "email.haemmerle.baseplugin"
            implementationClass = "email.haemmerle.gradle.base.BasePlugin"
        }
    }
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(gradleApi())
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.gradle:build-scan-plugin:2.4.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.50")
    implementation("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4")
    implementation("com.palantir.gradle.gitversion:gradle-git-version:0.12.2")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}
