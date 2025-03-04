plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    id("com.gladed.androidgitversion")
    id("com.google.devtools.ksp")
}

android {
    compileSdk = 35
    flavorDimensions += "main"
    namespace = "nl.mpcjanssen.simpletask"

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    packaging {
        resources {
            excludes += setOf(
                "META-INF/LICENSE.txt",
                "META-INF/DEPENDENCIES",
                "META-INF/LICENSE",
                "META-INF/NOTICE.txt",
                "LICENSE.txt"
            )
        }
    }

    defaultConfig {
        versionCode = 3
        versionName = "1.0.2"
        buildConfigField("String", "GIT_VERSION", "\"" + androidGitVersion.name() + "\"")

        // minSdk = 23
        minSdk = 29
        // targetSdk = 35
        targetSdk = 34

        applicationId = "willemw12.simpletask"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    productFlavors {
        create("cloudless") {
            dimension = "main"
            applicationId = "willemw12.simpletask"
            manifestPlaceholders["providerFlavour"] = "cloudless"
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug (Simpletask fork)"
            // manifestPlaceholders["providerBuildType"] = "debug"
        }
        release {
            versionNameSuffix = " (Simpletask fork)"

            // signingConfig signingConfigs.release
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            // manifestPlaceholders["providerBuildType"] = "release"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    // lint {
    //     disable += setOf("InvalidPackage", "MissingTranslation", "ResourceType")
    // }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.preference)
    implementation(libs.androidx.recyclerview)
    implementation(libs.commonmark)
    implementation(libs.hirondelle.date4j)
    implementation(libs.kotlin.stdlib)
    implementation(libs.luaj.jse)
    implementation(libs.material)

    annotationProcessor(libs.androidx.room.compiler)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.runtime)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    // androidTestImplementation(libs.androidx.espresso.core)
}

allprojects {
    afterEvaluate {
        tasks.withType<JavaCompile> {
            options.compilerArgs.addAll(listOf("-Xlint:unchecked", "-Xlint:deprecation"))
        }
    }
}
