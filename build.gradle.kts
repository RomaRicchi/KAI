plugins {
    alias(libs.plugins.android.application) apply false
    id("org.sonarqube") version "6.2.0.5505"
}

sonar {
    properties {
        property("sonar.projectKey", "labsegroma_KAIZEN")
        property("sonar.organization", "labsegroma")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}
