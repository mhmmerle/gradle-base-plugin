description = "Plugin implementation"

plugins {
    id("email.haemmerle.baseplugin")
    `java-gradle-plugin`
}

`email-haemmerle-base`{
    username = "mhmmerle"
}

gradlePlugin {
    plugins {
        create("basePlugin") {
            id = rootProject.name
            implementationClass = "email.haemmerle.gradle.base.BasePlugin"
        }
    }
}


extensions.getByType<BintrayExtension>().apply {
    setPublications(publications.component1(), "basePluginPluginMarkerMaven")
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
