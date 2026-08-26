plugins {
    id 'com.android.application' version '8.4.2' apply false
    id 'com.android.library' version '8.4.2' apply false
    id 'org.jetbrains.kotlin.android' version '1.9.20' apply false
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = '17'
        freeCompilerArgs = listOf("-Xjdk-release=17")
    }
}