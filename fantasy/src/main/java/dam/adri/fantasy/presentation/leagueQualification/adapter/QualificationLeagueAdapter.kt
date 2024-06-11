package dam.adri.fantasy.presentation.leagueQualification.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import dam.adri.domain.modelo.entities.UserLeague
import dam.adri.fantasy.R

class QualificationLeagueAdapter(
    private val context: Context,
    private var userLeagues: List<UserLeague>,
    private val onItemClickListener: (UserLeague) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int {
        return userLeagues.size
    }

    override fun getItem(position: Int): UserLeague {
        return userLeagues[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.qualification_item, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = convertView.tag as ViewHolder
        }

        val userLeague = getItem(position)
        viewHolder.textViewNombreUsuario.text = userLeague.userName
        viewHolder.textViewNumeroClasificacion.text = "#${position + 1}"
        viewHolder.textViewPuntosUsuario.text = "${userLeague.puntuation} pts"

        view.setOnClickListener {
            onItemClickListener(userLeague)
        }

        return view
    }

    fun updateUsuarios(newUsuarios: List<UserLeague>) {
        userLeagues = newUsuarios
        notifyDataSetChanged()
    }

    private class ViewHolder(view: View) {
        val textViewNombreUsuario: TextView = view.findViewById(R.id.textViewNombreUsuario)
        val textViewNumeroClasificacion: TextView = view.findViewById(R.id.textViewNumeroClasificacion)
        val textViewPuntosUsuario: TextView = view.findViewById(R.id.textViewPuntosUsuario)
    }
}
