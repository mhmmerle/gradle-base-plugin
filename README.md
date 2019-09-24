# Gradle Base Plugin

Gradle base plugin for kotlin projects and the CI chain **github -> travis -> bintray**.

## Apply the Plugin

settings.gradle.kts
```
pluginManagement{
    repositories{
        gradlePluginPortal()
        maven(url = "https://dl.bintray.com/mhmmerle/snapshots")
    }
}
```

build.gradle.kts
```
plugins {
    id("email.haemmerle.baseplugin").version("0.0.4")
}
```

## Versioning

For releasing a version create and push tags in the following format:
 ```
 "v-" + <SemVer Version Number>
 ```
 
 ## Publishing
 
 Publishes to bintray using the following environment variables:
 
 * BINTRAY_USER
 * BINTRAY_KEY
 
 Make sure to register the api key in bintray and register the variables in travis.
 
 ## CI Travis Build
 
Create the travis file with the following command:

```
./gradlew initTravis
```