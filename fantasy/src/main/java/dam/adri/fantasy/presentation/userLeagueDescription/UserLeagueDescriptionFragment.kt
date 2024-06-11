package dam.adri.fantasy.presentation.userLeagueDescription

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dam.adri.core.data.utils.viewBinding
import dam.adri.domain.modelo.entities.Driver
import dam.adri.fantasy.R
import dam.adri.fantasy.databinding.UserLeagueDescriptionFragmentBinding
import dam.adri.domain.modelo.entities.UserLeague

@AndroidEntryPoint
class UserLeagueDescriptionFragment : Fragment() {

    private val viewModel: UserLeagueDescriptionViewModel by viewModels()
    private val binding by viewBinding(UserLeagueDescriptionFragmentBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.user_league_description_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userLeague: UserLeague? = arguments?.getParcelable(ARG_USER_LEAGUE)
        if (userLeague != null) {
            viewModel.setUserLeague(userLeague)
            observeViewModel()
        }
    }

    private fun observeViewModel() {
        viewModel.userLeague.observe(viewLifecycleOwner) { userLeague ->
            binding.textViewNombreUsuario.text = userLeague.userName
            binding.textViewPuntuacionUsuario.text =
                getString(dam.adri.core.styles.R.string.puntuaci_n, userLeague.puntuation.toString())
        }

        viewModel.drivers.observe(viewLifecycleOwner) { drivers ->
            populateDrivers(drivers)
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun populateDrivers(drivers: List<Driver>) {
        binding.gridLayoutDrivers.removeAllViews()
        drivers.forEach { driver ->
            val itemView = layoutInflater.inflate(R.layout.driver_item, binding.gridLayoutDrivers, false)
            val imageViewPiloto: ImageView = itemView.findViewById(R.id.imageViewPiloto)
            val textViewNombre: TextView = itemView.findViewById(R.id.textViewNombre)
            val textViewEscuderia: TextView = itemView.findViewById(R.id.textViewEscuderia)
            val textViewNumeroPiloto: TextView = itemView.findViewById(R.id.textViewNumeroPiloto)

            Glide.with(this).load(driver.image).into(imageViewPiloto)
            textViewNombre.text = driver.lastName
            textViewEscuderia.text = driver.teamName
            textViewNumeroPiloto.text = driver.driverNumber.toString()

            binding.gridLayoutDrivers.addView(itemView)

            itemView.setBackgroundColor(Color.parseColor(driver.teamColour))
        }
    }

    companion object {
        private const val ARG_USER_LEAGUE = "userLeague"

        fun newInstance(userLeague: UserLeague): UserLeagueDescriptionFragment {
            val fragment = UserLeagueDescriptionFragment()
            val args = Bundle().apply {
                putParcelable(ARG_USER_LEAGUE, userLeague)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
