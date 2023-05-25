package pl.edu.uwr.pum.gardenway.forecast

import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import okhttp3.internal.format
//import com.google.android.gms.ads.AdRequest
//import com.google.android.gms.ads.AdView
//import com.google.android.gms.ads.MobileAds
//import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import org.json.JSONException
import org.json.JSONObject
import pl.edu.uwr.pum.gardenway.MainActivity
import pl.edu.uwr.pum.gardenway.PlantEntity
import pl.edu.uwr.pum.gardenway.R
import java.text.DecimalFormat

class ForecastFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    //    private lateinit var adapter: PlantAdapter
    private val forecastViewModel: ForecastViewModel by viewModels()

    var etCity: EditText? = null
    var etCountry: EditText? = null
    var tvResult: TextView? = null
    private val url = "https://api.openweathermap.org/data/2.5/"
    private val appid = "6e765f8d15f90bfd7e55039cf870045f"
    var df = DecimalFormat("#.##")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forecast, container, false)

        etCity = view.findViewById<EditText>(R.id.etCity)
        etCountry = view.findViewById<EditText>(R.id.etCountry)
        tvResult = view.findViewById<TextView>(R.id.tvResult)
        getWeatherDetails(view)
        return view
    }

    private fun getWeatherDetails(view: View?) {
        val getButton = view?.findViewById<Button>(R.id.btnGet)
        getButton?.setOnClickListener {
            val city = etCity?.text.toString().trim { it <= ' ' }
            val country = etCountry?.text.toString().trim { it <= ' ' }
//            forecastViewModel.readDataFromJson("?q=$city,$country&lang=pl&appid=$appid")
            forecastViewModel.readDataFromJson("?q=$city,$country&appid=$appid")
            forecastViewModel.getLiveData().observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    var output = ""
                    output += """Current weather of ${it.name}
Temp: ${format("%.2f", it.main.temp.toDouble().minus(273.15))} °C
Feels Like: ${format("%.2f", it.main.feels_like.toDouble().minus(273.15))} °C
Humidity: ${it.main.humidity}%
Description: ${it.weather?.get(0)?.description}
Wind Speed: ${it.wind.speed}m/s (meters per second)
Cloudiness: ${it.clouds.all}%
Pressure: ${it.main.pressure} hPa"""
                    tvResult?.text = output

                }
            })
        }
    }

}
