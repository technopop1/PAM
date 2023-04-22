package pl.edu.uwr.pum.gardenway.plants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import pl.edu.uwr.pum.gardenway.PlantEntity
import pl.edu.uwr.pum.gardenway.R
import java.time.LocalDate

class ListOfPlantsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PlantAdapter

    private val taskViewModel : PlantViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list_of_plants, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = PlantAdapter(taskViewModel, PlantComparator())
        taskViewModel.getAllPlants.observe(viewLifecycleOwner) {
            tasks -> tasks?.let { adapter.submitList(it) }
        }

        setFabOnClick(view)

        recyclerView.adapter = adapter

        return view
    }

    private fun setFabOnClick(view: View) {
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            var num = taskViewModel.getAllPlants.value?.maxByOrNull { it.id }?.id
            num = if (num != null) num + 1 else 0

            val task = PlantEntity(
                num, "Plant #$num", LocalDate.now().minusDays(2).toString(), LocalDate.now().minusDays(2).toString(), 1,"unknown", "unknown", 0.0f,"<Opis>", false
            )

            taskViewModel.insert(task)
            taskViewModel.getAllPlants.observe(viewLifecycleOwner) { tasks ->
                tasks?.let { adapter.submitList(it) }
            }

            goToNewTask(view, task)
        }
    }

    private fun goToNewTask(view : View, task : PlantEntity){
        val bundle = bundleOf()
        bundle.putLong("ID_KEY", task.id)
        bundle.putString("TITLE_KEY", task.title)
        bundle.putString("PLANT_DATE_KEY", task.plantDate)
        bundle.putString("LAST_WATERING_DATE_KEY", task.lastWateringDate)
        bundle.putInt("WATERING_INTERVAL_KEY", task.wateringInterval)
        bundle.putString("SPECIES_KEY", task.species)
        bundle.putString("SECTOR_KEY", task.sector)
        bundle.putFloat("COST_KEY", task.cost)
        bundle.putString("DESCRIPTION_KEY", task.description)
        bundle.putBoolean("WATER_DEMAND_KEY", task.waterDemand)

        view.findNavController().navigate(R.id.action_fragmentListOfPlants_to_fragmentPlantDetail, bundle)
    }
}
