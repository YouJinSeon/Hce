// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dep.androidGradlePlugin)
        classpath(Dep.Kotlin.gradlePlugin)
        classpath(Dep.Kotlin.serializationPlugin)
        classpath(Dep.Dagger.hiltGradlePlugin)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

subprojects {
    afterEvaluate {
        project.apply("$rootDir/gradle/common.gradle")
    }
}