package extensions

import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler


fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

fun DependencyHandler.annotationProcessor(dependencyNotation: Any): Dependency? =
    add("annotationProcessor", dependencyNotation)

fun DependencyHandler.api(dependencyNotation: Any): Dependency? =
    add("api", dependencyNotation)

fun DependencyHandler.kapt(dependencyNotation: Any): Dependency? =
    add("kapt", dependencyNotation)

fun DependencyHandler.testImplementation(dependencyNotation: Any): Dependency? =
    add("testImplementation", dependencyNotation)

fun DependencyHandler.androidTestImplementation(dependencyNotation: Any): Dependency? =
    add("androidTestImplementation", dependencyNotation)

fun DependencyHandler.ksp(dependencyNotation: Any): Dependency? =
    add("ksp", dependencyNotation)




val DependencyHandler.DATA_API
    get() = implementation(project(mapOf("path" to ":data:api")))

val DependencyHandler.FEATURES_POKEMON
    get() = implementation(project(mapOf("path" to ":features:pokemon")))

fun DependencyHandler.addCommonDependencies() {
    implementation(SupportLib.CoreKtx)
    implementation(SupportLib.Appcompat)
    implementation(SupportLib.Material)
}

fun DependencyHandler.addNavigationDependencies() {
    implementation(NavigationLib.FragmentNav)
    implementation(NavigationLib.NavigationUi)
    implementation(NavigationLib.DynamicFeaturesNav)
}

fun DependencyHandler.addNetworkDependencies() {
    implementation(platform(NetworkLib.OkhttpBom))
    implementation(NetworkLib.Okhttp)
    implementation(NetworkLib.LoggingInterceptor)
    implementation(NetworkLib.Retrofit)
    implementation(NetworkLib.RetrofitGson)
}

fun DependencyHandler.addPersistenceDependencies() {
    implementation(PersistenceLib.RoomKtx)
    annotationProcessor(PersistenceLib.RoomCompiler)
    implementation(PersistenceLib.RoomPaging)
}

fun DependencyHandler.addAllModules() {
    DATA_API
    FEATURES_POKEMON
}

fun DependencyHandler.addTestDependencies() {
    implementation(TestingLib.Junit)
    implementation(TestingLib.JunitAndroid)
    implementation(TestingLib.Espresso)
}
