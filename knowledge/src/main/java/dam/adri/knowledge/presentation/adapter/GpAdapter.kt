package dam.adri.knowledge.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import dam.adri.domain.modelo.entities.Gp
import dam.adri.knowledge.R

class GpAdapter(private val context: Context, private var gps: List<Gp>) : BaseAdapter() {

    override fun getCount(): Int = gps.size

    override fun getItem(position: Int): Gp = gps[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.circuit_item, parent, false)
        val gp = getItem(position)

        val imageViewCircuit: ImageView = view.findViewById(R.id.imageViewCircuit)
        val textViewGpName: TextView = view.findViewById(R.id.textViewGpName)
        val textViewCountry: TextView = view.findViewById(R.id.textViewCountry)

        Glide.with(context).load(gp.url).into(imageViewCircuit)
        textViewGpName.text = gp.racename
        textViewCountry.text = gp.country

        return view
    }

    fun updateCircuits(newCircuits: List<Gp>) {
        this.gps = newCircuits
        notifyDataSetChanged()
    }
}
