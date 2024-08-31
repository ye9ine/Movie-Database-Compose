plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("com.google.dagger.hilt.android")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    kotlin("kapt")
}

apply(plugin = "kotlin-kapt")
apply(plugin = "dagger.hilt.android.plugin")

android {
    namespace = "com.yeyint.movie_collection_compose"
    compileSdk = 34
    viewBinding.isEnabled = true

    defaultConfig {
        applicationId = "com.yeyint.movie_collection_compose"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    kapt {
        arguments {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
        }
        release {
            isMinifyEnabled = false
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    flavorDimensions += "version"
    productFlavors {
        create("development"){
            dimension = "version"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-debug"
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
            buildConfigField("String", "API_TOKEN", "\"a489082bd475daf345706635253e9f35\"")
        }

        create("uat"){
            dimension = "version"
            versionNameSuffix = "-uat"
            applicationIdSuffix = ".uat"
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
            buildConfigField("String", "API_TOKEN", "\"a489082bd475daf345706635253e9f35\"")
        }

        create("production"){
            dimension = "version"
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
            buildConfigField("String", "API_TOKEN", "\"a489082bd475daf345706635253e9f35\"")
        }
    }
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.material:material-icons-extended-android:1.6.8")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //splash api
    implementation("androidx.core:core-splashscreen:1.0.1")

    // For hilt Implementation
    implementation("com.google.dagger:hilt-android:2.48")
    annotationProcessor("com.google.dagger:hilt-android-compiler:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")
    kapt("androidx.hilt:hilt-compiler:1.1.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    implementation("androidx.compose.animation:animation:1.7.0-rc01")

    //LiveData and viewModel
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.activity:activity-ktx:1.8.2")

    // To use Kotlin coroutines with lifecycle-aware components
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    /*paging3*/
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")
    implementation("androidx.paging:paging-compose:3.3.0-alpha03")

    //room database
    implementation("androidx.room:room-ktx:2.4.3")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.4.3")

    /**Retrofit and okhttp lib**/
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.9.0"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")

    //compose system ui color
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")

    //network cache image library
    implementation("io.coil-kt:coil-compose:2.5.0")


    /*glide*/
    implementation("com.github.bumptech.glide:glide:4.14.2")
    kapt("com.github.bumptech.glide:compiler:4.14.2")
    implementation("com.github.bumptech.glide:compose:1.0.0-beta01")

    //pager
    implementation("com.google.accompanist:accompanist-pager:0.28.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.28.0")

    //navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    //rating bar
    implementation("com.github.a914-gowtham:compose-ratingbar:1.3.4")
    implementation("androidx.compose.runtime:runtime-livedata:1.4.0")
    implementation("io.github.fornewid:photo-compose:1.0.1")

    /*youtube player*/
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:11.0.1")

    /*implementation("androidx.compose.ui:ui:1.7.0-SNAPSHOT")
    implementation("androidx.compose.animation:animation-core:1.7.0-SNAPSHOT")
    implementation("androidx.compose.animation:animation:1.7.0-SNAPSHOT")*/
}