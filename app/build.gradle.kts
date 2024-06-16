import org.jetbrains.kotlin.storage.CacheResetOnProcessCanceled.enabled

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("androidx.navigation.safeargs")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.entsh104.highking"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.entsh104.highking"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        resValue("string", "google_maps_key", "AIzaSyDsbvczf_2-MgmpGzxjdrMRZPcatQI4bPA")
        buildConfigField("String", "BASE_URL", "\"https://highking.cloud/api/\"")
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

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.material.v140)
    implementation("com.mikepenz:iconics-core:5.3.4")
    implementation("com.mikepenz:iconics-views:5.3.4")
    implementation(libs.phosphor.typeface)
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.annotation:annotation:1.2.0")
    implementation(libs.circleimageview)
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.28")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation("androidx.preference:preference-ktx:1.1.1")
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.androidx.room.runtime)
    ksp(libs.room.compiler)
    implementation("androidx.room:room-ktx:2.5.2")


    // Midtrans Sandbox
    implementation("com.midtrans:uikit:2.0.0-SANDBOX")
    // Midtrans Production
    // implementation("com.midtrans:uikit:2.0.0")

    // Barcode
    implementation("com.google.zxing:core:3.4.1")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")

    //maps
    implementation("com.google.android.gms:play-services-maps:18.0.2")

    //paging 3
    implementation("androidx.paging:paging-runtime-ktx:3.3.0")

    // Library PhotoPicker
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.activity:activity-ktx:1.9.0")
    // Library Crop Image
    implementation("com.github.yalantis:ucrop:2.2.8")

    //skeleton
    implementation("com.facebook.shimmer:shimmer:0.5.0")
}
