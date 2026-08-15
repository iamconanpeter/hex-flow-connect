plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.hexflowconnect.game"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hexflowconnect.game"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.compose:compose-bom:2024.06.00")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.3")
    implementation("androidx.preference:preference-ktx:1.2.1")
    implementation("com.google.android.gms:play-services-games:24.0.0")
    implementation("com.google.android.play:core:2.2.0")

    // Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.compose.ui:ui-test-junit4:1.6.10")
}