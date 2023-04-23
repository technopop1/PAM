package pl.edu.uwr.pum.gardenway.calendar

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.edu.uwr.pum.gardenway.R
import pl.edu.uwr.pum.gardenway.calendar.CalendarAdapter.OnItemListener


class CalendarViewHolder internal constructor(itemView: View, onItemListener: OnItemListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {
    var dayOfMonth: TextView
    private var onItemListener: OnItemListener

    init {
        dayOfMonth = itemView.findViewById(R.id.cellDayText)

        this.onItemListener = onItemListener
        itemView.setOnClickListener(this)
    }

    override fun onClick(view: View) {

        val cellDayLayout : LinearLayout = itemView.findViewById(R.id.cellDayLayout)
        cellDayLayout.setBackgroundColor(R.color.blue)
        onItemListener.onItemClick(adapterPosition, dayOfMonth.text as String)
    }
}
