package dam.adri.grandprix.presentation.sessionResults.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import dam.adri.core.styles.R
import dam.adri.domain.modelo.entities.Driver

class SessionResultsAdapter(
    private val context: Context,
    private val results: List<Driver>
) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int = results.size

    override fun getItem(position: Int): Driver = results[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: inflater.inflate(R.layout.position_item, parent, false)
        val driver = getItem(position)

        val positionText: TextView = view.findViewById(R.id.position_text)
        val numberText: TextView = view.findViewById(R.id.number_text)
        val driverNameText: TextView = view.findViewById(R.id.driver_name_text)

        positionText.text = (position + 1).toString()
        numberText.text = driver.driverNumber.toString()
        driverNameText.text = driver.nameAcronym?: context.getString(dam.adri.core.styles.R.string.new_driver)
        if (driver.teamColour != null) {
            numberText.setTextColor(Color.parseColor(driver.teamColour))
        } else{
            numberText.setTextColor(Color.BLACK)
        }
        return view
    }
}
