apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.corePresentation))
    "implementation"(project(Modules.authDomain))
    Accompanist.accompanistDependencies.forEach {
        "implementation"(it)
    }
}