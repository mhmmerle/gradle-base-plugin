import com.jfrog.bintray.gradle.BintrayExtension

plugins {
    id("org.jetbrains.kotlin.jvm").version("1.3.21")
    application
    `maven-publish`
    id("com.palantir.git-version").version("0.12.2")
    id("com.gradle.build-scan").version("2.4.2")
    id("com.jfrog.bintray").version( "1.8.4")
}

repositories {
    jcenter()
}

val gitVersion: groovy.lang.Closure<String> by extra
version = gitVersion(mapOf ("prefix" to "v-"))
group = "email.haemmerle.test"
description = "Project for testing the CI chain github -> travis -> bintray"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
    mainClassName = "email.haemmerle.test.AppKt"
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

    pkg (delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = "snapshots"
        name = project.name
        setLicenses("MIT")
        vcsUrl = "https://github.com/mhmmerle/test-travis-ci.git"
        websiteUrl = "https://github.com/mhmmerle/test-travis-ci"
        issueTrackerUrl = "https://github.com/mhmmerle/test-travis-ci/issues"
    })

}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}
