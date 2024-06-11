package dam.adri.fantasy.presentation.createLeague

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import dam.adri.fantasy.R
import dam.adri.fantasy.databinding.DialogCreateLeagueBinding
import dagger.hilt.android.AndroidEntryPoint
import dam.adri.domain.modelo.entities.League
import dam.adri.fantasy.presentation.userLeagues.searchLeagueDialog.selectDrivers.SelectDriversFragment

@AndroidEntryPoint
class CreateLeagueDialogFragment : DialogFragment() {

    private var _binding: DialogCreateLeagueBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CreateLeagueViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogCreateLeagueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        setUpUI()
    }

    private fun setUpUI(){
        binding.buttonCreateLeague.setOnClickListener {
            val leagueName = binding.editTextLeagueName.text.toString()
            if (leagueName.isNotEmpty()) {
                viewModel.createLeague(leagueName)
            } else {
                Toast.makeText(requireContext(), "El nombre de la liga no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openSelectDriversFragment(league: League) {
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

    private fun observeViewModel() {
        viewModel.leagueCreated.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Liga creada correctamente", Toast.LENGTH_SHORT).show()
            openSelectDriversFragment(it)
        }
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): CreateLeagueDialogFragment {
            return CreateLeagueDialogFragment()
        }
    }
}
