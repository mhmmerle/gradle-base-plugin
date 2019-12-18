import com.jfrog.bintray.gradle.BintrayExtension
import groovy.lang.Closure

description = "Plugin implementation"

plugins {
    `java-gradle-plugin`
    //id("email.haemmerle.baseplugin").version("1.0.0")
            id("org.jetbrains.kotlin.jvm").version("1.3.61")
            id("maven-publish")
            id("com.jfrog.bintray").version("1.8.4")
            id("com.palantir.git-version").version("0.12.2")
}

val gitVersionClosure = extensions.extraProperties.get("gitVersion")
        if (gitVersionClosure is Closure<*>) {
            version = gitVersionClosure.call(mapOf("prefix" to "v-"))
        }

publishing { 
    publications {
        create<MavenPublication>("maven") {
            from(components.findByName("java"))
        }
    }
}

bintray {
    pkg.vcsUrl = "https://github.com/mhmmerle/${name}.git"
    pkg.websiteUrl = "https://github.com/mhmmerle/${name}"
    pkg.issueTrackerUrl = "https://github.com/mhmmerle/${name}/issues"

    user = System.getenv("BINTRAY_USER")
    key = System.getenv("BINTRAY_KEY")
    setPublications("maven")
    publish = true
    pkg.repo = "snapshots"
    pkg.name = name
    pkg.setLicenses("MIT")
}

gradlePlugin {
    plugins {
        create("basePlugin") {
            id = "email.haemmerle.baseplugin"
            implementationClass = "email.haemmerle.gradle.base.BasePlugin"
        }
    }
}

extensions.getByType<BintrayExtension>().apply {
    setPublications(publications.component1(), "basePluginPluginMarkerMaven")
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(gradleApi())
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.61")
    implementation("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4")
    implementation("com.palantir.gradle.gitversion:gradle-git-version:0.12.2")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}
