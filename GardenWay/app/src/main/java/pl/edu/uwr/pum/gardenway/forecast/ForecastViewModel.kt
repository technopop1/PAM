package pl.edu.uwr.pum.gardenway.forecast

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.edu.uwr.pum.gardenway.ForecastApi
import pl.edu.uwr.pum.gardenway.RetrofitFactory
import pl.edu.uwr.pum.gardenway.forecast.dto.Forecast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastViewModel : ViewModel() {
    private var forecastData: MutableLiveData<Forecast> = MutableLiveData()

    fun getLiveData(): MutableLiveData<Forecast> {
        return forecastData
    }

    init {
        forecastData = MutableLiveData()
    }

    fun readDataFromJson(url: String) {
        val service: ForecastApi = RetrofitFactory.service
        val call = service.getCountries(url)
        call.enqueue(object : Callback<Forecast?> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<Forecast?>, response: Response<Forecast?>) {
                if (response.isSuccessful) {
                    forecastData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<Forecast?>, t: Throwable) {
                t.printStackTrace()
                forecastData.postValue(null)
            }
        })
    }
}