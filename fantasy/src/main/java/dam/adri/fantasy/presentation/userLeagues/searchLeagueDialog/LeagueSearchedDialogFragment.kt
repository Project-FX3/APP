package dam.adri.fantasy.presentation.userLeagues.searchLeagueDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.commit
import dam.adri.fantasy.R
import dam.adri.fantasy.databinding.DialogLeagueSearchedBinding
import dam.adri.domain.modelo.entities.League
import dam.adri.fantasy.presentation.userLeagues.searchLeagueDialog.selectDrivers.SelectDriversFragment

class LeagueSearchedDialogFragment : DialogFragment() {

    private var _binding: DialogLeagueSearchedBinding? = null
    private val binding get() = _binding!!

    private var league: League? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogLeagueSearchedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        league = arguments?.getParcelable(ARG_LEAGUE)
        league?.let {
            binding.textViewNombreLiga.text = it.name
            binding.textViewNumeroUsuarios.text = "0 Usuarios"
        }

        binding.buttonUnirseLiga.setOnClickListener {
            openSelectDriversFragment()
        }
    }

    private fun openSelectDriversFragment() {
        league?.let {
            val fragment = it.id?.let { it1 -> SelectDriversFragment.newInstance(it1) }
            dismiss()
            parentFragmentManager.commit {
                if (fragment != null) {
                    replace(dam.adri.core.styles.R.id.fragment_container, fragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_LEAGUE = "league"

        fun newInstance(league: League): LeagueSearchedDialogFragment {
            val fragment = LeagueSearchedDialogFragment()
            val args = Bundle().apply {
                putParcelable(ARG_LEAGUE, league)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
