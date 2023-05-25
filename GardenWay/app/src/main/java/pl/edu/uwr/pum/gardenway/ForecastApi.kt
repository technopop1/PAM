package pl.edu.uwr.pum.gardenway

import pl.edu.uwr.pum.gardenway.forecast.dto.Forecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ForecastApi {
    @GET
    fun getCountries(@Url url: String): Call<Forecast>

}