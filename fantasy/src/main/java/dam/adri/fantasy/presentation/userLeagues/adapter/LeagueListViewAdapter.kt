package dam.adri.fantasy.presentation.userLeagues.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import dam.adri.fantasy.R
import dam.adri.domain.modelo.entities.League

class LeagueListViewAdapter(
    private val context: Context,
    private var leagues: List<League>,
    private val onItemClickListener: (League) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int {
        return leagues.size
    }

    override fun getItem(position: Int): League {
        return leagues[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_league, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = convertView.tag as ViewHolder
        }

        val league = getItem(position)
        viewHolder.textViewLeagueName.text = league.name
        viewHolder.codeAccess.text = context.getString(dam.adri.core.styles.R.string.code_access, league.accesscode)
        view.setOnClickListener {
            onItemClickListener(league)
        }

        return view
    }

    fun updateLeagues(newLeagues: List<League>) {
        leagues = newLeagues
        notifyDataSetChanged()
    }

    private class ViewHolder(view: View) {
        val textViewLeagueName: TextView = view.findViewById(R.id.textViewLeagueName)
        val codeAccess: TextView = view.findViewById(R.id.codeAccess)
    }
}
