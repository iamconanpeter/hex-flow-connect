# Build configuration for Hex Flow Connect
plugins {
  id('com.android.application')
}
externalLibs {
  minSdk 26
  compileSdk 35
}

dependencies {
  implementation("androidx.compose:compose-bom:2024.06.00")
  implementation("androidx.compose.ui:ui")
  implementation("androidx.compose.material3:material3")
  implementation("androidx.activity:activity-compose:1.9.0")
  testImplementation("junit:junit:4.13.2")
  testImplementation("androidx.compose.ui:ui-test-junit4:1.6.10")
}

class Main {}
