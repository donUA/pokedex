object Configs {
    private const val versionMajor = 1
    private const val versionMinor = 0
    private const val versionPatch = 0

    private fun generateVersionCode(): Int {
        return versionMajor * 10000 + versionMinor * 100 + versionPatch
    }

    private fun generateVersionName(): String {
        return "$versionMajor.$versionMinor.$versionPatch"
    }

    const val Id = "com.muthiani.pokedex"
    const val Id_DATA_API = "com.muthiani.api"
    const val Id_FEATURE_POKEMON = "com.muthiani.main"
    val VersionCode = generateVersionCode()
    val VersionName = generateVersionName()
    const val MinSdk = 24
    const val TargetSdk = 34
    const val CompileSdk = 34
    const val AndroidJunitRunner = "androidx.test.runner.AndroidJUnitRunner"

    object Release {
        const val BaseUrl = "https://pokeapi.co/api/v2/"
        const val DbName = "pokemon_db"
    }

    object Debug {
        const val BaseUrl = "https://pokeapi.co/api/v2/"
        const val DbName = "pokemon_db"
    }
}