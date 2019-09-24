# Gradle Base Plugin

Gradle base plugin for kotlin projects and the CI chain **github -> travis -> bintray**.

## Apply the Plugin

```
buildscript {
    repositories {
        maven { url = uri("https://dl.bintray.com/mhmmerle/snapshots") }
    }
    dependencies {
        classpath("email.haemmerle.gradle:base-plugin:cb0ab8a")
    }
}

apply<BasePlugin>()
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
 