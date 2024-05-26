//import com.vanniktech.maven.publish.SonatypeHost

plugins {
    val kotlinVersion = libs.versions.kotlin.get()
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.serialization")
    alias(libs.plugins.anvil)
//    alias(libs.plugins.vanniktech.mavenPublish)
    `maven-publish`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.michaelbull.kotlinResult)
    implementation(libs.kotlin.reflect)
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")

    kaptTest(libs.dagger.compiler)
    testImplementation(kotlin("test"))
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.mockk)
    testImplementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    val javaVersion = libs.versions.jvmVersion.get().toInt()
    jvmToolchain(javaVersion)
}

//mavenPublishing {
//
//    group = libs.versions.groupId.get()
//    version = libs.versions.version.get()
//    val artifactId =  "randomizer-lib"
//
//
//    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
//    signAllPublications()
//    coordinates(group.toString(), artifactId, version.toString())
//
//    pom{
//        name.set("Randomizer")
//        description.set("A randomizer library for Kotlin")
//        inceptionYear.set("2024")
//        url.set("https://github.com/x12q/randomizer")
//        licenses {
//            license {
//                name.set("The Apache License, Version 2.0")
//                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
//                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
//            }
//        }
//        developers {
//            developer {
//                id.set("x12q")
//                name.set("The-Phong Pham")
//                url.set("x12q.com")
//            }
//        }
//        scm {
//            url.set("https://github.com/x12q/randomizer")
//            connection.set("scm:git:git://github.com/x12q/randomizer.git")
//            developerConnection.set("scm:git:ssh://git@github.com/x12q/randomizer.git")
//        }
//        issueManagement {
//            system.set("GitHub Issues")
//            url.set("https://github.com/x12q/randomizer/issues")
//        }
//    }
//}



//publishing {
//    /**
//     * This is for local publishing
//     */
//    publications {
//        create<MavenPublication>("maven") {
//            groupId = group.toString()
//            artifactId = id
//            version = version
//            from(components["java"])
//        }
//    }
//}
