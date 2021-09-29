import Const.yandexInformersAddress
import Const.yandexWeatherKey
import java.net.URL

object Const {
    /**ApiConst
     *
     * При добавлении нового ключа,
     * его обязательно нужно инициализировать(добавить в функцию initKeys())*/
    const val yandexWeatherKey = "X-Yandex-API-Key"
    val APIkeys: HashMap<String, String> by lazy { getAPIKeys() }

    /**WebConst*/
    private const val httpsProtocol = "https"
    private const val yandexWeatherHost = "api.weather.yandex.ru"
    private const val pathToInformers = "v2/informers"
    const val yandexInformersAddress = "${httpsProtocol}://${yandexWeatherHost}/${pathToInformers}"
}

fun getWeatherULR(lat: Double, lon: Double): URL {
    val request = "?lat=${lat}&lon=${lon}"
    return URL("$yandexInformersAddress$request")
}

private fun initKeys(APIkeys: HashMap<String, String>) {
    APIkeys[yandexWeatherKey] = "4949ae6a-001d-421c-995f-4aca8186b4f0"
}

private fun getAPIKeys(): HashMap<String, String> {
    val keys = HashMap<String, String>()
    initKeys(keys)
    return keys
}
