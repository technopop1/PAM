package pl.edu.uwr.pum.gardenway.plants

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.edu.uwr.pum.gardenway.PlantEntity
import pl.edu.uwr.pum.gardenway.R
import java.time.LocalDate
import java.time.Period

class PlantAdapter(private val taskViewModel: PlantViewModel, plantComparator: PlantComparator) :
    ListAdapter<PlantEntity, PlantAdapter.TaskViewHolder>(plantComparator) {

    inner class TaskViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        private val title : TextView = itemView.findViewById(R.id.plant_title)
        private val sector : TextView = itemView.findViewById(R.id.plant_sector)
        private val waterDemand : ImageView = itemView.findViewById(R.id.plant_waterDemand)
        private val rvItem : ConstraintLayout = itemView.findViewById(R.id.rv_item)

        fun bind(task : PlantEntity){

            this.title.text = task.title
            this.sector.text = task.sector
            val isWaterDemand = (kotlin.math.abs(Period.between(LocalDate.now(), LocalDate.parse(task.lastWateringDate)).days) >= task.wateringInterval)
            if (isWaterDemand) {
                this.waterDemand.setBackgroundResource(R.drawable.danger)
            } else {
                this.waterDemand.setBackgroundResource(0)
            }

            rvItem.setOnClickListener{
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
                bundle.putBoolean("WATER_DEMAND_KEY", isWaterDemand)

                rvItem.findNavController().navigate(R.id.action_fragmentListOfPlants_to_fragmentPlantDetail, bundle)
            }
        }
    }

    init {
        setHasStableIds(true) //  zmiana na true oznacza że każdy element może być reprezentowany przez unikalny klucz
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemCount() = taskViewModel.getAllPlants.value?.toMutableList()?.size ?: mutableListOf<PlantEntity>().size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.plant_item, parent, false) )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val tasks = taskViewModel.getAllPlants.value?.get(position)!!
        holder.bind(tasks)
    }
}