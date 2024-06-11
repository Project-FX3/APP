package dam.adri.grandprix.presentation.grandPrixDescription.adapter

import dam.adri.grandprix.presentation.grandPrixDescription.SessionItem
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import dam.adri.core.styles.R

class SessionAdapter(private val context: Context, private val dataSource: List<SessionItem>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int = dataSource.size

    override fun getItem(position: Int): Any = dataSource[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: inflater.inflate(R.layout.session_item, parent, false)
        val titleTextView = view.findViewById<TextView>(R.id.session_title)
        val timeTextView = view.findViewById<TextView>(R.id.session_time)
        val dateTextView = view.findViewById<TextView>(R.id.session_date)
        val sessionItem = getItem(position) as SessionItem

        titleTextView.text = sessionItem.title
        timeTextView.text = sessionItem.time
        dateTextView.text = sessionItem.date

        return view
    }
}
