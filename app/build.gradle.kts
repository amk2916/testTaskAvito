import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    //alias(libs.plugins.kapt)
    alias(libs.plugins.ksp)

}

android {
    namespace = "com.example.testtaskavito"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.testtaskavito"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val  properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        resValue("string", "api_key",properties.getProperty("api_key", ""))
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.okHttp)
    implementation(libs.interceptor)
    implementation(libs.pagging)
    implementation(libs.picasso)
    implementation(libs.dagger)
    ksp(libs.daggerCompiler)
    implementation(libs.viewModel)
    implementation(libs.viewModelRKTX)
    implementation(libs.coroutinesCore)
    implementation(libs.coroutinesAndroid)
  //  annotationProcessor(libs.daggerCompiler)



    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}