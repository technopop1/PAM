package pl.edu.uwr.pum.gardenway.plants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import pl.edu.uwr.pum.gardenway.PlantEntity
import pl.edu.uwr.pum.gardenway.R
import java.time.LocalDate
import java.time.Period
import kotlin.math.abs

class PlantDetailFragment : Fragment() {
    private val taskViewModel: PlantViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_plant_detail, container, false)
        val title: EditText = view.findViewById(R.id.plant_title)
        val plantDate: TextView = view.findViewById(R.id.plant_date)
        val lastWatering: TextView = view.findViewById(R.id.last_watering_date)
        val interval: EditText = view.findViewById(R.id.watering_intervals)
        val species: EditText = view.findViewById(R.id.species)
        val sector: EditText = view.findViewById(R.id.sector)
        val cost: EditText = view.findViewById(R.id.cost)
        val description: EditText = view.findViewById(R.id.plantDescription)
        val dangerWater: ImageView = view.findViewById(R.id.plant_waterDemand)


        title.setText(arguments?.getString("TITLE_KEY"))
        plantDate.text = arguments?.getString("PLANT_DATE_KEY")
        lastWatering.text = arguments?.getString("LAST_WATERING_DATE_KEY")
        interval.setText(arguments?.getInt("WATERING_INTERVAL_KEY").toString())
        species.setText(arguments?.getString("SPECIES_KEY"))
        sector.setText(arguments?.getString("SECTOR_KEY"))
        cost.setText(arguments?.getFloat("COST_KEY").toString())
        description.setText(arguments?.getString("DESCRIPTION_KEY"))

        invervalTextListener(interval, lastWatering, dangerWater)
        backToListOfPlants(view, title, plantDate, description, lastWatering, interval, species, sector, cost)
        setWaterButtonListener(view, title, plantDate, description, lastWatering, interval, species, sector, cost, dangerWater)
        setDeleteButtonListener(view, title)

        return view
    }

    private fun setDeleteButtonListener(view: View, title: EditText) {
        val deleteLayout : ConstraintLayout = view.findViewById(R.id.deleteLayout)
        deleteLayout.setOnClickListener {
            taskViewModel.delete(arguments?.getLong("ID_KEY")!!)
            Navigation.findNavController(view)
                .navigate(PlantDetailFragmentDirections.actionFragmentPlantDetailToFragmentListOfPlants2())
        }
    }
    private fun setWaterButtonListener( view: View, title: EditText, plantDate: TextView, description: EditText,
                                        lastWatering : TextView, interval : EditText, species : EditText, sector : EditText, cost : EditText, dangerWater: ImageView ) {
        val waterLayout : ConstraintLayout = view.findViewById(R.id.waterLayout)
        val needWater = (kotlin.math.abs(Period.between(LocalDate.now(), LocalDate.parse(lastWatering.text)).days) >= interval.text.toString().toInt())

        // set image danger water
        setDangerImage(lastWatering, interval, dangerWater)

        waterLayout.setOnClickListener {
            lastWatering.text = LocalDate.now().toString()
            setDangerImage(lastWatering, interval, dangerWater)

            Thread {
                taskViewModel.update(
                    PlantEntity(
                        arguments?.getLong("ID_KEY")!!,
                        title.text.toString(),
                        plantDate.text.toString(),
                        LocalDate.now().toString(),
                        if(interval.text.isNotEmpty()) interval.text.toString().toInt() else 0,
                        species.text.toString(),
                        sector.text.toString(),
                        if(cost.text.isNotEmpty()) cost.text.toString().toFloat() else 0.0f,
                        description.text.toString(),
                        needWater
                    )
                )
            }.start()
        }
    }

    private fun setDangerImage( lastWatering: TextView, interval: EditText, dangerWater: ImageView ) {
        if (abs(
                Period.between(
                    LocalDate.now(),
                    LocalDate.parse(lastWatering.text)
                ).days
            ) >= interval.text.toString().toInt()
        ) {
            dangerWater.setBackgroundResource(R.drawable.danger)
        } else {
            dangerWater.setBackgroundResource(0)
        }
    }

    // back and update element from edit texts
    private fun backToListOfPlants( view: View, title: EditText, plantDate: TextView, description: EditText,
                                    lastWatering : TextView, interval : EditText, species : EditText, sector : EditText, cost : EditText) {
        view.findViewById<FloatingActionButton>(R.id.fab_to_fragmentListOfPlants)
            .setOnClickListener {
                Thread {
                    taskViewModel.update(
                        PlantEntity(
                            arguments?.getLong("ID_KEY")!!,
                            title.text.toString(),
                            plantDate.text.toString(),
                            lastWatering.text.toString(),
                            if(interval.text.isNotEmpty()) interval.text.toString().toInt() else 0,
                            species.text.toString(),
                            sector.text.toString(),
                            if(cost.text.isNotEmpty()) cost.text.toString().toFloat() else 0.0f,
                            description.text.toString(),
                            arguments?.getBoolean("DOES_WATERING_NEED_KEY")!!
                        )
                    )
                }.start()
                Navigation.findNavController(view)
                    .navigate(PlantDetailFragmentDirections.actionFragmentPlantDetailToFragmentListOfPlants2())
            }
    }

    private fun invervalTextListener(
        interval: EditText,
        lastWatering: TextView,
        dangerWater: ImageView
    ) {
        interval.addTextChangedListener {
            interval.doAfterTextChanged {
                if (it != null) {
                    if (it.isNotEmpty()) {
                        setDangerImage(lastWatering, interval, dangerWater)
                    }
                }
            }
            interval.doBeforeTextChanged { text, start, count, after ->  }
        }
    }
}
