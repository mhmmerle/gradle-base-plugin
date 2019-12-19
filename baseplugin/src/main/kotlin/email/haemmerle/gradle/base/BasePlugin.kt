package email.haemmerle.gradle.base

import com.jfrog.bintray.gradle.BintrayExtension
import groovy.lang.Closure
import org.gradle.api.Plugin
import org.gradle.kotlin.dsl.*
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import kotlin.text.Charsets.UTF_8

class BasePlugin : Plugin<Project> {
    private lateinit var target: Project

    override fun apply(target: Project) {
        this.target = target

        target.extensions.add("email-haemmerle-base", BaseExtension(target))

        applyPlugins()
        setKotlinJvmOptionTo18()
        registerInitTravisTask()
        setVersionFromGit()
        applyDefaultMavenRepos()
        configureMavenPublications()
        configureBintrayPublication()
    }

    private fun setKotlinJvmOptionTo18() {
        target.tasks.withType(KotlinCompile::class.java) {
            it.kotlinOptions.jvmTarget = "1.8"
        }
    }

    private fun configureBintrayPublication() {
        target.extensions.getByType<PublishingExtension>().publications.configureEach { publication ->
            target.extensions.configure(BintrayExtension::class.java) { bintray ->
                bintray.setPublications( *listOf<String>(*bintray.publications, publication.name).distinct().toTypedArray() )
            }
        }
        target.extensions.configure(BintrayExtension::class.java) { bintray ->
            bintray.user = System.getenv("BINTRAY_USER")
            bintray.key = System.getenv("BINTRAY_KEY")
            bintray.publish = true
            bintray.pkg.repo = "snapshots"
            bintray.pkg.name = target.name
            bintray.pkg.setLicenses("MIT")
        }
    }

    private fun configureMavenPublications() {
        target.extensions.configure(PublishingExtension::class.java) { publishing ->
            publishing.publications { publications ->
                publications.create("maven", MavenPublication::class.java) { publication: MavenPublication ->
                    publication.from(target.components.findByName("java"))
                }
            }
        }
    }

    private fun applyDefaultMavenRepos() {
        target.repositories.apply {
            mavenLocal()
            mavenCentral()
            jcenter()
        }
    }

    private fun setVersionFromGit() {
        val gitVersionClosure = target.extensions.extraProperties.get("gitVersion")
        if (gitVersionClosure is Closure<*>) {
            target.version = gitVersionClosure.call(mapOf("prefix" to "v-"))
        }
    }

    private fun applyPlugins() {
        target.plugins.apply {
            apply("org.jetbrains.kotlin.jvm")
            apply("maven-publish")
            apply("com.jfrog.bintray")
            apply("com.palantir.git-version")
        }
    }

    private fun registerInitTravisTask() {
        target.tasks.register("initTravis") {
            it.doLast {
                val travisFileTemplate = this::class.java.getResource("/.travis.yml").readText(UTF_8)
                target.file(".travis.yml").writeText(travisFileTemplate, UTF_8)
            }
        }
    }
}

open class BaseExtension(val target: Project) {
    var username : String = ""
        set(value) {
            field = value
            target.extensions.configure(BintrayExtension::class.java) { bintray ->
                bintray.pkg.vcsUrl = "https://github.com/${value}/${target.name}.git"
                bintray.pkg.websiteUrl = "https://github.com/${value}/${target.name}"
                bintray.pkg.issueTrackerUrl = "https://github.com/${value}/${target.name}/issues"
            }
        }
}