package dam.adri.fantasy.presentation.userLeagues.searchLeagueDialog.selectDrivers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dam.adri.core.data.utils.viewBinding
import dam.adri.domain.modelo.entities.Driver
import dam.adri.fantasy.R
import dam.adri.fantasy.databinding.DriversSelectionFragmentBinding
import dam.adri.fantasy.presentation.qualificationLeague.QualificationLeagueFragment
import dam.adri.fantasy.presentation.userLeagues.UserLeaguesFragment

@AndroidEntryPoint
class SelectDriversFragment : Fragment() {

    private val viewModel: SelectDriversViewModel by viewModels()
    private val binding by viewBinding(DriversSelectionFragmentBinding::bind)
    private lateinit var adapter: DriverAdapter
    private val selectedDrivers = mutableListOf<Driver>()

    private var leagueId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.drivers_selection_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        leagueId = arguments?.getInt(ARG_LEAGUE_ID) ?: 0

        setUpUI()
        setupAdapter()
        observeViewModel()
    }

    private fun setupAdapter() {
        adapter = DriverAdapter(emptyList())
        binding.gridViewDrivers.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.drivers.observe(viewLifecycleOwner) { drivers ->
            adapter.updateDrivers(drivers)
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

        viewModel.userCreated.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Creado correctamente", Toast.LENGTH_SHORT).show()
            closeFragment()
        }
    }

    private fun setUpUI() {
        binding.gridViewDrivers.onItemClickListener =
            AdapterView.OnItemClickListener { _, view, position, _ ->
                val driver = adapter.getItem(position)
                if (selectedDrivers.contains(driver)) {
                    selectedDrivers.remove(driver)
                    view.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.transparent))
                } else {
                    if (selectedDrivers.size < 4) {
                        selectedDrivers.add(driver)
                        view.setBackgroundColor(ContextCompat.getColor(requireContext(), dam.adri.core.styles.R.color.beigeF1))
                    } else {
                        Toast.makeText(requireContext(), "No puedes seleccionar más de 4 pilotos", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        binding.buttonFixed.setOnClickListener {
            if (selectedDrivers.size < 4) {
                Toast.makeText(requireContext(), "Debes seleccionar al menos 4 pilotos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.createUserLeague(
                idLeague = leagueId,
                driverNumber1 = selectedDrivers[0].driverNumber,
                driverNumber2 = selectedDrivers[1].driverNumber,
                driverNumber3 = selectedDrivers[2].driverNumber,
                driverNumber4 = selectedDrivers[3].driverNumber
            )
        }
    }

    private fun openUserLeaguesFragment() {
        val fragment = UserLeaguesFragment.newInstance()
        parentFragmentManager.commit {
            replace(dam.adri.core.styles.R.id.fragment_container, fragment)
        }
    }

    private fun closeFragment() {
        openUserLeaguesFragment()
    }

    companion object {
        private const val ARG_LEAGUE_ID = "leagueId"

        fun newInstance(leagueId: Int): SelectDriversFragment {
            val fragment = SelectDriversFragment()
            val args = Bundle().apply {
                putInt(ARG_LEAGUE_ID, leagueId)
            }
            fragment.arguments = args
            return fragment
        }
    }

    private inner class DriverAdapter(private var drivers: List<Driver>) : BaseAdapter() {

        override fun getCount(): Int = drivers.size

        override fun getItem(position: Int): Driver = drivers[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view: View = convertView ?: LayoutInflater.from(parent.context).inflate(R.layout.driver_item, parent, false)
            val driver = getItem(position)

            val imageViewPiloto: ImageView = view.findViewById(R.id.imageViewPiloto)
            val textViewNombre: TextView = view.findViewById(R.id.textViewNombre)
            val textViewEscuderia: TextView = view.findViewById(R.id.textViewEscuderia)
            val textViewNumeroPiloto: TextView = view.findViewById(R.id.textViewNumeroPiloto)

            Glide.with(requireContext()).load(driver.image).into(imageViewPiloto)
            textViewNombre.text = driver.lastName
            textViewEscuderia.text = driver.teamName
            textViewNumeroPiloto.text = driver.driverNumber.toString()

            if (selectedDrivers.contains(driver)) {
                view.setBackgroundColor(ContextCompat.getColor(requireContext(), dam.adri.core.styles.R.color.beigeF1))
            } else {
                view.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.transparent))
            }

            return view
        }

        fun updateDrivers(newDrivers: List<Driver>) {
            this.drivers = newDrivers
            notifyDataSetChanged()
        }
    }
}
