package dam.adri.grandprix.presentation.sessionResults

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dam.adri.core.styles.databinding.ClassificationLayoutBinding
import dam.adri.grandprix.presentation.sessionResults.adapter.SessionResultsAdapter

@AndroidEntryPoint
class SessionResultsFragment : Fragment() {

    private var _binding: ClassificationLayoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SessionResultsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ClassificationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sessionType = arguments?.getString(ARG_SESSION_TYPE) ?: ""
        val gpId = arguments?.getInt(ARG_GP_ID) ?: 0

        binding.sessionTitle.text = sessionType
        observeViewModel()

        var session = ""
        when(sessionType) {
            "Practice 1" -> {
                session = "firstPractice"
            }
            "Practice 2"  -> {
                session = "secondPractice"
            }
            "Practice 3"  -> {
                session = "thirdPractice"
            }
            "Qualifying"  -> {
                session = "qualifying"
            }
            "Sprint Qualifying"  -> {
                session = "qualifyingSprint"
            }
            "Sprint"  -> {
                session = "sprint"
            }
            "Race"  -> {
                session = "race"
            }
        }

        viewModel.loadSessionResults(session, gpId)
    }

    private fun observeViewModel() {
        viewModel.results.observe(viewLifecycleOwner) { results ->
            val adapter = SessionResultsAdapter(requireContext(), results)
            binding.classificationListView.adapter = adapter
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
        private const val ARG_SESSION_TYPE = "sessionType"
        private const val ARG_GP_ID = "gpId"

        fun newInstance(sessionType: String, gpId: Int): SessionResultsFragment {
            val fragment = SessionResultsFragment()
            val args = Bundle().apply {
                putString(ARG_SESSION_TYPE, sessionType)
                putInt(ARG_GP_ID, gpId)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
