plugins { id 'com.android.application'; id 'org.jetbrains.kotlin.android' }
android { namespace 'com.gamefactory.hexflowconnect'; compileSdk 34; defaultConfig { applicationId "com.gamefactory.hexflowconnect"; minSdk 21; targetSdk 34; versionCode 1; versionName "1.0" } }
dependencies { implementation 'androidx.core:core-ktx:1.12.0'; testImplementation 'junit:junit:4.13.2'; }
