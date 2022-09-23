plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id ("kotlin-android")
    id("dagger.hilt.android.plugin")
}

android {
}

dependencies {
    implementation(project(":shared"))
    implementation(Dep.AndroidX.core)

    implementation(Dep.Kotlin.serialization)

    //hilt
    implementation(Dep.Dagger.hiltAndroid)
    kapt(Dep.Dagger.hiltCompiler)
}