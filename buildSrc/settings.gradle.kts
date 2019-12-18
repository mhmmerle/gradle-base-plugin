pluginManagement{
    repositories{
        gradlePluginPortal()
        maven(url = "https://dl.bintray.com/mhmmerle/snapshots")
    }
}

plugins {
    id("com.gradle.enterprise")
  }
  
  gradleEnterprise {
      buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
      }
  }

rootProject.name = "base-plugin"