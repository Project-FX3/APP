package dam.adri.grandprix.presentation.grandPrixDescription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dam.adri.core.styles.databinding.GrandprixLayoutBinding
import dam.adri.domain.modelo.entities.Gp
import dam.adri.grandprix.presentation.grandPrixDescription.adapter.SessionAdapter
import dam.adri.grandprix.presentation.sessionResults.SessionResultsFragment

@AndroidEntryPoint
class NextGrandPrixFragment : Fragment() {

    private var _binding: GrandprixLayoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NextGrandPrixViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GrandprixLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        val gpId = arguments?.getInt(ARG_GP_ID)?: 0
        viewModel.loadNextGrandPrix(gpId)
    }

    private fun observeViewModel() {
        viewModel.grandPrix.observe(viewLifecycleOwner) { grandPrix ->
            binding.gpTitle.text = grandPrix.racename
            binding.gpLocalityData.text = grandPrix.locality
            binding.gpCountryData.text = grandPrix.country
            binding.gpCircuitData.text = grandPrix.circuitname

            Glide.with(this)
                .load(grandPrix.url)
                .into(binding.gpCircuitImage)

            val sessions = setUpSessions(grandPrix)

            val adapter = SessionAdapter(requireContext(), sessions)
            binding.sessionListView.adapter = adapter

            binding.sessionListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                val session = sessions[position]
                val sessionType = session.title
                val gpId = grandPrix.id

                val fragment = SessionResultsFragment.newInstance(sessionType, gpId)
                parentFragmentManager.commit {
                    replace(dam.adri.core.styles.R.id.fragment_container, fragment)
                    addToBackStack(null)
                }

            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpSessions(grandPrix: Gp): List<SessionItem> {
        val sessions = mutableListOf<SessionItem>()

        sessions.add(SessionItem("Practice 1", grandPrix.firstpracticedate.toString(),grandPrix.firstpracticetime.toString()))

        if(grandPrix.thirdpracticedate != null){
            sessions.add(SessionItem("Practice 2", grandPrix.secondpracticedate.toString(), grandPrix.secondpracticetime.toString()))
            sessions.add(SessionItem("Practice 3", grandPrix.thirdpracticedate.toString(), grandPrix.thirdpracticetime.toString()))
            sessions.add(SessionItem("Qualifying", grandPrix.qualifyingdate.toString(), grandPrix.qualifyingtime.toString()))
        } else{
            sessions.add(SessionItem("Sprint Qualifying", grandPrix.qualifyingSprintdate.toString(), grandPrix.qualifyingSprinttime.toString()))
            sessions.add(SessionItem("Qualifying", grandPrix.qualifyingdate.toString(), grandPrix.qualifyingtime.toString()))
            sessions.add(SessionItem("Sprint", grandPrix.sprintdate.toString(), grandPrix.sprinttime.toString()))
        }
        sessions.add(SessionItem("Race", grandPrix.racedate.toString(), grandPrix.racetime.toString()))
        return sessions
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_GP_ID = "gpId"

        fun newInstance(gpId: Int): NextGrandPrixFragment {
            val fragment = NextGrandPrixFragment()
            val args = Bundle().apply {
                putInt(ARG_GP_ID, gpId)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
