plugins {
    id("com.android.library")
    kotlin("android")
}

android {
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":data"))

    implementation(Dep.AndroidX.core)

    implementation(Dep.Kotlin.serialization)

    //coroutine
    implementation(Dep.Kotlin.coroutine)
    implementation(Dep.inject)
}