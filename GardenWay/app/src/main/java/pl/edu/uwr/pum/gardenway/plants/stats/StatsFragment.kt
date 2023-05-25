package pl.edu.uwr.pum.gardenway.plants.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import pl.edu.uwr.pum.gardenway.R
import pl.edu.uwr.pum.gardenway.plants.PlantViewModel

class StatsFragment : Fragment() {

    private val taskViewModel: PlantViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stats, container, false)

        val expenses: TextView = view.findViewById(R.id.garden_expenses)
        val numberOfSpecies: TextView = view.findViewById(R.id.number_of_species)
        val numberOfPlantation: TextView = view.findViewById(R.id.number_of_plantation)

        Thread {
            expenses.text = taskViewModel.getTotalKnownCost().toString()
            numberOfSpecies.text = taskViewModel.getNumberOfSpecies().toString()
            numberOfPlantation.text = taskViewModel.getNumberOfPlants().toString()
        }.start()
        return view
    }
}
