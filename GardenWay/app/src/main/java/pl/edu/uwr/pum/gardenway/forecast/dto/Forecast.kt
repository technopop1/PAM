package pl.edu.uwr.pum.gardenway.forecast.dto

data class Forecast(
    val weather: List<Weather>? = null,
    val main: Main,
    val wind: Wind,
    val clouds: Clouds,
    val name: String
)