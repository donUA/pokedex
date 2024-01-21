object Versions {
    const val CoreKtx = "1.7.0"
    const val nav_version = "2.7.6"
    const val retrofit_version = "2.9.0"
    const val paging_version = "3.2.1"
}

object SupportLib {
    const val CoreKtx = "androidx.core:core-ktx:${Versions.CoreKtx}"
    const val Appcompat = "androidx.appcompat:appcompat:1.4.1"
    const val Material = "com.google.android.material:material:1.5.0"
    const val CoroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1"
    const val CoroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1"
    const val LifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.1"
    const val ActivityKtx = "androidx.activity:activity-ktx:1.4.0"


    const val Splashscreen = "androidx.core:core-splashscreen:1.0.0-beta02"
    const val Timber = "com.jakewharton.timber:timber:5.0.1"
    const val Paging = "androidx.paging:paging-runtime-ktx:3.1.1"
}

object DependencyInjectionLib {
    const val koin = "io.insert-koin:koin-android:3.3.3"
}

object NavigationLib {
    const val FragmentNav =
        "androidx.navigation:navigation-fragment-ktx:${Versions.nav_version}"
    const val NavigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.nav_version}"
    const val DynamicFeaturesNav =
        "androidx.navigation:navigation-dynamic-features-fragment:${Versions.nav_version}"
}

object NetworkLib {
    const val Retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit_version}"
    const val RetrofitGson =
        "com.squareup.retrofit2:converter-gson:${Versions.retrofit_version}"
    const val OkhttpBom = "com.squareup.okhttp3:okhttp-bom:4.12.0"
    const val Okhttp = "com.squareup.okhttp3:okhttp"
    const val LoggingInterceptor = "com.squareup.okhttp3:logging-interceptor"
    const val Paging3 = "androidx.paging:paging-runtime:${Versions.paging_version}"
    const val Glide = "com.github.bumptech.glide:glide:4.16.0"
}

object PersistenceLib {
    const val RoomKtx = "androidx.room:room-ktx:2.4.2"
    const val RoomCompiler = "androidx.room:room-compiler:2.4.2"
    const val RoomPaging = "androidx.room:room-paging:2.4.2"
}

object TestingLib {
    const val Junit = "junit:junit:4.13.2"
    const val JunitAndroid = "androidx.test.ext:junit:1.1.5"
    const val Espresso = "androidx.test.espresso:espresso-core:3.5.1"
}
