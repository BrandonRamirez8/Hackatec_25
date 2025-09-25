plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.xalli"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.xalli"
        minSdk = 31
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    // implementation("com.google.ai.client.generativeai:generativeai:0.2.0") // Dependencia de Gemini
    implementation("com.squareup.okhttp3:okhttp:4.9.1") // Añadimos OkHttp para solicitudes HTTP
    implementation("com.google.code.gson:gson:2.8.8") // Añadimos Gson para parsear JSON
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("com.google.guava:guava:32.1.3-android")
    implementation(libs.generativeai)
    implementation(libs.play.services.location)
    implementation("com.google.android.gms:play-services-ads:23.0.0") // AdMob
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}