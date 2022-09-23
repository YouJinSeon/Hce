plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlinx-serialization")
}

android {
}

dependencies {
    implementation(Dep.AndroidX.core)

    implementation(Dep.Kotlin.serialization)

    //coroutine
    implementation(Dep.Kotlin.coroutine)
    implementation(Dep.inject)
}