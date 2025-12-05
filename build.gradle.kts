plugins {
    kotlin("jvm") version "2.2.21"
}

sourceSets {
    main {
        kotlin.srcDir("src")
        java.srcDir("src")
    }
}

tasks {
    wrapper {
        gradleVersion = "9.2.1"
    }
}
