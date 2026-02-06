plugins {
    alias(libs.plugins.android.application)

    id("com.gladed.androidgitversion")
    id("com.google.devtools.ksp")
}

android {
    // compileSdk { version = release(36) { minorApiLevel = 1 } }
    compileSdk = 36
    flavorDimensions += "main"
    namespace = "nl.mpcjanssen.simpletask"

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    packaging {
        resources {
            excludes +=
                setOf(
                    "META-INF/LICENSE.txt",
                    "META-INF/DEPENDENCIES",
                    "META-INF/LICENSE",
                    "META-INF/NOTICE.txt",
                    "LICENSE.txt",
                )
        }
    }

    defaultConfig {
        versionCode = 1010000
        versionName = "1.1.0"
        buildConfigField("String", "GIT_VERSION", "\"" + androidGitVersion.name() + "\"")

        // minSdk = 23
        minSdk = 29
        // targetSdk { version = release(36) { minorApiLevel = 1 } }
        targetSdk = 36

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
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            // manifestPlaceholders["providerBuildType"] = "release"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    // lint {
    //     disable += setOf("InvalidPackage", "MissingTranslation", "ResourceType")
    // }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.localbroadcastmanager)
    implementation(libs.androidx.preference)
    implementation(libs.androidx.recyclerview)
    implementation(libs.commonmark)
    implementation(libs.hirondelle.date4j)
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
