package dam.adri.fantasy.presentation.userLeagues

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dam.adri.core.data.utils.viewBinding
import dam.adri.domain.modelo.entities.League
import dam.adri.fantasy.R
import dam.adri.fantasy.databinding.FragmentUserLeaguesBinding
import dam.adri.fantasy.presentation.createLeague.CreateLeagueDialogFragment
import dam.adri.fantasy.presentation.leagueQualification.QualificationLeagueFragment
import dam.adri.fantasy.presentation.userLeagues.adapter.LeagueListViewAdapter
import dam.adri.fantasy.presentation.userLeagues.searchLeagueDialog.LeagueSearchedDialogFragment

@AndroidEntryPoint
class UserLeaguesFragment : Fragment() {

    private val viewModel: UserLeaguesViewModel by viewModels()
    private val binding by viewBinding(FragmentUserLeaguesBinding::bind)

    private lateinit var ligaAdapter: LeagueListViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_user_leagues, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupAdapter()
        observeViewModel()
    }

    private fun setupUI() {
        binding.buttonSearchLeague.setOnClickListener {
            val codeAccess = binding.editTextCodeAccess.text.toString()
           viewModel.searchLeague(codeAccess)

        }

        binding.buttonCreateLeague.setOnClickListener {
            showCreateLeagueDialog()
        }
    }

    private fun setupAdapter() {
        ligaAdapter = LeagueListViewAdapter(requireContext(), emptyList()) { league ->
            league.id?.let { openQualificationFragment(it) }
        }
        binding.listViewLeagues.adapter = ligaAdapter
    }

    private fun openQualificationFragment(leagueId: Int) {
        val fragment = QualificationLeagueFragment.newInstance(leagueId)
        parentFragmentManager.commit {
            replace(dam.adri.core.styles.R.id.fragment_container, fragment)
        }
    }

    private fun showLeagueSearchedDialog(league: League) {
        val dialogFragment = LeagueSearchedDialogFragment.newInstance(league)
        dialogFragment.show(parentFragmentManager, "LeagueSearchedDialogFragment")
    }

    private fun showCreateLeagueDialog() {
        val dialogFragment = CreateLeagueDialogFragment.newInstance()
        dialogFragment.show(parentFragmentManager, "CreateLeagueDialogFragment")
    }

    private fun observeViewModel() {
        viewModel.ligas.observe(viewLifecycleOwner) { leagues ->
            Log.d("UserLeaguesFragment", "Leagues updated: ${leagues.size} leagues")
            ligaAdapter.updateLeagues(leagues)
        }

        viewModel.ligaSearched.observe(viewLifecycleOwner) { league ->
            showLeagueSearchedDialog(league)
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun newInstance() = UserLeaguesFragment()
    }
}
