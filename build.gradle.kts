plugins {
    id("org.jetbrains.kotlin.jvm").version("1.3.21")
    application
    `maven-publish`
    id("com.palantir.git-version").version("0.12.2")
    id("com.gradle.build-scan").version("2.4.2")
}

repositories {
    jcenter()
}

val gitVersion: groovy.lang.Closure<String> by extra
version = gitVersion(mapOf ("prefix" to "v-"))
group = "email.haemmerle.test"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
    mainClassName = "email.haemmerle.test.AppKt"
}

publishing {
    repositories {
        maven (url = "https://api.bintray.com/content/mhmmerle/snapshots/")
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}
