apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.corePresentation))
    "implementation"(project(Modules.authDomain))
    "implementation"(Amazon.COGNITO_IDENTITY_PROVIDER)
}