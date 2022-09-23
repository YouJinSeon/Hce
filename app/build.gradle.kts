plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id ("kotlin-android")
    id("dagger.hilt.android.plugin")
}

android {
    defaultConfig {
        applicationId = "com.tmonet.hcetest"
        versionCode = Versions.versionCode
        versionName = Versions.versionName
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(project(":home"))
    implementation(project(":shared"))
    implementation(project(":data"))
    implementation(files("$projectDir/libs/HceService-1.0-release-220830.aar"))

    implementation(Dep.AndroidX.core)
    implementation(Dep.AndroidX.appcompat)
    implementation(Dep.AndroidX.material)
    implementation(Dep.AndroidX.constraintlayout)
    implementation(Dep.AndroidX.recyclerview)

    testImplementation(Dep.Test.junit)
    androidTestImplementation(Dep.Test.junitExt)
    androidTestImplementation(Dep.Test.espresso)

    implementation(Dep.Dagger.hiltAndroid)
    kapt(Dep.Dagger.hiltCompiler)
}