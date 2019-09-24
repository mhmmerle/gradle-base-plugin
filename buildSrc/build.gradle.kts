import com.jfrog.bintray.gradle.BintrayExtension

plugins {
    `java-gradle-plugin`
    id("org.jetbrains.kotlin.jvm").version("1.3.21")
    `maven-publish`
    id("com.jfrog.bintray").version("1.8.4")
    id("com.palantir.git-version").version("0.12.2")
}

val gitVersion: groovy.lang.Closure<String> by extra
version = gitVersion(mapOf("prefix" to "v-"))
group = "email.haemmerle.gradle"
description = "Gradle base plugin for kotlin projects and the CI chain github -> travis -> bintray"

gradlePlugin {
    plugins {
        create("basePlugin") {
            id = "email.haemmerle.baseplugin"
            implementationClass = "email.haemmerle.gradle.base.BasePlugin"
        }
    }
}

repositories {
    maven { url = uri("https://plugins.gradle.org/m2/") }
    jcenter()
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

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

bintray {
    user = System.getenv("BINTRAY_USER")
    key = System.getenv("BINTRAY_KEY")

    setPublications("maven")
    publish = true

    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = "snapshots"
        name = project.name
        setLicenses("MIT")
        vcsUrl = "https://github.com/mhmmerle/test-travis-ci.git"
        websiteUrl = "https://github.com/mhmmerle/test-travis-ci"
        issueTrackerUrl = "https://github.com/mhmmerle/test-travis-ci/issues"
    })

}