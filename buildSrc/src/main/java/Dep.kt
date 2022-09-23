object Versions {
    const val compileSdk = 32
    const val buildTools = "30.0.3"

    const val minSdk = 23
    const val targetSdk = 32
    const val versionCode = 1
    const val versionName = "1.0.0"
}

object Dep {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.0.3"

    object AndroidX {
        const val core = "androidx.core:core-ktx:1.6.0"
        const val fragment = "androidx.fragment:fragment-ktx:1.3.5"
        const val appcompat = "androidx.appcompat:appcompat:1.4.0"
        const val material = "com.google.android.material:material:1.4.0"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.3"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.2.1"

        object Lifecycle {
            private const val lifecycleVersion = "2.3.1"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
            const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
        }

    }

    object Kotlin {
        const val version = "1.7.10"
        const val coroutineVersion = "1.5.1"

        const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2"
        const val serializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:$version"
    }

    object Test {
        const val junit = "junit:junit:4.13.2"
        const val junitExt = "androidx.test.ext:junit:1.1.3"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
        const val mockitoKotlin = "org.mockito.kotlin:mockito-kotlin:3.2.0"
    }

    object Dagger {
        private const val daggerVersion = "2.42"
        const val hiltAndroid = "com.google.dagger:hilt-android:$daggerVersion"
        const val hiltCompiler = "com.google.dagger:hilt-compiler:$daggerVersion"
        const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$daggerVersion"
    }

    const val inject = "javax.inject:javax.inject:1"

}
