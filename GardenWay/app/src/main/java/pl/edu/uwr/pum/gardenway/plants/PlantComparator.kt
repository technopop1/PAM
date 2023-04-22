package pl.edu.uwr.pum.gardenway.plants

import androidx.recyclerview.widget.DiffUtil
import pl.edu.uwr.pum.gardenway.PlantEntity

class PlantComparator : DiffUtil.ItemCallback<PlantEntity>() {
    override fun areItemsTheSame(oldItem: PlantEntity, newItem: PlantEntity): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: PlantEntity, newItem: PlantEntity): Boolean {
        return oldItem.id == newItem.id
    }
}