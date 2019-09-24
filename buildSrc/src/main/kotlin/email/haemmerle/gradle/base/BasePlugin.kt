package email.haemmerle.gradle.base

import com.jfrog.bintray.gradle.BintrayExtension
import groovy.lang.Closure
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication

class BasePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.apply {
            apply( "org.jetbrains.kotlin.jvm")
            apply("maven-publish")
            apply("com.jfrog.bintray")
            apply("com.palantir.git-version")
            apply("com.gradle.build-scan")
        }
        val gitVersionClosure = target.extensions.extraProperties.get("gitVersion")
        if (gitVersionClosure is Closure<*>) {
            target.version = gitVersionClosure.call(mapOf("prefix" to "v-"))
        }
        target.repositories.apply {
            mavenLocal()
            mavenCentral()
            jcenter()
        }
        target.extensions.configure(PublishingExtension::class.java) { publishing ->
            publishing.publications {  publications ->
                publications.create("maven", MavenPublication::class.java) { publication  : MavenPublication ->
                    publication.from(target.components.findByName("java"))
                }
            }
        }
        target.extensions.configure(BintrayExtension::class.java) { bintray ->
            bintray.user = System.getenv("BINTRAY_USER")
            bintray.key = System.getenv("BINTRAY_KEY")

            bintray.setPublications("maven")
            bintray.publish = true

            bintray.pkg.repo = "snapshots"
            bintray.pkg.name = target.name
            bintray.pkg.setLicenses("MIT")
            bintray.pkg.vcsUrl = "https://github.com/mhmmerle/${target.name}.git"
            bintray.pkg.websiteUrl = "https://github.com/mhmmerle/${target.name}"
            bintray.pkg.issueTrackerUrl = "https://github.com/mhmmerle/${target.name}/issues"
        }
    }
}