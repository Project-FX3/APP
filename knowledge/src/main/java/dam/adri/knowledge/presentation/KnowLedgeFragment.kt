package dam.adri.knowledge.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dam.adri.grandprix.presentation.grandPrixDescription.NextGrandPrixFragment
import dam.adri.knowledge.databinding.KnowledgeFragmentBinding
import dam.adri.knowledge.presentation.adapter.GpAdapter

@AndroidEntryPoint
class KnowledgeFragment : Fragment() {

    private var _binding: KnowledgeFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: KnowledgeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = KnowledgeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.allGps.observe(viewLifecycleOwner) { gps ->
            val adapter = GpAdapter(requireContext(), gps)
            binding.gridViewCircuits.adapter = adapter

            binding.gridViewCircuits.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                val selectedGp = adapter.getItem(position)


                 navigateToGpDetailFragment(selectedGp.id)
            }
        }

        viewModel.error.observe(viewLifecycleOwner){ errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
    private fun navigateToGpDetailFragment(gpId: Int) {
        val fragment = NextGrandPrixFragment.newInstance(gpId)
        parentFragmentManager.beginTransaction()
            .replace(dam.adri.core.styles.R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        fun newInstance() = KnowledgeFragment()
    }
}
