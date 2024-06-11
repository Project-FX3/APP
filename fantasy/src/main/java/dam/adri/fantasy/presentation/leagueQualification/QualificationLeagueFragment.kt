package dam.adri.fantasy.presentation.leagueQualification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dam.adri.core.data.utils.viewBinding
import dam.adri.domain.modelo.entities.UserLeague
import dam.adri.fantasy.R
import dam.adri.fantasy.databinding.QualificationBinding
import dam.adri.fantasy.presentation.leagueQualification.adapter.QualificationLeagueAdapter
import dam.adri.fantasy.presentation.userLeagueDescription.UserLeagueDescriptionFragment
import dam.adri.fantasy.presentation.userLeagues.UserLeaguesFragment

@AndroidEntryPoint
class QualificationLeagueFragment : Fragment() {

    private val viewModel: QualificationLeagueViewModel by viewModels()
    private val binding by viewBinding(QualificationBinding::bind)

    private lateinit var adapter: QualificationLeagueAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.qualification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        observeViewModel()
        setUpUI()


        val leagueId = arguments?.getInt(ARG_LEAGUE_ID) ?: 0
        viewModel.loadQualification(leagueId)
    }

    private fun setupAdapter() {
        adapter = QualificationLeagueAdapter(requireContext(), emptyList()) { userleague ->
            openUserLeagueDescriptionFragment(userleague)
        }
        binding.listViewClasificacion.adapter = adapter
    }

    private fun openUserLeagueDescriptionFragment(userleague:UserLeague) {
        val fragment = UserLeagueDescriptionFragment.newInstance(userleague)
        parentFragmentManager.commit {
            replace(dam.adri.core.styles.R.id.fragment_container, fragment)
            addToBackStack(null)
        }
    }

    private fun openUserLeaguesFragment() {
        parentFragmentManager.commit {
            replace(dam.adri.core.styles.R.id.fragment_container, UserLeaguesFragment())
        }
    }

    private fun closeFragment(){
        openUserLeaguesFragment()
    }

    private fun setUpUI(){
        binding.buttonSalirLiga.setOnClickListener {
            val leagueId = arguments?.getInt(ARG_LEAGUE_ID) ?: 0
            viewModel.deleteLeague(leagueId)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            closeFragment()
        }
    }

    private fun observeViewModel() {
        viewModel.clasificacion.observe(viewLifecycleOwner) { usuarios ->
            adapter.updateUsuarios(usuarios)
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

        viewModel.deleted.observe(viewLifecycleOwner){
            closeFragment()
        }
    }

    companion object {
        private const val ARG_LEAGUE_ID = "leagueId"

        fun newInstance(leagueId: Int): QualificationLeagueFragment {
            val fragment = QualificationLeagueFragment()
            val args = Bundle().apply {
                putInt(ARG_LEAGUE_ID, leagueId)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
